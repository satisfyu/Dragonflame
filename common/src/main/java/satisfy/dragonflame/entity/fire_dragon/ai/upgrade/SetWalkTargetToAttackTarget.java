package satisfy.dragonflame.entity.fire_dragon.ai.upgrade;

import satisfy.dragonflame.entity.fire_dragon.FireDragon;

public class SetWalkTargetToAttackTarget<E extends FireDragon> extends net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget<E> {

    @Override
    protected void start(E entity) {
        //speedModifier = entity.isFlying() ? 1 : (float) entity.getAttributeBaseValue(Attributes.MOVEMENT_SPEED);
        super.start(entity);
    }
}
