package satisfy.dragonflame.entity.fire_dragon.ai;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;

import java.util.List;

public class JumpFromGround<E extends FireDragon> extends ExtendedBehaviour<E> {

    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED), Pair.of(MemoryModuleType.PATH, MemoryStatus.VALUE_ABSENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_PRESENT));

    public double y;

    public JumpFromGround() {
        runFor(entity -> entity.getRandom().nextInt(100) + 150);
        cooldownFor(entity -> entity.getRandom().nextIntBetweenInclusive(400, 600));
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        Brain<?> brain = entity.getBrain();
        WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);

        return walkTarget != null && entity.level().canSeeSky(entity.blockPosition()) && !hasReachedTarget(entity, walkTarget) && !entity.isFlying() && walkTarget.getTarget().currentPosition().distanceTo(entity.position()) > 40;
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return y < y + 15 && entity.level().canSeeSky(entity.blockPosition());
    }

    @Override
    protected void stop(E entity) {
        Dragonflame.LOGGER.error("Stop");
    }

    @Override
    protected void start(E entity) {
        y = entity.getY();
        Brain<?> brain = entity.getBrain();

        WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);


        BrainUtils.clearMemory(entity, MemoryModuleType.WALK_TARGET);

        float speed = entity.isFlying() ? 1 : (float) entity.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
        BrainUtils.setMemory(brain, MemoryModuleType.WALK_TARGET, new WalkTarget(walkTarget.getTarget().currentBlockPosition(), speed, 0));

        Dragonflame.LOGGER.error("Start");
    }

    @Override
    protected void tick(E entity) {
        if(entity.isFlying()){
            entity.getJumpControl().jump();
            return;
        }
        entity.jumpFromGround();
    }


    protected boolean hasReachedTarget(E entity, WalkTarget target) {
        return target.getTarget().currentBlockPosition().distManhattan(entity.blockPosition()) <= target.getCloseEnoughDist();
    }

}