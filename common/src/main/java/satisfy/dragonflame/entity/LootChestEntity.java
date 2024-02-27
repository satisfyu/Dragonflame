package satisfy.dragonflame.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.core.NonNullList;
import org.jetbrains.annotations.NotNull;
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
    private final Random random = new Random();

    public LootChestEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegistry.LOOTCHEST_BLOCK_ENTITY.get(), blockPos, blockState);
        this.items = NonNullList.withSize(9, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
                LootChestEntity.this.playSound(SoundEventRegistry.LOOTCHEST_OPEN.get());
            }

            protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
                LootChestEntity.this.playSound(SoundEventRegistry.LOOTCHEST_CLOSE.get());
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

    protected @NotNull Component getDefaultName() {
        return Component.empty();
    }

    protected @NotNull AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new LootChestScreenhandler(i, inventory, this);
    }

    protected @NotNull NonNullList<ItemStack> getItems() {
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
            this.openersCounter.incrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, Objects.requireNonNull(this.getLevel()), this.getBlockPos(), this.getBlockState());
        }
    }

    void playSound(SoundEvent soundEvent) {
        double d = this.worldPosition.getX() + 0.5D;
        double e = this.worldPosition.getY() + 0.5D;
        double f = this.worldPosition.getZ() + 0.5D;
        assert this.level != null;
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

    public void generateLoot() {
        addItemRandomly(new ItemStack(ObjectRegistry.HEART_OF_FLAME.get(), 1));
        addItemRandomly(new ItemStack(ObjectRegistry.DRAGON_EMBLEM.get(), random.nextInt(3) + 2));
        addItemRandomly(new ItemStack(ObjectRegistry.DRAGON_TEARS.get(), random.nextInt(44) + 17));
        addItemRandomly(new ItemStack(ObjectRegistry.DRAGON_BONES.get(), random.nextInt(54) + 12));
        addItemRandomly(new ItemStack(ObjectRegistry.DRAGONSCALE.get(), random.nextInt(12) + 4));
        List<ItemStack> possibleLoot = new ArrayList<>();
        if (random.nextFloat() < 0.25) for (int i = 0; i < (random.nextInt(4) + 1); i++) possibleLoot.add(new ItemStack(ObjectRegistry.TITAN_PLATES.get(), 1));
        if (random.nextFloat() < 0.05) possibleLoot.add(new ItemStack(ObjectRegistry.EMBERGRASP.get(), 1));
        if (random.nextFloat() < 0.1) possibleLoot.add(new ItemStack(ObjectRegistry.FLAMETHROWER.get(), 1));
        if (random.nextFloat() < 0.05) possibleLoot.add(new ItemStack(ObjectRegistry.THOAREL_BOW.get(), 1));
        if (random.nextFloat() < 0.3) possibleLoot.add(new ItemStack(ObjectRegistry.RAUBBAU.get(), 1));
        if (random.nextFloat() < 0.30) possibleLoot.add(new ItemStack(ObjectRegistry.DRACONIC_FOR_DUMMIES.get(), 1));
        if (random.nextFloat() < 0.05) possibleLoot.add(new ItemStack(ObjectRegistry.QUALAMRAR.get(), 1));
        if (random.nextFloat() < 0.40) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_HEARTH.get(), 1));
        if (random.nextFloat() < 0.15) possibleLoot.add(new ItemStack(ObjectRegistry.HEARTHSTONE.get(), 1));
        if (random.nextFloat() < 0.12) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_HEAD_HELMET.get(), 1));
        if (random.nextFloat() < 0.15) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_EYE.get(), 1));
        if (random.nextFloat() < 0.20) possibleLoot.add(new ItemStack(ObjectRegistry.FIERY_WARAXE.get(), 1));
        if (random.nextFloat() < 0.005) possibleLoot.add(new ItemStack(ObjectRegistry.FIERY_WARHORSE_SPAWN_EGG.get(), 1));
        if (random.nextFloat() < 0.008) possibleLoot.add(new ItemStack(ObjectRegistry.SHATTERBRAND.get(), 1));
        for (int i = 0; i < (random.nextInt(33) + 1); i++) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGON_BONES.get(), 1));
        for (int i = 0; i < (random.nextInt(44) + 1); i++) possibleLoot.add(new ItemStack(ObjectRegistry.DRAGONSCALE.get(), 1));

        int itemsToAdd = 4 + random.nextInt(4);
        for (int i = 0; i < itemsToAdd; i++) {
            if (possibleLoot.isEmpty()) break;
            ItemStack item = possibleLoot.remove(random.nextInt(possibleLoot.size()));
            addItemRandomly(item);
        }
    }


    private void addItemRandomly(ItemStack itemToAdd) {
        if (itemToAdd.isEmpty()) return;

        for (ItemStack stack : this.items) {
            if (ItemStack.isSameItemSameTags(stack, itemToAdd) && stack.getCount() < stack.getMaxStackSize()) {
                int countToAdd = Math.min(itemToAdd.getCount(), stack.getMaxStackSize() - stack.getCount());
                stack.grow(countToAdd);
                itemToAdd.shrink(countToAdd);
                if (itemToAdd.isEmpty()) {
                    return;
                }
            }
        }
        List<Integer> emptySlots = new ArrayList<>();
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).isEmpty()) {
                emptySlots.add(i);
            }
        }
        while (!itemToAdd.isEmpty() && !emptySlots.isEmpty()) {
            int randomIndex = this.random.nextInt(emptySlots.size());
            int slotIndex = emptySlots.get(randomIndex);
            ItemStack newItemStack = itemToAdd.copy();
            newItemStack.setCount(itemToAdd.getCount());
            this.items.set(slotIndex, newItemStack);
            itemToAdd.setCount(0);
        }
    }
}
