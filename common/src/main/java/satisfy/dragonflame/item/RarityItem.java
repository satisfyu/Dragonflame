package satisfy.dragonflame.item;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.util.IRarity;

public class RarityItem extends Item {
    @Nullable
    private final IRarity rarity;

    public RarityItem(Properties properties) {
        this(properties, null);
    }

    public RarityItem(Properties properties, @Nullable IRarity rarity) {
        super(properties);
        this.rarity = rarity;
    }

    public ChatFormatting getColor(@NotNull ItemStack itemStack) {
        return rarity != null ? rarity.color(itemStack) : getRarity(itemStack).color;
    }

    public boolean isRarity(ItemStack itemStack, IRarity rarity) {
        return IRarity.equals(this.rarity, rarity) || getRarity(itemStack).name().equals(rarity.name().toString());
    }
}
