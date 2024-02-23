package satisfy.dragonflame.enchantment;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import satisfy.dragonflame.Dragonflame;

public class DraconicForDummiesEnchantment extends DamageEnchantment {
    private static final int MAX_LEVEL = 10;

    public DraconicForDummiesEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON.ordinal());

    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public float getDamageBonus(int level, MobType mobType) {
        if (mobType == MobType.UNDEAD || mobType == MobType.ILLAGER) {
            return (float)level * 2.4F;
        }

        return (float)level * 0.4F;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return canApplyAtEnchantingTable(stack);
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return super.canEnchant(stack) && (stack.getItem() instanceof SwordItem || stack.getItem() instanceof AxeItem);
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public void doPostAttack(LivingEntity user, Entity target, int level) {

    }
}