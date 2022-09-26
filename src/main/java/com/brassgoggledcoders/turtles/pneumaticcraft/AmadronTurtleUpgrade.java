package com.brassgoggledcoders.turtles.pneumaticcraft;

import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import dan200.computercraft.api.client.TransformedModel;
import dan200.computercraft.api.turtle.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AmadronTurtleUpgrade extends AbstractTurtleUpgrade {
    public static final ResourceLocation NAME = new ResourceLocation("pneumaticcraft", "amadron_tablet");

    public AmadronTurtleUpgrade(ResourceLocation id) {
        super(
                id,
                TurtleUpgradeType.PERIPHERAL,
                "ordering",
                Optional.ofNullable(ForgeRegistries.ITEMS.getValue(NAME))
                        .map(Item::getDefaultInstance)
                        .orElse(ItemStack.EMPTY)
        );
    }

    @NotNull
    @Override
    public TransformedModel getModel(@Nullable ITurtleAccess turtle, @NotNull TurtleSide side) {
        return TransformedModel.of(getCraftingItem(), side == TurtleSide.LEFT ? Transforms.leftTransform : Transforms.rightTransform);
    }

    @NotNull
    @Override
    public TurtleCommandResult useTool(@NotNull ITurtleAccess turtle, @NotNull TurtleSide side, @NotNull TurtleVerb verb, @NotNull Direction direction) {
        return turtle.
    }

    private static class Transforms {
        static final Transformation leftTransform = getMatrixFor(-0.40625f);
        static final Transformation rightTransform = getMatrixFor(0.40625f);

        private static Transformation getMatrixFor(float offset) {
            return new Transformation(new Matrix4f(new float[]{
                    0.0f, 0.0f, -1.0f, 1.0f + offset,
                    1.0f, 0.0f, 0.0f, 0.0f,
                    0.0f, -1.0f, 0.0f, 1.0f,
                    0.0f, 0.0f, 0.0f, 1.0f,
            }));
        }
    }
}
