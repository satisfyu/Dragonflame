package satisfy.dragonflame.entity.fire_dragon.ai;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.behavior.Behavior;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;

public class FloatTask extends Behavior<FireDragon> {
    public FloatTask() {
        super(ImmutableMap.of());
    }

    @Override
    protected void tick(ServerLevel serverLevel, FireDragon dragon, long l) {
        if (dragon.getRandom().nextFloat() < 0.8f) {
            dragon.getJumpControl().jump();
        }
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverLevel, FireDragon dragon) {
        return dragon.isInWater() && dragon.getFluidHeight(FluidTags.WATER) > dragon.getFluidJumpThreshold() || dragon.isInLava();
    }
}
