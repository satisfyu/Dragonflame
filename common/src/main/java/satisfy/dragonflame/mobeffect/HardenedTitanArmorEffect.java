package satisfy.dragonflame.mobeffect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class HardenedTitanArmorEffect extends MobEffect {
    private static final String KNOCKBACK_RESISTANCE_UUID = "8E5D432F-91E5-4C0A-B556-3D4376F25F11";
    private static final String ARMOR_TOUGHNESS_UUID = "B5A8D51B-47EC-47FD-9886-7EBDFE81EBA7";
    private static final String ARMOR_UUID = "710D4861-7021-47DE-9F52-62F48D2B61EB";


    public HardenedTitanArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_UUID, 3.0F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_UUID, 2.0F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_UUID, 4.0F, AttributeModifier.Operation.ADDITION);

    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!(pLivingEntity instanceof Player player && player.isSpectator())) {
            Vec3 pos = pLivingEntity.position();
            Vec3 movement = pLivingEntity.getDeltaMovement();
            Vec3 futurePos = pos.add(movement);
            BlockPos onPos = pLivingEntity.getOnPos();
            BlockPos futureBlockPos = new BlockPos((int) futurePos.x, (int) futurePos.y, (int) futurePos.z);
            if (pLivingEntity.isInLava()) {
                pLivingEntity.setDeltaMovement(movement.add(0, 0.1, 0));
            } else if (pLivingEntity.level().getFluidState(onPos).is(FluidTags.LAVA)) {
                if (pLivingEntity.level() instanceof ServerLevel level) {
                    level.sendParticles(ParticleTypes.LAVA, pos.x(), pos.y() + 0.1D, pos.z(), 10, 0.2, 0.1, 0.2, 1.5);
                }
                pLivingEntity.setDeltaMovement(movement.x(), Math.max(movement.y(), 0D), movement.z());
                pLivingEntity.setOnGround(true);
            } else if (pLivingEntity.level().getFluidState(futureBlockPos).is(FluidTags.LAVA) && movement.y() > -0.8) {
                if (pLivingEntity.level() instanceof ServerLevel level) {
                    level.sendParticles(ParticleTypes.LAVA, pos.x(), pos.y() + 0.1D, pos.z(), 10, 0.2, 0.1, 0.2, 1.5);
                }
                pLivingEntity.setDeltaMovement(movement.x(), Math.max(movement.y(), movement.y() * 0.5), movement.z());
            }
            super.applyEffectTick(pLivingEntity, pAmplifier);
        }
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        if (modifier.getId().equals(UUID.fromString(KNOCKBACK_RESISTANCE_UUID)))
            return (amplifier + 1) * 2.0F;
        if (modifier.getId().equals(UUID.fromString(ARMOR_TOUGHNESS_UUID)))
            return (amplifier + 1) * 2.0F;
        if (modifier.getId().equals(UUID.fromString(ARMOR_UUID)))
            return (amplifier + 1) * 2.0F;
        return amplifier + 1;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}