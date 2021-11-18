// -----------------------------------------------------------
// Author: Luis Belloch
// DateOfCreation: 21/10/2021
// Description: Do when the client starts
// -----------------------------------------------------------
package net.lbelmar.espanya_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

import static net.lbelmar.espanya_mod.EspanyaMod.cropBlocks;

public class EspanyaModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        cropBlocks.forEach(this::addCutoutMipped);

    }

    /**
     * Adds coutout mipped for items with transparency
     * 
     * @param block
     */
    public void addCutoutMipped(Block block) {
        
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
    }
}