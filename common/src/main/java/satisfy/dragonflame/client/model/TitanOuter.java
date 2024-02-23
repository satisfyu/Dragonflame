package satisfy.dragonflame.client.model;

import de.cristelknight.doapi.DoApiRL;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;


public class TitanOuter<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DoApiRL( "titan"), "main");

    public TitanOuter(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body_decor = body.addOrReplaceChild("body_decor", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, 10.0F, -2.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition decor_r1 = body_decor.addOrReplaceChild("decor_r1", CubeListBuilder.create().texOffs(5, 19).addBox(-2.0F, 0.5F, -2.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(48, 48).mirror().addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.16F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition shoulder_r1 = right_arm.addOrReplaceChild("shoulder_r1", CubeListBuilder.create().texOffs(4, 2).mirror().addBox(0.1F, -1.1F, -0.5F, 1.495F, 1.0F, 7.0F, new CubeDeformation(0.09F)).mirror(false), PartPose.offsetAndRotation(-0.3F, 2.2F, -3.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition shoulder_r2 = right_arm.addOrReplaceChild("shoulder_r2", CubeListBuilder.create().texOffs(50, 34).mirror().addBox(-8.85F, -3.1F, -2.45F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(48, 32).mirror().addBox(-8.85F, -1.9F, -3.45F, 1.0F, 3.8F, 7.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(24, 37).mirror().addBox(-3.65F, 2.1F, -2.45F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F)).mirror(false)
                .texOffs(22, 35).mirror().addBox(-7.65F, 2.1F, -3.45F, 3.8F, 1.0F, 7.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-8.05F, -1.0F, 0.05F, 0.0F, -3.1416F, 0.0F));

        PartDefinition shoulder_r3 = right_arm.addOrReplaceChild("shoulder_r3", CubeListBuilder.create().texOffs(24, 40).mirror().addBox(-1.461F, -0.5F, -1.1F, 1.361F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-4.3F, 1.6F, 3.6F, 0.0F, -0.6938F, 0.0F));

        PartDefinition shoulder_r4 = right_arm.addOrReplaceChild("shoulder_r4", CubeListBuilder.create().texOffs(24, 40).mirror().addBox(-0.5F, -1.461F, -1.1F, 1.0F, 1.361F, 1.0F, new CubeDeformation(0.09F)).mirror(false), PartPose.offsetAndRotation(0.3F, -3.0F, 3.6F, 0.6946F, 0.0F, 0.0F));

        PartDefinition shoulder_r5 = right_arm.addOrReplaceChild("shoulder_r5", CubeListBuilder.create().texOffs(24, 40).mirror().addBox(-0.5F, -1.461F, 0.1F, 1.0F, 1.361F, 1.0F, new CubeDeformation(0.09F)).mirror(false), PartPose.offsetAndRotation(0.3F, -3.0F, -3.6F, -0.6946F, 0.0F, 0.0F));

        PartDefinition shoulder_r6 = right_arm.addOrReplaceChild("shoulder_r6", CubeListBuilder.create().texOffs(24, 40).mirror().addBox(-1.461F, -0.5F, 0.1F, 1.361F, 1.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(-4.3F, 1.6F, -3.6F, 0.0F, 0.6938F, 0.0F));

        PartDefinition shoulder_r7 = right_arm.addOrReplaceChild("shoulder_r7", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.8F, -0.5F, 0.0F, 0.0F, -3.1416F, 0.0F));

        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 48).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.16F)), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition shoulder_r8 = left_arm.addOrReplaceChild("shoulder_r8", CubeListBuilder.create().texOffs(4, 2).addBox(-1.595F, -1.1F, -0.5F, 1.495F, 1.0F, 7.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(0.3F, 2.2F, -3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition shoulder_r9 = left_arm.addOrReplaceChild("shoulder_r9", CubeListBuilder.create().texOffs(50, 34).addBox(7.85F, -3.1F, -2.45F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F))
                .texOffs(48, 32).addBox(7.85F, -1.9F, -3.45F, 1.0F, 3.8F, 7.0F, new CubeDeformation(0.1F))
                .texOffs(24, 37).addBox(2.65F, 2.1F, -2.45F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.1F))
                .texOffs(22, 35).addBox(3.85F, 2.1F, -3.45F, 3.8F, 1.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(8.05F, -1.0F, 0.05F, 0.0F, 3.1416F, 0.0F));

        PartDefinition shoulder_r10 = left_arm.addOrReplaceChild("shoulder_r10", CubeListBuilder.create().texOffs(24, 40).addBox(0.1F, -0.5F, -1.1F, 1.361F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(4.3F, 1.6F, 3.6F, 0.0F, 0.6938F, 0.0F));

        PartDefinition shoulder_r11 = left_arm.addOrReplaceChild("shoulder_r11", CubeListBuilder.create().texOffs(24, 40).addBox(-0.5F, -1.461F, -1.1F, 1.0F, 1.361F, 1.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(-0.3F, -3.0F, 3.6F, 0.6946F, 0.0F, 0.0F));

        PartDefinition shoulder_r12 = left_arm.addOrReplaceChild("shoulder_r12", CubeListBuilder.create().texOffs(24, 40).addBox(-0.5F, -1.461F, 0.1F, 1.0F, 1.361F, 1.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(-0.3F, -3.0F, -3.6F, -0.6946F, 0.0F, 0.0F));

        PartDefinition shoulder_r13 = left_arm.addOrReplaceChild("shoulder_r13", CubeListBuilder.create().texOffs(24, 40).addBox(0.1F, -0.5F, 0.1F, 1.361F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(4.3F, 1.6F, -3.6F, 0.0F, -0.6938F, 0.0F));

        PartDefinition shoulder_r14 = left_arm.addOrReplaceChild("shoulder_r14", CubeListBuilder.create().texOffs(0, 32).addBox(-2.5F, -2.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(40, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(40, 27).addBox(-2.2F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.3F))
                .texOffs(40, 9).addBox(-1.05F, 7.0F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(40, 9).addBox(-1.0F, 7.0F, -2.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(40, 27).mirror().addBox(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

}


