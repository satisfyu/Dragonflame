package satisfy.dragonflame.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import satisfy.dragonflame.item.armor.DragonHeadHelmet;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"))
    private void injectFireDamageOnAttack(net.minecraft.world.damagesource.DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player) {
            ItemStack helmet = player.getInventory().armor.get(3);
            if (!helmet.isEmpty() && helmet.getItem() instanceof DragonHeadHelmet) {
                if (source.getEntity() instanceof LivingEntity attacker) {
                    attacker.setSecondsOnFire(5);
                }
            }
        }
    }
}
