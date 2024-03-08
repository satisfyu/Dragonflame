package satisfy.dragonflame.registry;

import de.cristelknight.doapi.client.render.feature.CustomArmorManager;
import de.cristelknight.doapi.client.render.feature.CustomArmorSet;
import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.client.model.*;
import satisfy.dragonflame.config.DragonflameConfig;
import satisfy.dragonflame.util.DragonflameIdentifier;

import java.util.List;

public interface ArmorRegistry {
    static void registerArmorModelLayers() {
        EntityModelLayerRegistry.register(ReinforcedLeatherHelmet.LAYER_LOCATION, ReinforcedLeatherHelmet::getTexturedModelData);
        EntityModelLayerRegistry.register(ReinforcedLeatherInner.LAYER_LOCATION, ReinforcedLeatherInner::createBodyLayer);
        EntityModelLayerRegistry.register(ReinforcedLeatherOuter.LAYER_LOCATION, ReinforcedLeatherOuter::createBodyLayer);

        EntityModelLayerRegistry.register(DragonHelmet.LAYER_LOCATION, DragonHelmet::getTexturedModelData);
        EntityModelLayerRegistry.register(DragonInner.LAYER_LOCATION, DragonInner::createBodyLayer);
        EntityModelLayerRegistry.register(DragonOuter.LAYER_LOCATION, DragonOuter::createBodyLayer);


        EntityModelLayerRegistry.register(TitanHelmet.LAYER_LOCATION, satisfy.dragonflame.client.model.TitanHelmet::getTexturedModelData);
        EntityModelLayerRegistry.register(TitanInner.LAYER_LOCATION, TitanInner::createBodyLayer);
        EntityModelLayerRegistry.register(TitanOuter.LAYER_LOCATION, TitanOuter::createBodyLayer);

        EntityModelLayerRegistry.register(HardenedTitanHelmet.LAYER_LOCATION, HardenedTitanHelmet::getTexturedModelData);
        EntityModelLayerRegistry.register(HardenedTitanInner.LAYER_LOCATION, HardenedTitanInner::createBodyLayer);
        EntityModelLayerRegistry.register(HardenedTitanOuter.LAYER_LOCATION, HardenedTitanOuter::createBodyLayer);

        EntityModelLayerRegistry.register(DragonHeadHelmet.LAYER_LOCATION, DragonHeadHelmet::getTexturedModelData);

    }

    static <T extends LivingEntity> void registerArmorModels(CustomArmorManager<T> armors, EntityModelSet modelLoader) {
        armors.addArmor(new CustomArmorSet<T>(ObjectRegistry.REINFORCED_LEATHER_HELMET.get(), ObjectRegistry.REINFORCED_LEATHER_CHESTPLATE.get(), ObjectRegistry.REINFORCED_LEATHER_LEGGINGS.get(), ObjectRegistry.REINFORCED_LEATHER_BOOTS.get())
                .setTexture(new DragonflameIdentifier("reinforced_leather"))
                .setOuterModel(new ReinforcedLeatherOuter<>(modelLoader.bakeLayer(ReinforcedLeatherOuter.LAYER_LOCATION)))
                .setInnerModel(new ReinforcedLeatherInner<>(modelLoader.bakeLayer(ReinforcedLeatherInner.LAYER_LOCATION)))
                .setHatModel(new ReinforcedLeatherHelmet<>(modelLoader.bakeLayer(ReinforcedLeatherHelmet.LAYER_LOCATION))));

        armors.addArmor(new CustomArmorSet<>(ObjectRegistry.DRAGON_HELMET.get(), ObjectRegistry.DRAGON_CHESTPLATE.get(), ObjectRegistry.DRAGON_LEGGINGS.get(), ObjectRegistry.DRAGON_BOOTS.get()))
                .setTexture(new DragonflameIdentifier("dragon"))
                .setOuterModel(new DragonOuter<>(modelLoader.bakeLayer(DragonOuter.LAYER_LOCATION)))
                .setInnerModel(new DragonInner<>(modelLoader.bakeLayer(DragonInner.LAYER_LOCATION)))
                .setHatModel(new DragonHelmet<>(modelLoader.bakeLayer(DragonHelmet.LAYER_LOCATION)));

        armors.addArmor(new CustomArmorSet<>(ObjectRegistry.TITAN_HELMET.get(), ObjectRegistry.TITAN_CHESTPLATE.get(), ObjectRegistry.TITAN_LEGGINGS.get(), ObjectRegistry.TITAN_BOOTS.get()))
                .setTexture(new DragonflameIdentifier("titan"))
                .setOuterModel(new TitanOuter<>(modelLoader.bakeLayer(TitanOuter.LAYER_LOCATION)))
                .setInnerModel(new TitanInner<>(modelLoader.bakeLayer(TitanInner.LAYER_LOCATION)))
                .setHatModel(new TitanHelmet<>(modelLoader.bakeLayer(TitanHelmet.LAYER_LOCATION)));

        armors.addArmor(new CustomArmorSet<>(ObjectRegistry.HARDENED_TITAN_HELMET.get(), ObjectRegistry.HARDENED_TITAN_CHESTPLATE.get(), ObjectRegistry.HARDENED_TITAN_LEGGINGS.get(), ObjectRegistry.HARDENED_TITAN_BOOTS.get()))
                .setTexture(new DragonflameIdentifier("hardened_titan"))
                .setOuterModel(new HardenedTitanOuter<>(modelLoader.bakeLayer(HardenedTitanOuter.LAYER_LOCATION)))
                .setInnerModel(new HardenedTitanInner<>(modelLoader.bakeLayer(HardenedTitanInner.LAYER_LOCATION)))
                .setHatModel(new HardenedTitanHelmet<>(modelLoader.bakeLayer(HardenedTitanHelmet.LAYER_LOCATION)));

        armors.addArmor(new CustomArmorSet<>(ObjectRegistry.DRAGON_HEAD_HELMET.get()))
                .setTexture(new DragonflameIdentifier("dragon_head_helmet"))
                .setHatModel(new DragonHeadHelmet<>(modelLoader.bakeLayer(DragonHeadHelmet.LAYER_LOCATION)));
    }

