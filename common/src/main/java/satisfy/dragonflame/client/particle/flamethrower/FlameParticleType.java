package satisfy.dragonflame.client.particle.flamethrower;
/*
import com.mojang.serialization.Codec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.particle.FabricSpriteProviderImpl;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import team.lodestar.lodestone.systems.rendering.particle.world.WorldParticleEffect;

@Environment(EnvType.CLIENT)
public class FlameParticleType extends ParticleType<WorldParticleEffect> {

    public FlameParticleType() {
        super(false, WorldParticleEffect.DESERIALIZER);
    }

    @Override
    public boolean getOverrideLimiter() {
        return true;
    }

    @Override
    public Codec<WorldParticleEffect> codec() {
        return WorldParticleEffect.codecFor(this);
    }

    public record Factory(SpriteSet sprite) implements ParticleProvider<WorldParticleEffect> {
        @Override
        public Particle createParticle(WorldParticleEffect data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            return new FlameParticle(world, data, (FabricSpriteProviderImpl) sprite, x, y, z, mx, my, mz);
        }
    }
}

 */
