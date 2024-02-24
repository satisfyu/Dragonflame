package satisfy.dragonflame.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegisterEvent;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.client.DragonflameClient;

@Mod.EventBusSubscriber(modid = Dragonflame.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DoApiClientEvents {
    private static boolean initialized = false;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        DragonflameClient.onInitializeClient();
    }

    @SubscribeEvent
    public static void preClientSetup(RegisterEvent event) {
        if(!initialized){
            DragonflameClient.preInitClient();
            initialized = true;
        }
    }
}
