package satisfy.dragonflame.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.enchantment.DraconicForDummiesEnchantment;
import satisfy.dragonflame.enchantment.DragonEyeEnchantment;
import satisfy.dragonflame.enchantment.DragonHeartEnchantment;

import java.util.function.Supplier;


public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Dragonflame.MOD_ID, Registries.ENCHANTMENT);

    public static final Supplier<Enchantment> DRAGON_HEART = register("dragon_heart", DragonHeartEnchantment::new);
    public static final Supplier<Enchantment> DRAGON_EYE = register("dragon_eye", DragonEyeEnchantment::new);
    public static final Supplier<Enchantment> DRACONIC_FOR_DUMMIES = register("draconic_for_dummies", DraconicForDummiesEnchantment::new);

    public static void init() {
        Dragonflame.LOGGER.debug("Registering Enchantmens for " + Dragonflame.MOD_ID);
        ENCHANTMENTS.register();
    }

    public static RegistrySupplier<Enchantment> register(String name, Supplier<Enchantment> enchantment) {
        return ENCHANTMENTS.register(name, enchantment);
}
}
