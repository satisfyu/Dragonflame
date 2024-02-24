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
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.entity.GrimAnvilBlockEntity;
import satisfy.dragonflame.registry.BlockEntityRegistry;
import satisfy.dragonflame.registry.ObjectRegistry;
import satisfy.dragonflame.util.DragonflameUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("all")
public class GrimAnvilBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty ESSENCE = BooleanProperty.create("essence");

    public GrimAnvilBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.WEST).setValue(ESSENCE, false));
    }

    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0, 1, 0.6875, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.6875, 0.3125, 1, 0.8125, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.6875, 0, 0.6875, 0.8125, 0.3125), BooleanOp.OR);
        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = net.minecraft.Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, DragonflameUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection();
        BlockPos blockPos = getMainPos(ctx.getClickedPos(), direction);
        Level level = ctx.getLevel();
        boolean occupied = false;
        occupied |= !level.getBlockState(blockPos).canBeReplaced();
        occupied |= !level.getBlockState(blockPos.west()).canBeReplaced();
        occupied |= !level.getBlockState(blockPos.west().north()).canBeReplaced();
        occupied |= !level.getBlockState(blockPos.north()).canBeReplaced();
        return occupied ? null : this.defaultBlockState().setValue(FACING, direction);
    }

    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState, LivingEntity livingEntity, @NotNull ItemStack itemStack) {
        Direction direction = blockState.getValue(FACING);
        BlockPos mainPos = getMainPos(blockPos, direction);
        if (direction != Direction.WEST)
            level.setBlockAndUpdate(mainPos, blockState.setValue(FACING, Direction.WEST));
        if (direction != Direction.NORTH)
            level.setBlockAndUpdate(mainPos.west(), blockState.setValue(FACING, Direction.NORTH));
        if (direction != Direction.EAST)
            level.setBlockAndUpdate(mainPos.west().north(), blockState.setValue(FACING, Direction.EAST));
        if (direction != Direction.SOUTH)
            level.setBlockAndUpdate(mainPos.north(), blockState.setValue(FACING, Direction.SOUTH));
    }

    @Override
    public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Player player) {
        getGrimAnvilComponents(blockPos, blockState).forEach(pos -> level.removeBlock(pos, false));
        super.playerWillDestroy(level, blockPos, blockState, player);
    }

    @Override
    public void onRemove(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState2, boolean bl) {
        if (level.isClientSide) return;
        if (!blockState.is(blockState2.getBlock())) {
            if (blockState.getValue(ESSENCE))
                Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(ObjectRegistry.ESSENCE_OF_FIRE.get().asItem()));
            if (blockState.getValue(FACING) == Direction.WEST) {
                GrimAnvilBlockEntity grimAnvilEntity = getGrimAnvilEntity(blockState, level, blockPos);
                if (grimAnvilEntity != null) {
                    ItemStack returnStack = grimAnvilEntity.removeOre();
                    if (!returnStack.isEmpty()) {
                        Containers.dropItemStack(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), returnStack);
                    }
                }
            }
        }
        super.onRemove(blockState, level, blockPos, blockState2, bl);
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (interactionHand == InteractionHand.OFF_HAND) {
            return InteractionResult.CONSUME;
        }

        ItemStack itemStack = player.getItemInHand(interactionHand);
        GrimAnvilBlockEntity blockEntity = getGrimAnvilEntity(blockState, level, blockPos);

        if (!level.isClientSide) {
            if (itemStack.getItem() == ObjectRegistry.ESSENCE_OF_FIRE.get().asItem()) {
                if (!blockState.getValue(ESSENCE)) {
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    level.setBlockAndUpdate(blockPos, blockState.setValue(ESSENCE, true));
                    displaySuccessParticles(level, blockPos, blockState);
                    level.playSound(null, blockPos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 2.0F, 1.0F);
                    level.sendBlockUpdated(blockPos, blockState, blockState, 3);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                } else {
                    return InteractionResult.PASS;
                }
            } else if (itemStack.getItem() == ObjectRegistry.TITAN_INGOT.get().asItem()) {
                if (blockEntity != null) {
                    InteractionResult interactionResult = blockEntity.addOre(itemStack);
                    if (interactionResult.consumesAction()) {
                        update(blockPos, blockState, level);
                        return interactionResult;
                    }
                }
            } else if (itemStack.isEmpty()) {
                if (blockEntity != null) {
                    ItemStack returnStack = blockEntity.removeOre();
                    if (!returnStack.isEmpty()) {
                        if (!player.addItem(returnStack)) {
                            player.drop(returnStack, false);
                        }
                        update(blockPos, blockState, level);
                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        } else {
            if (itemStack.getItem() == ObjectRegistry.ESSENCE_OF_FIRE.get().asItem() && blockState.getValue(ESSENCE)) {
                displayLavaParticles(level, blockPos);
            }
        }

        return InteractionResult.PASS;
    }


    private void displaySuccessParticles(Level world, BlockPos pos, BlockState blockState) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), pos.getX() + 0.5, pos.getY() - 1, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.2);
            displayLavaParticles(world, pos);
        }
    }

    private void displayLavaParticles(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.05);
        }
    }

    @Override
    public void animateTick(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(ESSENCE)) {
            double d0 = world.random.nextGaussian() * 0.02D;
            double d1 = world.random.nextGaussian() * 0.02D;
            double d2 = world.random.nextGaussian() * 0.02D;
            double yOffset = 1.0D + world.random.nextDouble() * 2.0D;
            world.addParticle(ParticleTypes.SMOKE, pos.getX() + world.random.nextDouble(), pos.getY() + yOffset, pos.getZ() + world.random.nextDouble(), d0, d1, d2);
        }
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        boolean isLit = world.getBlockState(pos).getValue(ESSENCE);
        if (isLit && !entity.fireImmune() && entity instanceof Player player &&
                !EnchantmentHelper.hasFrostWalker(player)) {
            entity.hurt(world.damageSources().hotFloor(), 1.f);
        }
        super.stepOn(world, pos, state, entity);
    }

    public static BlockPos getMainPos(BlockPos blockPos, BlockState blockState) {
        return getMainPos(blockPos, blockState.getValue(FACING));
    }

    public static BlockPos getMainPos(BlockPos blockPos, Direction direction) {
        return switch (direction) {
            case NORTH -> blockPos.east();
            case EAST -> blockPos.south().east();
            case SOUTH -> blockPos.south();
            default -> blockPos;
        };
    }

    public static Set<BlockPos> getGrimAnvilComponents(BlockPos blockPos, BlockState blockState) {
        BlockPos mainPos = getMainPos(blockPos, blockState);
        return Set.of(mainPos, mainPos.west(), mainPos.west().north(), mainPos.north());
    }

    @Nullable
    public static GrimAnvilBlockEntity getGrimAnvilEntity(BlockState blockState, Level level, BlockPos blockPos) {
        BlockPos mainPos = getMainPos(blockPos, blockState);
        return findGrimAnvilEntity(mainPos, level);
    }

    @Nullable
    private static <T extends BlockGetter> GrimAnvilBlockEntity findGrimAnvilEntity(BlockPos blockPos, T blockGetter) {
        return blockGetter.getBlockEntity(blockPos, BlockEntityRegistry.GRIM_ANVIL_BLOCK_ENTITY.get()).orElse(null);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ESSENCE);
    }

    private void update(@NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Level level) {
        level.sendBlockUpdated(getMainPos(blockPos, blockState), blockState, blockState, 3);
    }
    @Override
    @Nullable
    public MenuProvider getMenuProvider(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos) {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, BlockState blockState) {
        return blockState.getValue(FACING) == Direction.WEST ? new GrimAnvilBlockEntity(blockPos, blockState) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof BlockEntityTicker<?>) {
                ((BlockEntityTicker<T>) blockEntity).tick(level, pos, state1, blockEntity);
            }
        };
    }

    public @NotNull BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.dragonflame.unobtainable").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED));
    }
}