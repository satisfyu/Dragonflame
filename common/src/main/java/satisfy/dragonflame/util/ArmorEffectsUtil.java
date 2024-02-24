package satisfy.dragonflame.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import satisfy.dragonflame.registry.ArmorRegistry;
import satisfy.dragonflame.registry.ArmorRegistry.ArmorSet;
import satisfy.dragonflame.registry.MobEffectRegistry;

public class ArmorEffectsUtil {
    public static void applyArmorSetBonuses(Player player) {
        if (ArmorRegistry.hasFullArmorSet(player, ArmorSet.REINFORCED_LEATHER)) {
            applyReinforcedLeatherSetBonus(player);
        }
        if (ArmorRegistry.hasFullArmorSet(player, ArmorSet.DRAGON)) {
            applyDragonSetBonus(player);
        }
        if (ArmorRegistry.hasFullArmorSet(player, ArmorSet.TITAN)) {
            applyTitanSetBonus(player);
        }
        if (ArmorRegistry.hasFullArmorSet(player, ArmorSet.HARDENED_TITAN)) {
            applyHardenedTitanSetBonus(player);
        }
    }

    private static void applyReinforcedLeatherSetBonus(Player player) {
        player.addEffect(new MobEffectInstance(MobEffectRegistry.IMPROVED_LEATHER_ARMOR.get(), 1200, 0));
    }

    private static void applyDragonSetBonus(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0));
    }

    private static void applyTitanSetBonus(Player player) {
        player.addEffect(new MobEffectInstance(MobEffectRegistry.TITAN_ARMOR.get(), 1200, 0));
    }

    private static void applyHardenedTitanSetBonus(Player player) {
        player.addEffect(new MobEffectInstance(MobEffectRegistry.HARDENED_TITAN_ARMOR.get(), 1200, 0));
    }
}
