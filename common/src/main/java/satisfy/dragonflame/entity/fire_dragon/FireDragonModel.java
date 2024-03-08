package satisfy.dragonflame.entity.fire_dragon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import satisfy.dragonflame.util.DragonflameIdentifier;


public class FireDragonModel<T extends FireDragon> extends EntityModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new DragonflameIdentifier("firedragon"), "main");
    private final ModelPart root;

    public FireDragonModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -16.0F, 23.0F));

        PartDefinition Tail = Root.addOrReplaceChild("Tail", CubeListBuilder.create().texOffs(0, 157).addBox(-11.992F, -9.6947F, 0.768F, 24.0F, 17.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.008F, -10.3053F, -0.768F));

        PartDefinition bone10 = Tail.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(-1, 234).addBox(-9.042F, -6.2903F, -1.8548F, 18.0F, 12.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0499F, -1.4044F, 26.6228F));

        PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(179, 391).addBox(-6.8829F, -4.2158F, -0.2526F, 13.0F, 8.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1591F, -1.0745F, 24.3978F));

        PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(193, 426).addBox(-3.5533F, -2.586F, 0.0918F, 7.0F, 5.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3296F, -0.6299F, 26.6556F));

        PartDefinition bone13 = bone12.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(-1, 88).addBox(-6.5533F, -0.4903F, -1.8471F, 13.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.0956F, 25.939F));

        PartDefinition cube_r1 = bone13.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-1, 88).addBox(-5.0F, -0.7735F, 124.0F, 13.0F, 0.0F, 35.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4467F, 1.2832F, -125.8471F, 0.0F, 0.0F, -1.5708F));

        PartDefinition LowerBody = Root.addOrReplaceChild("LowerBody", CubeListBuilder.create().texOffs(-1, 346).addBox(-14.8281F, -12.1056F, -35.3874F, 30.0F, 23.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1719F, -10.8944F, -0.6126F));

        PartDefinition UpperBody = LowerBody.addOrReplaceChild("UpperBody", CubeListBuilder.create().texOffs(330, 329).addBox(-17.1896F, -14.2355F, -32.6943F, 34.0F, 26.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.offset(0.3614F, 1.1299F, -35.6931F));

        PartDefinition Head = UpperBody.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(328, 98).addBox(0.2058F, -32.2998F, -17.6161F, 0.0F, 24.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(66, 121).addBox(0.2058F, -32.2998F, -24.6161F, 0.0F, 24.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(46, 432).addBox(0.2058F, -8.2998F, -22.6161F, 0.0F, 30.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3953F, 0.0643F, -33.0782F));

        PartDefinition cube_r2 = Head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(101, 183).addBox(-2.0F, -8.4735F, -52.0F, 2.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2058F, -6.8263F, 46.3839F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r3 = Head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-1, 234).addBox(-2.0F, -9.4735F, -65.0F, 2.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2058F, -6.8263F, 46.3839F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r4 = Head.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(203, 426).addBox(-1.0F, -16.7735F, -66.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2058F, -2.5263F, 46.3839F, -0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r5 = Head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(193, 426).addBox(-1.0F, -21.7735F, -52.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2058F, -2.5263F, 46.3839F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r6 = Head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(-1, -1).addBox(-11.0F, -9.7735F, -70.0F, 22.0F, 20.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2058F, -2.5263F, 46.3839F, 0.0436F, 0.0F, 0.0F));

        PartDefinition Neck = Head.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(75, 82).addBox(0.2605F, -31.6004F, -8.7479F, 0.0F, 24.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(64, 31).addBox(0.2605F, -31.6004F, -21.7479F, 0.0F, 24.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0547F, -0.6995F, -21.8682F));

        PartDefinition cube_r7 = Neck.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(303, 416).addBox(-1.0F, -16.7735F, -79.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2605F, -1.8269F, 68.2521F, -0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r8 = Neck.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(-1, 43).addBox(-10.0F, -3.7735F, -91.0F, 20.0F, 21.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2605F, -1.8269F, 68.2521F, -0.0873F, 0.0F, 0.0F));

        PartDefinition bone22 = Neck.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(-1, 409).addBox(0.6008F, -19.3058F, -10.7122F, 0.0F, 30.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.3403F, 11.7054F, -13.0357F));

        PartDefinition cube_r9 = bone22.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(74, 156).addBox(-2.0F, -19.4735F, -92.0F, 2.0F, 18.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(102, 248).addBox(-2.0F, -13.4735F, -79.0F, 2.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6008F, -17.8323F, 81.2878F, 0.2618F, 0.0F, 0.0F));

        PartDefinition Neck2 = Neck.addOrReplaceChild("Neck2", CubeListBuilder.create().texOffs(0, 32).addBox(0.1623F, -27.3078F, -11.7982F, 0.0F, 24.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(78, 122).addBox(0.1623F, -27.3078F, -16.7982F, 0.0F, 24.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(319, 359).addBox(-1.8377F, -2.3078F, -27.7982F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 94).addBox(0.1623F, -3.3078F, -31.7982F, 0.0F, 30.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0982F, -4.2925F, -21.9497F));

        PartDefinition cube_r10 = Neck2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(9, 234).addBox(-2.0F, -24.4735F, -105.0F, 2.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1623F, -1.8343F, 90.2018F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r11 = Neck2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(345, 427).addBox(-1.0F, 0.2265F, -92.0F, 2.0F, 12.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(87, 251).addBox(-1.0F, -3.7735F, -105.0F, 2.0F, 16.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(378, 259).addBox(-9.0F, 10.2265F, -108.0F, 18.0F, 21.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1623F, 2.4657F, 90.2018F, -0.2618F, 0.0F, 0.0F));

        PartDefinition bone7 = Neck2.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(399, 388).addBox(-11.0023F, -10.56F, -17.7807F, 21.0F, 15.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(195, 390).addBox(-1.0023F, -20.56F, -8.7807F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(431, 438).addBox(-8.0023F, -5.56F, -34.7807F, 15.0F, 7.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(429, 95).addBox(8.9977F, 4.44F, -14.7807F, 0.0F, 4.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(61, 82).addBox(-0.0023F, -21.56F, -6.7807F, 0.0F, 24.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1646F, -5.7479F, -17.0175F));

        PartDefinition cube_r12 = bone7.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(-1, 259).addBox(-56.0F, -7.7735F, -103.0F, 0.0F, 7.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(336, 435).addBox(-57.0F, -10.7735F, -103.0F, 3.0F, 3.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0023F, 8.2135F, 107.2193F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r13 = bone7.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(38, 132).addBox(-57.0F, 15.2265F, -102.0F, 3.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0023F, 8.2135F, 107.2193F, -0.2182F, -0.4363F, 0.0F));

        PartDefinition cube_r14 = bone7.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(371, 388).addBox(-30.0F, -7.1735F, -111.4F, 17.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0023F, 5.6135F, 99.6193F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r15 = bone7.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(53, 259).addBox(56.0F, -7.7735F, -103.0F, 0.0F, 7.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(234, 443).addBox(54.0F, -10.7735F, -103.0F, 3.0F, 3.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0023F, 8.2135F, 107.2193F, 0.0F, 0.4363F, 0.0F));

        PartDefinition cube_r16 = bone7.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(267, 443).addBox(54.0F, 15.2265F, -102.0F, 3.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0023F, 8.2135F, 107.2193F, -0.2182F, 0.4363F, 0.0F));

        PartDefinition cube_r17 = bone7.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(315, 346).addBox(15.0F, -6.1735F, -111.4F, 17.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0023F, 5.6135F, 99.6193F, 0.0F, 0.0F, -0.7854F));

        PartDefinition bone21 = bone7.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(22, 91).addBox(-2.068F, -2.6307F, -1.0414F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0657F, -5.9293F, -33.7392F));

        PartDefinition bone14 = bone7.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(92, 455).addBox(-6.0496F, -2.6746F, -19.5447F, 11.0F, 5.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0473F, 3.1146F, -14.236F));

        PartDefinition Wings = UpperBody.addOrReplaceChild("Wings", CubeListBuilder.create(), PartPose.offset(-0.1896F, 27.0198F, 13.3057F));

        PartDefinition LWing = Wings.addOrReplaceChild("LWing", CubeListBuilder.create(), PartPose.offset(-7.7413F, -37.6504F, -41.2609F));

        PartDefinition cube_r18 = LWing.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(251, 116).addBox(-63.0F, -4.7735F, -78.0F, 51.0F, 0.0F, 116.0F, new CubeDeformation(0.0F))
                .texOffs(-1, 217).addBox(-58.0F, -7.7735F, -46.0F, 46.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(467, 410).addBox(-68.0F, -8.7735F, -47.0F, 10.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.7413F, 8.1686F, 41.2609F, 0.0057F, -0.0433F, 0.4798F));

        PartDefinition bone5 = LWing.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-44.4733F, -23.5392F, -5.0783F));

        PartDefinition cube_r19 = bone5.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(95, 363).addBox(-142.0F, -7.7735F, 6.0F, 67.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(52.2146F, 31.7078F, 46.3393F, 0.0077F, -0.7414F, 0.4749F));

        PartDefinition cube_r20 = bone5.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(126, 184).addBox(-126.0F, -4.7735F, -80.0F, 63.0F, 0.0F, 115.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(52.2146F, 31.7078F, 46.3393F, 0.0057F, -0.0433F, 0.4798F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(-44.7403F, -24.9639F, -41.5058F));

        PartDefinition cube_r21 = bone6.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(327, 156).addBox(-107.0F, -63.7735F, -87.0F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(96.9549F, 56.6717F, 87.845F, 0.0852F, -0.0212F, 0.0043F));

        PartDefinition cube_r22 = bone6.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(193, 298).addBox(-110.0F, -66.7735F, -139.0F, 88.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(96.9549F, 56.6717F, 87.845F, 0.1131F, 0.7173F, 0.0807F));

        PartDefinition cube_r23 = bone6.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(181, 329).addBox(-24.0F, -66.7735F, -167.0F, 82.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 92).addBox(-25.0F, -61.7735F, -158.0F, 118.0F, 0.0F, 92.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(96.9549F, 56.6717F, 87.845F, 2.6316F, 1.3957F, 2.6443F));

        PartDefinition cube_r24 = bone6.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(375, 315).addBox(40.0F, -69.7735F, -116.0F, 43.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(96.9549F, 56.6717F, 87.845F, 3.01F, 0.9319F, 3.0017F));

        PartDefinition cube_r25 = bone6.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(187, 315).addBox(-53.0F, -69.7735F, -132.0F, 88.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(96.9549F, 56.6717F, 87.845F, 0.2144F, 1.1942F, 0.1658F));

        PartDefinition cube_r26 = bone6.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(366, 245).addBox(12.0F, -70.7735F, -109.0F, 64.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(96.9549F, 56.6717F, 87.845F, 0.7992F, 1.4807F, 0.7412F));

        PartDefinition cube_r27 = bone6.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(232, 402).addBox(60.0F, -70.7735F, -49.0F, 39.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(96.9549F, 56.6717F, 87.845F, 3.0618F, 0.6286F, 3.0386F));

        PartDefinition RWing = Wings.addOrReplaceChild("RWing", CubeListBuilder.create(), PartPose.offset(7.7413F, -37.6504F, -41.2609F));

        PartDefinition cube_r28 = RWing.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(212, 0).addBox(12.0F, -4.7735F, -78.0F, 51.0F, 0.0F, 116.0F, new CubeDeformation(0.0F))
                .texOffs(-1, 200).addBox(12.0F, -7.7735F, -46.0F, 46.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(-1, 462).addBox(57.0F, -8.7735F, -47.0F, 10.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7413F, 8.1686F, 41.2609F, 0.0057F, 0.0433F, -0.4798F));

        PartDefinition bone15 = RWing.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offset(44.4733F, -23.5392F, -5.0783F));

        PartDefinition cube_r29 = bone15.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(95, 346).addBox(75.0F, -7.7735F, 6.0F, 67.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-52.2146F, 31.7078F, 46.3393F, 0.0077F, 0.7414F, -0.4749F));

        PartDefinition cube_r30 = bone15.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 184).addBox(62.0F, -4.7735F, -80.0F, 63.0F, 0.0F, 115.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-52.2146F, 31.7078F, 46.3393F, 0.0057F, 0.0433F, -0.4798F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create(), PartPose.offset(44.7403F, -24.9639F, -41.5058F));

        PartDefinition cube_r31 = bone16.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(327, 139).addBox(98.0F, -63.7735F, -87.0F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.9549F, 56.6717F, 87.845F, 0.0852F, 0.0212F, -0.0043F));

        PartDefinition cube_r32 = bone16.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(-1, 298).addBox(22.0F, -66.7735F, -139.0F, 88.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.9549F, 56.6717F, 87.845F, 0.1131F, -0.7173F, -0.0807F));

        PartDefinition cube_r33 = bone16.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(-1, 329).addBox(-58.0F, -66.7735F, -167.0F, 82.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(-1, -1).addBox(-94.0F, -61.7735F, -158.0F, 118.0F, 0.0F, 93.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.9549F, 56.6717F, 87.845F, 2.6316F, -1.3957F, -2.6443F));

        PartDefinition cube_r34 = bone16.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(-1, 272).addBox(-83.0F, -69.7735F, -116.0F, 43.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.9549F, 56.6717F, 87.845F, 3.01F, -0.9319F, -3.0017F));

        PartDefinition cube_r35 = bone16.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(-1, 315).addBox(-35.0F, -69.7735F, -132.0F, 88.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.9549F, 56.6717F, 87.845F, 0.2144F, -1.1942F, -0.1658F));

        PartDefinition cube_r36 = bone16.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(366, 231).addBox(-76.0F, -69.7735F, -109.0F, 64.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.9549F, 56.6717F, 87.845F, 0.7992F, -1.4807F, -0.7412F));

        PartDefinition cube_r37 = bone16.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(384, 298).addBox(-98.0F, -70.7735F, -49.0F, 39.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.9549F, 56.6717F, 87.845F, 3.0618F, -0.6286F, -3.0386F));

        PartDefinition LFleg = UpperBody.addOrReplaceChild("LFleg", CubeListBuilder.create().texOffs(381, 421).addBox(-8.6845F, -8.6512F, -8.9621F, 16.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-18.5051F, 0.4157F, -27.7322F));

        PartDefinition bone24 = LFleg.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offset(0.7445F, 8.8937F, -1.8808F));

        PartDefinition cube_r38 = bone24.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(429, 31).addBox(-24.0F, 37.2265F, -17.0F, 13.0F, 11.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.571F, -11.7714F, 42.9187F, -1.0036F, 0.0F, 0.0F));

        PartDefinition bone8 = bone24.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offset(-0.2606F, 11.7252F, 7.5101F));

        PartDefinition cube_r39 = bone8.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(79, -1).addBox(17.0F, 24.2265F, -17.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.8316F, -23.4966F, 35.4086F, -1.8326F, 0.0F, 3.1416F));

        PartDefinition cube_r40 = bone8.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(152, 359).addBox(-18.0F, 0.2265F, 28.0F, 0.0F, 17.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(323, 388).addBox(-22.0F, 15.2265F, 32.0F, 10.0F, 11.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.8316F, -23.4966F, 35.4086F, -1.9635F, 0.0F, 0.0F));

        PartDefinition cube_r41 = bone8.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(11, -1).addBox(17.0F, 18.2265F, -30.0F, 3.0F, 21.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.8316F, -23.4966F, 35.4086F, -1.7453F, 0.0F, 3.1416F));

        PartDefinition cube_r42 = bone8.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(291, 416).addBox(17.0F, 6.2265F, -50.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.8316F, -23.4966F, 35.4086F, -1.2217F, 0.0F, 3.1416F));

        PartDefinition bone = bone8.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(1.3194F, 22.9389F, -6.3097F));

        PartDefinition cube_r43 = bone.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(178, 396).addBox(-8.4766F, -4.4837F, 9.6279F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(344, 172).addBox(-2.4766F, -4.4837F, 9.6279F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(435, 259).addBox(-8.4766F, -4.4837F, -5.3721F, 16.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(321, 401).addBox(3.5234F, -4.4837F, 9.6279F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0112F, 0.2747F, 1.0903F, 3.1416F, 0.0F, 0.0F));

        PartDefinition RFleg = UpperBody.addOrReplaceChild("RFleg", CubeListBuilder.create().texOffs(57, 419).addBox(-7.3155F, -8.6512F, -8.9621F, 16.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(18.126F, 0.4157F, -27.7322F));

        PartDefinition bone23 = RFleg.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offset(-0.7445F, 8.8937F, -1.8808F));

        PartDefinition cube_r44 = bone23.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(429, -1).addBox(11.0F, 37.2265F, -17.0F, 13.0F, 11.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.571F, -11.7714F, 42.9187F, -1.0036F, 0.0F, 0.0F));

        PartDefinition bone19 = bone23.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offset(0.2606F, 11.7252F, 7.5101F));

        PartDefinition cube_r45 = bone19.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(67, -1).addBox(-19.0F, 24.2265F, -17.0F, 3.0F, 18.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.8316F, -23.4966F, 35.4086F, -1.8326F, 0.0F, -3.1416F));

        PartDefinition cube_r46 = bone19.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(62, 213).addBox(18.0F, 0.2265F, 28.0F, 0.0F, 17.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(103, 380).addBox(12.0F, 15.2265F, 32.0F, 10.0F, 11.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.8316F, -23.4966F, 35.4086F, -1.9635F, 0.0F, 0.0F));

        PartDefinition cube_r47 = bone19.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(-1, -1).addBox(-19.0F, 18.2265F, -30.0F, 3.0F, 21.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.8316F, -23.4966F, 35.4086F, -1.7453F, 0.0F, -3.1416F));

        PartDefinition cube_r48 = bone19.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(366, 273).addBox(-19.0F, 6.2265F, -50.0F, 3.0F, 12.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.8316F, -23.4966F, 35.4086F, -1.2217F, 0.0F, -3.1416F));

        PartDefinition bone20 = bone19.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offset(-0.3438F, 22.9389F, -6.3097F));

        PartDefinition cube_r49 = bone20.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(178, 396).addBox(-8.4766F, -4.4837F, 9.6279F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(344, 172).addBox(-2.4766F, -4.4837F, 9.6279F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(435, 259).addBox(-8.4766F, -4.4837F, -5.3721F, 16.0F, 5.0F, 15.0F, new CubeDeformation(0.0F))
                .texOffs(321, 401).addBox(3.5234F, -4.4837F, 9.6279F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0112F, 0.2747F, 1.0903F, 3.1416F, 0.0F, 0.0F));

        PartDefinition Foot = Root.addOrReplaceChild("Foot", CubeListBuilder.create(), PartPose.offset(0.0F, 17.2553F, -23.0F));

        PartDefinition LBleg = Foot.addOrReplaceChild("LBleg", CubeListBuilder.create().texOffs(295, 427).addBox(-8.4675F, -8.4512F, -8.7581F, 16.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.5325F, -26.8041F, 26.7581F));

        PartDefinition bone2 = LBleg.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0795F, 8.1937F, -1.5808F));

        PartDefinition cube_r50 = bone2.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(431, 329).addBox(-6.547F, -0.8449F, -10.3773F, 13.0F, 11.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.2F, 3.2F, -1.9635F, 0.0F, 0.0F));

        PartDefinition bone4 = bone2.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.2978F, 12.8972F, -1.7831F));

        PartDefinition cube_r51 = bone4.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(267, 359).addBox(-4.8448F, -4.9693F, -17.4349F, 10.0F, 11.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.4272F, 7.0408F, -1.0908F, 0.0F, 0.0F));

        PartDefinition bone9 = bone4.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.1987F, 23.1265F, 9.6919F));

        PartDefinition cube_r52 = bone9.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(0, 406).addBox(3.0F, -62.0735F, -26.8F, 16.0F, 5.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(61, 113).addBox(3.0F, -62.0735F, -4.8F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(367, 260).addBox(9.0F, -62.0735F, -4.8F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 347).addBox(15.0F, -62.0735F, -4.8F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0435F, -57.5951F, -19.2861F, 3.1416F, 0.0F, 0.0F));

        PartDefinition RBleg = Foot.addOrReplaceChild("RBleg", CubeListBuilder.create().texOffs(125, 419).addBox(-7.5325F, -8.4512F, -8.7581F, 16.0F, 17.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(11.5325F, -26.8041F, 26.7581F));

        PartDefinition bone3 = RBleg.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offset(-0.0795F, 8.1937F, -1.5808F));

        PartDefinition cube_r53 = bone3.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(429, 63).addBox(-6.453F, -0.8449F, -10.3773F, 13.0F, 11.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.2F, 3.2F, -1.9635F, 0.0F, 0.0F));

        PartDefinition bone17 = bone3.addOrReplaceChild("bone17", CubeListBuilder.create(), PartPose.offset(-0.2978F, 12.8972F, -1.7831F));

        PartDefinition cube_r54 = bone17.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(215, 348).addBox(-5.1552F, -4.9693F, -17.4349F, 10.0F, 11.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.4272F, 7.0408F, -1.0908F, 0.0F, 0.0F));

        PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offset(-0.1117F, 23.1265F, 9.6919F));

        PartDefinition cube_r55 = bone18.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(0, 406).addBox(3.0F, -62.0735F, -26.8F, 16.0F, 5.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(61, 113).addBox(3.0F, -62.0735F, -4.8F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(367, 260).addBox(9.0F, -62.0735F, -4.8F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 347).addBox(15.0F, -62.0735F, -4.8F, 4.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.0435F, -57.5951F, -19.2861F, 3.1416F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 1024, 1024);
    }


    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}