package satisfy.dragonflame.block.arcanetorch;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.util.TextFormattingUtil;
import satisfy.dragonflame.util.TrackedPlayerState;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ArcaneTorchItem extends StandingAndWallBlockItem implements DyeableLeatherItem { // I hate these mappings

    protected static final Color defaultFirstColor = new Color(107, 59, 45);
    protected static final Color defaultSecondColor = new Color(79, 107, 45);
    public ArcaneTorchItem(Block block, Block block2, Properties settings) {
        super(block, block2, settings, Direction.DOWN);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(@NotNull BlockPlaceContext blockPlaceContext) {
        BlockState calculatedState = super.getPlacementState(blockPlaceContext);
        boolean shouldDrop = false;
        ItemStack stack = blockPlaceContext.getItemInHand();
        if (stack.getTag() != null) {
            shouldDrop = stack.getTag().getBoolean("should_drop");
        }
        if (Objects.nonNull(calculatedState)) {
            return calculatedState.setValue(AbstractArcaneTorchBlock.SHOULD_DROP, shouldDrop);
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        //super.appendHoverText(itemStack, level, list, tooltipFlag);
        if ((level == null || level.isClientSide) && Minecraft.getInstance().player.isCreative()) {
            if (itemStack.getTag() != null) {
                if (itemStack.getTag().getBoolean("should_drop")) {
                    list.add(TextFormattingUtil.withColor(Component.translatable("tooltip.dragonflame.does_drop"), new Color(0x7E9B4E).getRGB()));
                } else {
                    list.add(TextFormattingUtil.withColor(Component.translatable("tooltip.dragonflame.does_not_drop"), new Color(0x7E9B4E).getRGB()));
                }
            } else if (!tooltipFlag.isCreative()) {
                list.add(TextFormattingUtil.withColor(Component.translatable("tooltip.dragonflame.does_not_drop"), new Color(0x7E9B4E).getRGB()));
            }
            if (Screen.hasShiftDown()) {
                if (TrackedPlayerState.wasHoldingShiftWhenLastCached() != Screen.hasShiftDown()) {
                   // ClientPlayNetworking.send(DragonflameNetworking.SWITCH_TORCH_PACKET_ID, PacketByteBufs.create().writeItem(itemStack));
                    Dragonflame.LOGGER.info("Torch itemstack switch packet sent");
                }
                TrackedPlayerState.setHoldingShift(true);
            } else TrackedPlayerState.setHoldingShift(false);
        }
    }
    @Override
    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTagElement("display").putInt("FirstColor", color);
    }

    @Override
    public boolean hasCustomColor(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTagElement("display");
        return nbtCompound != null && nbtCompound.contains("FirstColor", 99);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTagElement("display");
        return nbtCompound != null && nbtCompound.contains("FirstColor", 99) ? nbtCompound.getInt("FirstColor") : -1;
    }
    public static int getFirstColor(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTagElement("display");
        return nbtCompound != null && nbtCompound.contains("FirstColor", 99) ? nbtCompound.getInt("FirstColor") : defaultFirstColor.getRGB();
    }
/*
    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

 */

    public static int getSecondColor(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTagElement("display");
        return nbtCompound != null && nbtCompound.contains("SecondColor", 99) ? nbtCompound.getInt("SecondColor") : defaultSecondColor.getRGB();
    }

    @Override
    public void clearColor(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTagElement("display");
        if (nbtCompound != null && nbtCompound.contains("FirstColor")) {
            nbtCompound.remove("FirstColor");
        }
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        CompoundTag nbt = new CompoundTag();
        CompoundTag nbtDisplay = new CompoundTag();
        nbtDisplay.putInt("FirstColor", defaultFirstColor.getRGB());
        nbtDisplay.putInt("SecondColor", defaultSecondColor.getRGB());
        nbt.putBoolean("should_drop", false);
        nbt.put("display", nbtDisplay);
        stack.setTag(nbt);
        return stack;
    }

    public static CompoundTag getDefaultTag() {
        CompoundTag nbt = new CompoundTag();
        CompoundTag nbtDisplay = new CompoundTag();
        nbtDisplay.putInt("FirstColor", defaultFirstColor.getRGB());
        nbtDisplay.putInt("SecondColor", defaultSecondColor.getRGB());
        nbt.putBoolean("should_drop", false);
        nbt.put("display", nbtDisplay);
        return nbt;
    }
}