package satisfy.dragonflame.mixin;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfy.dragonflame.client.ClientUtil;

@Mixin(Camera.class)
public class CameraMixin {

    @Inject(method = "setup", at = @At(value = "RETURN"))
    private void onCameraSetup(BlockGetter blockGetter, Entity entity, boolean bl, boolean bl2, float f, CallbackInfo ci) {
        Camera camera = (Camera) (Object) this;
        ClientUtil.dragonCamera(camera);
    }
}
