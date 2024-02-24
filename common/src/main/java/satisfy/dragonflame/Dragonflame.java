package satisfy.dragonflame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfy.dragonflame.config.DragonflameConfig;
import satisfy.dragonflame.registry.*;

public class Dragonflame {

	public static final String MOD_ID = "dragonflame";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void init() {
		DragonflameConfig.DEFAULT.getConfig();
		//ParticleRegistry.init();
		ObjectRegistry.init();
		BoatAndSignRegistry.init();
		BlockEntityRegistry.init();
		ScreenhandlerTypeRegistry.init();
		SoundEventRegistry.init();
		TabRegistry.init();
		EntityRegistry.init();
		MobEffectRegistry.init();
		EnchantmentRegistry.init();
		PlacerTypesRegistry.init();
	}

	public static void commonSetup(){
		DragonflameProperties.init();
	}
}