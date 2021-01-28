package com.brassgoggledcoders.turtles.farmersdelight;

import com.brassgoggledcoders.turtles.TurtlesBlockTags;
import com.brassgoggledcoders.turtles.turtle.BasicTurtleUpgrade;
import com.brassgoggledcoders.turtles.turtle.TurtlesFakePlayer;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleVerb;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class KnifeTurtleUpgrade extends BasicTurtleUpgrade {
    public KnifeTurtleUpgrade(String material) {
        super(new ResourceLocation("farmersdelight", material + "_knife"), "knife");
    }

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side, @Nonnull TurtleVerb verb,
                                       @Nonnull Direction direction) {
        if (verb == TurtleVerb.DIG) {
            BlockPos diggingPosition = turtle.getPosition().offset(direction);
            BlockState diggingBlockState = turtle.getWorld().getBlockState(diggingPosition);
            World world = turtle.getWorld();
            if (diggingBlockState.isIn(TurtlesBlockTags.KNIFE_CUT)) {
                if (checkInventory(world, diggingPosition, turtle)) {
                    TurtlesFakePlayer.rightClickBlock(turtle, this, diggingBlockState, direction);
                    return TurtleCommandResult.success();
                }
                return TurtleCommandResult.failure("Nothing to knife");
            } else if (diggingBlockState.isIn(TurtlesBlockTags.KNIFE_HARVEST)) {
                return TurtlesFakePlayer.harvestBlock();
            }
            return TurtleCommandResult.failure("Cannot Knife Block");
        }
        return TurtleCommandResult.failure("Can only dig");
    }

    public boolean checkInventory(IWorld world, BlockPos blockPos, ITurtleAccess turtleAccess) {
        if (turtleAccess.getItemHandler().getStackInSlot(turtleAccess.getSelectedSlot()).isEmpty()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            return tileEntity != null && tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .map(inventory -> !inventory.getStackInSlot(0).isEmpty())
                    .orElse(false);
        } else {
            return true;
        }
    }
}
