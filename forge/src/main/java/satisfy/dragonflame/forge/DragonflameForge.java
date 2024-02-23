package satisfy.dragonflame.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import satisfy.dragonflame.Dragonflame;

@Mod(Dragonflame.MOD_ID)
public class DragonflameForge {
    public DragonflameForge() {
        EventBuses.registerModEventBus(Dragonflame.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Dragonflame.init();
        DragonflameProperties.init();
    }
}
