package satisfy.dragonflame.entity.fire_dragon.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;
  /*
public class FireDragonAi {

    protected static final ImmutableList<SensorType<? extends Sensor<? super FireDragon>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ImmutableList.of(
            MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.PATH, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_VISIBLE_NEMESIS
    );

    public static void onInitialize(FireDragon dragon){
        //AttackRangeTask.rememberCoolDown(entity.getBrain());
    }

    protected static Brain<FireDragon> makeBrain(FireDragon dragon, Brain<FireDragon> brain) {
        FireDragonAi.initCoreActivitys(brain);
        FireDragonAi.initIdleActivitys(brain, dragon);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        /*
        addCoreActivities(captain, brain);
        addIdleActivities(captain, brain);
        addFightActivities(captain, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();


        return brain;
    }



    private static void initCoreActivitys(Brain<FireDragon> brain) {
        brain.addActivity(Activity.CORE, 10, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    private static void initIdleActivitys(Brain<FireDragon> brain, FireDragon dragon) {
        brain.addActivity(Activity.IDLE, 10, ImmutableList.of(createIdleMovementBehaviors(dragon)));
    }

    private static RunOne<FireDragon> createIdleMovementBehaviors(FireDragon dragon) {
        return new RunOne<>(ImmutableList.of(Pair.of(RandomStroll.stroll(dragon.getSpeedSpecial()), 2), Pair.of(RandomStroll.fly(dragon.getSpeedSpecial()), 2)));
    }


    protected static void updateActivity(FireDragon dragon) {
        Brain<FireDragon> brain = dragon.getBrain();
        Activity activity = brain.getActiveNonCoreActivity().orElse(null);
        //dragonflame.LOGGER.error(activity == null ? null : activity.getName());

        brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
        Activity activity2 = brain.getActiveNonCoreActivity().orElse(null);
        if (activity != activity2) {
            playActivitySound(dragon);
        }
        dragon.setAggressive(brain.hasMemoryValue(MemoryModuleType.ATTACK_TARGET));


    }


    protected static void maybePlayActivitySound(FireDragon dragon) {
        if ((double)dragon.level().random.nextFloat() < 0.0125) {
            playActivitySound(dragon);
        }
    }

    private static void playActivitySound(FireDragon dragon) {
        dragon.getBrain().getActiveNonCoreActivity().ifPresent(activity -> {
            if (activity == Activity.FIGHT) {

            }
        });
    }
}
   */