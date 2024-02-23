package satisfy.dragonflame.fabric;

import net.fabricmc.api.ModInitializer;
import satisfy.dragonflame.Dragonflame;

public class DragonflameFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Dragonflame.init();
        DragonflameProperties.init();

    }
}
