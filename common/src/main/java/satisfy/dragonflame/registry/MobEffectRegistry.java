package satisfy.dragonflame.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.mobeffect.ConfusionEffect;
import satisfy.dragonflame.mobeffect.HardenedTitanArmorEffect;
import satisfy.dragonflame.mobeffect.ImprovedLeatherArmorEffect;
import satisfy.dragonflame.mobeffect.TitanArmorEffect;

import java.util.function.Supplier;

public class MobEffectRegistry {

    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Dragonflame.MOD_ID, Registries.MOB_EFFECT);

    public static RegistrySupplier<MobEffect> TITAN_ARMOR;
    public static RegistrySupplier<MobEffect> HARDENED_TITAN_ARMOR;
    public static RegistrySupplier<MobEffect> IMPROVED_LEATHER_ARMOR;
    public static RegistrySupplier<MobEffect> CONFUSION;

    public static void registerEffects() {
        TITAN_ARMOR = registerEffect("titan_armor", TitanArmorEffect::new);
        HARDENED_TITAN_ARMOR = registerEffect("hardened_titan_armor", HardenedTitanArmorEffect::new);
        IMPROVED_LEATHER_ARMOR = registerEffect("improved_leather_armor", ImprovedLeatherArmorEffect::new);
        CONFUSION = registerEffect("confusion", ConfusionEffect::new);

    }

    private static RegistrySupplier<MobEffect> registerEffect(String name, Supplier<MobEffect> effect){
        return MOB_EFFECTS.register(name, effect);
    }

    public static void init(){
        Dragonflame.LOGGER.debug("Registering MobEffects for " + Dragonflame.MOD_ID);
        MobEffectRegistry.registerEffects();
        MOB_EFFECTS.register();
    }
}
