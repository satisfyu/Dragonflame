package satisfy.dragonflame.client.model;

import de.cristelknight.doapi.DoApiRL;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;


public class DragonInner<T extends LivingEntity> extends HumanoidModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DoApiRL("dragon_inner"), "main");

    public DragonInner(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 80).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.21F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(24, 80).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        PartDefinition left_leg_armor_bottom_r1 = right_leg.addOrReplaceChild("left_leg_armor_bottom_r1", CubeListBuilder.create().texOffs(50, 80).addBox(-4.9F, -11.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.21F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0436F));
        PartDefinition right_leg_armor_r1 = right_leg.addOrReplaceChild("right_leg_armor_r1", CubeListBuilder.create().texOffs(40, 80).addBox(-5.9F, -13.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.22F)), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(24, 80).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.2F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));
        PartDefinition left_leg_armor_bottom_r2 = left_leg.addOrReplaceChild("left_leg_armor_bottom_r2", CubeListBuilder.create().texOffs(50, 80).mirror().addBox(3.9F, -11.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.21F)).mirror(false), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, -0.0436F));
        PartDefinition left_leg_armor_r1 = left_leg.addOrReplaceChild("left_leg_armor_r1", CubeListBuilder.create().texOffs(40, 80).mirror().addBox(4.9F, -13.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.22F)).mirror(false), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        return LayerDefinition.create(meshdefinition, 96, 96);
    }
}