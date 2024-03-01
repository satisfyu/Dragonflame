package satisfy.dragonflame.world.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import satisfy.dragonflame.Dragonflame;

import java.util.OptionalLong;

public class DragonIslandDimension {
    public static final ResourceKey<Level> LIMBO = ResourceKey.create(Registries.DIMENSION, Dragonflame.MOD_ID("limbo"));

    public static final ResourceKey<DimensionType> LIMBO_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE, Dragonflame.MOD_ID("limbo"));

    public static ServerLevel LIMBO_DIMENSION;


    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(KAUPEN_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));
    }
}