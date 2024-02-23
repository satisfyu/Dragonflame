package satisfy.dragonflame.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import satisfy.dragonflame.util.DragonflameIdentifier;

public class TitanHelmet<T extends Entity> extends EntityModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DragonflameIdentifier("titan_helmet"), "main");

	private final ModelPart titan_helmet;
	public TitanHelmet(ModelPart root) {
		this.titan_helmet = root.getChild("titan_helmet");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition titan_helmet = partdefinition.addOrReplaceChild("titan_helmet", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.2F)).mirror(false)
				.texOffs(0, 48).mirror().addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.1F)).mirror(false)
				.texOffs(32, 0).mirror().addBox(-1.0F, 15.0F, -4.5F, 2.0F, 3.0F, 9.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = titan_helmet.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(52, 0).addBox(-1.4603F, -0.2608F, -0.6893F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-5.1605F, 16.9504F, -2.4868F, 0.5672F, 0.0F, -1.3963F));

		PartDefinition cube_r2 = titan_helmet.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(56, 9).addBox(-1.0397F, -2.4544F, -0.3544F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-5.1605F, 16.9504F, -2.4868F, 0.9599F, 0.0F, -1.3963F));

		PartDefinition cube_r3 = titan_helmet.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(60, 14).addBox(-0.5397F, -3.2352F, 1.2788F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-5.1605F, 16.9504F, -2.4868F, 1.5272F, 0.0F, -1.3963F));

		PartDefinition cube_r4 = titan_helmet.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(60, 14).mirror().addBox(-0.5397F, -3.2352F, 1.2788F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(5.0395F, 16.8504F, -2.3868F, 1.5272F, 0.0F, 1.3963F));

		PartDefinition cube_r5 = titan_helmet.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(56, 9).mirror().addBox(-1.0397F, -2.4544F, -0.3544F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(5.0395F, 16.8504F, -2.3868F, 0.9599F, 0.0F, 1.3963F));

		PartDefinition cube_r6 = titan_helmet.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(52, 0).mirror().addBox(-1.4603F, -0.2608F, -0.6893F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.1F)).mirror(false), PartPose.offsetAndRotation(5.0395F, 16.8504F, -2.3868F, 0.5672F, 0.0F, 1.3963F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		titan_helmet.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}