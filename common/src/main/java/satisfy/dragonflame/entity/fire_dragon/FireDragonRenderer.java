package satisfy.dragonflame.entity.fire_dragon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import satisfy.dragonflame.client.render.RenderPlayerLayer;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireDragonRenderer extends GeoEntityRenderer<FireDragon> {
    public FireDragonRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FireDragonModel());
        this.shadowRadius = 0.3f;
        this.addRenderLayer(new RenderPlayerLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, FireDragon animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        withScale(animatable.getScale());
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void renderCubesOfBone(PoseStack poseStack, GeoBone bone, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(!this.animatable.isSaddled() && bone.getName().equals("saddle")){
            return;
        }
        super.renderCubesOfBone(poseStack, bone, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    protected float getDeathMaxRotation(FireDragon animatable) {
        return 0;
    }
}
