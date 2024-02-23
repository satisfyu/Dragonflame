package satisfy.dragonflame.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class RaubbauItem extends ShovelItem  {
    public RaubbauItem(Tier toolMaterial, Properties properties) {
        super(toolMaterial, 2, -2.4F, properties);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            if (entityLiving instanceof Player) {
                Random random = new Random();
                if (random.nextFloat() < 0.2) {
                    Player player = (Player) entityLiving;
                    player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 120, 5));
                }
            }
        }
        return super.mineBlock(stack, world, state, pos, entityLiving);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.raubbau").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.dragonflame.raubbau").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}
