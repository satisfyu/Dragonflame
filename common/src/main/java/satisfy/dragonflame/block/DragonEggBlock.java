package satisfy.dragonflame.block;

import com.google.common.collect.Maps;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;
import satisfy.dragonflame.registry.EntityRegistry;

import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class DragonEggBlock extends Block {
    private final Block hostBlock;
    private static final Map<Block, Block> BLOCK_BY_HOST_BLOCK = Maps.newIdentityHashMap();
    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(3.5, 0, 3.5, 12.5, 2.0, 12.5),
            Block.box(2.0, 2.0, 2.0, 14.0, 11.0, 14.0),
            Block.box(3.5, 11.0, 3.5, 12.5, 14.0, 12.5),
            Block.box(5.0, 14.0, 5.0, 11.0, 16, 11.0)
    );

    public DragonEggBlock(Block block, BlockBehaviour.Properties properties) {
        super(properties.destroyTime(block.defaultDestroyTime() / 2.0F).explosionResistance(0.75F));
        this.hostBlock = block;
        BLOCK_BY_HOST_BLOCK.put(block, this);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    public Block getHostBlock() {
        return this.hostBlock;
    }

    public static boolean isCompatibleHostBlock(BlockState blockState) {
        return BLOCK_BY_HOST_BLOCK.containsKey(blockState.getBlock());
    }

    private void spawnDragon(ServerLevel serverLevel, BlockPos blockPos) {
        FireDragon fireDragon = new FireDragon(EntityRegistry.FIREDRAGON.get(), serverLevel); //TODO - change to whelpling
        fireDragon.moveTo((double) blockPos.getX() + 0.5, blockPos.getY(), (double) blockPos.getZ() + 0.5, 0.0F, 0.0F);
        serverLevel.addFreshEntity(fireDragon);
    }

    @Override
    public void fallOn(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float fallDistance) {
        super.fallOn(level, blockState, blockPos, entity, fallDistance);

        if (!level.isClientSide && entity instanceof LivingEntity) {
            level.destroyBlock(blockPos, false);
            spawnDragon((ServerLevel)level, blockPos);
            level.playSound(null, blockPos, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.BLOCKS, 1.0F, 1.0F);

            ServerLevel serverLevel = (ServerLevel) level;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos checkPos = blockPos.offset(dx, 0, dz);
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState),
                            checkPos.getX() + 0.5, checkPos.getY() + 1, checkPos.getZ() + 0.5,
                            20, 0.5, 0.5, 0.5, 0.2);
                }
            }
        }
    }




    @Override
    public void spawnAfterBreak(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull ItemStack itemStack, boolean bl) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);

        spawnDragon(serverLevel, blockPos);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level world, BlockPos pos, RandomSource random) {
        double d = (double) pos.getX() + 0.5;
        double e = pos.getY() + 0.7;
        double f = (double) pos.getZ() + 0.5;
        if (random.nextDouble() < 0.3) {
            world.playLocalSound(d, e, f, SoundEvents.SMOKER_SMOKE, SoundSource.BLOCKS, 1.0f, 0.2f, false);
        }
        double sidewaysMotion = random.nextDouble() * 0.2 - 0.1;
        double upwardMotion = random.nextDouble() * 0.3;
        double forwardMotion = random.nextDouble() * 0.2 - 0.1;

        world.addParticle(ParticleTypes.ASH, d + sidewaysMotion, e + upwardMotion, f + forwardMotion, 0.0, 0.0, 0.0);
    }



    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        tooltip.add(Component.translatable("tooltip.dragonflame.unobtainable").withStyle(ChatFormatting.ITALIC, ChatFormatting.DARK_RED));
    }
}
