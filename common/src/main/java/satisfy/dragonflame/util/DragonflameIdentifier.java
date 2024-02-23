package satisfy.dragonflame.util;

import net.minecraft.resources.ResourceLocation;
import satisfy.dragonflame.Dragonflame;

public class DragonflameIdentifier extends ResourceLocation {

    public DragonflameIdentifier(String path) {
        super(Dragonflame.MOD_ID, path);
    }

    public static String asString(String path) {
        return (Dragonflame.MOD_ID + ":" + path);
    }
}
