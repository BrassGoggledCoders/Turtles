package com.brassgoggledcoders.turtles.farmersdelight;

import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class FarmersDelightTurtles {
    public FarmersDelightTurtles() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerTurtles);
    }

    public void registerTurtles(RegistryEvent.Register<Item> itemRegister) {
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtle("flint"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtle("iron"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtle("diamond"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtle("netherite"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtle("golden"));
    }
}
