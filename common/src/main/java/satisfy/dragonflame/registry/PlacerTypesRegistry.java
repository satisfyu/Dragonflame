package satisfy.dragonflame.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.world.foliageplacers.DragonFoliagePlacer;

public class PlacerTypesRegistry {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(Dragonflame.MOD_ID, Registries.FOLIAGE_PLACER_TYPE);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Dragonflame.MOD_ID, Registries.TRUNK_PLACER_TYPE);

    public static final RegistrySupplier<FoliagePlacerType<DragonFoliagePlacer>> DRAGON_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPES.register("dragon_foliage_placer" ,() -> new FoliagePlacerType<>(DragonFoliagePlacer.CODEC));

    public static void init() {
        FOLIAGE_PLACER_TYPES.register();
        TRUNK_PLACER_TYPES.register();
    }
}
