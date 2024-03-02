package satisfy.dragonflame.world.dimension;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import satisfy.dragonflame.Dragonflame;

import java.util.OptionalLong;

public class DragonIslandDimension {
    public static final ResourceKey<Level> DRAGONISLAND = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Dragonflame.MOD_ID, "dragonisland_dimension"));

    public static final ResourceKey<DimensionType> DRAGONISLAND_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(Dragonflame.MOD_ID, "dragonisland_type"));

    public static DimensionType DRAGONISLAND_TYPE;

    public static ServerLevel DRAGONISLAND_DIMENSION;

    private static DimensionType dragonflameDimensionType() {
        return new DimensionType(
                OptionalLong.of(12000), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                false, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.OVERWORLD_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 7)
        );
    }

    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(DRAGONISLAND_TYPE_KEY, dragonflameDimensionType());
    }

    public static void init() {
        LifecycleEvent.SERVER_STARTED.register(server -> {
            DragonIslandDimension.DRAGONISLAND_TYPE = server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE).get(DRAGONISLAND_TYPE_KEY);
            DragonIslandDimension.DRAGONISLAND_DIMENSION = server.getLevel(DRAGONISLAND);
        });
        var deffered =DeferredRegister.create(Dragonflame.MOD_ID, Registries.CHUNK_GENERATOR);
        deffered.register("blank", () -> BlankChunkGenerator.CODEC);
        deffered.register();
    }
}
