package satisfy.dragonflame.item.armor;

import de.cristelknight.doapi.common.item.CustomHatItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.ArmorRegistry;
import satisfy.dragonflame.util.DragonflameIdentifier;

import java.util.List;


public class HardenedTitanHelmet extends CustomHatItem {
    public HardenedTitanHelmet(ArmorMaterial material, Properties settings) {
        super(material, Type.HELMET, settings);
    }

    @Override
    public ResourceLocation getTexture() {
        return new DragonflameIdentifier("textures/models/armor/hardened_titan.png");
    }

    @Override
    public Float getOffset() {
        return -1.5f;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        if(world != null && world.isClientSide()){
            ArmorRegistry.appendToolTipHardenedTitan(tooltip);
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("lore.dragonflame.hardened_titan").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
        }
    }
}
