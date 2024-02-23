package satisfy.dragonflame.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    public static final ConfigScreenFactory<?> screen = FabricLoader.getInstance().isModLoaded("cloth-config2") ? ClothConfigScreen::create : parent -> null;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen;
    }
}
