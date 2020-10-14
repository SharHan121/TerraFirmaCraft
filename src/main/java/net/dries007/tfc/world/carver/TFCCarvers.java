/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.world.carver;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import net.dries007.tfc.common.blocks.TFCBlocks;
import net.dries007.tfc.common.blocks.soil.SoilBlockType;
import net.dries007.tfc.common.types.Rock;
import net.dries007.tfc.common.types.RockManager;
import net.dries007.tfc.util.Helpers;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@SuppressWarnings("unused")
public class TFCCarvers
{
    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, MOD_ID);

    public static final RegistryObject<TFCCaveCarver> CAVE = CARVERS.register("cave", () -> new TFCCaveCarver(ProbabilityConfig.CODEC, 256));
    public static final RegistryObject<TFCRavineCarver> CANYON = CARVERS.register("canyon", () -> new TFCRavineCarver(ProbabilityConfig.CODEC));
    public static final RegistryObject<TFCUnderwaterCaveCarver> UNDERWATER_CAVE = CARVERS.register("underwater_cave", () -> new TFCUnderwaterCaveCarver(ProbabilityConfig.CODEC));
    public static final RegistryObject<TFCUnderwaterRavineCarver> UNDERWATER_CANYON = CARVERS.register("underwater_canyon", () -> new TFCUnderwaterRavineCarver(ProbabilityConfig.CODEC));

    public static final RegistryObject<WorleyCaveCarver> WORLEY_CAVES = CARVERS.register("worley_cave", () -> new WorleyCaveCarver(WorleyCaveConfig.CODEC));

    /**
     * Vanilla carvers have a set of blocks they think are valid for carving
     * We need to add our own to that list.
     */
    public static Set<Block> fixCarvableBlocksList(Set<Block> original)
    {
        Set<Block> carvableBlocks = new HashSet<>(original);
        for (Rock rock : RockManager.INSTANCE.getValues())
        {
            carvableBlocks.add(rock.getBlock(Rock.BlockType.RAW));
        }
        for (SoilBlockType.Variant variant : SoilBlockType.Variant.values())
        {
            carvableBlocks.add(TFCBlocks.SOIL.get(SoilBlockType.DIRT).get(variant).get());
            carvableBlocks.add(TFCBlocks.SOIL.get(SoilBlockType.GRASS).get(variant).get());
        }
        return carvableBlocks;
    }

    public static void setup()
    {
        // Registry dummy configured features, so they are present in the builtin registry prior to dynamic registry loading
        // I wouldn't think this would be required, but apparently it is, so we were go.
        register("cave");
        register("cavern");
        register("worley_cave");
    }

    private static void register(String name)
    {
        Registry.register(WorldGenRegistries.CONFIGURED_CARVER, Helpers.identifier(name), WorldCarver.CAVE.configured(new ProbabilityConfig(0)));
    }
}