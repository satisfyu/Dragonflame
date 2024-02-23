package satisfy.dragonflame.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.util.DragonflameIdentifier;

public class DragonHelmet<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DragonflameIdentifier("helmet"), "main");

	private final ModelPart helmet;
	public DragonHelmet(ModelPart root) {
		this.helmet = root.getChild("helmet");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition helmet = partdefinition.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(88, 50).addBox(-5.0F, -13.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(68, 58).addBox(-7.0F, -14.0F, 4.0F, 14.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(64, 0).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false).texOffs(64, 27).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition helmet_protection_r1 = helmet.addOrReplaceChild("helmet_protection_r1", CubeListBuilder.create().texOffs(64, 16).mirror().addBox(-4.0F, -2.0F, -4.0F, 8.0F, 3.0F, 8.5F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));
		PartDefinition horn_left_1_r1 = helmet.addOrReplaceChild("horn_left_1_r1", CubeListBuilder.create().texOffs(80, 43).addBox(1.0F, -9.0F, -3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition shoulder_spike_r1 = helmet.addOrReplaceChild("shoulder_spike_r1", CubeListBuilder.create().texOffs(41, 64).addBox(6.5F, -4.5F, 0.0F, 10.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.8F, -22.5F, 0.0F, 0.0F, 3.1416F, -1.5708F));

		return LayerDefinition.create(meshdefinition, 96, 96);
	}
	@Override
	public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void renderToBuffer(@NotNull PoseStack matrices, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		helmet.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}