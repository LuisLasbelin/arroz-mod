package net.lbelmar.espanya_mod.registry;

import java.util.HashMap;
import java.util.Map;

import net.lbelmar.espanya_mod.blocks.CustomCropBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.HeightmapDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import static net.lbelmar.espanya_mod.EspanyaMod.*;

public class GeneratorRegistry {
 
    private static Map<String, RegistryKey<ConfiguredFeature<?, ?>>> keyMap = new HashMap<>();
    private static Map<RegistryKey<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>> featureMap = new HashMap<>();
    
    public static final RandomPatchFeatureConfig config = new RandomPatchFeatureConfig.Builder(
        new WeightedBlockStateProvider(DataPool.<BlockState>builder()
        .add(BlockRegistry.ajoCropBlock.getDefaultState().with(CustomCropBlock.AGE, 7), 10)
        .add(BlockRegistry.arrozCropBlock.getDefaultState().with(CustomCropBlock.AGE, 7), 10)
        .add(BlockRegistry.garrofonCropBlock.getDefaultState().with(CustomCropBlock.AGE, 7), 10)
        .build()),
        SimpleBlockPlacer.INSTANCE
    ).tries(24).build();

    public static final ConfiguredFeature<?, ?> RANDOM_CROP = register(createIdentifier("random_crop"),
        Feature.RANDOM_PATCH.configure(config).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP_SPREAD_DOUBLE));

    /**
     * Registers a new configured feature
     * 
     * @param <FC>
     * @param id
     * @param configuredFeature
     * @return feature same as input
     */
    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(Identifier id, ConfiguredFeature<FC, ?> configuredFeature) {
        ConfiguredFeature<FC, ?> feature = Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configuredFeature);
        RegistryKey<ConfiguredFeature<?, ?>> key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, id);
        keyMap.put(id.getPath(), key);
        featureMap.put(key, feature);
        return feature;
    }

    /**
     * 
     * @return feature map
     */
    public static Map<RegistryKey<ConfiguredFeature<?, ?>>, ConfiguredFeature<?, ?>> getFeatureMap() {
        return featureMap;
    }

    /**
     * 
     * @return keymap
     */
    public static Map<String, RegistryKey<ConfiguredFeature<?, ?>>> getKeyMap() {
        return keyMap;
    }

    /**
     * 
     * @param key
     * @return registry key
     */
    public static RegistryKey<ConfiguredFeature<?, ?>> getFeatureKey(String key) {
        return keyMap.get(key);
    }

}
