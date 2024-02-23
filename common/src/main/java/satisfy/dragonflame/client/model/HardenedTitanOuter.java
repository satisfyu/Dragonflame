package satisfy.dragonflame.client.model;

import de.cristelknight.doapi.DoApiRL;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;


public class HardenedTitanOuter<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DoApiRL( "hardened_titan_outer"), "main");

    public HardenedTitanOuter(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition body_decor = body.addOrReplaceChild("body_decor", CubeListBuilder.create().texOffs(38, 16).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition decor_left_r1 = body_decor.addOrReplaceChild("decor_left_r1", CubeListBuilder.create().texOffs(20, 17).addBox(-4.0F, 0.5F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(20, 17).addBox(2.0F, 0.5F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(44, 26).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.16F)).mirror(false).texOffs(43, 43).mirror().addBox(-3.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.16F)).mirror(false).texOffs(43, 38).mirror().addBox(-3.0F, 6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.0F));
        PartDefinition shoulder_spike_r1 = right_arm.addOrReplaceChild("shoulder_spike_r1", CubeListBuilder.create().texOffs(44, 22).mirror().addBox(3.5F, -0.5F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(34, 32).mirror().addBox(2.5F, -1.5F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(16, 32).mirror().addBox(-1.5F, -3.5F, -4.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(24, 20).mirror().addBox(-1.5F, -2.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.8F, -0.5F, 0.0F, 0.0F, -3.1416F, 0.7854F));
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 26).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.16F)).texOffs(43, 43).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.16F)).texOffs(43, 38).addBox(-1.0F, 6.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(5.0F, 2.0F, 0.0F));
        PartDefinition shoulder_spike_r2 = left_arm.addOrReplaceChild("shoulder_spike_r2", CubeListBuilder.create().texOffs(44, 22).addBox(-8.5F, -0.5F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(34, 32).addBox(-3.5F, -1.5F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(16, 32).addBox(-3.5F, -3.5F, -4.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(24, 20).addBox(-2.5F, -2.5F, -3.0F, 4.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, -0.7854F));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.25F)).texOffs(0, 41).addBox(-2.2F, 7.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        PartDefinition right_leg_decor_top_r1 = right_leg.addOrReplaceChild("right_leg_decor_top_r1", CubeListBuilder.create().texOffs(0, 46).addBox(-3.0F, 19.5F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.9F, -12.0F, 0.0F, -0.1309F, 0.0F, 0.0F));
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.25F)).texOffs(0, 41).mirror().addBox(-2.0F, 7.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));
        PartDefinition left_leg_decor_top_r1 = left_leg.addOrReplaceChild("left_leg_decor_top_r1", CubeListBuilder.create().texOffs(0, 46).addBox(1.0F, 19.5F, 0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-1.9F, -12.0F, 0.0F, -0.1309F, 0.0F, 0.0F));
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

}


