package satisfy.dragonflame.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.util.GeneralUtil;
import satisfy.dragonflame.util.MathUtil;

import java.util.List;

public class HardenedTitanPickaxeItem extends PickaxeItem {
    public HardenedTitanPickaxeItem(Tier tier, int i, float f, Properties properties) {
        super(tier, i, f, properties);
    }

    private static boolean validateBlock(Block block){
        return (
                GeneralUtil.equalsMultiple(block, Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN)
                || block.getExplosionResistance() <= 1200.0F
        );
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        int swipeRadius = 4;
        boolean isSurvival = !(player.isCreative() || player.isSpectator());

        if (player.isShiftKeyDown()) {
            int cooldownCountingBlocks = MathUtil.getFOVDegreeCollection(140, MathUtil.roundToMultiple(player.getViewYRot(1.0f), 45)).stream().mapToInt((rotatoryAngle) -> {
                        int blocksCountingTowardsCooldown = 0;
                        for (int blockAuraRadius = 1; blockAuraRadius < swipeRadius; blockAuraRadius++) {
                            double xToBreak = player.getX() + Math.sin(Math.toRadians(rotatoryAngle)) * blockAuraRadius - 1;
                            double zToBreak = player.getZ() + Math.cos(Math.toRadians(rotatoryAngle)) * blockAuraRadius;
                            for (int yOffset = 0; yOffset <= 1; yOffset++) {
                                BlockPos blockToDestroy = new BlockPos((int) xToBreak, (int) player.getY() + yOffset, (int) zToBreak);
                                if (player.isCreative() || validateBlock(level.getBlockState(blockToDestroy).getBlock())) {
                                    level.destroyBlock(blockToDestroy, isSurvival);
                                    blocksCountingTowardsCooldown++;
                                }
                            }
                        }
                        return blocksCountingTowardsCooldown;
            }).sum();

            if (isSurvival) player.getCooldowns().addCooldown(this, (int) Math.ceil((double) cooldownCountingBlocks / 2));
            if (cooldownCountingBlocks <= 0) return InteractionResultHolder.fail(player.getItemInHand(interactionHand));

            Level world = player.level();
            if (world instanceof ServerLevel serverWorld) { // I'd rather be safe than cause a server crash
                //serverWorld.sendParticles(BLOCK_AURA_SWIPE_PARTICLES[player.getRandom().nextInt(BLOCK_AURA_SWIPE_PARTICLES.length)], player.getX() + deltaX, player.getY(0.5), player.getZ() + deltaZ, 0, deltaX, 0.0, deltaZ, 0.0);
            }

            return InteractionResultHolder.success(player.getItemInHand(interactionHand));
        }
        return InteractionResultHolder.fail(player.getItemInHand(interactionHand));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.hardened_titan_pickaxe_on_use1").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.translatable("tooltip.dragonflame.hardened_titan_pickaxe_on_use2").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.dragonflame.hardened_titan").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}