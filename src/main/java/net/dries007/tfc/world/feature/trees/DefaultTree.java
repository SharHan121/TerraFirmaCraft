/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.feature.trees;

import java.util.Random;
import java.util.function.Supplier;
import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraftforge.common.util.Lazy;

public class DefaultTree extends Tree
{
    private final Lazy<ConfiguredFeature<?, ?>> featureFactory;

    public DefaultTree(Supplier<ConfiguredFeature<?, ?>> featureFactory)
    {
        this.featureFactory = Lazy.of(featureFactory);
    }

    public ConfiguredFeature<?, ?> getFeature()
    {
        return featureFactory.get();
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<?> chunkGeneratorIn, BlockPos blockPosIn, BlockState blockStateIn, Random randomIn)
    {
        ConfiguredFeature<?, ?> feature = featureFactory.get();
        worldIn.setBlockState(blockPosIn, Blocks.AIR.getDefaultState(), 4);
        if (feature.place(worldIn, chunkGeneratorIn, randomIn, blockPosIn))
        {
            return true;
        }
        else
        {
            worldIn.setBlockState(blockPosIn, blockStateIn, 4);
            return false;
        }
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean flowersNearby)
    {
        return null; // Not using minecraft's tree configuration
    }
}
