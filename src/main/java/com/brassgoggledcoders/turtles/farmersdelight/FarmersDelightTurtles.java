package com.brassgoggledcoders.turtles.farmersdelight;

import dan200.computercraft.api.ComputerCraftAPI;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class FarmersDelightTurtles {
    public FarmersDelightTurtles() {
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, this::registerTurtles);
    }

    public void registerTurtles(RegistryEvent.Register<Item> itemRegister) {
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtleUpgrade("flint"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtleUpgrade("iron"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtleUpgrade("diamond"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtleUpgrade("netherite"));
        ComputerCraftAPI.registerTurtleUpgrade(new KnifeTurtleUpgrade("golden"));

        ComputerCraftAPI.registerTurtleUpgrade(new BowlTurtleUpgrade(new ResourceLocation("bowl")));
    }
}
