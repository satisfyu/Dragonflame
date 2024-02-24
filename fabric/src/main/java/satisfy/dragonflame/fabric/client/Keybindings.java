package satisfy.dragonflame.fabric.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import satisfy.dragonflame.client.DragonflameClient;

public class Keybindings {
    private static KeyMapping keyBinding;

    public static void init(){
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.dragonflame.down", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_Z, // The keycode of the key
                "category.dragonflame.dragon" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            DragonflameClient.isYPressed = keyBinding.isDown();
        });
    }

}
