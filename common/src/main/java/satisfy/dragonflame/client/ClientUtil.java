package satisfy.dragonflame.client;

import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
        if (null != player && player.getVehicle() instanceof FireDragon dragon) {
            if (CameraType.THIRD_PERSON_BACK == Minecraft.getInstance().options.getCameraType() || CameraType.THIRD_PERSON_FRONT == Minecraft.getInstance().options.getCameraType()) {
                float scale = dragon.getScale();
                camera.move(-camera.getMaxZoom(scale * 7.5F), scale / 1.25, 0);
            }
        }
    }

    static void registerColorArmor(Item item, int defaultColor) {
        ColorHandlerRegistry.registerItemColors((stack, tintIndex) -> 0 < tintIndex ? -1 : getColor(stack, defaultColor), item);
    }

    static int getColor(ItemStack itemStack, int defaultColor) {
        CompoundTag displayTag = itemStack.getTagElement("display");
        if (null != displayTag && displayTag.contains("color", Tag.TAG_ANY_NUMERIC))
            return displayTag.getInt("color");
        return defaultColor;
    }
}
