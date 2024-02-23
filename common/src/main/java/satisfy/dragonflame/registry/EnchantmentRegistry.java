package satisfy.dragonflame.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.enchantment.DraconicForDummiesEnchantment;
import satisfy.dragonflame.enchantment.DragonEyeEnchantment;
import satisfy.dragonflame.enchantment.DragonHeartEnchantment;

public interface EnchantmentRegistry {
    Enchantment DRACONIC_FOR_DUMMIES = new DraconicForDummiesEnchantment(Enchantment.Rarity.COMMON, EquipmentSlot.MAINHAND);
    Enchantment DRAGON_EYE = new DragonEyeEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.HEAD);
    Enchantment DRAGON_HEART = new DragonHeartEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.CHEST);


    static void init() {
        Dragonflame.LOGGER.debug("Registering Enchantmens for " + Dragonflame.MOD_ID);
        register("draconic_for_dummies", DRACONIC_FOR_DUMMIES);
        register("dragon_eye", DRAGON_EYE);
        register("dragon_heart", DRAGON_HEART);

    }

    static Enchantment register(String name, Enchantment enchantment) {
        ResourceLocation id = new ResourceLocation("dragonflame", name);
        return Registry.register(BuiltInRegistries.ENCHANTMENT, id, enchantment);
    }
}
