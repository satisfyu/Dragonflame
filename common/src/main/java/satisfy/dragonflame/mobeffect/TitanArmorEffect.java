package satisfy.dragonflame.mobeffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class TitanArmorEffect extends MobEffect {
    private static final String KNOCKBACK_RESISTANCE_UUID = "8E5D432F-91E5-4C0A-B556-3D4376F25F11";
    private static final String ARMOR_TOUGHNESS_UUID = "B5A8D51B-47EC-47FD-9886-7EBDFE81EBA7";
    private static final String ARMOR_UUID = "710D4861-7021-47DE-9F52-62F48D2B61EB";


    public TitanArmorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_UUID, 2.0F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, ARMOR_TOUGHNESS_UUID, 1.0F, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR_UUID, 2.0F, AttributeModifier.Operation.ADDITION);
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