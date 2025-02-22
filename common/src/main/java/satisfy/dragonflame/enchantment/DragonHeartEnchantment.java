package satisfy.dragonflame.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DragonHeartEnchantment extends Enchantment {
    private static final int[] MIN_COST = new int[]{10};
    private static final int[] LEVEL_COST = new int[]{10};
    private static final int[] LEVEL_COST_SPAN = new int[]{30};

    public DragonHeartEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.ARMOR_CHEST, EquipmentSlot.values());
    }

    @Override
    public int getMinCost(int level) {
        return MIN_COST[0] + (level - 1) * LEVEL_COST[0];
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + LEVEL_COST_SPAN[0];
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
