package satisfy.dragonflame.forge.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import org.lwjgl.glfw.GLFW;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.client.DragonflameClient;

@Mod.EventBusSubscriber(modid = Dragonflame.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DragonflameClientForge {
    private static boolean initialized = false;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        DragonflameClient.onInitializeClient();
        MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    @SubscribeEvent
    public static void preClientSetup(RegisterEvent event) {
        if(!initialized){
            DragonflameClient.preInitClient();
            initialized = true;
        }
    }

    // Keybindings

    public static final Lazy<KeyMapping> EXAMPLE_MAPPING = Lazy.of(() -> new KeyMapping(
            "key.dragonflame.down", // The translation key of the keybinding's name
            InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_Z, // The keycode of the key
            "category.dragonflame.dragon" // The translation key of the keybinding's category.
    ));



    @SubscribeEvent
    public void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(EXAMPLE_MAPPING.get());
    }
}
