package satisfy.dragonflame.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.entity.*;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;
import satisfy.dragonflame.util.DragonflameIdentifier;

import java.util.function.Supplier;

public class EntityRegistry {
    private static final Registrar<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Dragonflame.MOD_ID, Registries.ENTITY_TYPE).getRegistrar();
    public static final RegistrySupplier<EntityType<ArmoredVindicator>> ARMORED_VINDICATOR = create("armored_vindicator",  () -> EntityType.Builder.of(ArmoredVindicator::new, MobCategory.CREATURE).sized(0.9f, 1.87f).clientTrackingRange(10).updateInterval(3).build(new DragonflameIdentifier("armored_vindicator").toString()));
    public static final RegistrySupplier<EntityType<ArmoredPillager>> ARMORED_PILLAGER = create("armored_pillager",  () -> EntityType.Builder.of(ArmoredPillager::new, MobCategory.CREATURE).sized(0.9f, 1.87f).clientTrackingRange(10).updateInterval(3).build(new DragonflameIdentifier("armored_pillager_dog").toString()));
    public static final RegistrySupplier<EntityType<ArmoredPillagerDog>> ARMORED_PILLAGER_DOG = create("armored_pillager_dog",  () -> EntityType.Builder.of(ArmoredPillagerDog::new, MobCategory.CREATURE).sized(0.9f, 1.87f).clientTrackingRange(10).updateInterval(3).build(new DragonflameIdentifier("armored_pillager_dog").toString()));
    public static final RegistrySupplier<EntityType<FieryWarhorse>> FIERY_WARHORSE = create("fiery_warhorse",  () -> EntityType.Builder.of(FieryWarhorse::new, MobCategory.CREATURE).sized(0.9f, 1.87f).clientTrackingRange(10).updateInterval(3).build(new DragonflameIdentifier("fiery_warhorse").toString()));

    public static final RegistrySupplier<EntityType<FireDragon>> FIREDRAGON = create("firedragon",  () -> EntityType.Builder.of(FireDragon::new, MobCategory.CREATURE).sized(2.75f, 2.75f).clientTrackingRange(10).updateInterval(3).build(new DragonflameIdentifier("firedragon").toString()));

    public static final RegistrySupplier<EntityType<DragonWhelpling>> DRAGON_WHELPLING = create("dragon_whelpling",  () -> EntityType.Builder.of(DragonWhelpling::new, MobCategory.CREATURE).sized(2.75f, 2.75f).clientTrackingRange(10).updateInterval(3).build(new DragonflameIdentifier("dragon_whelpling").toString()));

    public static <T extends EntityType<?>> RegistrySupplier<T> create(final String path, final Supplier<T> type) {
        return ENTITY_TYPES.register(new DragonflameIdentifier(path), type);
    }



    public static void init() {
        Dragonflame.LOGGER.debug("Registering Mod Entities for " + Dragonflame.MOD_ID);
        registerAttributes();
    }


    static void registerAttributes(){
        EntityAttributeRegistry.register(ARMORED_VINDICATOR, ArmoredVindicator::createAttributes);
        EntityAttributeRegistry.register(FIREDRAGON, FireDragon::createAttributes);
        EntityAttributeRegistry.register(ARMORED_PILLAGER, ArmoredPillager::createAttributes);
        EntityAttributeRegistry.register(FIERY_WARHORSE, FieryWarhorse::createAttributes);
        EntityAttributeRegistry.register(ARMORED_PILLAGER_DOG, ArmoredPillagerDog::createAttributes);
        EntityAttributeRegistry.register(DRAGON_WHELPLING, DragonWhelpling::createAttributes);
    }
}
