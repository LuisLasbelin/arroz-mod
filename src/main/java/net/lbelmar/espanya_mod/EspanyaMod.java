// -----------------------------------------------------------
// Author: Luis Belloch
// DateOfCreation: 21/10/2021
// Description: Main class of the mod
// -----------------------------------------------------------
package net.lbelmar.espanya_mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.lbelmar.espanya_mod.biomes.CustomBiomes;
import net.lbelmar.espanya_mod.blocks.CustomCropBlock;
import net.lbelmar.espanya_mod.generator.BiomeModifiers;
import net.lbelmar.espanya_mod.items.SeedItem;
import net.lbelmar.espanya_mod.registry.BlockRegistry;
import net.lbelmar.espanya_mod.registry.ItemRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Material;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Settings;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EspanyaMod implements ModInitializer {
	public static final String MOD_ID = "espanya_mod"; 
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
	
	public static final ItemGroup ESPANYA_ITEM_GROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "espanya"))
	.icon(() -> new ItemStack(ItemRegistry.arrozSeed))
	.build();

	// BLOCK
	public static ArrayList<Block> cropBlocks = new ArrayList<>();

	// SURFACE BUILDER
	public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> MEDITERRANEAN_SURFACE_BUILDER = 
    SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(
        Blocks.GRASS_BLOCK.getDefaultState(), 
        Blocks.CLAY.getDefaultState(), 
        Blocks.CLAY.getDefaultState()));

	// BIOME
	private static final Biome MEDITERRANEAN_BIOME = CustomBiomes.createMediterraneanBiome();
	public static final RegistryKey<Biome> MEDITERRANEAN_BIOME_KEY = RegistryKey.of(Registry.BIOME_KEY, createIdentifier("mediterranean_biome"));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		
		// ITEMS
		LOGGER.info("Registering items.");
		ItemRegistry.init();
		
		// BLOCK
		LOGGER.info("Registering blocks.");
		BlockRegistry.init();

		// FEATURES
		LOGGER.info("Registering features.");

		// SURFACE BUILDER
		LOGGER.info("Registering surface builder.");
		Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, new Identifier(MOD_ID, "tierra"), MEDITERRANEAN_SURFACE_BUILDER);
		
		// BIOME
		LOGGER.info("Registering biomes.");
		Registry.register(BuiltinRegistries.BIOME, MEDITERRANEAN_BIOME_KEY.getValue(), MEDITERRANEAN_BIOME);
	
		OverworldBiomes.addContinentalBiome(MEDITERRANEAN_BIOME_KEY, OverworldClimate.TEMPERATE, 20);
		OverworldBiomes.addContinentalBiome(MEDITERRANEAN_BIOME_KEY, OverworldClimate.DRY, 20);
		
		// BIOMES
		BiomeModifiers.init();
	}

	/**
	 * name:text -> createIdentifier() -> Identifier
	 * Creates an Identifier for an item/block/etc
	 * 
	 * @param name
	 * @return Identifier with the name
	 */
	public static Identifier createIdentifier(String name) {
        //System.out.println("\"" + MOD_ID + ":" + name + "\",");
        return new Identifier(MOD_ID, name);
    }

	/**
	 * createCropSettings() -> FabricBlockSettings
	 * Returns default crop settings
	 * 
	 * @return FabricBlockSettings with default crop settings
	 */
	public static FabricBlockSettings createCropSettings() {
        return FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP);
    }

	/**
	 * blockname:text, item:Block -> registerBlock() -> item
	 * Register a new block into the game
	 * 
	 * @param blockName
	 * @param item
	 * @return item same as input
	 */
	public static Block registerBlock(String blockName, Block item) {
        cropBlocks.add(item);

        Registry.register(Registry.BLOCK, EspanyaMod.createIdentifier(blockName), item);
        return item;
    }

	/**
	 * Registers an item into the game
	 * 
	 * @param itemName
	 * @param item
	 * @return item same as input
	 */
	public static Item registerItem(String itemName, Item item) {

		Registry.register(Registry.ITEM, EspanyaMod.createIdentifier(itemName), item);

		// Add the seed to the item if it is a seed
		if (item instanceof SeedItem seedItem) {
            CustomCropBlock block = (CustomCropBlock) ((SeedItem) item).getBlock();
            block.setSeedsItem(seedItem);
        }

		return item;
	}

	/**
	 * Adds item to the custom mod group of items
	 * 
	 * @return item group for all items
	 */
	public static Settings createGroup() {
        return new Item.Settings().group(ESPANYA_ITEM_GROUP);
    }
}
