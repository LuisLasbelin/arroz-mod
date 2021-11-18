package net.lbelmar.espanya_mod.generator;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.lbelmar.espanya_mod.registry.GeneratorRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

public class BiomeModifiers {
    
    public static void init() {
        // generate seeds in ALL biomes
        BiomeModifications.addFeature(context -> {
            Biome biome = context.getBiome();
            return biome.getCategory() != Biome.Category.OCEAN;
        }, GenerationStep.Feature.VEGETAL_DECORATION, GeneratorRegistry.getFeatureKey("random_crop"));
    }

}
