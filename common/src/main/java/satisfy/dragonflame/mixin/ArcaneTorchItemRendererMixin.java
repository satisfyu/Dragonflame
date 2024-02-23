package satisfy.dragonflame.mixin;

import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemRenderer.class)
public abstract class ArcaneTorchItemRendererMixin {
    @Shadow
    public abstract ItemModelShaper getItemModelShaper();

    /*@Unique
    VFXBuilders.WorldVFXBuilder builder = new CustomWorldVFXBuilder().setPosColorTexLightmapDefaultFormat();

    @Inject(method = "Lnet/minecraft/client/renderer/entity/ItemRenderer;render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V", at = @At("HEAD"))
    public void renderItem(
            ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci
    ) {
        if (stack.getItem() instanceof ArcaneTorchItem) {
            matrices.pushPose();

            boolean delayRender = false;

            float scale = .25f;
            if (renderMode == ItemDisplayContext.GROUND) {
                scale = .13f;
                matrices.translate(0, .13, .0);
            } else if (renderMode == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
                matrices.mulPose(Axis.XP.rotationDegrees(-45f));
                matrices.mulPose(Axis.YP.rotationDegrees(-90f));
                matrices.translate(.2, .10, -(stack.is(ObjectRegistry.ARCANE_TORCH) ? .06 : .1));
                scale = .17f;
            } else if (renderMode == ItemDisplayContext.FIRST_PERSON_LEFT_HAND) {
                matrices.mulPose(Axis.XP.rotationDegrees(-45f));
                matrices.mulPose(Axis.YP.rotationDegrees(90f));
                matrices.translate(-.2, .10, -(stack.is(ObjectRegistry.ARCANE_TORCH) ? .06 : .1));
                scale = .17f;
            } else if (renderMode == ItemDisplayContext.FIXED) {
                matrices.mulPose(Axis.YP.rotationDegrees(-180f));
                matrices.translate(0, -.03, -.05);
                if (stack.is(ObjectRegistry.ARCANE_TORCH))
                    delayRender = true;
                scale = .28f;
            } else if (renderMode == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || renderMode == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
                scale = .14f;
                matrices.translate(0, .2, .07);
            }
            float time = ((float) (Minecraft.getInstance().level.getGameTime() % 2400000L) + Minecraft.getInstance().getFrameTime());

            matrices.scale(1f, 1, 0.01f);
            matrices.scale(scale, scale, scale);

            matrices.mulPose(Axis.XP.rotationDegrees(stack.is(ObjectRegistry.ARCANE_TORCH) ? 90f : 15f));
            matrices.mulPose(Axis.YP.rotationDegrees(time));
            // TODO: You should be able to use this, this is how Astronomical does it, though that's a sphere not a flame
            AstronomicalClient.renderAstralObject(matrices, vertexConsumers, this.builder, stack, 20, time, delayRender);
            matrices.popPose();
        }
    }*/

    /*

    // TODO: Find out how to get the item coordinates since this doesn't account for swimming animation, climing animation, and other shit
    @Inject(method = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", at = @At("HEAD"))
    protected void renderTorchParticlesSpecial(LivingEntity player, ItemStack itemStack, ItemDisplayContext itemDisplayContext, boolean bl, PoseStack poseStack, MultiBufferSource multiBufferSource, Level level, int i, int j, int k, CallbackInfo ci) {
        if (itemStack.is(ObjectRegistry.ARCANE_TORCH) && !(itemDisplayContext.equals(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) || itemDisplayContext.equals(ItemDisplayContext.FIRST_PERSON_LEFT_HAND))) {
            // itemDisplayContext.equals(ItemDisplayContext.GROUND)

            Color firstColor = new Color(ArcaneTorchItem.getFirstColor(itemStack));
            Color secondColor = new Color(ArcaneTorchItem.getSecondColor(itemStack));


            float playerBodyRotation = player.yBodyRot;
            if (Math.abs(playerBodyRotation) != playerBodyRotation) {
                // player rotation is negative, subtract from 360
                playerBodyRotation = 360 - Math.abs(playerBodyRotation);
                if (player.getItemInHand(InteractionHand.MAIN_HAND).equals(itemStack)) {
                    // Default starts at left hand, so we need to reflect
                    playerBodyRotation = (playerBodyRotation + 180 >= 360 ? playerBodyRotation - 180 : playerBodyRotation + 180);
                }
            }
            double bodyRotationInRad = Math.toRadians(playerBodyRotation); // AND ALL THE TIME I COULD JUST USE THIS 😭

            firstColor = ColorHelper.darker(firstColor, 1);
            secondColor = secondColor == null ? firstColor : ColorHelper.brighter(secondColor, 1);
            double x = player.getX() + (Math.cos(bodyRotationInRad) * (player.getBbWidth() - 0.2f));
            double y = player.getY() + (player.getBbHeight() / 2);
            double z = player.getZ() + (Math.sin(bodyRotationInRad) * (player.getBbWidth() - 0.2f)); // Don't subtract, because offset torch "head"
            int lifeTime = 14 + level.random.nextInt(4);
            float scale = 0.07f + level.random.nextFloat() * 0.03f;
            float velocity = 0.04f + level.random.nextFloat() * 0.02f;

            WorldParticleBuilder.create(LodestoneParticles.WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(scale, 0).build())
                    .setLifetime(lifeTime)
                    .setColorData(ColorParticleData.create(firstColor, secondColor)
                            .setCoefficient(0.8f)
                            .setEasing(Easing.CIRC_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.8f, 0.5f).build())
                    .setSpinData(SpinParticleData.create(0, 0.4f)
                            .setSpinOffset((level.getGameTime() * 0.2f) % 6.28f)
                            .setEasing(Easing.QUARTIC_IN).build())
                    .addMotion(0, velocity, 0)
                    .enableNoClip()
                    .spawn(level, x, y, z);

            WorldParticleBuilder.create(LodestoneParticles.SPARKLE_PARTICLE)
                    .setScaleData(GenericParticleData.create(scale * 2, 0).build())
                    .setLifetime(lifeTime)
                    .setColorData(ColorParticleData.create(firstColor, secondColor)
                            .setCoefficient(1.5f)
                            .setEasing(Easing.CIRC_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.2f)
                            .setCoefficient(1.5f).build())
                    .setSpinData(SpinParticleData.create(0, 2)
                            .setEasing(Easing.QUARTIC_IN).build())
                    .addMotion(0, velocity, 0)
                    .enableNoClip()
                    .spawn(level, x, y, z);
        }
    }
    */

}
