package satisfy.dragonflame.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import satisfy.dragonflame.util.IRarity;

@Mixin(Rarity.class)
public class RarityMixin implements IRarity {
    @Shadow
    @Final
    public ChatFormatting color;

    @Override
    public ResourceLocation name() {
        return new ResourceLocation(toString());
    }

    @Override
    public ChatFormatting color(ItemStack itemStack) {
        return color;
    }
}