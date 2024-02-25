package satisfy.dragonflame.block;


import de.cristelknight.doapi.common.block.FacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;
import satisfy.dragonflame.util.DragonflameUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * //TODO
 * Function as follows: Baby dragon eats, growth timer is reduced by 4%. No fixed time.
 */
public class DragonfodderBlock extends FacingBlock {

    public DragonfodderBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
        if (entity instanceof FireDragon) {
            FireDragon dragon = (FireDragon) entity;
          //  if (dragon.isBaby()) {
          //      int decreaseAmount = (int) (dragon.getGrowingAge() * 0.04);
          //      dragon.addGrowth(-decreaseAmount);
        }
    }


    private static final Supplier<VoxelShape> voxelShapeSupplier = () -> {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.5, 0, 0.375, 0.9375, 0.4375, 0.9375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.1875, 0, 0.3125, 0.4375, 0.4375, 0.5625), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.25, 0.4375, 0.375, 0.375, 0.8125, 0.5), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.375, 0, 0.0625, 0.875, 0.0625, 0.3125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.125, 0, 0.125, 0.3125, 0.1875, 0.3125), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.15625, 0.4375, 0.71875, 0.28125, 0.8125, 0.84375), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0.09375, 0, 0.65625, 0.34375, 0.4375, 0.90625), BooleanOp.OR);

        return shape;
    };

    public static final Map<Direction, VoxelShape> SHAPE = net.minecraft.Util.make(new HashMap<>(), map -> {
        for (Direction direction : Direction.Plane.HORIZONTAL.stream().toList()) {
            map.put(direction, DragonflameUtil.rotateShape(Direction.NORTH, direction, voxelShapeSupplier.get()));
        }
    });

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE.get(state.getValue(FACING));
    }
}