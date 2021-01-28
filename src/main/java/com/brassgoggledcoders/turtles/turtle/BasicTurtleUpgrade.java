package com.brassgoggledcoders.turtles.turtle;

import com.brassgoggledcoders.turtles.Turtles;
import dan200.computercraft.api.client.TransformedModel;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasicTurtleUpgrade implements ITurtleUpgrade {
    private final RegistryObject<Item> item;
    private final String adjective;
    private final ResourceLocation upgradeId;

    private ItemStack itemStack;

    public BasicTurtleUpgrade(ResourceLocation itemName) {
        this(itemName, itemName.getPath());
    }

    public BasicTurtleUpgrade(ResourceLocation itemName, String adjective) {
        this.item = RegistryObject.of(itemName, ForgeRegistries.ITEMS);
        this.adjective = adjective;
        this.upgradeId = new ResourceLocation(Turtles.ID, itemName.getNamespace() + "_" + itemName.getPath());
    }

    public void setup(TurtlesFakePlayer fakePlayer, ITurtleAccess turtleAccess) {
        fakePlayer.setHeldItem(Hand.MAIN_HAND, this.getItemStack());
        fakePlayer.setHeldItem(Hand.OFF_HAND, turtleAccess.getItemHandler().extractItem(
                turtleAccess.getSelectedSlot(), 64, false));
    }

    public void clear(TurtlesFakePlayer fakePlayer, ITurtleAccess turtleAccess) {
        fakePlayer.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
        turtleAccess.getItemHandler().setStackInSlot(turtleAccess.getSelectedSlot(),
                fakePlayer.getHeldItem(Hand.OFF_HAND));
        fakePlayer.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);

    }

    @Nonnull
    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.TOOL;
    }

    @Nonnull
    @Override
    public TransformedModel getModel(@Nullable ITurtleAccess iTurtleAccess, @Nonnull TurtleSide turtleSide) {
        float xOffset = turtleSide == TurtleSide.LEFT ? -0.40625f : 0.40625f;
        Matrix4f transform = new Matrix4f(new float[]{
                0.0f, 0.0f, -1.0f, 1.0f + xOffset,
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, -1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f,
        });
        return TransformedModel.of(getCraftingItem(), new TransformationMatrix(transform));
    }

    @Nonnull
    @Override
    public ResourceLocation getUpgradeID() {
        return upgradeId;
    }

    @Nonnull
    @Override
    public String getUnlocalisedAdjective() {
        return "upgrade.turtles." + this.adjective + ".adjective";
    }

    @Nonnull
    @Override
    public ItemStack getCraftingItem() {
        return this.getItemStack();
    }

    @Nonnull
    protected ItemStack getItemStack() {
        if (this.itemStack == null) {
            this.itemStack = this.item.map(ItemStack::new)
                    .orElse(ItemStack.EMPTY);
        }
        return this.itemStack;
    }
}
