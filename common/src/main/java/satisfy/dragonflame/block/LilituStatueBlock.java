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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("all")
public class LilituStatueBlock extends StatueBlock {
    private static final Map<UUID, Long> lastUseTime = new HashMap<>();
    private static final VoxelShape LOWER_SHAPE = LilituStatueBlock.LowerShape.makeShape();
    private static final VoxelShape UPPER_SHAPE = LilituStatueBlock.UpperShape.makeShape();

    public LilituStatueBlock(Properties properties) {
        super(properties);
    }


    public class LowerShape {
        public static VoxelShape makeShape() {
            VoxelShape shape = Shapes.empty();
            shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.1875, 1), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0.9375, 0.375, 0.75, 1, 0.625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.49375, 0.1875, 0.375, 0.74375, 0.9375, 0.625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.24375, 0.125, 0.5, 0.49375, 0.9375, 0.75), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0.0625, -0.0625, 0.75, 0.375, 0.0625), BooleanOp.OR);
            return shape;
        }
    }

    public class UpperShape {
        public static VoxelShape makeShape() {
            VoxelShape shape = Shapes.empty();
            shape = Shapes.join(shape, Shapes.box(0, -0.125, 0.5, 0.25, 0.625, 0.75), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.375, 0.75, 0.6875, 0.625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.25, 0.6875, 0.25, 0.75, 1.1875, 0.75), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.2375, 0.9875, 0.2375, 0.7625, 1.2, 0.7625), BooleanOp.OR);
            shape = Shapes.join(shape, Shapes.box(0.75, 0.5, -0.125, 1, 0.75, 0.625), BooleanOp.OR);
            return shape;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        DoubleBlockHalf half = state.getValue(HALF);
        Direction facing = state.getValue(FACING);
        VoxelShape shape = half == DoubleBlockHalf.LOWER ? LOWER_SHAPE : UPPER_SHAPE;
        return rotateVoxelShape(facing, shape);
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
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 12000, 2));
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
            displayVillagerParticles(world, pos);
        }
    }

    private void displayVillagerParticles(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    20, 0.5, 0.5, 0.5, 0.05);
        }
    }

    private void displayCooldownParticles(Level world, BlockPos pos) {
        for (int i = 0; i < 10; ++i) {
            double d0 = world.random.nextGaussian() * 0.02D;
            double d1 = world.random.nextGaussian() * 0.02D;
            double d2 = world.random.nextGaussian() * 0.02D;
            world.addParticle(ParticleTypes.ANGRY_VILLAGER, pos.getX() + world.random.nextDouble(), pos.getY() + world.random.nextDouble() * 1.0D, pos.getZ() + world.random.nextDouble(), d0, d1, d2);
        }
    }
}
