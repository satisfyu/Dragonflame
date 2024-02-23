package satisfy.dragonflame.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@SuppressWarnings("all")
public class StatueBlock extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final Map<UUID, Long> lastUseTime = new HashMap<>();
    private static final VoxelShape LOWER_SHAPE = LowerShape.makeShape();
    private static final VoxelShape UPPER_SHAPE = UpperShape.makeShape();

    public class LowerShape {
        public static VoxelShape makeShape() {
            VoxelShape shape = Shapes.empty();
            shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.1875, 1), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25625, 0.1875, 0.375, 0.50625, 0.9375, 0.625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.50625, 0.6875, 0.125, 0.75625, 1, 0.375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0.9375, 0.375, 0.75, 1, 0.625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.40625, 0.1875, -0.03125, 0.90625, 0.6875, 0.46875), BooleanOp.OR);
            return shape;
        }
    }

    public class UpperShape {
        public static VoxelShape makeShape() {
            VoxelShape shape = Shapes.empty();
            shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.375, 0.75, 0.6875, 0.625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.50625, 0, 0.125, 0.75625, 0.3125, 0.375), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0, -0.0625, 0.375, 0.25, 0.6875, 0.625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0.6875, 0.25, 0.75, 1.1875, 0.75), BooleanOp.OR);
            return shape;
        }
    }


    public StatueBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, DoubleBlockHalf.LOWER).setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        DoubleBlockHalf half = state.getValue(HALF);
        Direction facing = state.getValue(FACING);
        VoxelShape shape = half == DoubleBlockHalf.LOWER ? LOWER_SHAPE : UPPER_SHAPE;
        return rotateVoxelShape(facing, shape);
    }



    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!blockState2.is(this) || blockState2.getValue(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !blockState.canSurvive(levelAccessor, blockPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
        }
    }

    private VoxelShape rotateVoxelShape(Direction facing, VoxelShape shape) {
        VoxelShape rotatedShape = Shapes.empty();
        for (var box : shape.toAabbs()) {
            double minX = box.minX;
            double minY = box.minY;
            double minZ = box.minZ;
            double maxX = box.maxX;
            double maxY = box.maxY;
            double maxZ = box.maxZ;

            double newMinX = 0;
            double newMinZ = 0;
            double newMaxX = 0;
            double newMaxZ = 0;

            switch (facing) {
                case NORTH:
                    rotatedShape = Shapes.or(rotatedShape, shape);
                    continue;
                case SOUTH:
                    newMinX = 1.0 - maxX;
                    newMaxX = 1.0 - minX;
                    newMinZ = 1.0 - maxZ;
                    newMaxZ = 1.0 - minZ;
                    break;
                case WEST:
                    newMinX = minZ;
                    newMaxX = maxZ;
                    newMinZ = 1.0 - maxX;
                    newMaxZ = 1.0 - minX;
                    break;
                case EAST:
                    newMinX = 1.0 - maxZ;
                    newMaxX = 1.0 - minZ;
                    newMinZ = minX;
                    newMaxZ = maxX;
                    break;
            }

            if (facing != Direction.NORTH) {
                rotatedShape = Shapes.or(rotatedShape, Shapes.box(newMinX, minY, newMinZ, newMaxX, maxY, newMaxZ));
            }
        }
        return rotatedShape;
    }




    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        if (!belowState.isAir() && belowState.isFaceSturdy(level, belowPos, Direction.UP)) {
            Direction direction = context.getHorizontalDirection().getOpposite();
            boolean canPlaceUpper = pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).canBeReplaced(context);
            DoubleBlockHalf half = canPlaceUpper ? DoubleBlockHalf.LOWER : null;
            return half != null ? this.defaultBlockState().setValue(FACING, direction).setValue(HALF, half) : null;
        }
        return null;
    }


    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        BlockPos blockPos2 = blockPos.above();
        if (blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            Direction direction = livingEntity.getDirection().getOpposite();
            level.setBlock(blockPos2, copyWaterloggedFrom(level, blockPos2, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER).setValue(FACING, direction)), 3);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos belowPos = pos.below();
            BlockState belowState = level.getBlockState(belowPos);
            return belowState.isFaceSturdy(level, belowPos, Direction.UP);
        } else {
            BlockState blockStateBelow = level.getBlockState(pos.below());
            return blockStateBelow.getBlock() == this && blockStateBelow.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    public static BlockState copyWaterloggedFrom(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) ? blockState.setValue(BlockStateProperties.WATERLOGGED, levelReader.isWaterAt(blockPos)) : blockState;
    }

    public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                preventCreativeDropFromBottomPart(level, blockPos, blockState, player);
            } else {
                dropResources(blockState, level, blockPos, null, player, player.getMainHandItem());
            }
        }
        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, Blocks.AIR.defaultBlockState(), blockEntity, itemStack);
    }

    protected static void preventCreativeDropFromBottomPart(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        DoubleBlockHalf doubleBlockHalf = blockState.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState2 = level.getBlockState(blockPos2);
            if (blockState2.getBlock() == blockState.getBlock() && blockState2.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockState blockState3 = blockState2.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(blockPos2, blockState3, 35);
                level.levelEvent(player, 2001, blockPos2, Block.getId(blockState2));
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING);
    }

    public long getSeed(BlockState blockState, BlockPos blockPos) {
        return Mth.getSeed(blockPos.getX(), blockPos.below(blockState.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), blockPos.getZ());
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!world.isClientSide) {
            if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
                UUID playerId = player.getUUID();
                long currentTime = world.getGameTime();
                long cooldown = 12000;

                Long lastUse = lastUseTime.get(playerId);
                if (lastUse == null || (currentTime - lastUse) >= cooldown) {
                    lastUseTime.put(playerId, currentTime);
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 12000, 2));
                    world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                    displaySuccessParticles(world, pos, state);
                    return InteractionResult.SUCCESS;
                } else {
                    if (hand == InteractionHand.MAIN_HAND) {
                        player.displayClientMessage(Component.translatable("tooltip.dragonflame.statue_cooldown").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC), true);
                        world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        displayCooldownParticles(world, pos);
                    }
                    return InteractionResult.FAIL;
                }
            }
        }
        return InteractionResult.PASS;
    }


    private void displaySuccessParticles(Level world, BlockPos pos, BlockState blockState) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                    pos.getX() + 0.5, pos.getY() - 1, pos.getZ() + 0.5,
                    20, 0.5, 0.5, 0.5, 0.2);
            displayFlameParticles(world, pos);
        }
    }

    private void displayFlameParticles(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FLAME,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    15, 0.5, 0.5, 0.5, 0.05);
        }
    }

    private void displayCooldownParticles(Level world, BlockPos pos) {
        for (int i = 0; i < 10; ++i) {
            double d0 = world.random.nextGaussian() * 0.02D;
            double d1 = world.random.nextGaussian() * 0.02D;
            double d2 = world.random.nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.ASH, pos.getX() + world.random.nextDouble(), pos.getY() + world.random.nextDouble() * 1.0D, pos.getZ() + world.random.nextDouble(), d0, d1, d2);
        }
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.dragonflame.unobtainable").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED));
    }
}