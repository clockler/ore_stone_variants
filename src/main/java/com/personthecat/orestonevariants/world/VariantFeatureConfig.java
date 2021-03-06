package com.personthecat.orestonevariants.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.personthecat.orestonevariants.properties.OreProperties;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class VariantFeatureConfig implements IFeatureConfig {

    /** Required so that VFC may be serialized internally via vanilla functions. */
    public static final Codec<VariantFeatureConfig> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            OreProperties.CODEC.fieldOf("target").forGetter(config -> config.target),
            Codec.intRange(0, 64).fieldOf("size").forGetter(config -> config.size),
            Codec.doubleRange(0.0, 1.0).fieldOf("denseChance").forGetter(config -> config.denseChance))
        .apply(instance, VariantFeatureConfig::new)
    );

    /** Used to retrieve a spawn candidate for the current ore type. */
    public final OreProperties target;
    /** The size of the ore cluster being spawned. */
    public final int size;
    /** The chance that any given block in the current cluster will be dense. */
    public final double denseChance;

    public VariantFeatureConfig(OreProperties target, int size, /* double chance, */ double denseChance) {
        this.target = target;
        this.size = size;
        this.denseChance = denseChance;
    }
}
