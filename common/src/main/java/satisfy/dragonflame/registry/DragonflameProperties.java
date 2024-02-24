package satisfy.dragonflame.registry;


import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class DragonflameProperties {
    public static void addCustomBowProperties() {
        makeBow(ObjectRegistry.DRAGON_BOW.get(), ObjectRegistry.TITAN_BOW.get(), ObjectRegistry.HARDENED_TITAN_BOW.get(), ObjectRegistry.THOAREL_BOW.get());
    }

    private static void makeBow(Item... items) {
        for (Item item : items) {
            ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
                if (p_174637_ == null) {
                    return 0.0F;
                } else {
                    return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
                }
            });

            ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
                return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
            });
        }
    }

    public static void addCustomCrossbowProperties() {
        makeCrossbow(ObjectRegistry.DRAGON_CROSSBOW.get(), ObjectRegistry.TITAN_CROSSBOW.get(), ObjectRegistry.HARDENED_TITAN_CROSSBOW.get());
    }

    private static void makeCrossbow(Item... items) {
        for (Item item : items) {
            ItemProperties.register(item, new ResourceLocation("pull"), (stack, world, entity, seed) -> {
                if (entity == null) {
                    return 0.0F;
                } else {
                    return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float) CrossbowItem.getChargeDuration(stack);
                }
            });

            ItemProperties.register(item, new ResourceLocation("pulling"), (stack, world, entity, seed) -> {
                return entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
            });

            ItemProperties.register(item, new ResourceLocation("charged"), (stack, world, entity, seed) -> {
                return entity != null && CrossbowItem.isCharged(stack) ? 1.0F : 0.0F;
            });

            ItemProperties.register(item, new ResourceLocation("firework"), (stack, world, entity, seed) -> {
                return entity != null && CrossbowItem.isCharged(stack) && CrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
            });
        }
    }

    public static void init() {
        addCustomCrossbowProperties();
        addCustomBowProperties();
    }
}


