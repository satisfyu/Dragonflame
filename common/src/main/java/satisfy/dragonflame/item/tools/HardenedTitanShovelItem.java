package satisfy.dragonflame.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HardenedTitanShovelItem extends ShovelItem {
    public HardenedTitanShovelItem(Tier toolMaterial, Properties properties) {
        super(toolMaterial,1.6F, -2.8F, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack itemStack = context.getItemInHand();
        Player player = context.getPlayer();

        if (!world.isClientSide && player != null) {
            if (player.isCrouching() && !player.getCooldowns().isOnCooldown(this)) {
                boolean changed = false;
                Iterable<BlockPos> nearbyPositions = BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1));

                for (BlockPos checkPos : nearbyPositions) {
                    BlockState blockState = world.getBlockState(checkPos);
                    if (blockState.is(Blocks.DIRT) || blockState.is(Blocks.COARSE_DIRT) || blockState.is(Blocks.PODZOL) || blockState.is(Blocks.ROOTED_DIRT) || blockState.is(Blocks.DIRT_PATH)) {
                        world.setBlock(checkPos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
                        changed = true;

                        ((ServerLevel)world).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                                checkPos.getX() + 0.5, checkPos.getY() + 1, checkPos.getZ() + 0.5,
                                20, 0.5, 0.5, 0.5, 0.2);
                        ((ServerLevel)world).sendParticles(ParticleTypes.COMPOSTER,
                                checkPos.getX() + 0.5, checkPos.getY() + 1, checkPos.getZ() + 0.5,
                                10, 0.5, 0.5, 0.5, 0.2);
                    }
                }

                if (changed) {
                    world.playSound(null, pos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    itemStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));

                    player.getCooldowns().addCooldown(this, 100);

                    return InteractionResult.sidedSuccess(world.isClientSide);
                }
            }
        }
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.hardened_titan_shovel_on_use1").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.translatable("tooltip.dragonflame.hardened_titan_shovel_on_use2").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.dragonflame.hardened_titan").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}
