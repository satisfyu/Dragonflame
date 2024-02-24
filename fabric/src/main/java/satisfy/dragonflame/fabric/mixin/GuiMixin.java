package satisfy.dragonflame.fabric.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import satisfy.dragonflame.item.RarityItem;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow
    private ItemStack lastToolHighlight;

    @Redirect(method = "renderSelectedItemName", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Rarity;color:Lnet/minecraft/ChatFormatting;", opcode = Opcodes.GETFIELD))
    private ChatFormatting injected(Rarity rarity) {
        return lastToolHighlight.getItem() instanceof RarityItem rarityItem ? rarityItem.getColor(lastToolHighlight) : rarity.color;
    }
}
