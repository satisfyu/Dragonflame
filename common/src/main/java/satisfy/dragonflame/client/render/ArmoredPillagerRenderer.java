package satisfy.dragonflame.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.client.model.ArmoredPillagerModel;
import satisfy.dragonflame.entity.ArmoredPillagerEntity;


@Environment(EnvType.CLIENT)
public class ArmoredPillagerRenderer extends MobRenderer<ArmoredPillagerEntity, ArmoredPillagerModel<ArmoredPillagerEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Dragonflame.MOD_ID, "textures/entity/armored_pillager.png");

    public ArmoredPillagerRenderer(EntityRendererProvider.Context context) {
        super(context, new ArmoredPillagerModel<>(context.bakeLayer(ArmoredPillagerModel.ARMORED_PILLAGER_MODEL_LAYER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()) {
            @Override
            public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, @NotNull ArmoredPillagerEntity entity, float f, float g, float h, float j, float k, float l) {
                super.render(poseStack, multiBufferSource, i, entity, f, g, h, j, k, l);
            }
        });
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ArmoredPillagerEntity entity) {
        return TEXTURE;
    }
}
