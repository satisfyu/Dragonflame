package satisfy.dragonflame.util;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class CustomRarity implements IRarity {
    private final ResourceLocation name;
    private final ChatFormatting color;
    @Nullable
    private final IRarity superior;

    private CustomRarity(ResourceLocation name, ChatFormatting color, @Nullable IRarity superior) {
        this.name = name;
        this.color = color;
        this.superior = superior;
    }

    public static IRarity create(ResourceLocation name, ChatFormatting color) {
        return new CustomRarity(name, color, null);
    }

    public static IRarity create(ResourceLocation name, ChatFormatting color, @Nullable IRarity superior) {
        return new CustomRarity(name, color, superior);
    }

    @Override
    public ResourceLocation name() {
        return name;
    }

    @Override
    public ChatFormatting color(ItemStack itemStack) {
        return !itemStack.isEnchanted() || this.superior == null ? this.color : this.superior.color(new ItemStack(Items.AIR));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CustomRarity that = (CustomRarity) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
