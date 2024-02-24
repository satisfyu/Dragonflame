package satisfy.dragonflame.registry;

import de.cristelknight.doapi.DoApi;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfy.dragonflame.block.arcanetorch.ArcaneBlockEntity;
import satisfy.dragonflame.entity.LootChestEntity;
import satisfy.dragonflame.entity.GrimAnvilBlockEntity;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(DoApi.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<ArcaneBlockEntity>> ARCANE_BLOCK_ENTITY = register("arcane", () -> BlockEntityType.Builder.of(ArcaneBlockEntity::new, ObjectRegistry.ARCANE_GROUND_TORCH.get(), ObjectRegistry.ARCANE_WALL_TORCH.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<GrimAnvilBlockEntity>> GRIM_ANVIL_BLOCK_ENTITY = register("grim_anvil", () -> BlockEntityType.Builder.of(GrimAnvilBlockEntity::new, ObjectRegistry.GRIM_ANVIL.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<LootChestEntity>> LOOTCHEST_BLOCK_ENTITY = register("lootchest", () -> BlockEntityType.Builder.of(LootChestEntity::new, ObjectRegistry.LOOTCHEST.get()).build(null));

    private static <T extends BlockEntityType<?>> RegistrySupplier<T> register(final String path, final Supplier<T> type) {
        return BLOCK_ENTITY_TYPES.register(path, type);
    }

    public static void init() {
        BLOCK_ENTITY_TYPES.register();
    }
}