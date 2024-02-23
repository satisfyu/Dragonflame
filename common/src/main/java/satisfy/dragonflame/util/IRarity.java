package satisfy.dragonflame.util;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public interface IRarity {
    ResourceLocation name();

    ChatFormatting color(ItemStack itemStack);

    static boolean equals(IRarity r1, IRarity r2) {
        return r1 != null && r2 != null && r1.name().equals(r2.name());
    }
}
