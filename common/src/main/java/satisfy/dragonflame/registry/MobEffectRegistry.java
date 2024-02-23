package satisfy.dragonflame.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.mobeffect.ConfusionEffect;
import satisfy.dragonflame.mobeffect.HardenedTitanArmorEffect;
import satisfy.dragonflame.mobeffect.ImprovedLeatherArmorEffect;
import satisfy.dragonflame.mobeffect.TitanArmorEffect;

public class MobEffectRegistry {
    public static MobEffect TITAN_ARMOR;
    public static MobEffect HARDENED_TITAN_ARMOR;
    public static MobEffect IMPROVED_LEATHER_ARMOR;
    public static MobEffect CONFUSION;

    public static MobEffect registerStatusEffect(String name, MobEffect effect) {
        return Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(Dragonflame.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        TITAN_ARMOR = registerStatusEffect("titan_armor", new TitanArmorEffect());
        HARDENED_TITAN_ARMOR = registerStatusEffect("hardened_titan_armor", new HardenedTitanArmorEffect());
        IMPROVED_LEATHER_ARMOR = registerStatusEffect("improved_leather_armor", new ImprovedLeatherArmorEffect());
        CONFUSION = registerStatusEffect("confusion", new ConfusionEffect());

    }

    public static void init(){
        Dragonflame.LOGGER.debug("Registering MobEffects for " + Dragonflame.MOD_ID);
        MobEffectRegistry.registerEffects();
    }
}
