package satisfy.dragonflame.block.arcanetorch;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.BlockEntityRegistry;
import satisfy.dragonflame.registry.ParticleRegistry;
import satisfy.dragonflame.util.NbtUtil;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneParticles;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;

import java.awt.*;

public class ArcaneBlockEntity extends BlockEntity {
    public int firstColorRGB;
    public Color firstColor;
    public int secondColorRGB;
    public Color secondColor;

    public ArcaneBlockEntity(BlockPos pos, BlockState state, int firstColor, int secondColor) {
        this(BlockEntityRegistry.ARCANE_BLOCK_ENTITY, pos, state, firstColor, secondColor);
    }

    public ArcaneBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int firstColor, int secondColor) {
        super(type, pos, state);
        this.firstColorRGB = firstColor;
        this.secondColorRGB = secondColor;
        this.firstColor = new Color(firstColorRGB);
        this.secondColor = new Color(secondColorRGB);
    }

    public void clientTick(Level world, BlockPos pos, BlockState state) {
        if (firstColor == null) {
            return;
        }
        Color firstColor = ColorHelper.darker(this.firstColor, 1);
        Color secondColor = this.secondColor == null ? firstColor : ColorHelper.brighter(this.secondColor, 1);
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 0.6;
        double z = pos.getZ() + 0.5;
        int lifeTime = 14 + world.random.nextInt(4);
        float scale = 0.17f + world.random.nextFloat() * 0.03f;
        float velocity = 0.04f + world.random.nextFloat() * 0.02f;
        if (getBlockState().getBlock() instanceof ArcaneWallTorchBlock) {
            Direction direction = getBlockState().getValue(WallTorchBlock.FACING);
            x += direction.getNormal().getX() * -0.28f;
            y += 0.2f;
            z += direction.getNormal().getZ() * -0.28f;
            lifeTime -= 6;
        }

        if (getBlockState().getBlock() instanceof ArcaneGroundTorchBlock) {
            lifeTime -= 4;
        }
        /*if (getBlockState().getBlock() instanceof EtherBrazierBlock) {
            y -= 0.2f;
            lifeTime -= 2;
            scale *= 1.25f;
        }*/
        WorldParticleBuilder.create(LodestoneParticles.WISP_PARTICLE)
                .setScaleData(GenericParticleData.create(scale, 0).build())
                .setLifetime(lifeTime)
                .setColorData(ColorParticleData.create(firstColor, secondColor)
                        .setCoefficient(0.8f)
                        .setEasing(Easing.CIRC_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.8f, 0.5f).build())
                .setSpinData(SpinParticleData.create(0, 0.4f)
                        .setSpinOffset((world.getGameTime() * 0.2f) % 6.28f)
                        .setEasing(Easing.QUARTIC_IN).build())
                .addMotion(0, velocity, 0)
                .enableNoClip()
                .spawn(world, x, y, z);

        WorldParticleBuilder.create(LodestoneParticles.SPARKLE_PARTICLE)
                .setScaleData(GenericParticleData.create(scale * 2, 0).build())
                .setLifetime(lifeTime)
                .setColorData(ColorParticleData.create(firstColor, secondColor)
                        .setCoefficient(1.5f)
                        .setEasing(Easing.CIRC_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.2f)
                        .setCoefficient(1.5f).build())
                .setSpinData(SpinParticleData.create(0, 2)
                        .setEasing(Easing.QUARTIC_IN).build())
                .addMotion(0, velocity, 0)
                .enableNoClip()
                .spawn(world, x, y, z);

        if (world.getGameTime() % 2L == 0 && world.random.nextFloat() < 0.25f) {
            y += 0.15f;

            WorldParticleBuilder.create(ParticleRegistry.ARCANE_FLAME_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.75f, 0).build())
                    .setColorData(ColorParticleData.create(firstColor, secondColor)
                            .setCoefficient(2f).build())
                    .setTransparencyData(GenericParticleData.create(1f)
                            .setCoefficient(3f).build())
                    .setRandomOffset(0.15f, 0.2f)
                    .addMotion(0, 0.03f, 0)
                    .enableNoClip()
                    .spawn(world, x, y, z);

            WorldParticleBuilder.create(ParticleRegistry.ARCANE_FLAME_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.5f, 0).build())
                    .setColorData(ColorParticleData.create(firstColor, secondColor)
                            .setCoefficient(3f).build())
                    .setTransparencyData(GenericParticleData.create(1f)
                            .setCoefficient(3f).build())
                    .setRandomOffset(0.15f, 0.2f)
                    .addMotion(0, velocity, 0)
                    .enableNoClip()
                    .spawn(world, x, y, z);

        }
    }

    public ArcaneBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, new Color(107, 59, 45).getRGB(), new Color(79, 107, 45).getRGB());
    }

    @Override
    public void load(CompoundTag nbt) {
        this.firstColorRGB = nbt.getInt("FirstColor");
        this.firstColor = new Color(firstColorRGB);
        this.secondColorRGB = nbt.getInt("SecondColor");
        this.secondColor = new Color(secondColorRGB);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        nbt.putInt("FirstColor", firstColorRGB);
        nbt.putInt("SecondColor", secondColorRGB);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        firstColorRGB = NbtUtil.getOrDefaultInt(nbt -> nbt.getCompound("display").getInt("FirstColor"), new Color(107, 59, 45).getRGB(), itemStack.getTag());
        this.firstColor = new Color(firstColorRGB);
        secondColorRGB =NbtUtil.getOrDefaultInt(nbt -> nbt.getCompound("display").getInt("SecondColor"), new Color(79, 107, 45).getRGB(), itemStack.getTag());
        this.secondColor = new Color(secondColorRGB);
    }
}
