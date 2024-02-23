package satisfy.dragonflame.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FieryWarhorseSpawnEgg extends SpawnEggItem {
    public FieryWarhorseSpawnEgg(EntityType<? extends Mob> entityType, int i, int j, Properties properties) {
        super(entityType, i, j, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("lore.dragonflame.fiery_warhorse").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}
