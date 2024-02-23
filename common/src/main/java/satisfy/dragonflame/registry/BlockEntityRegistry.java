package satisfy.dragonflame.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import satisfy.dragonflame.entity.LootChestEntity;
import satisfy.dragonflame.entity.GrimAnvilBlockEntity;
import satisfy.dragonflame.util.DragonflameIdentifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface BlockEntityRegistry {
    Map<ResourceLocation, BlockEntityType<?>> BLOCK_ENTITY_TYPES  = new LinkedHashMap<>();

    //BlockEntityType<ArcaneBlockEntity> ARCANE_BLOCK_ENTITY = register("arcane", BlockEntityType.Builder.of(ArcaneBlockEntity::new, ObjectRegistry.ARCANE_GROUND_TORCH.get(), ObjectRegistry.ARCANE_WALL_TORCH.get()).build(null));
    BlockEntityType<GrimAnvilBlockEntity> GRIM_ANVIL_BLOCK_ENTITY = register("grim_anvil", BlockEntityType.Builder.of(GrimAnvilBlockEntity::new, ObjectRegistry.GRIM_ANVIL.get()).build(null));
    BlockEntityType<LootChestEntity> LOOTCHEST_BLOCK_ENTITY = register("lootchest", BlockEntityType.Builder.of(LootChestEntity::new, ObjectRegistry.LOOTCHEST.get()).build(null));

    static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(new DragonflameIdentifier(id), type);
        return type;
    }

    static void init() {
        BLOCK_ENTITY_TYPES.forEach((id, entityType) -> Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, entityType));
    }
}