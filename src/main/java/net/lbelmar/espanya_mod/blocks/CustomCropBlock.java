package net.lbelmar.espanya_mod.blocks;

import javax.swing.text.html.BlockView;

import net.lbelmar.espanya_mod.items.SeedItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkStatus;

public class CustomCropBlock extends CropBlock {

    protected static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
        Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
    };

    private SeedItem seed;

    public CustomCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, net.minecraft.world.BlockView world, BlockPos pos,
            ShapeContext context) {
        return AGE_TO_SHAPE[state.get(this.getAgeProperty())];
    }

    public void setSeedsItem(SeedItem seed) {
        this.seed = seed;
    }

    @Override // JANK
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Biome.Category biomeCat = world.getBiome(pos).getCategory();
        if (world.getChunk(pos).getStatus().getIndex() < ChunkStatus.FULL.getIndex()) {
            // ON WORLD GENERATION
            if (biomeCat.equals(seed.getCategory())) {
                return super.canPlaceAt(state, world, pos);
            }
        } else if (world.getChunk(pos).getStatus().getIndex() == ChunkStatus.FULL.getIndex()) {
            // ON PLAYER PLACEMENT
            return super.canPlaceAt(state, world, pos);
        }
        return false;
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.GRASS_BLOCK) || floor.isOf(Blocks.FARMLAND) || floor.isOf(Blocks.SAND) || floor.isOf(Blocks.RED_SAND);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (getAge(state) == getMaxAge()) {
            world.setBlockState(pos, this.withAge(0), 2);
            dropStacks(state, world, pos);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    protected ItemConvertible getSeedsItem() {

        return seed;
    }
}
