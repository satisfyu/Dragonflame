package satisfy.dragonflame.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.registry.MobEffectRegistry;

import java.util.List;

public class HardenedTitanAxeItem extends AxeItem {
    public HardenedTitanAxeItem(Tier toolMaterial, Properties properties) {
        super(toolMaterial, 5, -3F, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, LivingEntity attacker) {
        if (!attacker.getCommandSenderWorld().isClientSide && Math.random() < 0.25) {
            BlockPos pos = target.blockPosition();
            attacker.getCommandSenderWorld().playSound(null, pos, SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR, SoundSource.PLAYERS, 0.2F, 0.15F);
            attacker.getCommandSenderWorld().gameEvent(attacker, GameEvent.INSTRUMENT_PLAY, pos);
            applyEffectToNearbyEntities(attacker, attacker.getCommandSenderWorld(), pos);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    protected void applyEffectToNearbyEntities(LivingEntity attacker, Level world, BlockPos pos) {
        double radius = 2.0D;
        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, attacker.getBoundingBox().inflate(radius), e -> e != attacker && e.isAlive());
        for (LivingEntity entity : nearbyEntities) {
            entity.addEffect(new MobEffectInstance(MobEffectRegistry.CONFUSION.get(), 100, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 0));
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip.dragonflame.hardened_titan_axe").withStyle(ChatFormatting.WHITE));
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("lore.dragonflame.hardened_titan").withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));
    }
}
