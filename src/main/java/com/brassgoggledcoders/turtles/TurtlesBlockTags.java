package com.brassgoggledcoders.turtles;

import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

public class TurtlesBlockTags {
    public static final Tags.IOptionalNamedTag<Block> CAN_KNIFE = BlockTags.createOptional(
            new ResourceLocation(Turtles.ID, "can_knife"));
}
