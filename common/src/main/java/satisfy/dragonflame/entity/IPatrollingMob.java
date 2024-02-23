package satisfy.dragonflame.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.LevelAccessor;

public interface IPatrollingMob {
    boolean isPatrolling();

    void setPatrolling(boolean bl);

    boolean hasPatrolTarget();

    BlockPos getPatrolTarget();

    void setPatrolTarget(BlockPos blockPos);

    void findPatrolTarget();

    void setPatrolLeader(boolean bl);

    boolean isPatrolLeader();

    default boolean canBeLeader() {
        return true;
    }

    default boolean canJoinPatrol() {
        return true;
    }

    static <T extends Mob & IPatrollingMob> boolean checkPatrolSpawnRules(EntityType<T> entityType, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return levelAccessor.getDifficulty() != Difficulty.PEACEFUL && Mob.checkMobSpawnRules(entityType, levelAccessor, mobSpawnType, blockPos, randomSource);
    }
}
