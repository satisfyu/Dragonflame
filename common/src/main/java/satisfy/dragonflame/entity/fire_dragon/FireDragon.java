package satisfy.dragonflame.entity.fire_dragon;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.client.DragonflameClient;
import satisfy.dragonflame.entity.fire_dragon.ai.FireDragonAi;
import satisfy.dragonflame.entity.fire_dragon.ai.JumpFromGround;
import satisfy.dragonflame.entity.fire_dragon.ai.upgrade.GroundNavigation;
import satisfy.dragonflame.entity.fire_dragon.ai.upgrade.MoveToWalkTarget;
import satisfy.dragonflame.entity.fire_dragon.ai.upgrade.SetWalkTargetToAttackTarget;
import satisfy.dragonflame.entity.fire_dragon.control.DragonBodyController;
import satisfy.dragonflame.entity.fire_dragon.control.DragonMoveControl;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.keyframe.event.SoundKeyframeEvent;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class FireDragon extends Monster implements GeoEntity, Saddleable, FlyingAnimal, PlayerRideableJumping, SmartBrainOwner<FireDragon> {

    public static final double BASE_SPEED_GROUND = 0.6;
    public static final double BASE_SPEED_FLYING = 0.525;
    public static final double BASE_DAMAGE = 8;
    public static final double BASE_HEALTH = 60;
    public static final float BASE_WIDTH = 2.75f;
    public static final float BASE_HEIGHT = 2.75f;
    public static final int DEATH_DURATION = 100;
    public static final float BASE_SIZE_MODIFIER = 1f; //min (riding) 0.83
    public static final int ALTITUDE_FLYING_THRESHOLD = 4;

    private final GroundPathNavigation groundNavigation;
    private final FlyingPathNavigation flyingNavigation;

    private float playerJumpPendingScale;

    private boolean changedFly = false;

    public FireDragon(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

        setMaxUpStep(1);
        noCulling = true;
        moveControl = new DragonMoveControl(this);
        flyingNavigation = new FlyingPathNavigation(this, level());
        groundNavigation = new GroundNavigation(this, level());
        flyingNavigation.setCanFloat(true);
        groundNavigation.setCanFloat(true);
        navigation = groundNavigation;
    }

    private static final RawAnimation WALK = RawAnimation.begin().thenPlay("Walk");
    private static final RawAnimation FLY = RawAnimation.begin().thenPlay("Flyingv2");
    private static final RawAnimation TAKE_OFF = RawAnimation.begin().thenPlay("TakeOffv2");
    private static final RawAnimation DEATH = RawAnimation.begin().thenPlay("Death");
    private static final RawAnimation IDLE = RawAnimation.begin().thenPlay("Idle");
    private static final RawAnimation HOVER = RawAnimation.begin().thenPlay("Hovering");

    public static final int TRANSITION_TICK = 10;

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "Fly/Walk/Hover/Idle", TRANSITION_TICK, this::move).triggerableAnim("take_off", TAKE_OFF).setSoundKeyframeHandler(this::soundHandler),
                new AnimationController<>(this, "Death", TRANSITION_TICK, state -> PlayState.STOP).triggerableAnim("death", DEATH)
        );
    }

    private PlayState move(AnimationState<FireDragon> state) {
        if(state.isMoving()){
            if(this.isFlying()){
                //if(this.getDeltaMovement().y < -0.1) state.setAndContinue(HOVER);
                return state.setAndContinue(FLY);
            }
            return state.setAndContinue(WALK);
        }
        return state.setAndContinue(this.isFlying() ? HOVER : IDLE);
    }

    private void soundHandler(SoundKeyframeEvent<FireDragon> event) {
        if(event.getKeyframeData().getSound().equals("wing_down")){
            double speed = event.getController().getAnimationSpeed() / 2;

            float pitch = (float) (1 - speed);
            float volume = (float) (0.3f + (1 - speed) * 0.2f);
            pitch *= getVoicePitch();
            volume *= getSoundVolume();
            level().playLocalSound(getX(), getY(), getZ(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.VOICE, volume, pitch, true);
        }
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        super.customServerAiStep();
    }
    @Override
    public void tick() {
        super.tick();

        if (isServer()) {
            // update flying state based on the distance to the ground
            boolean flying = shouldFly();
            if (flying != isFlying()) {
                changedFly = true;
                // notify client
                setFlying(flying);

                // update pathfinding method
                setNavigation(flying);
                return;
            }
            changedFly = false;
        }
    }


    @Override
    public void travel(Vec3 vec3) {
        boolean isFlying = isFlying();
        float speed = getSpeedSpecial();

        LivingEntity driver = getControllingPassenger();
        if (driver != null) {// Were being controlled; override ai movement
            double moveSideways = vec3.x;
            double moveY = vec3.y;
            double moveForward = Math.min(Math.abs(driver.zza) + Math.abs(driver.xxa), 1);

            // rotate head to match driver.
            float yaw = driver.yHeadRot;
            if (moveForward > 0) // rotate in the direction of the drivers controls
                yaw += (float) Mth.atan2(driver.zza, driver.xxa) * (180f / (float) Math.PI) - 90;
            yHeadRot = yaw;
            setXRot(driver.getXRot() * 0.68f);

            // rotate body towards the head
            setYRot(Mth.rotateIfNecessary(yHeadRot, getYRot(), 4));

            if (isControlledByLocalInstance()) { // Client applies motion
                if (isFlying) {
                    moveForward = moveForward > 0 ? moveForward : 0;
                    if (moveForward > 0) moveY = -driver.getXRot() * (Math.PI / 180);
                    else{
                        moveY = driver.jumping? 1 : DragonflameClient.isYPressed ? -1 : 0;
                    }
                }
                else if (playerJumpPendingScale > 0.0F) jumpFromGround();
                if(!isFlying()) playerJumpPendingScale = 0.0F;

                vec3 = new Vec3(moveSideways, moveY, moveForward);
                setSpeed(speed);
            }
            else if (driver instanceof Player) { // other clients receive animations
                //calculateEntityAnimation(true);
                setDeltaMovement(Vec3.ZERO);
                return;
            }
        }

        if (isFlying) {
            // Move relative to yaw - handled in the move controller or by driver
            moveRelative(speed, vec3);
            move(MoverType.SELF, getDeltaMovement());
            if (getDeltaMovement().lengthSqr() < 0.1) // we're not actually going anywhere, bob up and down.
                setDeltaMovement(getDeltaMovement().add(0, Math.sin(tickCount / 4f) * 0.03, 0));
            setDeltaMovement(getDeltaMovement().scale(0.9f)); // smoothly slow down

            calculateEntityAnimation(true);
        }
        else super.travel(vec3);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DATA_FLYING, false);
        entityData.define(DATA_SADDLED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean(NBT_SADDLED, isSaddled());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        entityData.set(DATA_SADDLED, compound.getBoolean(NBT_SADDLED));
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        boolean server = isServer();
        if(stack.getItem().equals(Items.SADDLE) && !isSaddled()){
            if(server){
                stack.shrink(1);
                equipSaddle(SoundSource.NEUTRAL);
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }
            return InteractionResult.sidedSuccess(!server);
        } else if (player.isCrouching() && isSaddled() && getPassengers().isEmpty()) {
            if(server){
                ItemStack saddle = new ItemStack(Items.SADDLE);
                if (!player.getInventory().add(saddle)) {
                    player.drop(saddle, false);
                }
                setSaddled(false);
            }
            return InteractionResult.sidedSuccess(!server);

        } else if(isSaddled()){
            if(server){
                setRidingPlayer(player);
            }
            return InteractionResult.sidedSuccess(!server);
        }
        return InteractionResult.PASS;
    }


    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return sizeIn.height * 1.2f;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        //var height = isInSittingPose()? 2.15f : BASE_HEIGHT;
        var scale = getScale();
        return new EntityDimensions(BASE_WIDTH * scale, BASE_HEIGHT * scale, false);
    }

    ///////////////////////////------Ai--------///////////////////////////////
    @Override
    public List<? extends ExtendedSensor<? extends FireDragon>> getSensors() {
        return ObjectArrayList.of(
                new NearbyLivingEntitySensor<FireDragon>().setPredicate((target, entity) -> !(target instanceof FireDragon)),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<FireDragon> getCoreTasks() { // These are the tasks that run all the time (usually)
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new JumpFromGround<>(), // Have the entity turn to face and look at its current look target
                new MoveToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<FireDragon> getIdleTasks() { // These are the tasks that run when the mob isn't doing anything else (usually)
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<FireDragon>(      // Run only one of the below behaviours, trying each one in order. Include the generic type because JavaC is silly
                        new TargetOrRetaliate<>(),            // Set the attack target and walk target based on nearby entities
                        new SetPlayerLookTarget<>(),          // Set the look target for the nearest player
                        new SetRandomLookTarget<>()),         // Set a random look target
                new OneRandomBehaviour<>(// Run a random task from the below options
                        //new SetRandomHoverTarget<>().speedModifier((float) BASE_SPEED_FLYING).startCondition(e -> this.isFlying()),
                        new SetRandomWalkTarget<>().speedModifier((float) BASE_SPEED_GROUND).startCondition(e -> !this.isFlying()),          // Set a random walk target to a nearby position
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(90, 120)))); // Do nothing for 1.5->3 seconds
    }

    @Override
    public BrainActivityGroup<FireDragon> getFightTasks() { // These are the tasks that handle fighting
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().stopTryingToPathAfter(600).invalidateIf((t, e) -> changedFly), // Cancel fighting if the target is no longer valid
                new SetWalkTargetToAttackTarget<>(),      // Set the walk target to the attack target
                new AnimatableMeleeAttack<>(0)
        ); // Melee attack the target if close enough
    }

    ///////////////////////////------Riding--------///////////////////////////////
    @Override
    public double getPassengersRidingOffset() {
        return getBbHeight() + 0.9;
    }

    @Override
    public void positionRider(Entity passenger, MoveFunction moveFunction) {
        LivingEntity riddenByEntity = getControllingPassenger();
        if (riddenByEntity != null) {

            Vec3 pos = new Vec3(0, getPassengersRidingOffset() + riddenByEntity.getMyRidingOffset(), getScale() * 1.4)
                    .yRot((float) Math.toRadians(-yBodyRot))
                    .add(position());
            passenger.setPos(pos.x, pos.y, pos.z);

            // fix rider rotation
            riddenByEntity.xRotO = riddenByEntity.getXRot();
            riddenByEntity.yRotO = riddenByEntity.getYRot();
            riddenByEntity.yBodyRot = yBodyRot;
        }
    }
    @Override
    public boolean isSaddleable() {
        return true;
    }

    ///////////////////////////------Jump--------///////////////////////////////
    @Override
    public void jumpFromGround() {
        super.jumpFromGround();
        triggerAnim("Fly/Walk/Hover/Idle", "take_off");
    }
    @Override
    protected float getJumpPower() {
        return super.getJumpPower() * 10 /* * playerJumpPendingScale*/ * getScale(); // original super.getJumpPower() * 3
    }
    public void onPlayerJump(int i) {
        if (this.isSaddled()) {
            if (i < 0) {
                i = 0;
            }

            if (i >= 90) {
                this.playerJumpPendingScale = 1.0F;
            } else {
                this.playerJumpPendingScale = 0.4F + 0.4F * (float)i / 90.0F;
            }

        }
    }

    @Override
    public boolean canJump() {
        return !isFlying();
    }

    @Override
    public void handleStartJump(int i) {

    }

    @Override
    public void handleStopJump() {

    }

    ///////////////////////////------Sound--------///////////////////////////////
    @Override
    public void equipSaddle(@Nullable SoundSource soundSource) {
        setSaddled(true);
        if (soundSource != null) {
            this.level().playSound(null, this, getSaddleSoundEvent(), soundSource, 0.5f, 1.0f);
        }
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.RAVAGER_STEP, 0.15F, 1.0F);
    }
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDER_DRAGON_AMBIENT;
    }
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        List<SoundEvent> hurtSounds = Arrays.asList(
                SoundEvents.RAVAGER_STUNNED,
                SoundEvents.RAVAGER_ROAR,
                SoundEvents.RAVAGER_HURT
        );
        Random random = new Random();
        int index = random.nextInt(hurtSounds.size());
        return hurtSounds.get(index);
    }
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDER_DRAGON_GROWL;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final EntityDataAccessor<Boolean> DATA_FLYING = SynchedEntityData.defineId(FireDragon.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SADDLED = SynchedEntityData.defineId(FireDragon.class, EntityDataSerializers.BOOLEAN);
    private static final String NBT_SADDLED = "Saddle";

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        FireDragonAi.onInitialize(this);
        return super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }
    @Override
    public float getScale() {
        return BASE_SIZE_MODIFIER;
        //return (0.33f + (0.67f * BASE_SIZE_MODIFIER));
    }
    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, BASE_SPEED_GROUND)
                .add(Attributes.MAX_HEALTH, BASE_HEALTH)
                .add(Attributes.FOLLOW_RANGE, 64)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2.5)
                .add(Attributes.ATTACK_DAMAGE, BASE_DAMAGE)
                .add(Attributes.FLYING_SPEED, BASE_SPEED_FLYING);
    }
    public void setRidingPlayer(Player player) {
        player.setYRot(getYRot());
        player.setXRot(getXRot());
        player.startRiding(this);
    }
    @Override
    public LivingEntity getControllingPassenger() {
        List<Entity> list = getPassengers();
        Entity e = list.isEmpty() ? null : list.get(0);
        if(e instanceof LivingEntity l) return l;
        return null;
    }
    public boolean isServer() {
        return !level().isClientSide();
    }
    @Override
    protected float getSoundVolume() {
        return getScale();
    }
    @Override
    public boolean canSprint() {
        return true;
    }
    @Override
    public float getVoicePitch() {
        return 2 - getScale();
    }
    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }
    @Override
    public boolean onClimbable() {
        return false;
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        if (isSaddled()) spawnAtLocation(Items.SADDLE);
    }
    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        triggerAnim("Death", "death");
    }
    @Override
    protected void tickDeath() {
        ejectPassengers();

        // freeze at place
        setDeltaMovement(Vec3.ZERO);
        //setYRot(yRotO);
        //setYHeadRot(yHeadRotO);
        if (deathTime >= DEATH_DURATION) remove(RemovalReason.KILLED); // actually delete entity after the time is up

        deathTime++;
    }
    @Override
    public boolean isSaddled() {
        return entityData.get(DATA_SADDLED);
    }
    public void setSaddled(boolean bl) {
        entityData.set(DATA_SADDLED, bl);
    }
    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }
    public void setFlying(boolean flying) {
        entityData.set(DATA_FLYING, flying);
    }
    @Override
    public boolean isFlying() {
        return entityData.get(DATA_FLYING);
    }



    public void setNavigation(boolean flying) {
        navigation = flying ? flyingNavigation : groundNavigation;
    }
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }
    public float getSpeedSpecial(){
        return (float) (getAttributeValue(isFlying() ? Attributes.FLYING_SPEED : Attributes.MOVEMENT_SPEED) * 0.225f);
    }
    @Override
    protected @NotNull BodyRotationControl createBodyControl() {
        return new DragonBodyController(this);
    }
    public boolean shouldFly() {
        return !isInWater() && isHighEnough(ALTITUDE_FLYING_THRESHOLD);
    }

    /**
     * Returns the int-precision distance to solid ground.
     * Describe an inclusive limit to reduce iterations.
     */
    public double getAltitude(int limit) {
        var pointer = blockPosition().mutable().move(0, -1, 0);
        var min = level().dimensionType().minY();
        var i = 0;

        while(i <= limit && pointer.getY() > min && !level().getBlockState(pointer).isSolid())
            pointer.setY(getBlockY() - ++i);

        return i;
    }
    public double getAltitude2(int limit) {
        var pointer = blockPosition().mutable().move(0, -1, 0);
        var min = level().dimensionType().minY();
        var i = 0;

        while(i <= limit && pointer.getY() > min && !level().getBlockState(pointer).isSolid())
            pointer.setY(getBlockY() - ++i);

        return i;
    }


    /**
     * Returns the distance to the ground while the entity is flying.
     */
    public double getAltitude() {
        return getAltitude(level().getMaxBuildHeight());
    }
    public boolean isHighEnough(int height) {
        return getAltitude(height) >= height;
    }


}
