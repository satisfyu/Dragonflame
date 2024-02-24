package satisfy.dragonflame.client.particle;

/*
public class HearthstoneParticleEffect {
    private int ticksSinceInit;
    private double basePlayerRotation;
    private double originalPlayerHeight;
    private double processedPlayerYLevel;

    public HearthstoneParticleEffect(Player player) {
        this.ticksSinceInit = 1;
        this.basePlayerRotation = player.getYRot();
        this.originalPlayerHeight = player.getBbHeight();
        this.processedPlayerYLevel = player.getY();
    }

    public void resetTicks() {
        this.ticksSinceInit = 1;
    }

    public void particleTick(Level world, Player player) {
        int lifeTime = 16;
        double particleDistanceFromPlayer = 0.9;
        double allowedYDeviation = 1.9;
        int particleDensity = 14;
        double particleSpeedMultiplier = 1.08;
        // particle speed multiplier, bear in mind particles will appear segmented the higher this is in comparison with particleDensity.

        double interpolatedParticleSpeed = ((float) this.ticksSinceInit / HearthstoneItem.USAGE_TICKS) * (particleSpeedMultiplier * 1000);
        if (player.getY() != this.processedPlayerYLevel) {
            if (player.hasEffect(MobEffects.JUMP)) {
                allowedYDeviation = allowedYDeviation + ((double) Objects.requireNonNull(player.getEffect(MobEffects.JUMP)).getAmplifier() / 2);
            }
            boolean isInAir = world.getBlockState(player.blockPosition().below(Mth.ceil(allowedYDeviation))).getBlock() == Blocks.AIR;
            if (Math.abs(player.getY() - this.processedPlayerYLevel) > allowedYDeviation || isInAir) {
                this.processedPlayerYLevel = player.getY();
            }
        } // Player jump not do particle jump but creative flight do
        double deltaY = this.processedPlayerYLevel + (this.originalPlayerHeight * (float) this.ticksSinceInit / HearthstoneItem.USAGE_TICKS);

        for (int i = 0; i <= particleDensity; i++) {
            double particleStripeY = (((double) i /particleDensity) * (this.originalPlayerHeight / HearthstoneItem.USAGE_TICKS)) + deltaY;

            double blueParticleX = player.getX() + (Mth.cos((float) Math.toRadians((this.basePlayerRotation -5) + i + interpolatedParticleSpeed)) * particleDistanceFromPlayer);
            double blueParticleZ = player.getZ() + (Mth.sin((float) Math.toRadians((this.basePlayerRotation -5) + i + interpolatedParticleSpeed)) * particleDistanceFromPlayer);
            WorldParticleBuilder.create(ParticleRegistry.CHROMA_WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.07f, 0).setEasing(Easing.LINEAR).build())
                    .setLifetime(lifeTime)
                    .setColorData(ColorParticleData.create(new Color(0x344d9e), new Color(0x1d2553))
                            .setEasing(Easing.CIRC_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.8f, 0.5f).build())
                    .enableNoClip()
                    .spawn(world, blueParticleX, particleStripeY , blueParticleZ);

            double grayParticleX = player.getX() + (Mth.cos((float) Math.toRadians((this.basePlayerRotation -5) + i + interpolatedParticleSpeed + 180)) * particleDistanceFromPlayer);
            double grayParticleZ = player.getZ() + (Mth.sin((float) Math.toRadians((this.basePlayerRotation -5) + i + interpolatedParticleSpeed + 180)) * particleDistanceFromPlayer);
            WorldParticleBuilder.create(ParticleRegistry.CHROMA_WISP_PARTICLE)
                    .setScaleData(GenericParticleData.create(0.07f, 0).setEasing(Easing.LINEAR).build())
                    .setLifetime(lifeTime)
                    .setColorData(ColorParticleData.create(new Color(0x6B6868), new Color(0x2E2C37))
                            .setEasing(Easing.CIRC_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0.8f, 0.5f).build())
                    .enableNoClip()
                    .spawn(world, grayParticleX, particleStripeY , grayParticleZ);
        }

        this.ticksSinceInit++;
    }
}

*/
