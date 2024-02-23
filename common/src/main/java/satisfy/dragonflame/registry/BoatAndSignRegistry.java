package satisfy.dragonflame.registry;


import de.cristelknight.doapi.DoApiExpectPlatform;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import de.cristelknight.doapi.terraform.boat.item.TerraformBoatItemHelper;
import de.cristelknight.doapi.terraform.sign.TerraformSignHelper;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import satisfy.dragonflame.util.DragonflameIdentifier;

public class BoatAndSignRegistry {

    public static ResourceLocation DRAGON_BOAT_TYPE = new DragonflameIdentifier("dragon");

    public static final ResourceLocation DRAGON_SIGN_TEXTURE = new DragonflameIdentifier("entity/signs/dragon");
    public static final RegistrySupplier<Block> DRAGON_SIGN = ObjectRegistry.registerWithoutItem("dragon_sign", () -> TerraformSignHelper.getSign(DRAGON_SIGN_TEXTURE));
    public static final RegistrySupplier<Block> DRAGON_WALL_SIGN = ObjectRegistry.registerWithoutItem("dragon_wall_sign", () -> TerraformSignHelper.getWallSign(DRAGON_SIGN_TEXTURE));
    public static final RegistrySupplier<Item> DRAGON_SIGN_ITEM = ObjectRegistry.registerItem("dragon_sign", () -> new SignItem(ObjectRegistry.getSettings().stacksTo(16), DRAGON_SIGN.get(), DRAGON_WALL_SIGN.get()));
    public static final ResourceLocation DRAGON_HANGING_SIGN_TEXTURE = new DragonflameIdentifier("entity/signs/hanging/dragon");
    public static final ResourceLocation DRAGON_HANGING_SIGN_GUI_TEXTURE = new DragonflameIdentifier("textures/gui/hanging_signs/dragon");

    public static final RegistrySupplier<Block> DRAGON_HANGING_SIGN = ObjectRegistry.registerWithoutItem("dragon_hanging_sign", () -> TerraformSignHelper.getHangingSign(DRAGON_HANGING_SIGN_TEXTURE, DRAGON_HANGING_SIGN_GUI_TEXTURE));
    public static final RegistrySupplier<Block> DRAGON_WALL_HANGING_SIGN = ObjectRegistry.registerWithoutItem("dragon_wall_hanging_sign", () -> TerraformSignHelper.getWallHangingSign(DRAGON_HANGING_SIGN_TEXTURE, DRAGON_HANGING_SIGN_GUI_TEXTURE));
    public static final RegistrySupplier<Item> DRAGON_HANGING_SIGN_ITEM = ObjectRegistry.registerItem("dragon_hanging_sign", () -> new HangingSignItem(DRAGON_HANGING_SIGN.get(), DRAGON_WALL_HANGING_SIGN.get(), ObjectRegistry.getSettings().stacksTo(16)));

    public static RegistrySupplier<Item> DRAGON_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "dragon_boat", DRAGON_BOAT_TYPE, false);
    public static RegistrySupplier<Item> DRAGON_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS, "dragon_chest_boat", DRAGON_BOAT_TYPE, true);
    
    
    public static void init() {
        DoApiExpectPlatform.registerBoatType(DRAGON_BOAT_TYPE, new TerraformBoatType.Builder().item(DRAGON_BOAT).chestItem(DRAGON_CHEST_BOAT).build());
    }
}
