package satisfy.dragonflame.registry;

import de.cristelknight.doapi.Util;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.block.DragonEggBlock;
import satisfy.dragonflame.block.*;
import satisfy.dragonflame.item.*;
import satisfy.dragonflame.item.armor.*;
import satisfy.dragonflame.item.tools.*;
import satisfy.dragonflame.util.DragonflameIdentifier;
import satisfy.dragonflame.util.GeneralUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ObjectRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Dragonflame.MOD_ID, Registries.ITEM);
    public static final Registrar<Item> ITEM_REGISTRAR = ITEMS.getRegistrar();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Dragonflame.MOD_ID, Registries.BLOCK);
    public static final Registrar<Block> BLOCK_REGISTRAR = BLOCKS.getRegistrar();

    public static final RegistrySupplier<Block> DRAGON_LOG = registerWithItem("dragon_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> DRAGON_WOOD = registerWithItem("dragon_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_DRAGON_WOOD = registerWithItem("stripped_dragon_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> STRIPPED_DRAGON_LOG = registerWithItem("stripped_dragon_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> DRAGON_PLANKS = registerWithItem("dragon_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> DRAGON_STAIRS = registerWithItem("dragon_stairs", () -> new StairBlock(DRAGON_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> DRAGON_SLAB = registerWithItem("dragon_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> DRAGON_PRESSURE_PLATE = registerWithItem("dragon_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(DRAGON_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> DRAGON_BUTTON = registerWithItem("dragon_button", () -> woodenButton(BlockSetType.OAK, FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> DRAGON_TRAPDOOR = registerWithItem("dragon_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> DRAGON_DOOR = registerWithItem("dragon_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(DRAGON_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> DRAGON_FENCE = registerWithItem("dragon_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> DRAGON_FENCE_GATE = registerWithItem("dragon_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(DRAGON_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> DRAGON_LEAVES = registerWithItem("dragon_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GRASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false)));
    public static final RegistrySupplier<Block> DRAGON_WINDOW = registerWithItem("dragon_window", () -> new DragonWindowBlock(BlockBehaviour.Properties.of().strength(0.2f).randomTicks().sound(SoundType.GLASS).noOcclusion().isViewBlocking((state, world, pos) -> false).isSuffocating((state, world, pos) -> false)));
    public static final RegistrySupplier<Block> DRAGON_SAPLING = registerWithItem("dragon_sapling", () -> new SaplingBlock(new AbstractTreeGrower() {@Override protected @NotNull ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {return GeneralUtil.configuredFeatureKey("dragon");}}, BlockBehaviour.Properties.copy(Blocks.SPRUCE_SAPLING)));
    public static final RegistrySupplier<Block> BURNT_LOG = registerWithItem("burnt_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> BURNT_WOOD = registerWithItem("burnt_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).sound(SoundType.WOOD).strength(2.0f)));
    public static final RegistrySupplier<Block> BURNT_PLANKS = registerWithItem("burnt_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final RegistrySupplier<Block> BURNT_STAIRS = registerWithItem("burnt_stairs", () -> new StairBlock(BURNT_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> BURNT_SLAB = registerWithItem("burnt_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> BURNT_PRESSURE_PLATE = registerWithItem("burnt_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().noCollission().strength(0.5f).sound(SoundType.WOOD).mapColor(BURNT_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> BURNT_BUTTON = registerWithItem("burnt_button", () -> woodenButton(BlockSetType.OAK, FeatureFlags.VANILLA));
    public static final RegistrySupplier<Block> BURNT_TRAPDOOR = registerWithItem("burnt_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
    public static final RegistrySupplier<Block> BURNT_DOOR = registerWithItem("burnt_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.WOOD).noOcclusion().mapColor(BURNT_PLANKS.get().defaultMapColor()), BlockSetType.OAK));
    public static final RegistrySupplier<Block> BURNT_FENCE = registerWithItem("burnt_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> BURNT_FENCE_GATE = registerWithItem("burnt_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().strength(2.0f, 3.0f).sound(SoundType.WOOD).mapColor(BURNT_PLANKS.get().defaultMapColor()), WoodType.OAK));
    public static final RegistrySupplier<Block> WHITE_SAND = registerWithItem("white_sand", () -> new SandBlock(0xffffff, BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.SAND)));
    public static final RegistrySupplier<Block> BLACK_SAND = registerWithItem("black_sand", () -> new SandBlock(0x000000, BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.SAND)));
    public static final RegistrySupplier<Block> DRAGONSTONE = registerWithItem("dragonstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> DRAGONSTONE_STAIRS = registerWithItem("dragonstone_stairs", () -> new StairBlock(DRAGONSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> DRAGONSTONE_SLAB = registerWithItem("dragonstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> COBBLED_DRAGONSTONE = registerWithItem("cobbled_dragonstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE)));
    public static final RegistrySupplier<Block> COBBLED_DRAGONSTONE_STAIRS = registerWithItem("cobbled_dragonstone_stairs", () -> new StairBlock(COBBLED_DRAGONSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(COBBLED_DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> COBBLED_DRAGONSTONE_SLAB = registerWithItem("cobbled_dragonstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(COBBLED_DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> DRAGONSTONE_BRICKS = registerWithItem("dragonstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistrySupplier<Block> DRAGONSTONE_BRICK_STAIRS = registerWithItem("dragonstone_brick_stairs", () -> new StairBlock(DRAGONSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DRAGONSTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> DRAGONSTONE_BRICK_SLAB = registerWithItem("dragonstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(DRAGONSTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_DRAGONSTONE = registerWithItem("mossy_cobbled_dragonstone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE)));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_DRAGONSTONE_STAIRS = registerWithItem("mossy_cobbled_dragonstone_stairs", () -> new StairBlock(MOSSY_COBBLED_DRAGONSTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(MOSSY_COBBLED_DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_DRAGONSTONE_SLAB = registerWithItem("mossy_cobbled_dragonstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_COBBLED_DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> MOSSY_DRAGONSTONE_BRICKS = registerWithItem("mossy_dragonstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS)));
    public static final RegistrySupplier<Block> MOSSY_DRAGONSTONE_BRICK_STAIRS = registerWithItem("mossy_dragonstone_brick_stairs", () -> new StairBlock(DRAGONSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(DRAGONSTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_DRAGONSTONE_BRICK_SLAB = registerWithItem("mossy_dragonstone_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(MOSSY_DRAGONSTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> CRACKED_DRAGONSTONE_BRICKS = registerWithItem("cracked_dragonstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final RegistrySupplier<Block> DRAGONSTONE_WALL = registerWithItem("dragonstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> COBBLED_DRAGONSTONE_WALL = registerWithItem("cobbled_dragonstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(COBBLED_DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> DRAGONSTONE_BRICK_WALL = registerWithItem("dragonstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(DRAGONSTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> MOSSY_COBBLED_DRAGONSTONE_WALL = registerWithItem("mossy_cobbled_dragonstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_COBBLED_DRAGONSTONE.get())));
    public static final RegistrySupplier<Block> MOSSY_DRAGONSTONE_BRICK_WALL = registerWithItem("mossy_dragonstone_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(MOSSY_DRAGONSTONE_BRICKS.get())));
    public static final RegistrySupplier<Block> CHISELED_DRAGONSTONE_BRICKS = registerWithItem("chiseled_dragonstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHISELED_STONE_BRICKS)));
    public static final RegistrySupplier<Block> POLISHED_DRAGONSTONE_BRICKS = registerWithItem("polished_dragonstone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_ANDESITE)));
    public static final RegistrySupplier<Block> TITAN_ORE = registerWithItem("titan_ore", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(50.0F, 1200.0F)));
    public static final RegistrySupplier<Block> TITAN_BLOCK = registerWithItem("titan_block", () -> new Block(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Block> TITAN_STAIRS = registerWithItem("titan_stairs", () -> new StairBlock(TITAN_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(2.0F, 3.0F).sound(SoundType.METAL)));
    public static final RegistrySupplier<Block> TITAN_SLAB = registerWithItem("titan_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistrySupplier<Item>  ARMORED_PILLAGER_SPAWN_EGG = registerItem("armored_pillager_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.ARMORED_PILLAGER, -1, -1, getSettings()));
    public static final RegistrySupplier<Item>  ARMORED_VINDICATOR_SPAWN_EGG = registerItem("armored_vindicator_spawn_egg", () -> new ArchitecturySpawnEggItem(EntityRegistry.ARMORED_VINDICATOR, -1, -1, getSettings()));
    public static final RegistrySupplier<Item>  FIREDRAGON_SPAWN_EGG = registerItem("fire_dragon_spawn_egg", () ->  new ArchitecturySpawnEggItem(EntityRegistry.FIREDRAGON, 0x725D78, 0xE6792C, getSettings()));
    public static final RegistrySupplier<Item>  PILLAGER_DOG_SPAWN_EGG = registerItem("pillager_dog_spawn_egg",  () -> new ArchitecturySpawnEggItem(EntityRegistry.ARMORED_PILLAGER_DOG, -1, -1, getSettings()));
    //public static final RegistrySupplier<Block> ARCANE_WALL_TORCH = registerWithItem("arcane_wall_torch", () -> new ArcaneWallTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(state -> 5).sound(SoundType.WOOD)));
    //public static final RegistrySupplier<Block> ARCANE_GROUND_TORCH = registerWithItem("arcane_ground_torch", () -> new ArcaneGroundTorchBlock(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel(state -> 5).sound(SoundType.WOOD)));
    //public static final RegistrySupplier<Item> ARCANE_TORCH = registerWithItem("arcane_torch", () -> new ArcaneTorchItem(ARCANE_GROUND_TORCH.get(), ARCANE_WALL_TORCH.get(), getSettings()));
    public static final RegistrySupplier<Item> REINFORCED_LEATHER_HELMET = registerItem("reinforced_leather_helmet", () -> new ReinforcedLeatherHelmet(ArmorMaterialRegistry.REINFORCED_LEATHER_ARMOR, getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> REINFORCED_LEATHER_CHESTPLATE = registerItem("reinforced_leather_chestplate", () -> new ReinforcedLeatherChestplate(ArmorMaterialRegistry.REINFORCED_LEATHER_ARMOR, getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> REINFORCED_LEATHER_LEGGINGS = registerItem("reinforced_leather_leggings", () -> new ReinforcedLeatherLeggings(ArmorMaterialRegistry.REINFORCED_LEATHER_ARMOR, getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> REINFORCED_LEATHER_BOOTS = registerItem("reinforced_leather_boots", () -> new ReinforcedLeatherBoots(ArmorMaterialRegistry.REINFORCED_LEATHER_ARMOR, getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> DRAGON_HELMET = registerItem("dragon_helmet", () -> new DragonHelmet(ArmorMaterialRegistry.DRAGON_ARMOR, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> DRAGON_CHESTPLATE = registerItem("dragon_chestplate", () -> new DragonChestplate(ArmorMaterialRegistry.DRAGON_ARMOR, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> DRAGON_LEGGINGS = registerItem("dragon_leggings", () -> new DragonLeggings(ArmorMaterialRegistry.DRAGON_ARMOR, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> DRAGON_BOOTS = registerItem("dragon_boots", () -> new DragonBoots(ArmorMaterialRegistry.DRAGON_ARMOR, getSettings().rarity(Rarity.COMMON)));
    public static final RegistrySupplier<Item> DRAGON_SWORD = registerItem("dragon_sword", () -> new SwordItem(ToolTiersRegistry.DRAGON, 3, -2.4F, getSettings().fireResistant().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DRAGON_AXE = registerItem("dragon_axe", () -> new AxeItem(ToolTiersRegistry.DRAGON, 5.0F, -3.0F, getSettings().fireResistant().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DRAGON_PICKAXE = registerItem("dragon_pickaxe", () -> new PickaxeItem(ToolTiersRegistry.DRAGON, 1, -2.8F, getSettings().fireResistant().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DRAGON_SHOVEL = registerItem("dragon_shovel", () -> new ShovelItem(ToolTiersRegistry.DRAGON, 1.5F, -3.0F, getSettings().fireResistant().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DRAGON_HOE = registerItem("dragon_hoe", () -> new HoeItem(ToolTiersRegistry.DRAGON, -3, 0.0F, getSettings().fireResistant().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DRAGON_BOW = registerItem("dragon_bow", () -> new BowItem(new Item.Properties().stacksTo(1).durability(520).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> DRAGON_CROSSBOW = registerItem("dragon_crossbow", () -> new CrossbowItem(new Item.Properties().stacksTo(1).durability(520).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> TITAN_HELMET = registerItem("titan_helmet", () -> new TitanHelmet(ArmorMaterialRegistry.TITAN_ARMOR, getSettings().rarity(Rarity.RARE).fireResistant()));
    public static final RegistrySupplier<Item> TITAN_CHESTPLATE = registerItem("titan_chestplate", () -> new TitanChestplate(ArmorMaterialRegistry.TITAN_ARMOR, getSettings().rarity(Rarity.RARE).fireResistant()));
    public static final RegistrySupplier<Item> TITAN_LEGGINGS = registerItem("titan_leggings", () -> new TitanLeggings(ArmorMaterialRegistry.TITAN_ARMOR, getSettings().rarity(Rarity.RARE).fireResistant()));
    public static final RegistrySupplier<Item> TITAN_BOOTS = registerItem("titan_boots", () -> new TitanBoots(ArmorMaterialRegistry.TITAN_ARMOR, getSettings().rarity(Rarity.RARE).fireResistant()));
    public static final RegistrySupplier<Item> TITAN_SWORD = registerItem("titan_sword", () -> new SwordItem(ToolTiersRegistry.TITAN, 4, -2.4F, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> TITAN_AXE = registerItem("titan_axe", () -> new AxeItem(ToolTiersRegistry.TITAN, 5.0F, -3.0F, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> TITAN_PICKAXE = registerItem("titan_pickaxe", () -> new PickaxeItem(ToolTiersRegistry.TITAN, 1, -2.8F, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> TITAN_SHOVEL = registerItem("titan_shovel", () -> new ShovelItem(ToolTiersRegistry.TITAN, 1.5F, -3.0F, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> TITAN_HOE = registerItem("titan_hoe", () -> new HoeItem(ToolTiersRegistry.TITAN, -4, 0.0F, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> TITAN_BOW = registerItem("titan_bow", () -> new BowItem(new Item.Properties().stacksTo(1).durability(800).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> TITAN_CROSSBOW = registerItem("titan_crossbow", () -> new CrossbowItem(new Item.Properties().stacksTo(1).durability(800).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HARDENED_TITAN_HELMET = registerItem("hardened_titan_helmet", () -> new HardenedTitanHelmet(ArmorMaterialRegistry.HARDENED_TITAN_ARMOR, getSettings().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistrySupplier<Item> HARDENED_TITAN_CHESTPLATE = registerItem("hardened_titan_chestplate", () -> new HardenedTitanChestplate(ArmorMaterialRegistry.HARDENED_TITAN_ARMOR, getSettings().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistrySupplier<Item> HARDENED_TITAN_LEGGINGS = registerItem("hardened_titan_leggings", () -> new HardenedTitanLeggings(ArmorMaterialRegistry.HARDENED_TITAN_ARMOR, getSettings().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistrySupplier<Item> HARDENED_TITAN_BOOTS = registerItem("hardened_titan_boots", () -> new HardenedTitanBoots(ArmorMaterialRegistry.HARDENED_TITAN_ARMOR, getSettings().rarity(Rarity.EPIC).fireResistant()));
    public static final RegistrySupplier<Item> HARDENED_TITAN_SWORD = registerItem("hardened_titan_sword", () -> new HardenedTitanSwordItem(ToolTiersRegistry.HARDENED_TITAN, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HARDENED_TITAN_AXE = registerItem("hardened_titan_axe", () -> new HardenedTitanAxeItem(ToolTiersRegistry.HARDENED_TITAN, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HARDENED_TITAN_PICKAXE = registerItem("hardened_titan_pickaxe", () -> new HardenedTitanPickaxeItem(ToolTiersRegistry.HARDENED_TITAN, 1, -2.8F, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HARDENED_TITAN_SHOVEL = registerItem("hardened_titan_shovel", () -> new HardenedTitanShovelItem(ToolTiersRegistry.HARDENED_TITAN, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HARDENED_TITAN_SCYTHE = registerItem("hardened_titan_scythe", () -> new HardenedTitanScytheItem(ToolTiersRegistry.HARDENED_TITAN, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HARDENED_TITAN_BOW = registerItem("hardened_titan_bow", () -> new BowItem(new Item.Properties().stacksTo(1).durability(1200).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> HARDENED_TITAN_CROSSBOW = registerItem("hardened_titan_crossbow", () -> new CrossbowItem(new Item.Properties().stacksTo(1).durability(1200).fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> QUALAMRAR = registerItem("quelamrar", () -> new QuelAmrarItem(ToolTiersRegistry.QUALAMRAR, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> RAUBBAU = registerItem("raubbau", () -> new RaubbauItem(ToolTiersRegistry.RAUBBAU, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> THOAREL_BOW = registerItem("thoarel_bow", () -> new ThoaRelBowItem(new Item.Properties().stacksTo(1).durability(800).fireResistant().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> FLAMETHROWER = registerItem("flamethrower", () -> new FlamethrowerItem(getSettings()));
    public static final RegistrySupplier<Item> EMBERGRASP = registerItem("embergrasp", () -> new EmbergraspItem(ToolTiersRegistry.EMBERGRASP, getSettings().fireResistant().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> ARMORED_VINDICATOR_AXE = registerItem("armored_vindicator_axe", () -> new AxeItem(Tiers.DIAMOND, 5.0F, -3.0F, new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> DRAGON_BONES = registerItem("dragon_bones", () -> new TooltipItem(getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> DRAGON_EYE = registerItem("dragon_eye", () -> new EnchantedItem(getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DRAGON_HEARTH = registerItem("dragon_hearth", () -> new EnchantedItem(getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Item> DRAGON_HEAD_HELMET = registerItem("dragon_head_helmet", () -> new TooltipItem(getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> DRAGON_EMBLEM = registerItem("dragon_emblem", () -> new TooltipItem(getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> DRAGONSCALE = registerItem("dragonscale", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> DRAGONFODDER = registerItem("dragonfodder", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> DRAGON_SADDLE = registerItem("dragon_saddle", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> DRACONIC_FOR_DUMMIES = registerItem("draconic_for_dummies", () -> new DraconicForDummiesItem(getSettings().rarity(Rarity.EPIC)));
    public static final RegistrySupplier<Block> GRIM_ANVIL = registerWithItem("grim_anvil", () -> new GrimAnvilBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable().sound(SoundType.STONE).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> LOOTCHEST = registerWithItem("lootchest", () -> new LootChestBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable().sound(SoundType.STONE).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Item> HEARTHSTONE = registerItem("hearthstone", () -> new HearthstoneItem(getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item> DRAGON_TEARS = registerItem("dragon_tears", () -> new DragonTearsItem(getSettings().rarity(Rarity.RARE)));
    public static final RegistrySupplier<Item>  FIERY_WARHORSE_SPAWN_EGG = registerItem("reins_of_the_fiery_warhorse",  () -> new ArchitecturySpawnEggItem(EntityRegistry.FIERY_WARHORSE, -1, -1, getSettings()));
    public static final RegistrySupplier<Block> DRAGON_EGG = registerWithItem("dragon_egg", () -> new DragonEggBlock(Blocks.DRAGON_EGG, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED)));
    public static final RegistrySupplier<Item> ESSENCE_OF_FIRE = registerItem("essence_of_fire", () -> new Item(getSettings().rarity(Rarity.UNCOMMON)));
    public static final RegistrySupplier<Item> HEART_OF_FLAME = registerItem("heart_of_flame", () -> HeartSmithingTemplateItem.createHeartUpgradeTemplate(Rarity.RARE));
    public static final RegistrySupplier<Item> TITAN_DUST = registerItem("titan_dust", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> TITAN_INGOT = registerItem("titan_ingot", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> TITAN_PLATES = registerItem("titan_plates", () -> new Item(getSettings()));
    public static final RegistrySupplier<Item> HARDENED_TITAN_PLATES = registerItem("hardened_titan_plates", () -> new Item(getSettings()));
    public static final RegistrySupplier<Block> STATUE_LILITU = registerWithItem("statue_lilitu", () -> new LilituStatueBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable().sound(SoundType.STONE).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> STATUE_ADOREDU = registerWithItem("statue_adoredu", () -> new StatueBlock(BlockBehaviour.Properties.of().strength(-1.0F, 3600000.0F).noLootTable().sound(SoundType.STONE).pushReaction(PushReaction.IGNORE)));


    public static void init() {
        Dragonflame.LOGGER.debug("Registering Mod Block and Items for " + Dragonflame.MOD_ID);
        ITEMS.register();
        BLOCKS.register();
    }

    public static BlockBehaviour.Properties properties(float strength) {
        return properties(strength, strength);
    }

    public static BlockBehaviour.Properties properties(float breakSpeed, float explosionResist) {
        return BlockBehaviour.Properties.of().strength(breakSpeed, explosionResist);
    }


    private static Item.Properties getSettings(Consumer<Item.Properties> consumer) {
        Item.Properties settings = new Item.Properties();
        consumer.accept(settings);
        return settings;
    }

    static Item.Properties getSettings() {
        return getSettings(settings -> {
        });
    }

    static ButtonBlock woodenButton(BlockSetType blockSetType, FeatureFlag... featureFlags) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (featureFlags.length > 0) {
            properties = properties.requiredFeatures(featureFlags);
        }

        return new ButtonBlock(properties, blockSetType, 30, true);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> block) {
        return Util.registerWithItem(BLOCKS, BLOCK_REGISTRAR, ITEMS, ITEM_REGISTRAR, new DragonflameIdentifier(name), block);
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String path, Supplier<T> block) {
        return Util.registerWithoutItem(BLOCKS, BLOCK_REGISTRAR, new DragonflameIdentifier(path), block);
    }

    public static <T extends Item> RegistrySupplier<T> registerItem(String path, Supplier<T> itemSupplier) {
        return Util.registerItem(ITEMS, ITEM_REGISTRAR, new DragonflameIdentifier(path), itemSupplier);
    }
    
    private static BlockBehaviour.Properties getLogBlockSettings() {
        return BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties getWoodenSlabSettings() {
        return getLogBlockSettings().explosionResistance(3.0F);
    }
}