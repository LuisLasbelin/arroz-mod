package net.lbelmar.espanya_mod.registry;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import static net.lbelmar.espanya_mod.EspanyaMod.*;
import static net.minecraft.world.biome.Biome.Category.*;

import net.lbelmar.espanya_mod.items.SeedItem;

public class ItemRegistry {
    public static SeedItem arrozSeed = new SeedItem(BlockRegistry.arrozCropBlock, createGroup(), FOREST);
    public static SeedItem garrofonSeed = new SeedItem(BlockRegistry.garrofonCropBlock, createGroup(), FOREST);
    public static final SeedItem dienteAjoItem = new SeedItem(BlockRegistry.ajoCropBlock, createGroup(), FOREST);

	public static final Item aceiteItem = new Item(createGroup());
    public static final Item ajoTierno = new Item(createGroup());
	public static final Item cabezaAjoItem = new Item(createGroup());
	public static final Item onigiriItem = new Item(new Item.Settings().food(new FoodComponent.Builder().hunger(6).saturationModifier(6).build()).group(ItemGroup.FOOD));

    public static void init() {

        registerItem("arroz_grano", arrozSeed);
        registerItem("garrofon_grano", garrofonSeed);
        registerItem("aceite", aceiteItem);
        registerItem("ajo_cabeza", cabezaAjoItem);
        registerItem("ajo_diente", dienteAjoItem);
        registerItem("onigiri", onigiriItem);
        registerItem("ajo_tierno", ajoTierno);
    }
}
