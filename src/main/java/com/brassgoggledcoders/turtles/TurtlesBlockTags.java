package com.brassgoggledcoders.turtles;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class TurtlesBlockTags {
    public static final Tags.IOptionalNamedTag<Block> KNIFE_CUT = BlockTags.createOptional(
            new ResourceLocation(Turtles.ID, "knife_cut"));

    public static final Tags.IOptionalNamedTag<Block> KNIFE_HARVEST = BlockTags.createOptional(
            new ResourceLocation(Turtles.ID, "knife_harvest"));

    public static final Tags.IOptionalNamedTag<Block> FEAST = BlockTags.createOptional(
            new ResourceLocation(Turtles.ID, "feast"));

}
