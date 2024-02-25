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

public class DragonHeadHelmet<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DragonflameIdentifier("dragon_head_helmet"), "main");

	private final ModelPart helmet;
	public DragonHeadHelmet(ModelPart root) {
		this.helmet = root.getChild("helmet");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition helmet = partdefinition.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.5F))
				.texOffs(-1, 15).addBox(-3.0F, -7.0F, -13.0F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.2F))
				.texOffs(22, 2).addBox(0.0F, -13.0F, -2.0F, 0.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(-1, -1).addBox(0.0F, -9.0F, 4.0F, 0.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition horn_right_bottom_r1 = helmet.addOrReplaceChild("horn_right_bottom_r1", CubeListBuilder.create().texOffs(-1, 27).addBox(2.0F, -7.0F, 2.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.48F, 0.0F));

		PartDefinition horn_left_bottom_r1 = helmet.addOrReplaceChild("horn_left_bottom_r1", CubeListBuilder.create().texOffs(17, 25).addBox(-4.0F, -7.0F, 2.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.48F, 0.0F));

		PartDefinition horn_right_r1 = helmet.addOrReplaceChild("horn_right_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-4.0F, -6.0F, 5.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(24, 0).addBox(2.0F, -6.0F, 6.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6545F, 0.0F, 0.0F));

		PartDefinition snout_bottom_r1 = helmet.addOrReplaceChild("snout_bottom_r1", CubeListBuilder.create().texOffs(21, 17).addBox(-3.0F, -3.0F, -11.0F, 6.0F, 2.0F, 7.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void renderToBuffer(@NotNull PoseStack matrices, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		helmet.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}