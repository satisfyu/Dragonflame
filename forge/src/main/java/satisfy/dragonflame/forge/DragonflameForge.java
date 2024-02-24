package satisfy.dragonflame.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.client.config.ClothConfigScreen;

@Mod(Dragonflame.MOD_ID)
public class DragonflameForge {
    public DragonflameForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Dragonflame.MOD_ID, modEventBus);
        Dragonflame.init();

        modEventBus.addListener(this::commonSetup);
        if(isClothConfigLoaded()) ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ClothConfigScreen.create(parent)));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        //event.enqueueWork(VineryCompostables::registerCompostable);
        Dragonflame.commonSetup();
    }

    public static boolean isClothConfigLoaded(){
        return ModList.get().isLoaded("cloth_config");
    }
}
