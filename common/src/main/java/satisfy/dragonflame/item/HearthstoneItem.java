package satisfy.dragonflame.item;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.client.particle.HearthstoneParticleEffect;
import satisfy.dragonflame.networking.DragonflameNetworking;
import satisfy.dragonflame.registry.ParticleRegistry;
import team.lodestar.lodestone.setup.LodestoneParticles;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;

import java.awt.*;
import java.nio.Buffer;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HearthstoneItem extends Item {
    public HearthstoneItem(Properties settings) {
        super(settings);
    }

    public static final int USAGE_TICKS = 80;
    private static final int COOLDOWN_TICKS = 6000;

    private HearthstoneParticleEffect particleEffect;

    private final Random random = new Random();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack, int i) {
        super.onUseTick(level, livingEntity, itemStack, i);

        if (!(livingEntity instanceof ServerPlayer player) || level.isClientSide()) {
            particleEffect.particleTick(level, (Player) livingEntity);
            return;
        }

        int duration = this.getUseDuration(itemStack) - i;
        if (duration < USAGE_TICKS) return;

        if (player.getCommandSenderWorld().dimension() != Level.OVERWORLD) {
            player.displayClientMessage(Component.translatable("item.dragonflame.hearthstone.only_overworld"), true);

        }
        BlockPos respawnPos = player.getRespawnPosition();

        if (respawnPos == null) {
            player.displayClientMessage(Component.translatable("item.dragonflame.hearthstone.no_bed"), true);
            return;
        }

        if (level.getBlockState(respawnPos).getBlock() instanceof BedBlock) {
            double destinationX = respawnPos.getX() + 0.5;
            double destinationY = respawnPos.getY() + 0.6D;
            double destinationZ = respawnPos.getZ() + 0.5;

            if (player.getVehicle() != null) {
                Entity riddenEntity = player.getVehicle();

                player.stopRiding();

                executor.schedule(() -> {
                    assert riddenEntity != null;
                    riddenEntity.teleportTo(destinationX, destinationY, destinationZ);
                }, 100, TimeUnit.MILLISECONDS);

                player.teleportTo(destinationX, destinationY, destinationZ);
            } else {
                player.teleportTo(destinationX, destinationY, destinationZ);
            }

            level.playSound(null, respawnPos, SoundEvents.PORTAL_TRAVEL, SoundSource.PLAYERS, 1.0F, 1.0F);

            DustColorTransitionOptions dustColorTransitionOptions = new DustColorTransitionOptions(
                    new Vector3f(0.0f, 1.0f, 1.0f),
                    new Vector3f(1.0f, 0.0f, 1.0f),
                    1.0f
            );

            ServerLevel serverLevel = (ServerLevel) level;

            serverLevel.players().forEach((sendToPlayer) -> {
                ServerPlayNetworking.send(sendToPlayer, DragonflameNetworking.HEARTHSTONE_TP_PACKET_ID, new FriendlyByteBuf(
                        PacketByteBufs.create().writeDouble(destinationX).writeDouble(destinationY).writeDouble(destinationZ)));
            });

            if (duration % 5 == 0) {
                for (int j = 0; j < 8; j++) {
                    level.addParticle(ParticleTypes.REVERSE_PORTAL,
                            destinationX + (random.nextDouble() - 0.5) * 1.5f,
                            destinationY + random.nextDouble(),
                            destinationZ + (random.nextDouble() - 0.5) * 1.5f,
                            0,
                            random.nextDouble(),
                            0);
                }
            }
        } else {
            player.displayClientMessage(Component.translatable("item.dragonflame.hearthstone.no_bed"), true);
        }

        player.stopUsingItem();
        if (!player.isCreative()) player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
    }

    public static void setCountdownStart(ItemStack stack) {
        stack.getOrCreateTag().putLong("CountdownStart", System.currentTimeMillis());
    }

    public static int getRemainingTime(ItemStack stack) {
        if (stack.hasTag()) {
            assert stack.getTag() != null;
            if (stack.getTag().contains("CountdownStart")) {
                long startTime = stack.getTag().getLong("CountdownStart");
                long elapsed = System.currentTimeMillis() - startTime;
                int remaining = (int) (300000 - elapsed) / 1000;
                return Math.max(remaining, 0);
            }
        }
        return 0;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        int remainingSeconds = getRemainingTime(stack);

        if (remainingSeconds > 0) {
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;

            Component countdownText;
            if (minutes > 0) {
                countdownText = Component.translatable("item.dragonflame.hearthstone.cooldown.active", minutes, seconds).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC);
            } else {
                countdownText = Component.translatable("item.dragonflame.hearthstone.cooldown.active.seconds", seconds).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC);
            }

            tooltip.add(countdownText);
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("lore.dragonflame.hearthstone").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        } else {
            Component noCooldownText = Component.translatable("item.dragonflame.hearthstone.cooldown.none").withStyle(ChatFormatting.GREEN);
            tooltip.add(noCooldownText);
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("lore.dragonflame.hearthstone").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemStack = playerIn.getItemInHand(handIn);
        particleEffect = new HearthstoneParticleEffect(playerIn);
        setCountdownStart(itemStack);
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.consume(itemStack);
    }
}
