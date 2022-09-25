package com.brassgoggledcoders.turtles.farmersdelight;

import com.brassgoggledcoders.turtles.TurtlesBlockTags;
import com.brassgoggledcoders.turtles.turtle.TurtlesFakePlayer;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleVerb;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;

public class BowlTurtleUpgrade extends BasicTurtleUpgrade {
    public BowlTurtleUpgrade(ResourceLocation itemName) {
        super(itemName);
    }

    @Override
    public void setup(TurtlesFakePlayer fakePlayer, ITurtleAccess turtleAccess) {
        fakePlayer.setHeldItem(Hand.MAIN_HAND, turtleAccess.getItemHandler().extractItem(
                turtleAccess.getSelectedSlot(), 64, false));
    }

    @Override
    public void clear(TurtlesFakePlayer fakePlayer, ITurtleAccess turtleAccess) {
        turtleAccess.getItemHandler().setStackInSlot(turtleAccess.getSelectedSlot(),
                fakePlayer.getHeldItem(Hand.MAIN_HAND));
    }

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side, @Nonnull TurtleVerb verb,
                                       @Nonnull Direction direction) {
        if (verb == TurtleVerb.DIG) {
            BlockPos diggingPosition = turtle.getPosition().offset(direction);
            BlockState diggingBlockState = turtle.getWorld().getBlockState(diggingPosition);
            if (diggingBlockState.isIn(TurtlesBlockTags.FEAST)) {
                TurtlesFakePlayer.rightClickBlock(turtle, this, diggingBlockState, direction);
                return TurtleCommandResult.success();
            }
            return TurtleCommandResult.failure("Cannot Bowl Block");
        }
        return TurtleCommandResult.failure("Can only Dig");
    }
}
