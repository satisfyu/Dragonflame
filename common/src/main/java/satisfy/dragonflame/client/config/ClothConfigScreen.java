package satisfy.dragonflame.client.config;

import de.cristelknight.doapi.DoApiRL;
import de.cristelknight.doapi.config.cloth.CCUtil;
import de.cristelknight.doapi.config.cloth.LinkEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.gui.entries.TextListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.config.DragonflameConfig;
import satisfy.dragonflame.util.DragonflameIdentifier;

public class ClothConfigScreen {
    private static Screen lastScreen;

    public static Screen create(Screen parent) {
        lastScreen = parent;
        DragonflameConfig config = DragonflameConfig.DEFAULT.getConfig();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setDefaultBackgroundTexture(new DragonflameIdentifier("textures/block/burnt_planks.png"))
                .setTitle(Component.translatable( Dragonflame.MOD_ID + ".config.title").withStyle(ChatFormatting.BOLD));

        ConfigEntries entries = new ConfigEntries(builder.entryBuilder(), config, builder.getOrCreateCategory(CCUtil.categoryName("main", Dragonflame.MOD_ID)));
        builder.setSavingRunnable(() -> {
            DragonflameConfig.DEFAULT.setInstance(entries.createConfig());
            DragonflameConfig.DEFAULT.getConfig(true, true);
        });
        return builder.build();
    }


    private static class ConfigEntries {
        private final ConfigEntryBuilder builder;
        private final ConfigCategory category;
        private final BooleanListEntry simplifiedArmorSetTooltip, simplifiedArmorSetBonusTooltip;
        //private final IntegerListEntry a, b, c;



        public ConfigEntries(ConfigEntryBuilder builder, DragonflameConfig config, ConfigCategory category) {
            this.builder = builder;
            this.category = category;

            SubCategoryBuilder simplifiedTooltips = new SubCategoryBuilder(Component.empty(), Component.translatable(Dragonflame.MOD_ID + ".config.subCategory.simplified_tooltips"));

            simplifiedArmorSetTooltip = createBooleanField("simplifiedArmorSetTooltip", config.simplifiedArmorSetTooltip(), DragonflameConfig.DEFAULT.simplifiedArmorSetTooltip(), simplifiedTooltips);
            simplifiedArmorSetBonusTooltip = createBooleanField("simplifiedArmorSetBonusTooltip", config.simplifiedArmorSetBonusTooltip(), DragonflameConfig.DEFAULT.simplifiedArmorSetBonusTooltip(), simplifiedTooltips);

            category.addEntry(simplifiedTooltips.build());
            linkButtons(Dragonflame.MOD_ID, category, builder, "https://discord.gg/Vqu6wYZwdZ", lastScreen);
        }


        public DragonflameConfig createConfig() {
            return new DragonflameConfig(simplifiedArmorSetTooltip.getValue(), simplifiedArmorSetBonusTooltip.getValue());
        }


        public BooleanListEntry createBooleanField(String id, boolean value, boolean defaultValue, SubCategoryBuilder subCategoryBuilder){
            BooleanListEntry e = CCUtil.createBooleanField(Dragonflame.MOD_ID, id, value, defaultValue, builder);

            if(subCategoryBuilder == null) category.addEntry(e);
            else subCategoryBuilder.add(e);

            return e;
        }

        public IntegerListEntry createIntField(String id, int value, int defaultValue, SubCategoryBuilder subCategoryBuilder, int min, int max){
            IntegerListEntry e = CCUtil.createIntField(Dragonflame.MOD_ID, id, value, defaultValue, builder).setMaximum(max).setMinimum(min);

            if(subCategoryBuilder == null) category.addEntry(e);
            else subCategoryBuilder.add(e);

            return e;
        }
    }

    public static void linkButtons(String MOD_ID, ConfigCategory category, ConfigEntryBuilder builder, String dcLink, Screen lastScreen){
        if(lastScreen == null) lastScreen = Minecraft.getInstance().screen;

        TextListEntry tle = builder.startTextDescription(Component.literal(" ")).build();
        category.addEntry(tle);
        Screen finalLastScreen = lastScreen;
        category.addEntry(new LinkEntry(CCUtil.entryName(MOD_ID,"dc"), buttonWidget -> Minecraft.getInstance().setScreen(new ConfirmLinkScreen(confirmed -> {
            if (confirmed) {
                Util.getPlatform().openUri(dcLink);
            }
            Minecraft.getInstance().setScreen(create(finalLastScreen)); }, dcLink, true)), new DoApiRL("textures/gui/dc.png"), 3));
    }
}
