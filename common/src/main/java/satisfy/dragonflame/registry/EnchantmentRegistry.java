package satisfy.dragonflame.registry;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.enchantment.DraconicForDummiesEnchantment;
import satisfy.dragonflame.enchantment.DragonEyeEnchantment;
import satisfy.dragonflame.enchantment.DragonHeartEnchantment;
import satisfy.dragonflame.util.DragonflameIdentifier;

import java.util.function.Supplier;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTS = DeferredRegister.create(Dragonflame.MOD_ID, Registries.ENCHANTMENT);

    public static final Supplier<Enchantment> DRAGON_HEART = ENCHANTS.register(new DragonflameIdentifier("dragon_heart"), DragonHeartEnchantment::new);
    public static final Supplier<Enchantment> DRAGON_EYE = ENCHANTS.register(new DragonflameIdentifier("dragon_eye"), DragonEyeEnchantment::new);
    public static final Supplier<Enchantment> DRACONIC_FOR_DUMMIES = ENCHANTS.register(new DragonflameIdentifier("draconic_for_dummies"), DraconicForDummiesEnchantment::new);

    public static void init() {
        Dragonflame.LOGGER.debug("Registering Enchantmens for " + Dragonflame.MOD_ID);
        ENCHANTS.register();
    }
}
