package satisfy.dragonflame.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import satisfy.dragonflame.registry.EnchantmentRegistry;

import java.util.Objects;
import java.util.UUID;

@Mixin(Player.class)
public class EnchantmentMixin {

    @Unique
    private static final UUID HEART_BOOST_ID = UUID.fromString("a62f6f3f-19b9-4d58-a499-795b9a205203");
    @Unique
    private static final double HEARTS_AMOUNT = 6.0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void applyAdditionalHearts(CallbackInfo info) {
        Player player = (Player) (Object) this;
        boolean hasEnchantment = false;

        for (ItemStack itemStack : player.getArmorSlots()) {
            if (EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.DRAGON_HEART, itemStack) > 0) {
                hasEnchantment = true;
                break;
            }
        }

        AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        AttributeModifier modifier = new AttributeModifier(HEART_BOOST_ID, "Heart boost enchantment", HEARTS_AMOUNT, AttributeModifier.Operation.ADDITION);

        if (hasEnchantment) {
            if (healthAttribute != null && healthAttribute.getModifier(HEART_BOOST_ID) == null) {
                healthAttribute.addPermanentModifier(modifier);
            }
        } else if (healthAttribute != null && healthAttribute.getModifier(HEART_BOOST_ID) != null) {
            healthAttribute.removeModifier(modifier);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void checkForNightVisionEnchantment(CallbackInfo info) {
        Player player = (Player) (Object) this;
        Iterable<ItemStack> equipment = player.getArmorSlots();
        boolean hasEnchantment = false;

        for (ItemStack itemStack : equipment) {
            if (EnchantmentHelper.getEnchantments(itemStack).containsKey(EnchantmentRegistry.DRAGON_EYE)) {
                hasEnchantment = true;
                break;
            }
        }

        if (hasEnchantment) {
            if (player.getEffect(MobEffects.NIGHT_VISION) == null || Objects.requireNonNull(player.getEffect(MobEffects.NIGHT_VISION)).getDuration() < 220) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false, true));
            }
        } else if (player.hasEffect(MobEffects.NIGHT_VISION)) {
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }
}