    static void appendArmorToolTip(@NotNull List<Component> tooltip, List<Item> itemsInOrder, String armorSetName, List<Object> fullSetBonuses) {
        Player player = Minecraft.getInstance().player;
        if (null == player) return;

        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        assert 4 == itemsInOrder.size();

        boolean isFullSet = helmet.getItem().getDefaultInstance() == itemsInOrder.get(0).getDefaultInstance() &&
                chestplate.getItem().getDefaultInstance() == itemsInOrder.get(1).getDefaultInstance() &&
                leggings.getItem().getDefaultInstance() == itemsInOrder.get(2).getDefaultInstance() &&
                boots.getItem().getDefaultInstance() == itemsInOrder.get(3).getDefaultInstance();

        ChatFormatting color = isFullSet ? ChatFormatting.GREEN : ChatFormatting.GRAY;

        tooltip.add(Component.nullToEmpty(""));
        if (Screen.hasAltDown() || !DragonflameConfig.DEFAULT.getConfig().simplifiedArmorSetTooltip()) {
            tooltip.add(Component.nullToEmpty((isFullSet ? ChatFormatting.GREEN : ChatFormatting.DARK_GREEN) + armorSetName));
            tooltip.add(Component.nullToEmpty(color + "- [" + itemsInOrder.get(0).getDescription().getString() + "]"));
            tooltip.add(Component.nullToEmpty(color + "- [" + itemsInOrder.get(1).getDescription().getString() + "]"));
            tooltip.add(Component.nullToEmpty(color + "- [" + itemsInOrder.get(2).getDescription().getString() + "]"));
            tooltip.add(Component.nullToEmpty(color + "- [" + itemsInOrder.get(3).getDescription().getString() + "]"));
        } else {
            tooltip.add(Component.nullToEmpty(ChatFormatting.GRAY + I18n.get("tooltip.dragonflame.alt_for_fullset_items")));
        }

        tooltip.add(Component.nullToEmpty(""));
        if (Screen.hasShiftDown() || !DragonflameConfig.DEFAULT.getConfig().simplifiedArmorSetBonusTooltip()) {
            tooltip.add(Component.nullToEmpty(color + I18n.get("tooltip.dragonflame.fullsetbonus")));
            fullSetBonuses.forEach(bonus -> {
                String bonusString;
                if (bonus instanceof String bonusAsString) bonusString = bonusAsString;
                else if (bonus instanceof Component bonusComponent) bonusString = bonusComponent.getString();
                else bonusString = "";
                tooltip.add(Component.nullToEmpty(color + bonusString));
            });
        } else {
            tooltip.add(Component.nullToEmpty(ChatFormatting.GRAY + I18n.get("tooltip.dragonflame.shift_for_fullset_bonus")));
        }
    }

    // TODO: Fix set bonus not working

