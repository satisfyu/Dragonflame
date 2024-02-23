package satisfy.dragonflame.item.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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

public class FlamethrowerItem extends Item {
    public FlamethrowerItem(Properties properties) {
        super(properties.durability(100));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        triggerFlamethrower(context.getPlayer(), context.getLevel());
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
    }

    //TODO - still plays a 'hit' animation
    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, net.minecraft.world.InteractionHand hand) {
        triggerFlamethrower(player, world);
        return new InteractionResultHolder<>(InteractionResult.sidedSuccess(world.isClientSide()), player.getItemInHand(hand));
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
        for (int i = 0; i < 100; i++) {
            double dirX = -Math.sin(Math.toRadians(player.getYRot())) * Math.cos(Math.toRadians(player.getXRot()));
            double dirZ = Math.cos(Math.toRadians(player.getYRot())) * Math.cos(Math.toRadians(player.getXRot()));
            double dirY = -Math.sin(Math.toRadians(player.getXRot()));
            double velocity = 0.5;

            double px = player.getX() + dirX * i * 0.1;
            double py = player.getEyeY() + dirY * i * 0.1;
            double pz = player.getZ() + dirZ * i * 0.1;

            player.getCommandSenderWorld().addParticle(ParticleTypes.FLAME, px, py, pz, dirX * velocity, dirY * velocity, dirZ * velocity);

            if (i % 5 == 0) {
                player.getCommandSenderWorld().addParticle(ParticleTypes.LAVA, px, py, pz, 0, 0, 0);
            }
        }
    }
}