package satisfy.dragonflame.util;

import com.google.gson.JsonArray;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfy.dragonflame.Dragonflame;

import java.util.*;

public interface GeneralUtil {
	static Collection<ServerPlayer> tracking(ServerLevel world, BlockPos pos) {
		Objects.requireNonNull(pos, "BlockPos cannot be null");

		return tracking(world, new ChunkPos(pos));
	}

	static boolean isFullAndSolid(LevelReader levelReader, BlockPos blockPos){
		return isFaceFull(levelReader, blockPos) && isSolid(levelReader, blockPos);
	}

	static boolean isFaceFull(LevelReader levelReader, BlockPos blockPos){
		BlockPos belowPos = blockPos.below();
		return Block.isFaceFull(levelReader.getBlockState(belowPos).getShape(levelReader, belowPos), Direction.UP);
	}

	static boolean isSolid(LevelReader levelReader, BlockPos blockPos){
		return levelReader.getBlockState(blockPos.below()).isSolid();
	}


	static Collection<ServerPlayer> tracking(ServerLevel world, ChunkPos pos) {
		Objects.requireNonNull(world, "The world cannot be null");
		Objects.requireNonNull(pos, "The chunk pos cannot be null");

		return world.getChunkSource().chunkMap.getPlayers(pos, false);
	}


	static RotatedPillarBlock logBlock() {
		return new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG));
	}

	static boolean matchesRecipe(Container inventory, NonNullList<Ingredient> recipe, int startIndex, int endIndex) {
		final List<ItemStack> validStacks = new ArrayList<>();
		for (int i = startIndex; i <= endIndex; i++) {
			final ItemStack stackInSlot = inventory.getItem(i);
			if (!stackInSlot.isEmpty())
				validStacks.add(stackInSlot);
		}
		for (Ingredient entry : recipe) {
			boolean matches = false;
			for (ItemStack item : validStacks) {
				if (entry.test(item)) {
					matches = true;
					validStacks.remove(item);
					break;
				}
			}
			if (!matches) {
				return false;
			}
		}
		return true;
	}
	
	static NonNullList<Ingredient> deserializeIngredients(JsonArray json) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (int i = 0; i < json.size(); i++) {
			Ingredient ingredient = Ingredient.fromJson(json.get(i));
			if (!ingredient.isEmpty()) {
				ingredients.add(ingredient);
			}
		}
		return ingredients;
	}
	
	static boolean isIndexInRange(int index, int startInclusive, int endInclusive) {
		return index >= startInclusive && index <= endInclusive;
	}
	
	static VoxelShape rotateShape(Direction from, Direction to, VoxelShape shape) {
		VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };
		
		int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
		for (int i = 0; i < times; i++) {
			buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.joinUnoptimized(buffer[1],
					Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX),
					BooleanOp.OR
			                                                                                            ));
			buffer[0] = buffer[1];
			buffer[1] = Shapes.empty();
		}
		return buffer[0];
	}
	
	static Optional<Tuple<Float, Float>> getRelativeHitCoordinatesForBlockFace(BlockHitResult blockHitResult, Direction direction, Direction[] unAllowedDirections) {
		Direction direction2 = blockHitResult.getDirection();
		if (unAllowedDirections == null)
			unAllowedDirections = new Direction[] { Direction.DOWN, Direction.UP };
		if (Arrays.stream(unAllowedDirections).toList().contains(direction2))
			return Optional.empty();
		if (direction != direction2 && direction2 != Direction.UP && direction2 != Direction.DOWN) {
			return Optional.empty();
		} else {
			BlockPos blockPos = blockHitResult.getBlockPos().relative(direction2);
			Vec3 vec3 = blockHitResult.getLocation().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
			float d = (float) vec3.x();
			float f = (float) vec3.z();
			
			float y = (float) vec3.y();
			
			if (direction2 == Direction.UP || direction2 == Direction.DOWN)
				direction2 = direction;
			return switch (direction2) {
				case NORTH -> Optional.of(new Tuple<>((float) (1.0 - d), y));
				case SOUTH -> Optional.of(new Tuple<>(d, y));
				case WEST -> Optional.of(new Tuple<>(f, y));
				case EAST -> Optional.of(new Tuple<>((float) (1.0 - f), y));
				case DOWN, UP -> Optional.empty();
			};
		}
	}

	static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Dragonflame.MOD_ID, name));
	}

	static <T extends BlockEntity> void renderItem(ItemStack itemStack, PoseStack poseStack, MultiBufferSource multiBufferSource, T blockEntity) {
		Level level = blockEntity.getLevel();
		if (level != null) {
			Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.GUI, getLightLevel(level, blockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, level, 1);
		}
	}

	static int getLightLevel(Level world, BlockPos pos) {
		int bLight = world.getBrightness(LightLayer.BLOCK, pos);
		int sLight = world.getBrightness(LightLayer.SKY, pos);
		return LightTexture.pack(bLight, sLight);
	}

	/**
	 * @param searchFor The object to search for
	 * @param searchIn The objects to check in
	 * @return Whether the object is equal to any of the objects in the array
	 */
	static boolean equalsMultiple(Object searchFor, Object... searchIn) {
		return Arrays.stream(searchIn).toList().contains(searchFor);
	}
}