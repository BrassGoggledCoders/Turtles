package com.brassgoggledcoders.turtles;

import com.brassgoggledcoders.turtles.farmersdelight.FarmersDelightTurtles;
import com.brassgoggledcoders.turtles.pneumaticcraft.PneumaticCraftTurtles;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(Turtles.ID)
public class Turtles {
    public static final String ID = "turtles";

    public Turtles() {
        tryActivateMod("pneumaticcraft", () -> PneumaticCraftTurtles::setup);
    }

    public void tryActivateMod(String modId, Supplier<Consumer<IEventBus>> contentSupplier) {
        if (ModList.get().isLoaded(modId)) {
            contentSupplier.get().accept(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }
}
