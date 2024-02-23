package satisfy.dragonflame.item;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EnchantedItem extends Item {

    public EnchantedItem(Properties settings) {
        super(settings);
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.enchantment").withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.empty());
        Component combined = Component.literal("")
                .append(Component.translatable("tooltip.dragonflame.enchantment_for").withStyle(ChatFormatting.GRAY))
                .append(Component.translatable("item.dragonflame.tooltip." + this.getDescriptionId()).withStyle(ChatFormatting.GOLD));
        tooltip.add(combined);
        tooltip.add(Component.translatable("tooltip.dragonflame.enchantment." + this.getDescriptionId()).withStyle(ChatFormatting.GREEN));
    }
}