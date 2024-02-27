package satisfy.dragonflame.client.render;
/*
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import satisfy.dragonflame.client.DragonflameClient;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class RenderPlayerLayer extends GeoRenderLayer<FireDragon> {

    public RenderPlayerLayer(GeoRenderer<FireDragon> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void renderForBone(PoseStack poseStack, FireDragon dragon, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        super.renderForBone(poseStack, dragon, bone, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        LivingEntity livingEntity = dragon.getControllingPassenger();
        if(livingEntity != null && bone.getName().equals("saddle")){
            DragonflameClient.dragonRiders.remove(livingEntity.getUUID());
            poseStack.pushPose();
            preparePose(poseStack, dragon, livingEntity, partialTick);
            renderEntity(livingEntity, partialTick, poseStack, bufferSource, packedLight);
            poseStack.popPose();
            DragonflameClient.dragonRiders.add(livingEntity.getUUID());

            bufferSource.getBuffer(renderType); //Forge fix
        }
    }



    public void preparePose(PoseStack poseStack, FireDragon dragon, LivingEntity rider, float partialTick){
        float dragonScale = dragon.getScale(); // only works if dragon scale == 1
        poseStack.translate(0, dragonScale * 3.35f, -dragonScale * 1.5);
        float riderRot = rider.yRotO + (rider.getYRot() - rider.yRotO) * partialTick;

        poseStack.mulPose(Axis.YP.rotationDegrees(riderRot + 180));

        poseStack.scale(1 / dragonScale, 1 / dragonScale, 1 / dragonScale); //scale player to real size

    }

    public <E extends Entity> void renderEntity(E entityIn, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render = null;
        try {
            render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entityIn);
            try {
                render.render(entityIn, 0, partialTicks, matrixStack, bufferIn, packedLight);
            } catch (Throwable throwable1) {
                throw new ReportedException(CrashReport.forThrowable(throwable1, "Rendering entity in world"));
            }

        } catch (Throwable throwable3) {
            CrashReport crashreport = CrashReport.forThrowable(throwable3, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Entity being rendered");
            entityIn.fillCrashReportCategory(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.addCategory("Renderer details");
            crashreportcategory1.setDetail("Assigned renderer", render);

            crashreportcategory1.setDetail("Location", CrashReportCategory.formatLocation(entityIn.level, x, y, z));
            crashreportcategory1.setDetail("Rotation", yaw);

            crashreportcategory1.setDetail("Delta", partialTicks);
            throw new ReportedException(crashreport);
        }
    }

}

*/
