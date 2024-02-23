package satisfy.dragonflame.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.cristelknight.doapi.config.jankson.config.CommentedConfig;
import net.minecraft.Util;

import java.util.HashMap;

public record DragonflameConfig(boolean simplifiedArmorSetTooltip, boolean simplifiedArmorSetBonusTooltip)
        implements CommentedConfig<DragonflameConfig> {

    private static DragonflameConfig INSTANCE = null;

    public static final DragonflameConfig DEFAULT = new DragonflameConfig(true, true);

    public static final Codec<DragonflameConfig> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    Codec.BOOL.fieldOf("simplified_armorset_tooltip").orElse(DEFAULT.simplifiedArmorSetTooltip).forGetter(c -> c.simplifiedArmorSetTooltip),
                    Codec.BOOL.fieldOf("simplified_armorset_bonus_tooltip").orElse(DEFAULT.simplifiedArmorSetBonusTooltip).forGetter(c -> c.simplifiedArmorSetBonusTooltip)
            ).apply(builder, DragonflameConfig::new)
    );

    @Override
    public HashMap<String, String> getComments() {
        return Util.make(new HashMap<>(), map -> {
            map.put("simplified_armorset_tooltip", """
                    Whether the armor piece's tooltip should list all the items in the set without
                    having to press [ALT] to show. Works with all armors added to Dragonflame""");
            map.put("simplified_armorset_bonus_tooltip", """
                    Similar to above, whether the armor piece's tooltip should display the bonus
                    effects for having a full armor set without having to press [SHIFT] to show""");
        });
    }

    @Override
    public String getHeader() {
        return """
               Dragonflame Config
               
               ===========
               Discord: https://discord.gg/Vqu6wYZwdZ
               Modrinth: InDev version, not published
               CurseForge: InDev version, not published""";
    }

    @Override
    public String getSubPath() {
        return "dragonflame/config";
    }

    @Override
    public DragonflameConfig getInstance() {
        return INSTANCE;
    }

    @Override
    public DragonflameConfig getDefault() {
        return DEFAULT;
    }

    @Override
    public Codec<DragonflameConfig> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isSorted() {
        return false;
    }

    @Override
    public void setInstance(DragonflameConfig instance) {
        INSTANCE = instance;
    }
}