    static void appendToolTipReinforcedLeather(@NotNull List<Component> tooltip) {
        appendArmorToolTip(tooltip,
                List.of(ObjectRegistry.REINFORCED_LEATHER_HELMET.get(), ObjectRegistry.REINFORCED_LEATHER_CHESTPLATE.get(), ObjectRegistry.REINFORCED_LEATHER_LEGGINGS.get(), ObjectRegistry.REINFORCED_LEATHER_BOOTS.get()),
                I18n.get("tooltip.dragonflame.reinforced_leather_armor"),
                List.of(I18n.get("tooltip.dragonflame.reinforced_leather_armor_bonus")));
    }

    @Environment(EnvType.CLIENT)
    static void appendToolTipDragon(@NotNull List<Component> tooltip) {
        appendArmorToolTip(tooltip,
                List.of(ObjectRegistry.DRAGON_HELMET.get(), ObjectRegistry.DRAGON_CHESTPLATE.get(), ObjectRegistry.DRAGON_LEGGINGS.get(), ObjectRegistry.DRAGON_BOOTS.get()),
                I18n.get("tooltip.dragonflame.dragon_armor"),
                List.of(I18n.get("tooltip.dragonflame.dragon_armor_bonus")));
    }

    @Environment(EnvType.CLIENT)
    static void appendToolTipTitan(@NotNull List<Component> tooltip) {
        appendArmorToolTip(tooltip,
                List.of(ObjectRegistry.TITAN_HELMET.get(), ObjectRegistry.TITAN_CHESTPLATE.get(), ObjectRegistry.TITAN_LEGGINGS.get(), ObjectRegistry.TITAN_BOOTS.get()),
                I18n.get("tooltip.dragonflame.titan_armor"),
                List.of(I18n.get("tooltip.dragonflame.titan_armor_bonus_1"),
                        I18n.get("tooltip.dragonflame.titan_armor_bonus_2"),
                        I18n.get("tooltip.dragonflame.titan_armor_bonus_3")));
    }


    static void appendToolTipHardenedTitan(@NotNull List<Component> tooltip) {
        appendArmorToolTip(tooltip,
                List.of(ObjectRegistry.HARDENED_TITAN_HELMET.get(), ObjectRegistry.HARDENED_TITAN_CHESTPLATE.get(), ObjectRegistry.HARDENED_TITAN_LEGGINGS.get(), ObjectRegistry.HARDENED_TITAN_BOOTS.get()),
                I18n.get("tooltip.dragonflame.hardened_titan_armor"),
                List.of(I18n.get("tooltip.dragonflame.hardened_titan_armor_bonus_1"),
                        I18n.get("tooltip.dragonflame.hardened_titan_armor_bonus_2"),
                        I18n.get("tooltip.dragonflame.hardened_titan_armor_bonus_3"),
                        Component.empty(), 
                        I18n.get("tooltip.dragonflame.hardened_titan_armor_bonus_4")));

    }

    static boolean hasFullArmorSet(Player player, ArmorSet set) {
        Item helmet = null, chestplate = null, leggings = null, boots = null;
        switch (set) {
            case TITAN:
                helmet = ObjectRegistry.TITAN_HELMET.get();
                chestplate = ObjectRegistry.TITAN_CHESTPLATE.get();
                leggings = ObjectRegistry.TITAN_LEGGINGS.get();
                boots = ObjectRegistry.TITAN_BOOTS.get();
                break;
            case HARDENED_TITAN:
                helmet = ObjectRegistry.HARDENED_TITAN_HELMET.get();
                chestplate = ObjectRegistry.HARDENED_TITAN_CHESTPLATE.get();
                leggings = ObjectRegistry.HARDENED_TITAN_LEGGINGS.get();
                boots = ObjectRegistry.HARDENED_TITAN_BOOTS.get();
                break;
            case DRAGON:
                helmet = ObjectRegistry.DRAGON_HELMET.get();
                chestplate = ObjectRegistry.DRAGON_CHESTPLATE.get();
                leggings = ObjectRegistry.DRAGON_LEGGINGS.get();
                boots = ObjectRegistry.DRAGON_BOOTS.get();
                break;
            case REINFORCED_LEATHER:
                helmet = ObjectRegistry.REINFORCED_LEATHER_HELMET.get();
                chestplate = ObjectRegistry.REINFORCED_LEATHER_CHESTPLATE.get();
                leggings = ObjectRegistry.REINFORCED_LEATHER_LEGGINGS.get();
                boots = ObjectRegistry.REINFORCED_LEATHER_BOOTS.get();
                break;
        }
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == helmet &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == chestplate &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == leggings &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == boots;
    }

    enum ArmorSet {
        TITAN,
        HARDENED_TITAN,
        DRAGON,
        REINFORCED_LEATHER
    }
}