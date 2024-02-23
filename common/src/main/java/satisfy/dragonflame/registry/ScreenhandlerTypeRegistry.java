package satisfy.dragonflame.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.client.gui.LootChestScreenhandler;

import java.util.function.Supplier;


public class ScreenhandlerTypeRegistry {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Dragonflame.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<LootChestScreenhandler>> LOOTCHEST_SCREENHANDLER = create("lootchest", () -> new MenuType<>(LootChestScreenhandler::new, FeatureFlags.VANILLA_SET));

    public static void init() {
        MENU_TYPES.register();
    }

    private static <T extends MenuType<?>> RegistrySupplier<T> create(String name, Supplier<T> type) {
        return MENU_TYPES.register(name, type);
    }
}