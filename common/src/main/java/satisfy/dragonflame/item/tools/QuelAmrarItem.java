package satisfy.dragonflame.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.util.EnchantingBehavior;

import java.util.List;
import java.util.Random;

public class QuelAmrarItem extends SwordItem implements EnchantingBehavior {

    public QuelAmrarItem(Tier toolMaterial, Properties properties) {
        super(toolMaterial, 5, -2.2F, properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.FIRE_ASPECT;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return !EnchantmentHelper.getEnchantments(book).containsKey(Enchantments.FIRE_ASPECT) && EnchantingBehavior.super.isBookEnchantable(stack, book);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        Level world = target.getCommandSenderWorld();
        Random random = new Random();
        if (result && !world.isClientSide() && random.nextFloat() < 0.15) {
            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(world);
            lightningbolt.moveTo(target.getX(), target.getY(), target.getZ());
            world.addFreshEntity(lightningbolt);
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.quelamrar").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.dragonflame.quelamrar").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}
