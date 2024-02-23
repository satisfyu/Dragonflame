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

public class HardenedTitanHelmet<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DragonflameIdentifier("hardened_titan_helmet"), "main");

	private final ModelPart hardened_titan_helmet;
	public HardenedTitanHelmet(ModelPart root) {
		this.hardened_titan_helmet = root.getChild("hardened_titan_helmet");

	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition hardened_titan_helmet = partdefinition.addOrReplaceChild("hardened_titan_helmet", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false).texOffs(0, 0).mirror().addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false).texOffs(34, 0).mirror().addBox(-1.0F, -9.0F, -3.0F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.2F)).mirror(false).texOffs(58, 8).mirror().addBox(-1.0F, -10.0F, -4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.21F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition decor_right_r1 = hardened_titan_helmet.addOrReplaceChild("decor_right_r1", CubeListBuilder.create().texOffs(34, 2).addBox(-5.0F, -8.0F, 0.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6109F, 0.0F));
		PartDefinition decor_right_r2 = hardened_titan_helmet.addOrReplaceChild("decor_right_r2", CubeListBuilder.create().texOffs(34, 2).mirror().addBox(5.0F, -8.0F, 0.0F, 0.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6109F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
	public void setupAnim(@NotNull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void renderToBuffer(@NotNull PoseStack matrices, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		hardened_titan_helmet.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}