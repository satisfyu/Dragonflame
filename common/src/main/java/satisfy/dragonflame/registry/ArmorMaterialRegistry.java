package satisfy.dragonflame.registry;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public interface ArmorMaterialRegistry {
    ArmorMaterial REINFORCED_LEATHER_ARMOR = new ArmorMaterial() {
        private final int durabilityMultiplier = 28;
        private final int[] defensePoints = {1, 4, 4, 2};
        private final int enchantability = 15;
        private final SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_LEATHER;
        private final float toughness = 0F;
        private final float knockbackResistance = 0.1F;


        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            int durability = switch (type) {
                case BOOTS -> 13;
                case LEGGINGS -> 15;
                case CHESTPLATE -> 16;
                case HELMET -> 11;
                default -> 0;
            };
            return durability * this.durabilityMultiplier;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case BOOTS -> this.defensePoints[0];
                case LEGGINGS -> this.defensePoints[1];
                case CHESTPLATE -> this.defensePoints[2];
                case HELMET -> this.defensePoints[3];
                default -> 0;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return this.enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return this.equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ObjectRegistry.DRAGONSCALE.get());
        }

        @Override
        public String getName() {
            return "reinforced_leather";
        }

        @Override
        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    };


    ArmorMaterial DRAGON_ARMOR = new ArmorMaterial() {
        private final int durabilityMultiplier = 39;
        private final int[] defensePoints = {2, 6, 6, 3};
        private final int enchantability = 20;
        private final SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_GENERIC;
        private final float toughness = 1.0F;
        private final float knockbackResistance = 0.1F;

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            int durability = switch (type) {
                case BOOTS -> 13;
                case LEGGINGS -> 15;
                case CHESTPLATE -> 16;
                case HELMET -> 11;
                default -> 0;
            };
            return durability * this.durabilityMultiplier;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case BOOTS -> this.defensePoints[0];
                case LEGGINGS -> this.defensePoints[1];
                case CHESTPLATE -> this.defensePoints[2];
                case HELMET -> this.defensePoints[3];
                default -> 0;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return this.enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return this.equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ObjectRegistry.DRAGONSCALE.get());
        }

        @Override
        public String getName() {
            return "dragon";
        }

        @Override
        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    };

    ArmorMaterial TITAN_ARMOR = new ArmorMaterial() {
        private final int durabilityMultiplier = 42;
        private final int[] defensePoints = {4, 8, 8, 4};
        private final int enchantability = 25;
        private final SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_GENERIC;
        private final float toughness = 4.0F;
        private final float knockbackResistance = 0.2F;

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            int durability = switch (type) {
                case BOOTS -> 13;
                case LEGGINGS -> 15;
                case CHESTPLATE -> 16;
                case HELMET -> 11;
                default -> 0;
            };
            return durability * this.durabilityMultiplier;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case BOOTS -> this.defensePoints[0];
                case LEGGINGS -> this.defensePoints[1];
                case CHESTPLATE -> this.defensePoints[2];
                case HELMET -> this.defensePoints[3];
                default -> 0;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return this.enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return this.equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ObjectRegistry.TITAN_INGOT.get());
        }


        @Override
        public String getName() {
            return "titan";
        }

        @Override
        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    };

    ArmorMaterial HARDENED_TITAN_ARMOR = new ArmorMaterial() {
        private final int durabilityMultiplier = 45;
        private final int[] defensePoints = {5, 10, 10, 5};
        private final int enchantability = 25;
        private final SoundEvent equipSound = SoundEvents.ARMOR_EQUIP_GENERIC;
        private final float toughness = 5.0F;
        private final float knockbackResistance = 0.4F;

        @Override
        public int getDurabilityForType(ArmorItem.Type type) {
            int durability = switch (type) {
                case BOOTS -> 13;
                case LEGGINGS -> 15;
                case CHESTPLATE -> 16;
                case HELMET -> 11;
                default -> 0;
            };
            return durability * this.durabilityMultiplier;
        }

        @Override
        public int getDefenseForType(ArmorItem.Type type) {
            return switch (type) {
                case BOOTS -> this.defensePoints[0];
                case LEGGINGS -> this.defensePoints[1];
                case CHESTPLATE -> this.defensePoints[2];
                case HELMET -> this.defensePoints[3];
                default -> 0;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return this.enchantability;
        }

        @Override
        public SoundEvent getEquipSound() {
            return this.equipSound;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(ObjectRegistry.HARDENED_TITAN_PLATES.get());
        }


        @Override
        public String getName() {
            return "hardened_titan";
        }

        @Override
        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float getKnockbackResistance() {
            return this.knockbackResistance;
        }
    };
}
