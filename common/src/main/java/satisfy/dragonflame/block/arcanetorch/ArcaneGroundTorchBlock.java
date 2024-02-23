package satisfy.dragonflame.block.arcanetorch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArcaneGroundTorchBlock extends AbstractArcaneTorchBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape BOUNDING_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

    public ArcaneGroundTorchBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return BOUNDING_SHAPE;
    }

    public boolean canPlace(BlockState state, LevelReader world, BlockPos pos) {
        return canSupportCenter(world, pos.below(), Direction.UP);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor world, BlockPos pos, BlockPos neighbourPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (direction == Direction.DOWN && !this.canPlace(state, world, pos)) {
            this.playerWillDestroy(((Level) world), pos, state, null);
            return Blocks.AIR.defaultBlockState();
        }
        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        BlockState blockState = this.defaultBlockState();
        LevelAccessor worldView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();

        boolean shouldDropTag = false;
        if (ctx.getItemInHand().getTag() != null) shouldDropTag = ctx.getItemInHand().getTag().getBoolean("should_drop");
        if (!ctx.getPlayer().isCreative() || shouldDropTag) {
            blockState = blockState.setValue(SHOULD_DROP, true);
        }

        blockState = blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        if (blockState.canSurvive(worldView, blockPos)) {
            return blockState;
        }

        return null;
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        SpecialRitualHandler ritualHandler = new SpecialRitualHandler(level, blockPos);
        // This will auto-execute ritual if a valid set of torches are found
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        if (!super.canSurvive(blockState, levelReader, blockPos)) return false;
        return levelReader.getBlockState(blockPos.below()).isCollisionShapeFullBlock(levelReader, blockPos.below());
    }
}
