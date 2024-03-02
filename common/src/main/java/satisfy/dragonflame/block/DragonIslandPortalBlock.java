package satisfy.dragonflame.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import satisfy.dragonflame.registry.ObjectRegistry;
import satisfy.dragonflame.world.dimension.DragonIslandDimension;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"deprecation", "overrides"})
public class DragonIslandPortalBlock extends Block {
    public static final BooleanProperty ESSENCE = BooleanProperty.create("essence");
    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1, 13/16.0, 1);

    public DragonIslandPortalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ESSENCE, false));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ESSENCE);
    }

    //TODO: Block actually directly teleports the player into the new dimension
    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        boolean hasEssence = state.getValue(ESSENCE);

        if (!world.isClientSide) {
            if (itemStack.getItem() == ObjectRegistry.ESSENCE_OF_FIRE.get()) {
                if (!hasEssence) {
                    world.setBlock(pos, state.setValue(ESSENCE, true), 3);
                    displayLavaParticles(world, pos);
                    world.playSound(null, pos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    return InteractionResult.CONSUME;
                }
                return InteractionResult.PASS;
            } else if (hasEssence) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (serverPlayer.level().dimension() == Level.OVERWORLD) {
                        serverPlayer.changeDimension(serverPlayer.server.getLevel(DragonIslandDimension.DRAGONISLAND));
                    } else if (serverPlayer.level().dimension() == DragonIslandDimension.DRAGONISLAND) {
                        serverPlayer.changeDimension(serverPlayer.server.getLevel(Level.OVERWORLD));
                    }
                    world.setBlock(pos, state.setValue(ESSENCE, false), 3);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }


    private void displayLavaParticles(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.05);
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
}
