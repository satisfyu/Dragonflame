package satisfy.dragonflame.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.util.DragonflameIdentifier;

public class HeartSmithingTemplateItem extends SmithingTemplateItem {
    private static final ResourceLocation EMPTY_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SLOT_HOE = new ResourceLocation("item/empty_slot_hoe");
    private static final ResourceLocation EMPTY_SLOT_AXE = new ResourceLocation("item/empty_slot_axe");
    private static final ResourceLocation EMPTY_SLOT_SWORD = new ResourceLocation("item/empty_slot_sword");
    private static final ResourceLocation EMPTY_SLOT_SHOVEL = new ResourceLocation("item/empty_slot_shovel");
    private static final ResourceLocation EMPTY_SLOT_PICKAXE = new ResourceLocation("item/empty_slot_pickaxe");
    private static final ResourceLocation EMPTY_SLOT_INGOT  = new ResourceLocation("item/empty_slot_ingot");

    public HeartSmithingTemplateItem(Component appliesTo, Component ingredients, Component upgradeDescription, Component baseSlotDescription, Component additionsSlotDescription, List<ResourceLocation> baseSlotEmptyIcons, List<ResourceLocation> additionalSlotEmptyIcons, Item.Properties properties) {
        super(appliesTo, ingredients, upgradeDescription, baseSlotDescription, additionsSlotDescription, baseSlotEmptyIcons, additionalSlotEmptyIcons);
    }

    public static HeartSmithingTemplateItem createHeartUpgradeTemplate(Rarity rarity) {
        List<ResourceLocation> baseSlotEmptyIcons = List.of(
                EMPTY_SLOT_HELMET,
                EMPTY_SLOT_CHESTPLATE,
                EMPTY_SLOT_LEGGINGS,
                EMPTY_SLOT_BOOTS,
                EMPTY_SLOT_HOE,
                EMPTY_SLOT_AXE,
                EMPTY_SLOT_SWORD,
                EMPTY_SLOT_SHOVEL,
                EMPTY_SLOT_PICKAXE
        );
        List<ResourceLocation> additionalSlotEmptyIcons = List.of(
                EMPTY_SLOT_INGOT
        );
        Item.Properties properties = new Item.Properties().rarity(rarity);
        return new HeartSmithingTemplateItem(
                getAppliesToComponent(),
                getIngredientsComponent(),
                getUpgradeDescriptionComponent(),
                getBaseSlotDescriptionComponent(),
                getAdditionsSlotDescriptionComponent(),
                baseSlotEmptyIcons,
                additionalSlotEmptyIcons,
                properties
        );
    }
    private static Component getAppliesToComponent() {
        return Component.translatable("template.dragonflame.applies_to").withStyle(ChatFormatting.BLUE);
    }

    private static Component getIngredientsComponent() {
        return Component.translatable("template.dragonflame.ingredient_for").withStyle(ChatFormatting.BLUE);
    }

    private static Component getUpgradeDescriptionComponent() {
        return Component.translatable("template.dragonflame.upgrade_description").withStyle(ChatFormatting.GRAY);
    }

    private static Component getBaseSlotDescriptionComponent() {
        return Component.translatable("item.minecraft.smithing_template.armor_trim.base_slot_description");
    }

    private static Component getAdditionsSlotDescriptionComponent() {
        return Component.translatable("item.minecraft.smithing_template.armor_trim.additions_slot_description");
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "item.dragonflame.heart_of_flame";
    }

}

