package net.lbelmar.espanya_mod.registry;

import net.lbelmar.espanya_mod.blocks.CustomCropBlock;
import net.minecraft.block.Block;

import static net.lbelmar.espanya_mod.EspanyaMod.*;

public class BlockRegistry {
    public static Block ajoCropBlock = new CustomCropBlock(createCropSettings());
    public static Block arrozCropBlock = new CustomCropBlock(createCropSettings());
    public static Block garrofonCropBlock = new CustomCropBlock(createCropSettings());

    public static void init() {

        registerBlock("ajo_crop_block", ajoCropBlock);
        registerBlock("arroz_crop_block", arrozCropBlock);
        registerBlock("garrofon_crop_block", garrofonCropBlock);
    }
}
