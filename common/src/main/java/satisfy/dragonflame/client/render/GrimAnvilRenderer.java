package satisfy.dragonflame.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import satisfy.dragonflame.block.GrimAnvilBlock;
import satisfy.dragonflame.entity.GrimAnvilBlockEntity;
import satisfy.dragonflame.util.GeneralUtil;

public class GrimAnvilRenderer implements BlockEntityRenderer<GrimAnvilBlockEntity> {
    private int time = 0;
    private double timeAccumulator = 0.0;
    public GrimAnvilRenderer(BlockEntityRendererProvider.Context ctx) {

    }

    @Override
    public void render(@NotNull GrimAnvilBlockEntity blockEntity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int overlay) {
        if (!blockEntity.hasLevel()) {
            return;
        }
        BlockState selfState = blockEntity.getBlockState();
        if (selfState.getBlock() instanceof GrimAnvilBlock) {
            ItemStack oreStack = blockEntity.getOre();
            if (oreStack.isEmpty())
                return;
            poseStack.pushPose();
            poseStack.translate(0.0f, 1.2F, 0.0f);
            poseStack.scale(0.5F, 0.5F, 0.5F);

            poseStack.translate(0, 0.2 * Math.sin(((2 * Math.PI) / 180) * this.time), 0);

            poseStack.mulPose(new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F).fromAxisAngleDeg(new Vector3f(0.0F, 1.0F, 0.0F), (float) this.time));

            GeneralUtil.renderItem(oreStack, poseStack, multiBufferSource, blockEntity);
            poseStack.popPose();
        }

        this.timeAccumulator += 0.05;
        this.time = (int) this.timeAccumulator % 360;
    }
}