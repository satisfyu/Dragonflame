package satisfy.dragonflame.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.util.EnchantingBehavior;

import java.util.List;
import java.util.Random;

public class EmbergraspItem extends SwordItem implements EnchantingBehavior {
    public EmbergraspItem(Tier toolMaterial, Properties properties) {
        super(toolMaterial, 5, -3F, properties);
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
        if (result && !world.isClientSide()) {
            if (new Random().nextInt(5) == 0) {
                Vec3 targetPos = new Vec3(target.getX(), target.getY(), target.getZ());
                Vec3 attackerPos = new Vec3(attacker.getX(), attacker.getY(), attacker.getZ());
                Vec3 direction = targetPos.subtract(attackerPos).normalize().scale(0.5);
                Fireball fireball = EntityType.FIREBALL.create(world);
                fireball.setPos(attacker.getX(), attacker.getY(), attacker.getZ());
                fireball.setDeltaMovement(direction);
                world.addFreshEntity(fireball);
            }
        }
        if (result && !target.fireImmune()) {
            target.setSecondsOnFire(15);
        } else {
            for (int var1 = 0; var1 < 20; ++var1) {
                double px = target.getX() + world.getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                double py = target.getY() + world.getRandom().nextFloat() * target.getBbHeight();
                double pz = target.getZ() + world.getRandom().nextFloat() * target.getBbWidth() * 2.0F - target.getBbWidth();
                world.addParticle(ParticleTypes.FLAME, px, py, pz, 0.02, 0.02, 0.02);
            }
        }
        return result;
    }



    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.embergrasp").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.dragonflame.embergrasp").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}