package net.dries007.tfc.world.decorator;

import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.IPlacementConfig;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BoundedCarvingMaskConfig implements IPlacementConfig
{
    public static final Codec<BoundedCarvingMaskConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.intRange(0, 256).optionalFieldOf("min_y", 0).forGetter(c -> c.minY),
        Codec.intRange(0, 256).optionalFieldOf("max_y", 256).forGetter(c -> c.maxY),
        Codec.floatRange(0, 1).fieldOf("probability").forGetter(c -> c.probability),
        GenerationStage.Carving.CODEC.fieldOf("step").forGetter(c -> c.step)
    ).apply(instance, BoundedCarvingMaskConfig::new));

    protected final int minY, maxY;
    protected final float probability;
    protected final GenerationStage.Carving step;

    public BoundedCarvingMaskConfig(int minY, int maxY, float probability, GenerationStage.Carving step)
    {
        this.minY = minY;
        this.maxY = maxY;
        this.probability = probability;
        this.step = step;
    }
}
