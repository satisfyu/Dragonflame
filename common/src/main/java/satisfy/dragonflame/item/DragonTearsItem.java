package satisfy.dragonflame.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class DragonTearsItem extends Item {
	public DragonTearsItem(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.DRINK;
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack) {
		return 30;
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, @NotNull InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (!world.isClientSide) {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemStack);
		}
		return InteractionResultHolder.pass(itemStack);
	}

	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, Level level, @NotNull LivingEntity livingEntity) {
		if (!level.isClientSide) {
			if (livingEntity instanceof Player player) {
				Random random = new Random();
				int experience = 2 + random.nextInt(6);
				player.giveExperiencePoints(experience);
				if (!player.isCreative()) {
					itemStack.shrink(1);
				}
			}
		}
		return itemStack;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("tooltip.dragonflame.dragon_tears_on_use1").withStyle(ChatFormatting.WHITE));
		tooltip.add(Component.translatable("tooltip.dragonflame.dragon_tears_on_use2").withStyle(ChatFormatting.WHITE));

	}
}