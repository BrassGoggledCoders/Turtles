package com.brassgoggledcoders.turtles;

import com.brassgoggledcoders.turtles.farmersdelight.FarmersDelightTurtles;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod(Turtles.ID)
public class Turtles {
    public static final String ID = "turtles";

    public Turtles() {
        tryActivateMod("farmersdelight", () -> FarmersDelightTurtles::new);
    }

    public void tryActivateMod(String modId, Supplier<Supplier<?>> contentSupplier) {
        if (ModList.get().isLoaded(modId)) {
            contentSupplier.get().get();
        }
    }
}
