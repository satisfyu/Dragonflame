package satisfy.dragonflame.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.entity.ai.PatrolGoal;
import satisfy.dragonflame.entity.ai.PillagerDogAttackGoal;
import satisfy.dragonflame.registry.EntityRegistry;
import satisfy.dragonflame.registry.ObjectRegistry;
import satisfy.dragonflame.registry.SoundEventRegistry;

import java.util.List;
import java.util.UUID;

public class ArmoredPillagerDog extends Animal implements NeutralMob, IPatrollingMob {
    public static final EntityDataSerializer<State> DOG_STATE;
    private static final EntityDataAccessor<State> DATA_STATE;
    @Nullable
    private BlockPos patrolTarget;
    private boolean patrolLeader;
    private boolean patrolling;
    private boolean hasSummonedPillagers = false;
    private long pillagerSpawnTick = 0;
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    public final AnimationState howlAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public ArmoredPillagerDog(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);

        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new PatrolGoal<>(this, 0.7, 0.595));
        this.goalSelector.addGoal(11, new PillagerDogAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(ObjectRegistry.DRAGON_BONES.get()), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(7, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(8, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
        this.targetSelector.addGoal(9, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(10, new NearestAttackableTargetGoal<>(this, EnderMan.class, true));
        //TODO: Add Dragons
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896)
                .add(Attributes.MAX_HEALTH, 12.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_STATE, State.IDLE);
    }

    //TODO - DOESNT WORK YET
    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(ObjectRegistry.DRAGON_BONES.get())) {
            if (!this.level().isClientSide) {
                if (this.isAngry()) {
                    this.setTarget(null);
                    for (int i = 0; i < 10; i++) {
                        double d0 = this.random.nextGaussian() * 0.02D;
                        double d1 = this.random.nextGaussian() * 0.02D;
                        double d2 = this.random.nextGaussian() * 0.02D;
                        this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
                    }

                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }

                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.CONSUME;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isAngry()) {
            return SoundEvents.WOLF_GROWL;
        } else {
            return SoundEvents.WOLF_AMBIENT;
        }
    }


    @Nullable
    public ArmoredPillagerDog getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return EntityRegistry.ARMORED_PILLAGER_DOG.get().create(serverLevel);
    }


    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return SoundEvents.WOLF_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    protected float getSoundVolume() {
        return 0.3F;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide() && this.level().getDifficulty() == Difficulty.PEACEFUL) {
            this.remove(RemovalReason.DISCARDED);
            return;
        }
        super.tick();
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
        if (!this.level().isClientSide) {
            if (this.pillagerSpawnTick > 0) {
                if (this.level().getGameTime() >= this.pillagerSpawnTick) {
                    this.summonPillagers();
                    setState(State.HOWL);
                    this.howlAnimationState.start(this.tickCount);
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GOAT_HORN_PLAY, this.getSoundSource(), 32.0F, 1.0F);
                    this.pillagerSpawnTick = 0;
                }
            } else {
                List<Player> players = this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(25), player -> true);
                if (!players.isEmpty() && !this.hasSummonedPillagers) {
                    Player targetPlayer = players.get(0);
                    ItemStack mainHandItem = targetPlayer.getMainHandItem();
                    if (!mainHandItem.is(ObjectRegistry.DRAGON_BONES.get())) {
                        this.setTarget(targetPlayer);
                        this.spawnSpottingParticles();
                        this.hasSummonedPillagers = true;
                        this.pillagerSpawnTick = this.level().getGameTime() + 100L;
                        this.playSound(SoundEventRegistry.IMPROVED_RAID_HORN.get(), 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.setState(State.IDLE);
        } else {
            --this.idleAnimationTimeout;
        }
    }


    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING)
            f = Math.min(pPartialTick * 6F, 1f);
        else
            f = 0f;

        this.walkAnimation.update(f, 0.2f);
    }

    private void summonPillagers() {
        if (!this.level().isClientSide) {
            for (int i = 0; i < 2; i++) {
                Pillager pillager = EntityRegistry.ARMORED_PILLAGER.get().create(this.level());
                if (pillager != null) {
                    double spawnX = this.getX() + (i == 0 ? -1 : 1) * 4;
                    double spawnY = this.getY();
                    double spawnZ = this.getZ();
                    pillager.moveTo(spawnX, spawnY, spawnZ, this.getYRot(), 0.0F);
                    this.level().addFreshEntity(pillager);
                }
            }
            this.playSound(SoundEvents.WOLF_HOWL, 2.0F, 1.0F);
        }
    }

    private void spawnSpottingParticles() {
        if (this.level().isClientSide) {
            for (int i = 0; i < 20; i++) {
                double offsetX = this.random.nextGaussian() * 0.2D;
                double offsetY = this.random.nextGaussian() * 0.2D;
                double offsetZ = this.random.nextGaussian() * 0.2D;
                this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, this.getX() + offsetX, this.getY() + offsetY + 1.0D, this.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uUID) {

    }

    @Override
    public void startPersistentAngerTimer() {
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor serverLevelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        if (serverLevelAccessor.getDifficulty() == Difficulty.PEACEFUL) {
            return null;
        }
        if (mobSpawnType != MobSpawnType.PATROL && mobSpawnType != MobSpawnType.EVENT && mobSpawnType != MobSpawnType.STRUCTURE && serverLevelAccessor.getRandom().nextFloat() < 0.06F && this.canBeLeader()) {
            this.patrolLeader = true;
        }

        if (mobSpawnType == MobSpawnType.PATROL) {
            this.patrolling = true;
        }

        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }


    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        this.addPatrolData(compoundTag);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.readPatrolData(compoundTag);
    }

    /**
     * ANIMATION
     */

    public State getState() {
        return this.entityData.get(DATA_STATE);
    }

    public ArmoredPillagerDog setState(ArmoredPillagerDog.State state) {
        this.entityData.set(DATA_STATE, state);
        return this;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_STATE.equals(key)) {
            State state = this.getState();
            this.resetAnimations();
            switch (state) {
                case IDLE:
                    this.idleAnimationState.startIfStopped(this.tickCount);
                    break;
                case HOWL:
                    this.howlAnimationState.startIfStopped(this.tickCount);
                    break;
                case ATTACK:
                    this.attackAnimationState.startIfStopped(this.tickCount);
                    break;
            }

            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }

    private void resetAnimations() {
        this.idleAnimationState.stop();
        this.howlAnimationState.stop();
        this.attackAnimationState.stop();
    }

    /**
     * PARTROL
     **/

    @Override
    public boolean isPatrolling() {
        return this.patrolling;
    }

    @Override
    public void setPatrolling(boolean bl) {
        this.patrolling = bl;
    }

    @Override
    public boolean hasPatrolTarget() {
        return this.patrolTarget != null;
    }

    @Override
    public @Nullable BlockPos getPatrolTarget() {
        return this.patrolTarget;
    }

    @Override
    public void setPatrolTarget(BlockPos blockPos) {
        this.patrolTarget = blockPos;
        this.patrolling = true;
    }

    @Override
    public void findPatrolTarget() {
        this.patrolTarget = this.blockPosition().offset(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
        this.patrolling = true;
    }

    @Override
    public boolean isPatrolLeader() {
        return this.patrolLeader;
    }

    @Override
    public void setPatrolLeader(boolean bl) {
        this.patrolLeader = bl;
        this.patrolling = true;
    }

    public void addPatrolData(CompoundTag compoundTag) {
        if (this.patrolTarget != null) {
            compoundTag.put("PatrolTarget", NbtUtils.writeBlockPos(this.patrolTarget));
        }

        compoundTag.putBoolean("PatrolLeader", this.patrolLeader);
        compoundTag.putBoolean("Patrolling", this.patrolling);
    }

    public void readPatrolData(CompoundTag compoundTag) {
        if (compoundTag.contains("PatrolTarget")) {
            this.patrolTarget = NbtUtils.readBlockPos(compoundTag.getCompound("PatrolTarget"));
        }

        this.patrolLeader = compoundTag.getBoolean("PatrolLeader");
        this.patrolling = compoundTag.getBoolean("Patrolling");
    }

    static {
        DOG_STATE = EntityDataSerializer.simpleEnum(State.class);
        EntityDataSerializers.registerSerializer(DOG_STATE);
        DATA_STATE = SynchedEntityData.defineId(ArmoredPillagerDog.class, DOG_STATE);
    }

    public enum State {
        IDLE, HOWL, ATTACK
    }
}