package com.brassgoggledcoders.turtles.turtle;

import com.brassgoggledcoders.turtles.Turtles;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

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

    public void setup(ITurtleAccess turtleAccess, ITurtleUpgrade upgrade) {
        this.setHeldItem(Hand.MAIN_HAND, upgrade.getCraftingItem().copy());
        this.setHeldItem(Hand.OFF_HAND, turtleAccess.getItemHandler().getStackInSlot(turtleAccess.getSelectedSlot()));
    }

    public void clear() {
        this.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
        this.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
    }

    public static TurtlesFakePlayer get(ServerWorld serverWorld) {
        return FAKE_PLAYER_MAP.computeIfAbsent(serverWorld.getDimensionKey(), key ->
                new TurtlesFakePlayer(serverWorld, TURTLES));
    }
}
