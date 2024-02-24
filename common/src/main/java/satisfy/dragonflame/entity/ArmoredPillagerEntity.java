package satisfy.dragonflame.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.entity.ai.PatrolGoal;
import satisfy.dragonflame.registry.ObjectRegistry;

public class ArmoredPillagerEntity extends Pillager implements IPatrollingMob {
    private final SimpleContainer inventory = new SimpleContainer(10);

    public ArmoredPillagerEntity(EntityType<? extends Pillager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, DragonWhelpling.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, EnderMan.class, true));
        this.goalSelector.addGoal(0, new PatrolGoal<>(this, 0.7, 0.595));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 48.0)
                .add(Attributes.ARMOR, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 0.30)
                .add(Attributes.ARMOR_TOUGHNESS, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.3599999940395355)
                .add(Attributes.FOLLOW_RANGE, 18.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ObjectRegistry.TITAN_CROSSBOW.get()));
        this.inventory.addItem(new ItemStack(Items.ARROW, 640));

        return spawnData;
    }

    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    public AbstractIllager.@NotNull IllagerArmPose getArmPose() {
        if (this.isChargingCrossbow()) {
            return IllagerArmPose.CROSSBOW_CHARGE;
        } else if (this.isHolding(ObjectRegistry.TITAN_CROSSBOW.get())) {
            return IllagerArmPose.CROSSBOW_HOLD;
        } else {
            return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.NEUTRAL;
        }
    }

    @Override
    public boolean canJoinRaid() {
        return false;
    }

    @Override
    public boolean isPatrolling() {
        return super.isPatrolling();
    }

    @Override
    public void setPatrolling(boolean bl) {
        super.setPatrolling(bl);
    }

    @Override
    public boolean hasPatrolTarget() {
        return super.hasPatrolTarget();
    }

    @Override
    public @NotNull BlockPos getPatrolTarget() {
        return super.getPatrolTarget();
    }

    @Override
    public void setPatrolTarget(@NotNull BlockPos blockPos) {
        super.setPatrolTarget(blockPos);
    }

    @Override
    public void findPatrolTarget() {
        super.findPatrolTarget();
    }

    @Override
    public boolean isPatrolLeader() {
        return super.isPatrolLeader();
    }

    @Override
    public void setPatrolLeader(boolean bl) {
        super.setPatrolLeader(bl);
    }

    @Override
    public boolean canBeLeader() {
        return false;
    }
}
