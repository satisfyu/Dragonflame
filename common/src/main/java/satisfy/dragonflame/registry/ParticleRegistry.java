package satisfy.dragonflame.registry;
/*
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import satisfy.dragonflame.client.particle.arcane_torch.ArcaneFlameParticleType;
import satisfy.dragonflame.client.particle.arcane_torch.ArcaneWallParticleEmitter;
import satisfy.dragonflame.client.particle.flamethrower.FlameParticleType;
import satisfy.dragonflame.util.DragonflameIdentifier;
import satisfy.dragonflame.client.particle.TitanPickaxeSweepParticle;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import team.lodestar.lodestone.systems.rendering.particle.type.LodestoneParticleType;

import java.util.function.BiConsumer;


public interface ParticleRegistry {

    ArcaneFlameParticleType ARCANE_FLAME_PARTICLE = new ArcaneFlameParticleType();
    FlameParticleType FLAMETHROWER_JET = new FlameParticleType();

    SimpleParticleType TITAN_PICKAXE_SWIPE_01 = FabricParticleTypes.simple(true);
    SimpleParticleType TITAN_PICKAXE_SWIPE_02 = FabricParticleTypes.simple(true);
    SimpleParticleType TITAN_PICKAXE_SWIPE_03 = FabricParticleTypes.simple(true);

    LodestoneParticleType CHROMA_WISP_PARTICLE = new LodestoneParticleType();

    static void init() {
        initParticles(bind(BuiltInRegistries.PARTICLE_TYPE));
    }

    static void registerFactories() {
        ParticleFactoryRegistry.getInstance().register(ARCANE_FLAME_PARTICLE, LodestoneParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(FLAMETHROWER_JET, FlameParticleType.Factory::new);
        ParticleFactoryRegistry.getInstance().register(TITAN_PICKAXE_SWIPE_01, TitanPickaxeSweepParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(TITAN_PICKAXE_SWIPE_02, TitanPickaxeSweepParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(TITAN_PICKAXE_SWIPE_03, TitanPickaxeSweepParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(CHROMA_WISP_PARTICLE, LodestoneParticleType.Factory::new);
    }

    private static void initParticles(BiConsumer<ParticleType<?>, ResourceLocation> registry) {
        registry.accept(ARCANE_FLAME_PARTICLE, new DragonflameIdentifier("arcane_flame"));
        ParticleEmitterHandler.registerItemParticleEmitter(ObjectRegistry.ARCANE_TORCH, ArcaneWallParticleEmitter::particleTick);
        registry.accept(FLAMETHROWER_JET, new DragonflameIdentifier("flamethrower_jet"));
        registry.accept(TITAN_PICKAXE_SWIPE_01, new DragonflameIdentifier("titan_pickaxe_blockaura_swipe_01"));
        registry.accept(TITAN_PICKAXE_SWIPE_02, new DragonflameIdentifier("titan_pickaxe_blockaura_swipe_02"));
        registry.accept(TITAN_PICKAXE_SWIPE_03, new DragonflameIdentifier("titan_pickaxe_blockaura_swipe_03"));
        registry.accept(CHROMA_WISP_PARTICLE, new DragonflameIdentifier("chroma_wisp"));
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
 */
