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
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class KnifeTurtle extends BasicTurtleUpgrade {
    public KnifeTurtle(String material) {
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
            if (diggingBlockState.isIn(TurtlesBlockTags.CAN_KNIFE)) {
                TileEntity tileEntity = world.getTileEntity(diggingPosition);
                if (tileEntity != null && tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                        .map(inventory -> !inventory.getStackInSlot(0).isEmpty())
                        .orElse(false)
                ) {
                    if (world instanceof ServerWorld) {
                        TurtlesFakePlayer turtlesFakePlayer = TurtlesFakePlayer.get((ServerWorld) world);
                        turtlesFakePlayer.setup(turtle, this);
                        diggingBlockState.onBlockActivated(turtle.getWorld(), turtlesFakePlayer, Hand.MAIN_HAND,
                                new BlockRayTraceResult(new Vector3d(0.5D, 0.5D, 0.5D), direction.getOpposite(),
                                        diggingPosition, true));
                        turtlesFakePlayer.clear();
                    }
                    return TurtleCommandResult.success();
                }
                return TurtleCommandResult.failure("Nothing to knife");
            }
            return TurtleCommandResult.failure("Cannot Knife Block");
        }
        return TurtleCommandResult.failure("Can only dig");
    }
}
