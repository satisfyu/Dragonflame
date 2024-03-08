package satisfy.dragonflame.item.armor;

import de.cristelknight.doapi.common.item.ICustomArmor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.ArmorRegistry;

import java.util.List;

public class HardenedTitanChestplate extends ArmorItem implements ICustomArmor {
    public HardenedTitanChestplate(ArmorMaterial material, Properties settings) {
        super(material, Type.CHESTPLATE, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if (null != world && world.isClientSide()) {
            ArmorRegistry.appendToolTipTitan(tooltip);
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("lore.dragonflame.hardened_titan").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        }
    }
}