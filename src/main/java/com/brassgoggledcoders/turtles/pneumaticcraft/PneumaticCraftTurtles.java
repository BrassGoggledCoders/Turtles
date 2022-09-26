package com.brassgoggledcoders.turtles.pneumaticcraft;

import com.brassgoggledcoders.turtles.Turtles;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PneumaticCraftTurtles {
    private final static DeferredRegister<TurtleUpgradeSerialiser<?>> TURTLE_UPGRADES = DeferredRegister.create(
            TurtleUpgradeSerialiser.REGISTRY_ID,
            Turtles.ID
    );

    public final static RegistryObject<TurtleUpgradeSerialiser<AmadronTurtleUpgrade>> AMADRON = TURTLE_UPGRADES.register(
            AmadronTurtleUpgrade.NAME.getPath(),
            () -> TurtleUpgradeSerialiser.simple(AmadronTurtleUpgrade::new)
    );

    public static void setup(IEventBus eventBus) {
        TURTLE_UPGRADES.register(eventBus);
        eventBus.addListener(PneumaticCraftTurtles::dataGenerate);
    }

    public static void dataGenerate(GatherDataEvent event) {
        if (event.includeServer()) {
            event.getGenerator().addProvider(new PneumaticCraftTurtlesDataProvider(event.getGenerator()));
        }
    }
}
