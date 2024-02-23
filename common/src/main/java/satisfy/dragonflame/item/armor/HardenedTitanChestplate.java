package satisfy.dragonflame.item.armor;

import de.cristelknight.doapi.common.item.CustomArmorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.ArmorRegistry;
import satisfy.dragonflame.registry.ObjectRegistry;

import java.util.List;

public class HardenedTitanChestplate extends CustomArmorItem {
    public HardenedTitanChestplate(ArmorMaterial material, Properties settings) {
        super(material, Type.CHESTPLATE, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if(world != null && world.isClientSide()){
            ArmorRegistry.appendToolTipTitan(tooltip);
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("lore.dragonflame.hardened_titan").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        }
    }
}