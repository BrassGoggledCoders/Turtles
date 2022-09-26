package com.brassgoggledcoders.turtles.pneumaticcraft;

import com.brassgoggledcoders.turtles.Turtles;
import dan200.computercraft.api.turtle.TurtleUpgradeDataProvider;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PneumaticCraftTurtlesDataProvider extends TurtleUpgradeDataProvider {
    public PneumaticCraftTurtlesDataProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addUpgrades(@NotNull Consumer<Upgrade<TurtleUpgradeSerialiser<?>>> addUpgrade) {
        addUpgrade.accept(simple(
                new ResourceLocation(Turtles.ID, AmadronTurtleUpgrade.NAME.getPath()),
                PneumaticCraftTurtles.AMADRON.get()
        ));
    }
}
