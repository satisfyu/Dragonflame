package satisfy.dragonflame.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import satisfy.dragonflame.entity.IPatrollingMob;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class PatrolGoal<T extends PathfinderMob & IPatrollingMob> extends Goal {
    private final T mob;
    private final double speedModifier;
    private final double leaderSpeedModifier;
    private long cooldownUntil;

    public PatrolGoal(T patrollingMonster, double d, double e) {
        this.mob = patrollingMonster;
        this.speedModifier = d;
        this.leaderSpeedModifier = e;
        this.cooldownUntil = -1L;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        boolean bl = this.mob.level().getGameTime() < this.cooldownUntil;
        return this.mob.isPatrolling() && this.mob.getTarget() == null && !this.mob.isVehicle() && this.mob.hasPatrolTarget() && !bl;
    }

    public void start() {
    }

    public void stop() {
    }

    public void tick() {
        boolean bl = this.mob.isPatrolLeader();
        PathNavigation pathNavigation = this.mob.getNavigation();
        if (pathNavigation.isDone()) {
            List<IPatrollingMob> list = this.findPatrolCompanions();
            if (this.mob.isPatrolling() && list.isEmpty()) {
                this.mob.setPatrolling(false);
            } else if (bl && this.mob.getPatrolTarget().closerToCenterThan(this.mob.position(), 10.0)) {
                this.mob.findPatrolTarget();
            } else {
                Vec3 vec3 = Vec3.atBottomCenterOf(this.mob.getPatrolTarget());
                Vec3 vec32 = this.mob.position();
                Vec3 vec33 = vec32.subtract(vec3);
                vec3 = vec33.yRot(90.0F).scale(0.4).add(vec3);
                Vec3 vec34 = vec3.subtract(vec32).normalize().scale(10.0).add(vec32);
                BlockPos blockPos = BlockPos.containing(vec34);
                blockPos = this.mob.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, blockPos);
                if (!pathNavigation.moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), bl ? this.leaderSpeedModifier : this.speedModifier)) {
                    this.moveRandomly();
                    this.cooldownUntil = this.mob.level().getGameTime() + 200L;
                } else if (bl) {
                    for (IPatrollingMob patrollingMonster : list) {
                        patrollingMonster.setPatrolTarget(blockPos);
                    }
                }
            }
        }

    }

    private List<IPatrollingMob> findPatrolCompanions() {
        List<PathfinderMob> pathfinderMobs = this.mob.level().getEntitiesOfClass(PathfinderMob.class, this.mob.getBoundingBox().inflate(16.0), (patrollingMonster) -> !patrollingMonster.is(this.mob));

        List<IPatrollingMob> patrollingMobs = new ArrayList<>();
        for (PathfinderMob pathfinderMob : pathfinderMobs) {
            if (pathfinderMob instanceof IPatrollingMob patrollingMob && patrollingMob.canJoinPatrol()) {
                patrollingMobs.add(patrollingMob);
            }
        }
        return patrollingMobs;
    }

    private void moveRandomly() {
        RandomSource randomSource = this.mob.getRandom();
        BlockPos blockPos = this.mob.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, this.mob.blockPosition().offset(-8 + randomSource.nextInt(16), 0, -8 + randomSource.nextInt(16)));
        this.mob.getNavigation().moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.speedModifier);
    }
}