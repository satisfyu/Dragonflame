package satisfy.dragonflame.world.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.registry.PlacerTypesRegistry;

public class DragonFoliagePlacer extends FoliagePlacer {
   public static final Codec<DragonFoliagePlacer> CODEC =
           RecordCodecBuilder.create(instance -> foliagePlacerParts(instance)
                   .and(IntProvider.codec(0, 24).fieldOf("trunk_height")
                           .forGetter(placer -> placer.trunkHeight)).apply(instance, DragonFoliagePlacer::new));
   private final IntProvider trunkHeight;

   public DragonFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider trunkHeight) {
      super(radius, offset);
      this.trunkHeight = trunkHeight;
   }

   protected @NotNull FoliagePlacerType<?> type() {
      return PlacerTypesRegistry.DRAGON_FOLIAGE_PLACER.get();
   }

   protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter blockPlacer, RandomSource random, @NotNull TreeConfiguration config, int trunkHeight, FoliageAttachment treeNode, int foliageHeight, int radius, int offset) {
      BlockPos blockPos = treeNode.pos();
      BlockPos.MutableBlockPos mutable = blockPos.mutable();
      boolean nextBoolean = random.nextBoolean();
      boolean nextBoolean2 = random.nextBoolean();
      boolean nextBoolean3 = random.nextBoolean();

      for (int l = offset; l >= -foliageHeight; --l) {
         mutable.set(blockPos.getX(), blockPos.getY() + l, blockPos.getZ());

         if (l >= offset - 4 && l <= offset) {
            this.generateSquare(level, blockPlacer, random, config, blockPos, l, treeNode.doubleTrunk());
            if (!shouldSkipLocation2(random, 0, l, 0, treeNode.doubleTrunk())) {
               if ((nextBoolean || nextBoolean2 || nextBoolean3) && l <= offset) {
                  tryPlaceLeaf(level, blockPlacer, random, config, mutable);
               }
            }
         } else if (l == offset - 5) {
            // Random placement for the given layer
            if (random.nextBoolean()) {
               tryPlaceLeaf(level, blockPlacer, random, config, mutable.relative(Direction.getRandom(random)).relative(Direction.getRandom(random)).above());
            }
         } else if (l >= offset - 10) {
            // Random placement for the given layer
            if (random.nextBoolean()) {
               tryPlaceLeaf(level, blockPlacer, random, config, mutable.relative(Direction.NORTH).relative(Direction.EAST));
            }
            if (random.nextBoolean()) {
               tryPlaceLeaf(level, blockPlacer, random, config, mutable.relative(Direction.NORTH).relative(Direction.WEST));
            }
            if (random.nextBoolean()) {
               tryPlaceLeaf(level, blockPlacer, random, config, mutable.relative(Direction.SOUTH).relative(Direction.EAST));
            }
            if (random.nextBoolean()) {
               tryPlaceLeaf(level, blockPlacer, random, config, mutable.relative(Direction.SOUTH).relative(Direction.WEST));
            }
         } else if (l >= offset - 14) {
            this.generateDiagonalSquare(level, blockPlacer, random, config, blockPos, l, treeNode.doubleTrunk());
         }
      }
   }

   protected void generateSquare(LevelSimulatedReader level, FoliageSetter blockPlacer, RandomSource random, TreeConfiguration config, BlockPos centerPos, int y, boolean giantTrunk) {
      int i = giantTrunk ? 1 : 0;
      BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

      for(int j = -1; j <= 1 + i; ++j) {
         for(int k = -1; k <= 1 + i; ++k) {
            mutable.set(centerPos.getX() + j, centerPos.getY() + y, centerPos.getZ() + k);
            if (isPositionInvalid2(random, j, y, k, 1, giantTrunk)) {
               tryPlaceLeaf(level, blockPlacer, random, config, mutable);
            }
         }
      }

   }

   protected void generateDiagonalSquare(LevelSimulatedReader level, FoliageSetter blockPlacer, RandomSource random, TreeConfiguration config, BlockPos centerPos, int y, boolean giantTrunk) {
      int i = giantTrunk ? 1 : 0;
      BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

      for(int j = -2; j <= 2 + i; ++j) {
         for(int k = -2; k <= 2 + i; ++k) {
            if(this.isPositionInvalid2(random, j, y, k, 2, giantTrunk)) {
               mutable.set(centerPos.getX() + j, centerPos.getY() + y, centerPos.getZ() + k);
               tryPlaceLeaf(level, blockPlacer, random, config, mutable);
            }
         }
      }
   }

   protected boolean isPositionInvalid2(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
      // Beispiel: Anpassung der Bedingung für die Platzierung
      int distance = Math.max(Math.abs(dx), Math.abs(dz)); // Distanz vom Zentrum
      return distance <= radius && !giantTrunk; // Ermöglicht Platzierung in einem bestimmten Radius
   }




   public int foliageHeight(@NotNull RandomSource random, int trunkHeight, @NotNull TreeConfiguration config) {
      return Math.max(8, trunkHeight - this.trunkHeight.sample(random));
   }

   protected boolean shouldSkipLocation(@NotNull RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
      return Math.max(Math.abs(dx), Math.abs(dz)) == radius && (random.nextBoolean() || y > 4);
   }

   protected boolean shouldSkipLocation2(RandomSource random, int dx, int y, int dz, boolean giantTrunk) {
      int trunkHeight = this.trunkHeight.sample(random);
      // Skip positions to reduce leaf density towards the bottom of the canopy
      boolean isFarFromCenter = Math.max(Math.abs(dx), Math.abs(dz)) > 1;
      boolean isNotAtTrunkLevel = y < trunkHeight - 2;
      return (isFarFromCenter && random.nextInt(3) != 0) || (isNotAtTrunkLevel && random.nextBoolean());
   }
}