package satisfy.dragonflame.client;

import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import satisfy.dragonflame.entity.fire_dragon.FireDragon;

public interface ClientUtil {
    static boolean shouldCancelRender(LivingEntity living) {
        if (living.getVehicle() instanceof FireDragon) {
            return DragonflameClient.dragonRiders.contains(living.getUUID()) || living == Minecraft.getInstance().player && Minecraft.getInstance().options.getCameraType().isFirstPerson();
        }
        return false;
    }

    static void dragonCamera(Camera camera){
        Player player = Minecraft.getInstance().player;
        if (player != null && player.getVehicle() instanceof FireDragon dragon) {
            if (Minecraft.getInstance().options.getCameraType() == CameraType.THIRD_PERSON_BACK || Minecraft.getInstance().options.getCameraType() == CameraType.THIRD_PERSON_FRONT) {
                float scale = dragon.getScale();
                camera.move(-camera.getMaxZoom(scale * 7.5F), scale / 1.25, 0);
            }
        }
    }
}
