package satisfy.dragonflame.fabric.client;


import net.fabricmc.api.ClientModInitializer;
import satisfy.dragonflame.client.DragonflameClient;

public class DragonflameClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Keybindings.init();
        DragonflameClient.preInitClient();
        DragonflameClient.onInitializeClient();
    }
}
