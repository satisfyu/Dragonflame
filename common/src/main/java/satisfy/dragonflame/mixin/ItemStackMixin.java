package satisfy.dragonflame.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import satisfy.dragonflame.item.RarityItem;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Redirect(method = "getTooltipLines", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Rarity;color:Lnet/minecraft/ChatFormatting;", opcode = Opcodes.GETFIELD))
    public ChatFormatting getTooltipLines(Rarity rarity) {
        return getItem() instanceof RarityItem rarityItem ? rarityItem.getColor((ItemStack) (Object) this) : rarity.color;
    }

    @Redirect(method = "getDisplayName", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Rarity;color:Lnet/minecraft/ChatFormatting;", opcode = Opcodes.GETFIELD))
    private ChatFormatting injected(Rarity rarity) {
        return getItem() instanceof RarityItem rarityItem ? rarityItem.getColor((ItemStack) (Object) this) : rarity.color;
    }
}
