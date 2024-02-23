package satisfy.dragonflame.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import satisfy.dragonflame.block.LootChestBlock;
import satisfy.dragonflame.registry.BlockEntityRegistry;
import satisfy.dragonflame.client.gui.LootChestScreenhandler;
import satisfy.dragonflame.registry.ObjectRegistry;
import satisfy.dragonflame.registry.SoundEventRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class LootChestEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {
    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;
    private final ChestLidController chestLidController = new ChestLidController();
    private Random random = new Random();
    private int fireworkTicks = 0;
    private boolean isFireworkActive = false;


    public LootChestEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.LOOTCHEST_BLOCK_ENTITY, blockPos, blockState);
        this.items = NonNullList.withSize(9, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
                LootChestEntity.this.playSound(blockState, SoundEventRegistry.LOOTCHEST_OPEN.get());
            }

            protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
                LootChestEntity.this.playSound(blockState, SoundEventRegistry.LOOTCHEST_CLOSE.get());
            }

            protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int i, int j) {
                LootChestEntity.this.signalOpenCount(level, blockPos, blockState, i, j);
            }

            protected boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)player.containerMenu).getContainer();
                    return container == LootChestEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.items);
        }
    }

    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)) {
            ContainerHelper.loadAllItems(compoundTag, this.items);
        }
    }

    protected Component getDefaultName() {
        return Component.translatable(this.getBlockState().getBlock().getDescriptionId());
    }

    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new LootChestScreenhandler(i, inventory, this);
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    public int getContainerSize() {
        return 9;
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            if (this.items.stream().allMatch(ItemStack::isEmpty)) {
                generateLoot();
            }
            if (!level.isClientSide) {
                this.isFireworkActive = true;
                this.fireworkTicks = 100;
                shootFireworks(level, worldPosition, random);
            }
            this.openersCounter.incrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
    }


    public void shootFireworks(Level level, BlockPos pos, Random random) {
        int fireworkCount = 8 + random.nextInt(7);

        for (int i = 0; i < fireworkCount; i++) {
            int color = random.nextInt(0xFFFFFF);
            ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
            CompoundTag fireworkTag = new CompoundTag();
            CompoundTag fireworkExplosions = new CompoundTag();
            ListTag explosionsList = new ListTag();
            CompoundTag explosion = new CompoundTag();

            explosion.putByte("Flicker", (byte) (random.nextBoolean() ? 1 : 0));
            explosion.putByte("Trail", (byte) (random.nextBoolean() ? 1 : 0));
            explosion.putIntArray("Colors", new int[]{color});
            explosion.putByte("Type", (byte) 0);
            explosionsList.add(explosion);

            fireworkExplosions.put("Explosions", explosionsList);
            fireworkExplosions.putByte("Flight", (byte) (1 + random.nextInt(2)));
            fireworkTag.put("Fireworks", fireworkExplosions);
            fireworkStack.setTag(fireworkTag);

            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 2.0;
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 2.0;

            FireworkRocketEntity fireworkRocket = new FireworkRocketEntity(level, x, y, z, fireworkStack);
            level.addFreshEntity(fireworkRocket);
        }
    }

    public void tick(Level level, BlockPos pos, BlockState state, LootChestEntity entity) {
        if (!level.isClientSide && entity.isFireworkActive) {
            if (entity.fireworkTicks > 0) {
                if (entity.fireworkTicks % 15 == 0) {
                    entity.shootFireworks(level, pos, entity.random);
                }
                entity.fireworkTicks--;
            } else {
                entity.isFireworkActive = false;
            }
        }
    }


    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
    }

    void playSound(BlockState blockState, SoundEvent soundEvent) {
        Vec3i vec3i = blockState.getValue(LootChestBlock.FACING).getNormal();
        double d = this.worldPosition.getX() + 0.5D + vec3i.getX() / 2.0D;
        double e = this.worldPosition.getY() + 0.5D + vec3i.getY() / 2.0D;
        double f = this.worldPosition.getZ() + 0.5D + vec3i.getZ() / 2.0D;
        this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    public float getOpenNess(float f) {
        return this.chestLidController.getOpenness(f);
    }

    public static void lidAnimateTick(Level level, BlockPos blockPos, BlockState blockState, LootChestEntity lootChestEntity) {
        lootChestEntity.chestLidController.tickLid();
    }

    public boolean triggerEvent(int i, int j) {
        if (i == 1) {
            this.chestLidController.shouldBeOpen(j > 0);
            return true;
        } else {
            return super.triggerEvent(i, j);
        }
    }

    protected void signalOpenCount(Level level, BlockPos blockPos, BlockState blockState, int i, int j) {
        Block block = blockState.getBlock();
        level.blockEvent(blockPos, block, 1, j);
    }
    //TODO: Rework loottable
    public void generateLoot() {
        List<ItemStack> possibleLoot = new ArrayList<>();
        possibleLoot.add(new ItemStack(ObjectRegistry.HEART_OF_FLAME.get(), 1));
        for (int i = 0; i < (random.nextInt(3) + 1); i++) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_EMBLEM.get(), 1));
        if (random.nextFloat() < 0.25) for (int i = 0; i < (random.nextInt(4) + 1); i++) possibleLoot.add(new ItemStack(ObjectRegistry.TITAN_INGOT.get(), 1));
        for (int i = 0; i < (random.nextInt(12) + 1); i++) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_TEARS.get(), 1));
        if (random.nextFloat() < 0.02) possibleLoot.add(new ItemStack(ObjectRegistry.EMBERGRASP.get(), 1));
        if (random.nextFloat() < 0.05) possibleLoot.add(new ItemStack(ObjectRegistry.FLAMETHROWER.get(), 1));
        if (random.nextFloat() < 0.01) possibleLoot.add(new ItemStack(ObjectRegistry.THOAREL_BOW.get(), 1));
        if (random.nextFloat() < 0.03) possibleLoot.add(new ItemStack(ObjectRegistry.RAUBBAU.get(), 1));
        if (random.nextFloat() < 0.01) possibleLoot.add(new ItemStack(ObjectRegistry.QUALAMRAR.get(), 1));
        if (random.nextFloat() < 0.40) possibleLoot.add(new ItemStack(ObjectRegistry.HEARTHSTONE.get(), 1));
        if (random.nextFloat() < 0.40) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_HEARTH.get(), 1));
        if (random.nextFloat() < 0.15) possibleLoot.add(new ItemStack(ObjectRegistry.HEARTHSTONE.get(), 1));
        if (random.nextFloat() < 0.12) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_HEAD_HELMET.get(), 1));
        if (random.nextFloat() < 0.15) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_EYE.get(), 1));

        if (random.nextFloat() < 0.005) possibleLoot.add(new ItemStack(ObjectRegistry.FIERY_WARHORSE_SPAWN_EGG.get(), 1));
        for (int i = 0; i < (random.nextInt(54) + 1); i++) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_BONES.get(), 1));

        int itemsToAdd = 4 + random.nextInt(4);
        for (int i = 0; i < itemsToAdd; i++) {
            if (possibleLoot.isEmpty()) break;
            ItemStack item = possibleLoot.remove(random.nextInt(possibleLoot.size()));
            addItem(item);
        }
    }

    private void addItem(ItemStack itemToAdd) {
        for (ItemStack stack : items) {
            if (stack.isEmpty()) {
                items.set(items.indexOf(stack), itemToAdd.copy());
                break;
            } else if (ItemStack.isSameItemSameTags(stack, itemToAdd) && stack.getCount() < stack.getMaxStackSize()) {
                int countToAdd = Math.min(itemToAdd.getCount(), stack.getMaxStackSize() - stack.getCount());
                stack.grow(countToAdd);
                itemToAdd.shrink(countToAdd);
                if (itemToAdd.isEmpty()) {
                    break;
                }
            }
        }
    }
}
