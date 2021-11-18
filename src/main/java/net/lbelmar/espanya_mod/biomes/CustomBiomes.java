package net.lbelmar.espanya_mod.biomes;

import net.lbelmar.espanya_mod.EspanyaMod;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

public class CustomBiomes {


    /**
     * Generate a biome
     * 
     * @return biome created
     */
    public  static Biome createMediterraneanBiome() {
		SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
		DefaultBiomeFeatures.addMonsters(spawnBuilder, 95, 5, 100);
		DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);

		GenerationSettings.Builder generatorSettings = new GenerationSettings.Builder();
		generatorSettings.surfaceBuilder(EspanyaMod.MEDITERRANEAN_SURFACE_BUILDER);
		DefaultBiomeFeatures.addDungeons(generatorSettings);
		DefaultBiomeFeatures.addMineables(generatorSettings);
		DefaultBiomeFeatures.addDefaultFlowers(generatorSettings);
		DefaultBiomeFeatures.addBirchTrees(generatorSettings);

		return (new Biome.Builder()
		.precipitation(Biome.Precipitation.RAIN)
		.category(Biome.Category.FOREST)
		.depth(0.125F)
		.scale(0.05F)
		.temperature(0.8F)
		.downfall(0.04F)
		.effects(
			(new BiomeEffects.Builder()
			.waterColor(0x19ace6)
			.grassColor(0x154f12)
			.skyColor(0x83d8fc))
			.fogColor(0xf7faff)
			.waterFogColor(0xf7faff)
			.build())
		.spawnSettings(spawnBuilder.build())
		.generationSettings(generatorSettings.build())
		.build());
		
	}
}
