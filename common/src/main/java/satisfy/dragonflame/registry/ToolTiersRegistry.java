package satisfy.dragonflame.registry;

import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ToolTiersRegistry implements Tier
{
    DRAGON(3, 1717, 9f, 3.5f, 11, () -> Ingredient.of(ObjectRegistry.DRAGONSCALE.get())),
    TITAN(4, 2133, 10f, 4f, 16, () -> Ingredient.of(ObjectRegistry.TITAN_PLATES.get())),
    HARDENED_TITAN(5, 2437, 11f, 4.5f, 18, () -> Ingredient.of(ObjectRegistry.HARDENED_TITAN_PLATES.get())),
    QUALAMRAR(6, 2500, 11.5f, 5.5f, 20, () -> Ingredient.of(ObjectRegistry.HARDENED_TITAN_PLATES.get())),
    EMBERGRASP(6, 2500, 7f, 6f, 20, () -> Ingredient.of(ObjectRegistry.HEART_OF_FLAME.get())),
    RAUBBAU(5, 2437, 11f, 5f, 18, () -> Ingredient.of(ObjectRegistry.HEART_OF_FLAME.get()));

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    ToolTiersRegistry(int j, int k, float f, float g, int l, Supplier<Ingredient> supplier) {
        this.level = j;
        this.uses = k;
        this.speed = f;
        this.damage = g;
        this.enchantmentValue = l;
        this.repairIngredient = new LazyLoadedValue<Ingredient>(supplier);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}

