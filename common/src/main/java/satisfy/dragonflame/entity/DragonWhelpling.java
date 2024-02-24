package satisfy.dragonflame.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.entity.ai.WhelplingFlyingGoal;
import satisfy.dragonflame.registry.SoundEventRegistry;

import java.util.List;


public class DragonWhelpling extends PathfinderMob implements FlyingAnimal {
    private int attackTime;
    private boolean isCastingFireball = false;
    private int castingTime = 0;
    private boolean isBreathingFire = false;
    private int fireBreathDuration = 0;
    private int fireBreathCooldown = 0;
    private int fireballCooldown = 0;

    public DragonWhelpling(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.attackTime = 0;
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setCanPickUpLoot(true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_OTHER, 0.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_OTHER, 0.0F);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0F)
                .add(Attributes.FLYING_SPEED, 0.799292938447F)
                .add(Attributes.MOVEMENT_SPEED, 0.4F)
                .add(Attributes.ATTACK_DAMAGE, 8.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WhelplingFlyingGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, 10, false, false, entity -> entity.getType().equals(EntityType.PILLAGER)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, false, false, null));
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEventRegistry.DRAGON_WHELPLING_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEventRegistry.DRAGON_WHELPLING_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEventRegistry.DRAGON_WHELPLING_AMBIENT.get();
    }


    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_FIRE)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        return false;
    }


    @Override
    protected @NotNull PathNavigation createNavigation(Level pLevel) {
        WhelplingPathNavigation navigation = new WhelplingPathNavigation(this, pLevel);
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    static class WhelplingPathNavigation extends FlyingPathNavigation {
        public WhelplingPathNavigation(Mob mob, Level level) {
            super(mob, level);
        }
    }

    @Override
    public void tick() {
        super.tick();

        boolean isCasting = this.isBreathingFire || this.isCastingFireball;

        if (this.isBreathingFire) {
            if (this.fireBreathDuration > 0) {
                LivingEntity target = this.getTarget();
                if (target != null) {
                    this.fireBreathAttack(target);
                }
                this.fireBreathDuration--;
            } else {
                this.isBreathingFire = false;
            }
        }
        if (this.isCastingFireball) {
            this.castingTime--;
            if (this.castingTime <= 0) {
                this.isCastingFireball = false;
                this.shootFireballAtTarget(this.getTarget());
                this.fireballCooldown = 140;
            }
            this.getNavigation().stop();
        }

        if (!this.level().isClientSide && !isCasting) {
            --this.attackTime;
            LivingEntity target = this.getTarget();
            if (target != null && target.isAlive() && this.getSensing().hasLineOfSight(target)) {
                double distanceSq = this.distanceToSqr(target);
                if (distanceSq < 4.0D && this.fireBreathCooldown <= 0) {
                    this.isBreathingFire = true;
                    this.fireBreathDuration = 80;
                    this.attackTime = 180;
                } else if (distanceSq < getFollowDistance() * getFollowDistance() && this.fireballCooldown <= 0) {
                    this.isCastingFireball = true;
                    this.castingTime = 40;
                    this.attackTime = 140;
                }
            }
        }
        if (this.fireBreathCooldown > 0) {
            this.fireBreathCooldown--;
        }
        if (this.fireballCooldown > 0) {
            this.fireballCooldown--;
        }
    }


    private void shootFireballAtTarget(LivingEntity target) {
        if (target != null) {
            double dX = target.getX() - this.getX();
            double dY = target.getEyeY() - this.getEyeY();
            double dZ = target.getZ() - this.getZ();
            SmallFireball fireball = new SmallFireball(this.level(), this, dX, dY, dZ);
            fireball.setPos(this.getX(), this.getEyeY(), this.getZ());
            this.level().addFreshEntity(fireball);
        }
    }

    private void fireBreathAttack(LivingEntity target) {
        if (target != null) {
            if (this.level().isClientSide) {
                for (int i = 0; i < 100; i++) {
                    double d0 = this.getRandom().nextGaussian() * 0.02D;
                    double d1 = this.getRandom().nextGaussian() * 0.02D;
                    double d2 = this.getRandom().nextGaussian() * 0.02D;
                    this.level().addParticle(ParticleTypes.FLAME, this.getX() + this.getRandom().nextDouble() - 0.5D, this.getEyeY(), this.getZ() + this.getRandom().nextDouble() - 0.5D, d0, d1, d2);
                }
            }
            if (!this.level().isClientSide && this.fireBreathDuration % 4 == 0) {
                List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(1.0D, 0.5D, 1.0D));
                for (LivingEntity entity : entities) {
                    if (!entity.equals(this)) {
                        entity.setSecondsOnFire(4);
                    }
                }
            }
        }
    }



    protected double getFollowDistance() {
        return this.getAttributeValue(Attributes.FOLLOW_RANGE);
    }
}

