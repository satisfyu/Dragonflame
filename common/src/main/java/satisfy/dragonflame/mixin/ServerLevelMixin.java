package satisfy.dragonflame.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfy.dragonflame.world.worldgen.PatrolSpawner;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Unique
    final
    CustomSpawner dragonflame$patrolSpawner = new PatrolSpawner();

    @Inject(method = "tickCustomSpawners", at = @At("TAIL"))
    public void tickPatrolSpawner(boolean bl, boolean bl2, CallbackInfo ci) {
        dragonflame$patrolSpawner.tick((ServerLevel) (Object) this, bl, bl2);
    }
}
