package satisfy.dragonflame.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

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

    private final Random random = new Random();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack itemStack, int remainingTicks) {
        super.onUseTick(level, entity, itemStack, remainingTicks);

        if (level.isClientSide) {
            final int duration = getUseDuration(itemStack);
            final float progress = (duration - remainingTicks) / (float) duration;
            final int maxParticles = Math.max(20, (int) (progress * 30));
            final double radius = 1.5;

            double time = System.currentTimeMillis() / 1000.0 * 2 * Math.PI;
            double xOffset = Math.cos(time) * radius;
            double zOffset = Math.sin(time) * radius;

            if (remainingTicks % 5 == 0) {
                for (int i = 0; i < maxParticles; i++) {
                    double angle = (2 * Math.PI / maxParticles) * i;
                    double particleXOffset = Math.cos(angle + time) * radius;
                    double particleZOffset = Math.sin(angle + time) * radius;

                    level.addParticle(ParticleTypes.PORTAL,
                            entity.getX() + particleXOffset,
                            entity.getY() + random.nextDouble() * 2.0,
                            entity.getZ() + particleZOffset,
                            0, 0, 0);
                    level.addParticle(ParticleTypes.REVERSE_PORTAL,
                            entity.getX() + particleXOffset,
                            entity.getY() + random.nextDouble() * 2.0,
                            entity.getZ() + particleZOffset,
                            0, 0, 0);
                }
            }
        }

        if (!(entity instanceof ServerPlayer player) || level.isClientSide()) {
            return;
        }

        int duration = this.getUseDuration(itemStack) - remainingTicks;
        if (duration < USAGE_TICKS) return;

        if (player.getCommandSenderWorld().dimension() != Level.OVERWORLD) {
            player.displayClientMessage(Component.translatable("item.dragonflame.hearthstone.only_overworld"), true);
            return;
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

            for (int i = 0; i < 8; i++) {
                double d0 = random.nextGaussian() * 0.02D;
                double d1 = random.nextGaussian() * 0.02D;
                double d2 = random.nextGaussian() * 0.02D;
                ((ServerLevel) level).sendParticles(ParticleTypes.REVERSE_PORTAL,
                        destinationX + random.nextFloat() * 2 - 1,
                        destinationY + random.nextFloat() * 2 - 1,
                        destinationZ + random.nextFloat() * 2 - 1,
                        15, d0, d1, d2, 0.3D);
                ((ServerLevel) level).sendParticles(ParticleTypes.WARPED_SPORE,
                        destinationX + random.nextFloat() * 2 - 1,
                        destinationY + random.nextFloat() * 2 - 1,
                        destinationZ + random.nextFloat() * 2 - 1,
                        15, d0, d1, d2, 0.3D);
                ((ServerLevel) level).sendParticles(ParticleTypes.PORTAL,
                        destinationX + random.nextFloat() * 2 - 1,
                        destinationY + random.nextFloat() * 2 - 1,
                        destinationZ + random.nextFloat() * 2 - 1,
                        15, d0, d1, d2, 0.3D);

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
        } else {
            Component noCooldownText = Component.translatable("item.dragonflame.hearthstone.cooldown.none").withStyle(ChatFormatting.GREEN);
            tooltip.add(noCooldownText);
        }
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.dragonflame.hearthstone").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack itemStack = playerIn.getItemInHand(handIn);
        setCountdownStart(itemStack);
        playerIn.startUsingItem(handIn);
        return InteractionResultHolder.consume(itemStack);
    }
}
