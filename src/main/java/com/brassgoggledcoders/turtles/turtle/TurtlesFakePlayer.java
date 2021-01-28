package com.brassgoggledcoders.turtles.turtle;

import com.brassgoggledcoders.turtles.Turtles;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TurtlesFakePlayer extends FakePlayer {
    private static final Map<RegistryKey<World>, TurtlesFakePlayer> FAKE_PLAYER_MAP = Maps.newHashMap();

    private static final GameProfile TURTLES = new GameProfile(
            UUID.fromString("dadcb2be-3958-4e02-8189-4796b0985d24"),
            Turtles.ID
    );

    public TurtlesFakePlayer(ServerWorld world, GameProfile name) {
        super(world, name);
    }

    public void setup(ITurtleAccess turtleAccess, BasicTurtleUpgrade upgrade) {
        upgrade.setup(this, turtleAccess);
        for (int i = 0; i < turtleAccess.getItemHandler().getSlots(); i++) {
            this.inventory.mainInventory.set(i, turtleAccess.getItemHandler().extractItem(i, 64, false));
        }
    }

    public void clear(ITurtleAccess turtleAccess, BasicTurtleUpgrade upgrade) {
        upgrade.clear(this, turtleAccess);
        IItemHandlerModifiable itemHandler = turtleAccess.getItemHandler();
        NonNullList<ItemStack> inventoryList = this.inventory.mainInventory;
        List<ItemStack> remainingStacks = Lists.newArrayList();
        for (int i = 0; i < inventoryList.size(); i++) {
            ItemStack inventoryItem = inventoryList.get(i);
            if (!inventoryItem.isEmpty()) {
                if (i < itemHandler.getSlots() && i != turtleAccess.getSelectedSlot()) {
                    ItemStack remaining = itemHandler.insertItem(i, inventoryItem, false);
                    if (!remaining.isEmpty()) {
                        remainingStacks.add(remaining);
                    }
                } else {
                    remainingStacks.add(inventoryItem);
                }
            }

        }
        inventoryList.clear();
        for (ItemStack itemStack : remainingStacks) {
            ItemStack remaining = ItemHandlerHelper.insertItem(itemHandler, itemStack, false);
            if (!remaining.isEmpty()) {
                BlockPos turtlePos = turtleAccess.getPosition();
                InventoryHelper.spawnItemStack(turtleAccess.getWorld(), turtlePos.getX(), turtlePos.getY(),
                        turtlePos.getZ(), remaining);
            }
        }
        this.world = null;
    }

    public static void rightClickBlock(ITurtleAccess turtle, BasicTurtleUpgrade upgrade, BlockState blockState,
                                       Direction direction) {
        IWorld world = turtle.getWorld();
        if (world instanceof ServerWorld) {
            TurtlesFakePlayer turtlesFakePlayer = TurtlesFakePlayer.get((ServerWorld) world);
            turtlesFakePlayer.setup(turtle, upgrade);
            BlockRayTraceResult rayTraceResult = new BlockRayTraceResult(new Vector3d(0.5D, 0.5D, 0.5D),
                    direction.getOpposite(), turtle.getPosition().offset(direction), true);
            ActionResultType resultType = blockState.onBlockActivated(turtle.getWorld(), turtlesFakePlayer,
                    Hand.MAIN_HAND, rayTraceResult);
            if (resultType == ActionResultType.PASS) {
                blockState.onBlockActivated(turtle.getWorld(), turtlesFakePlayer,
                        Hand.OFF_HAND, rayTraceResult);
            }
            turtlesFakePlayer.clear(turtle, upgrade);
        }
    }

    public static TurtleCommandResult harvestBlock() {
        return TurtleCommandResult.failure("No Block to Harvest");
    }

    public static TurtlesFakePlayer get(ServerWorld serverWorld) {
        TurtlesFakePlayer fakePlayer = FAKE_PLAYER_MAP.computeIfAbsent(serverWorld.getDimensionKey(), key ->
                new TurtlesFakePlayer(serverWorld, TURTLES));
        fakePlayer.world = serverWorld;
        return fakePlayer;
    }
}
