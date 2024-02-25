package satisfy.dragonflame.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.entity.ai.PatrolGoal;
import satisfy.dragonflame.registry.ObjectRegistry;

public class ArmoredVindicator extends Vindicator implements IPatrollingMob {

    public ArmoredVindicator(EntityType<? extends Vindicator> entityType, Level level) {
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
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.ARMOR, 2)
                .add(Attributes.ATTACK_DAMAGE, 0.40)
                .add(Attributes.ARMOR_TOUGHNESS, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.3399999940395355)
                .add(Attributes.FOLLOW_RANGE, 18.0);
    }

    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor world, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        super.finalizeSpawn(world, difficulty, reason, spawnData, dataTag);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ObjectRegistry.ARMORED_VINDICATOR_AXE.get()));
        return spawnData;
    }


    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
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
