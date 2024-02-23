package satisfy.dragonflame.item.tools;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.ObjectRegistry;
import satisfy.dragonflame.registry.ParticleRegistry;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.SimpleParticleEffect;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("all")
public class FlamethrowerItem extends Item {
    public FlamethrowerItem(Properties properties) {
        super(properties.durability(50));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        triggerFlamethrower(context.getPlayer(), context.getLevel());
        context.getItemInHand().hurtAndBreak(1, context.getPlayer(), (player) -> player.broadcastBreakEvent(context.getHand()));
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        triggerFlamethrower(player, world);
        player.getItemInHand(hand).hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
        return new InteractionResultHolder<>(InteractionResult.sidedSuccess(world.isClientSide()), player.getItemInHand(hand));
    }


    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }


    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity player, int i, boolean bl) {
        super.inventoryTick(itemStack, level, player, i, bl);
        Collection<Item> myMentalHealthConditions = new ArrayList<>();
        player.getHandSlots().forEach((ItemStack insanity) -> {
            myMentalHealthConditions.add(insanity.getItem());
        });

        if (myMentalHealthConditions.contains(ObjectRegistry.FLAMETHROWER)) {
            ((Player) player).swingTime = 0;
            ((Player) player).swinging = false;
            ((Player) player).stopUsingItem();
            ((Player) player).releaseUsingItem();
        }
    }

    @Nullable
    public HumanoidModel.ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging) {
            return HumanoidModel.ArmPose.EMPTY;
        }
        return null;
    }

    private void triggerFlamethrower(Player player, Level world) {
        if (player == null) {
            return;
        }

        if (!world.isClientSide()) {
            ServerLevel serverWorld = (ServerLevel) world;
            AABB area = player.getBoundingBox().inflate(5.0);
            serverWorld.getEntitiesOfClass(LivingEntity.class, area, entity -> entity != player).forEach(entity -> {
                entity.setSecondsOnFire(5);
                entity.hurt(serverWorld.damageSources().onFire(), 6.0f);
            });
            setFireInFrontOfPlayer(player, world);
            player.hurt(serverWorld.damageSources().generic(), 1.0f);
        } else {
            generateParticles(player);
            player.getCommandSenderWorld().playSound(player, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
        }
    }

    private void setFireInFrontOfPlayer(Player player, Level world) {
        Vec3 lookVec = player.getLookAngle();
        Vec3 aheadVec = player.position().add(lookVec); 

        BlockPos position = new BlockPos((int) aheadVec.x(), (int) aheadVec.y(), (int) aheadVec.z());

        BlockState blockState = world.getBlockState(position);

        if (blockState.isAir()) {
            world.setBlockAndUpdate(position, Blocks.FIRE.defaultBlockState());
        }
    }



    private void generateParticles(Player player) {
        double distance = 0.4;
        double step = distance / 100;

        for (int i = 0; i <= 100; i++) {
            double dirX = -Math.sin(Math.toRadians(player.getYRot())) * Math.cos(Math.toRadians(player.getXRot()));
            double dirZ = Math.cos(Math.toRadians(player.getYRot())) * Math.cos(Math.toRadians(player.getXRot()));
            double dirY = -Math.sin(Math.toRadians(player.getXRot()));
            double velocity = 0.5;

            double px = player.getX() + dirX * i * step;
            double py = player.getEyeY() + dirY * i * step;
            double pz = player.getZ() + dirZ * i * step;

            if (i <= 50) {
                //player.getCommandSenderWorld().addParticle(ParticleTypes.FLAME, px, py, pz, dirX * velocity, dirY * velocity, dirZ * velocity);
                if (i % 10 == 0) {
                    player.getCommandSenderWorld().addParticle(ParticleTypes.LAVA, px, py, pz, 0, 0, 0);
                }

                float jetLengthThreshold = 0.01f;
                double totalParticlesBeingSpawned = ((velocity - 0.03f) / jetLengthThreshold);
                totalParticlesBeingSpawned = Math.ceil(totalParticlesBeingSpawned);
                int particleSpawningIndex = 0;
                for (float currentJetLength = 0.03f; currentJetLength <= velocity;) {
                    currentJetLength += jetLengthThreshold;

                    // Good easings are QUINTIC_OUT, BACK_OUT, LINEAR, in order of preference
                    // Note2self: Don't use IN easings, looks dog poop
                    float scale = Easing.QUINTIC_OUT.ease(particleSpawningIndex, 0.03f, currentJetLength * 1.5f, (float) totalParticlesBeingSpawned);
                    WorldParticleBuilder.create(ParticleRegistry.FLAMETHROWER_JET)
                            .setSpinData(SpinParticleData.create((float) (player.level().random.nextGaussian() / 5f)).build())
                            .setScaleData(GenericParticleData.create(scale).setEasing(Easing.EXPO_OUT).build())
                            .setTransparencyData(GenericParticleData.create(1f, 0f).setEasing(Easing.QUAD_OUT).build())
                            .setColorData(
                                    ColorParticleData.create(new Color(0xFF5A1E), new Color(0xFF9100))
                                            .setEasing(Easing.CIRC_OUT)
                                            .build()
                            )
                            .disableNoClip()
                            .setDiscardFunction(SimpleParticleEffect.ParticleDiscardFunctionType.INVISIBLE)
                            .setLifetime(30)
                            .setGravity(0.7f)
                            .setMotion(dirX * currentJetLength * 2, dirY * currentJetLength * 2, dirZ * currentJetLength * 2)
                            .spawn(player.level(), px, py, pz);

                    particleSpawningIndex++;
                }
            }
        }
    }

}
