package satisfy.dragonflame.entity;

import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import satisfy.dragonflame.entity.ai.PatrolGoal;

import java.util.Map;

public class ArmoredPillager extends AbstractIllager implements CrossbowAttackMob, InventoryCarrier, IPatrollingMob {
    private final SimpleContainer inventory = new SimpleContainer(10);
    private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW;

    public ArmoredPillager(EntityType<? extends ArmoredPillager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, DragonWhelpling.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Skeleton.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Zombie.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, EnderMan.class, true));
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new RangedCrossbowAttackGoal<>(this, 1.0D, 14.0F));
        this.goalSelector.addGoal(1, new PatrolGoal<>(this, 0.7, 0.595));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 15.0F));

    }
    
    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 48.0)
                .add(Attributes.ARMOR, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.ARMOR_TOUGHNESS, 1.5)
                .add(Attributes.MOVEMENT_SPEED, 0.3599999940395355)
                .add(Attributes.FOLLOW_RANGE, 18.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CHARGING_CROSSBOW, false);
    }

    public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeapon) {
        return projectileWeapon == Items.CROSSBOW;
    }

    public boolean isChargingCrossbow() {
        return this.entityData.get(IS_CHARGING_CROSSBOW);
    }

    public void setChargingCrossbow(boolean charging) {
        this.entityData.set(IS_CHARGING_CROSSBOW, charging);
    }

    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.writeInventoryToTag(compound);
    }

    public AbstractIllager.@NotNull IllagerArmPose getArmPose() {
        if (this.isChargingCrossbow()) {
            return IllagerArmPose.CROSSBOW_CHARGE;
        } else if (this.isHolding(Items.CROSSBOW)) {
            return IllagerArmPose.CROSSBOW_HOLD;
        } else {
            return this.isAggressive() ? IllagerArmPose.ATTACKING : IllagerArmPose.NEUTRAL;
        }
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readInventoryFromTag(compound);
        this.setCanPickUpLoot(true);
    }

    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0.0F;
    }

    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        RandomSource randomSource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, difficulty);
        this.populateDefaultEquipmentEnchantments(randomSource, difficulty);
        return super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
    }

    protected void populateDefaultEquipmentEnchantments(RandomSource random, DifficultyInstance difficulty) {
        ItemStack itemStack = new ItemStack(Items.CROSSBOW);
        Map<Enchantment, Integer> enchantments = Map.of(
                Enchantments.PIERCING, 4,
                Enchantments.MULTISHOT, 1

        );
        EnchantmentHelper.setEnchantments(enchantments, itemStack);
        this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
    }


    protected void enchantSpawnedWeapon(RandomSource random, float chanceMultiplier) {
        super.enchantSpawnedWeapon(random, chanceMultiplier);
        if (random.nextInt(300) == 0) {
            ItemStack itemStack = this.getMainHandItem();
            if (itemStack.is(Items.CROSSBOW)) {
                Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
                map.putIfAbsent(Enchantments.PIERCING, 1);
                EnchantmentHelper.setEnchantments(map, itemStack);
                this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
            }
        }
    }

    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) {
            return true;
        } else if (entity instanceof LivingEntity && ((LivingEntity)entity).getMobType() == MobType.ILLAGER) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PILLAGER_HURT;
    }

    public void performRangedAttack(LivingEntity target, float velocity) {
        this.performCrossbowAttack(this, 1.6F);
    }

    public void shootCrossbowProjectile(LivingEntity target, ItemStack crossbowStack, Projectile projectile, float projectileAngle) {
        this.shootCrossbowProjectile(this, target, projectile, projectileAngle, 1.6F);
    }

    public @NotNull SimpleContainer getInventory() {
        return this.inventory;
    }

    protected void pickUpItem(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        if (itemStack.getItem() instanceof BannerItem) {
            super.pickUpItem(itemEntity);
        } else if (this.wantsItem(itemStack)) {
            this.onItemPickup(itemEntity);
            ItemStack itemStack2 = this.inventory.addItem(itemStack);
            if (itemStack2.isEmpty()) {
                itemEntity.discard();
            } else {
                itemStack.setCount(itemStack2.getCount());
            }
        }

    }

    private boolean wantsItem(ItemStack item) {
        return this.hasActiveRaid() && item.is(Items.WHITE_BANNER);
    }

    public @NotNull SlotAccess getSlot(int slot) {
        int i = slot - 300;
        return i >= 0 && i < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, i) : super.getSlot(slot);
    }

    public void applyRaidBuffs(int wave, boolean unusedFalse) {
        Raid raid = this.getCurrentRaid();
        assert raid != null;
        boolean bl = this.random.nextFloat() <= raid.getEnchantOdds();
        if (bl) {
            ItemStack itemStack = new ItemStack(Items.CROSSBOW);
            Map<Enchantment, Integer> map = Maps.newHashMap();
            if (wave > raid.getNumGroups(Difficulty.NORMAL)) {
                map.put(Enchantments.QUICK_CHARGE, 2);
            } else if (wave > raid.getNumGroups(Difficulty.EASY)) {
                map.put(Enchantments.QUICK_CHARGE, 1);
            }

            map.put(Enchantments.MULTISHOT, 1);
            EnchantmentHelper.setEnchantments(map, itemStack);
            this.setItemSlot(EquipmentSlot.MAINHAND, itemStack);
        }

    }

    public @NotNull SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    static {
        IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(ArmoredPillager.class, EntityDataSerializers.BOOLEAN);
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
