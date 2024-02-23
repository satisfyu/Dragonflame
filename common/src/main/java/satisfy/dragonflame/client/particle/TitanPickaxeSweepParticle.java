package satisfy.dragonflame.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class TitanPickaxeSweepParticle extends TextureSheetParticle {

    private final SpriteSet spriteWithAge;

    protected TitanPickaxeSweepParticle(ClientLevel clientLevel, double x, double y, double z, double scale, SpriteSet spriteWithAge) {
        super(clientLevel, x, y, z);
        this.spriteWithAge = spriteWithAge;
        this.lifetime = 4;
        this.quadSize = 1.0F - (float)scale * 0.5F;
        this.setSpriteFromAge(spriteWithAge);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    protected int getLightColor(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.spriteWithAge);
        }
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new TitanPickaxeSweepParticle(world, x, y, z, velocityX, this.spriteSet);
        }

        public SpriteSet spriteSet() {
            return this.spriteSet;
        }
    }
}
