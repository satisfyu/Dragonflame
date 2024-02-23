package satisfy.dragonflame.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.client.model.ArmoredPillagerDogModel;
import satisfy.dragonflame.entity.ArmoredPillagerDog;

public class PillagerDogAttackGoal extends MeleeAttackGoal {
    private final ArmoredPillagerDog entity;
    private int ticksSinceLastAttack = 0;
    private boolean attacked = false;

    public PillagerDogAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        entity = ((ArmoredPillagerDog) pMob);
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity pEnemy, double pDistToEnemySqr) {
        double d = this.getAttackReachSqr(pEnemy);
        if (pDistToEnemySqr <= d && isTimeToAttack()) {
            this.entity.setState(ArmoredPillagerDog.State.ATTACK);
            this.attacked = true;
            this.ticksSinceLastAttack = 0;
        }
        super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
    }

    @Override
    public void tick() {
        super.tick();
        if (attacked)
            this.ticksSinceLastAttack++;
        if (this.ticksSinceLastAttack >= ArmoredPillagerDogModel.bite.lengthInSeconds() * 20) {
            this.attacked = false;
            this.ticksSinceLastAttack = 0;
            if (this.entity.getState() == ArmoredPillagerDog.State.ATTACK)
                this.entity.setState(ArmoredPillagerDog.State.IDLE);
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.attacked = false;
        if (this.entity.getState() == ArmoredPillagerDog.State.ATTACK)
            this.entity.setState(ArmoredPillagerDog.State.IDLE);
    }

    @Override
    protected int getAttackInterval() {
        return 40;
    }
}