package satisfy.dragonflame.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.EnchantmentRegistry;

import java.util.List;

public class DraconicForDummiesItem extends EnchantedBookItem {
    public DraconicForDummiesItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!player.level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.DRACONIC_FOR_DUMMIES.get(), stack) == 0) {
                stack.enchant(EnchantmentRegistry.DRACONIC_FOR_DUMMIES.get(), 1);
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }
        return super.interactLivingEntity(stack, player, target, hand);
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.enchantment").withStyle(ChatFormatting.BLUE, ChatFormatting.OBFUSCATED));
        tooltip.add(Component.empty());
        Component combined = Component.literal("")
                .append(Component.translatable("tooltip.dragonflame.enchantment_for").withStyle(ChatFormatting.GRAY, ChatFormatting.OBFUSCATED))
                .append(Component.translatable("item.dragonflame.tooltip." + this.getDescriptionId()).withStyle(ChatFormatting.GOLD, ChatFormatting.OBFUSCATED));
        tooltip.add(combined);
        tooltip.add(Component.translatable("tooltip.dragonflame.enchantment." + this.getDescriptionId()).withStyle(ChatFormatting.GREEN, ChatFormatting.OBFUSCATED));
    }
}
