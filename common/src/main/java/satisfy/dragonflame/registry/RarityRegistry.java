package satisfy.dragonflame.registry;


import net.minecraft.ChatFormatting;
import satisfy.dragonflame.util.DragonflameIdentifier;
import satisfy.dragonflame.util.CustomRarity;
import satisfy.dragonflame.util.IRarity;

public interface RarityRegistry {
    IRarity DRAGON = CustomRarity.create(new DragonflameIdentifier("dragon"), ChatFormatting.BLUE);
    IRarity LEGENDARY = CustomRarity.create(new DragonflameIdentifier("bob"), ChatFormatting.DARK_PURPLE);
    IRarity MYTHIC = CustomRarity.create(new DragonflameIdentifier("bob"), ChatFormatting.DARK_RED);
}
