package satisfy.dragonflame.entity.fire_dragon.ai.upgrade;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.level.pathfinder.Path;
import net.tslat.smartbrainlib.util.BrainUtils;

public class MoveToWalkTarget<E extends PathfinderMob> extends net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget<E> {

    public MoveToWalkTarget() {
        super();
    }


    @Override
    protected void tick(E entity) {
        Path path = entity.getNavigation().getPath();
        Brain<?> brain = entity.getBrain();

        if (this.path != path) {
            this.path = path;

            BrainUtils.setMemory(brain, MemoryModuleType.PATH, path);
        }

        if (path != null && this.lastTargetPos != null) {
            WalkTarget walkTarget = BrainUtils.getMemory(brain, MemoryModuleType.WALK_TARGET);

            if (walkTarget.getTarget().currentBlockPosition().distSqr(this.lastTargetPos) > 7.5 && attemptNewPath(entity, walkTarget, hasReachedTarget(entity, walkTarget))) {
                this.lastTargetPos = walkTarget.getTarget().currentBlockPosition();

                startOnNewPath(entity);
            }
        }
    }
}