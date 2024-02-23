package satisfy.dragonflame.block.arcanetorch;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.BlockEntityRegistry;
import satisfy.dragonflame.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public abstract class AbstractArcaneTorchBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final BooleanProperty SHOULD_DROP = BooleanProperty.create("should_drop");
    protected AbstractArcaneTorchBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(SHOULD_DROP, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArcaneBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public static ItemStack getAsDroppableItemStack() {
        ItemStack stack = new ItemStack(ObjectRegistry.ARCANE_TORCH.get());
        CompoundTag nbt = ArcaneTorchItem.getDefaultTag();
        nbt.putBoolean("should_drop", true);
        stack.setTag(nbt);
        return stack;
        // this will ignore all other blockstates, but not a huge issue since the colors aren't supposed to change by default
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if(world.getBlockEntity(pos) instanceof ArcaneBlockEntity arcane)
            arcane.onPlaced(world, pos, state, placer, itemStack);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor world, BlockPos pos, BlockPos neighbourPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighbourState, world, pos, neighbourPos);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(SHOULD_DROP);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        if (!blockState.getValue(SHOULD_DROP)) return Collections.emptyList();
        return new ArrayList<>(Collections.singleton(getAsDroppableItemStack()));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getPlayer().blockPosition());
        BlockState blockState = this.defaultBlockState();

        boolean shouldDropTag = false;
        if (ctx.getItemInHand().getTag() != null) shouldDropTag = ctx.getItemInHand().getTag().getBoolean("should_drop");
        if (!ctx.getPlayer().isCreative() || shouldDropTag) {
            blockState = blockState.setValue(SHOULD_DROP, true);
        }
        return blockState.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    /*
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? createTickerHelper(type, BlockEntityRegistry.ARCANE_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> blockEntity.clientTick(world1, pos, state1)) : null;
    }
     */

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public abstract VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context);

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        if (!super.canSurvive(blockState, levelReader, blockPos)) return false;
        return levelReader.getBlockState(blockPos.below()).isCollisionShapeFullBlock(levelReader, blockPos.below());
    }
}