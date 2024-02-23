package satisfy.dragonflame.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import satisfy.dragonflame.entity.LootChestEntity;
import satisfy.dragonflame.util.DragonflameIdentifier;

public class LootChestRenderer implements BlockEntityRenderer<LootChestEntity> {
    private static final ResourceLocation TEXTURE = new DragonflameIdentifier("textures/entity/lootchest.png");
    private final ModelPart bottom;
    private final ModelPart lid;

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DragonflameIdentifier("basket"), "main");

    public LootChestRenderer(BlockEntityRendererProvider.Context context) {
        ModelPart modelPart = context.bakeLayer(LAYER_LOCATION);
        this.bottom = modelPart.getChild("bottom");
        this.lid = modelPart.getChild("lid");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bottom = partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -8.0F, -8.0F, 26.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition lid = partdefinition.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 24).addBox(-14.0F, -3.75F, -17.0F, 28.0F, 4.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-13.0F, -8.75F, -16.0F, 26.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(80, 28).addBox(-3.0F, -10.75F, -16.0F, 6.0F, 10.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(112, 11).addBox(-3.0F, -10.75F, -18.0F, 6.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-14.0F, -11.75F, -3.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(10.0F, -11.75F, -3.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-14.0F, -11.75F, -17.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(10.0F, -11.75F, -17.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.75F, 8.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void render(LootChestEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.5D, 0.5D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        float lidAngle = blockEntity.getOpenNess(partialTick);
        lid.xRot = -(lidAngle * (70.0F * ((float) Math.PI / 180F)));

        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        bottom.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        lid.render(poseStack, vertexConsumer, packedLight, packedOverlay);

        poseStack.popPose();
    }

    public ModelPart getLid() {
        return this.lid;
    }

    public ModelPart getBottom() {
        return this.bottom;
    }
}