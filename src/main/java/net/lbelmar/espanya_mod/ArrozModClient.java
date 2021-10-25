// -----------------------------------------------------------
// Author: Luis Belloch
// DateOfCreation: 21/10/2021
// Description: Do when the client starts
// -----------------------------------------------------------
package net.lbelmar.espanya_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class ArrozModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        // Cutout transparency for the crop blocks
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ArrozMod.ARROZ_CROP_BLOCK);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ArrozMod.GARROFON_CROP_BLOCK);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ArrozMod.AJO_PLANT_BLOCK);

    }
}