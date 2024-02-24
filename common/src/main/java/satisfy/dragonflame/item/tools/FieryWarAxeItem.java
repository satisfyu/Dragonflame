package satisfy.dragonflame.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.util.EnchantingBehavior;

import java.util.List;

public class FieryWarAxeItem extends AxeItem implements EnchantingBehavior {

    public FieryWarAxeItem(Tier toolMaterial, Properties properties) {
        super(toolMaterial, 3, -1.4F, properties);
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
        if (result && !target.getCommandSenderWorld().isClientSide() && !target.fireImmune()) {
            target.setSecondsOnFire(15);
        } else {
            for (int var1 = 0; var1 < 20; ++var1) {
                double px = target.getX() + target.getCommandSenderWorld().getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                double py = target.getY() + target.getCommandSenderWorld().getRandom().nextFloat() * target.getBbHeight();
                double pz = target.getZ() + target.getCommandSenderWorld().getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                target.getCommandSenderWorld().addParticle(ParticleTypes.FLAME, px, py, pz, 0.02, 0.02, 0.02);
            }
        }

        return result;
    }



    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.hardened_titan_sword").withStyle(ChatFormatting.WHITE));
    }
}