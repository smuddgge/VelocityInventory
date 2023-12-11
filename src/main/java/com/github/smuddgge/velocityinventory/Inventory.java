package com.github.smuddgge.velocityinventory;

import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.data.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

/**
 * Represents a customisable inventory.
 */
public class Inventory {

    private final @NotNull dev.simplix.protocolize.api.inventory.Inventory inventory;

    /**
     * Used to create a new instance of an inventory.
     *
     * @param type The type of inventory to create.
     */
    public Inventory(@NotNull InventoryType type) {
        this.inventory = new dev.simplix.protocolize.api.inventory.Inventory(type);
    }

    /**
     * Used to get the inventory's type.
     *
     * @return The type of inventory.
     */
    public @NotNull InventoryType getType() {
        return this.inventory.type();
    }

    /**
     * Used to get the title as a legacy string.
     *
     * @return The instance of the title.
     */
    public @NotNull String getTitle() {
        return this.inventory.title(true);
    }

    /**
     * Used to get the base item stack from the
     * inventory at a specific index.
     *
     * @param index The index to get the item from.
     * @return This instance.
     */
    public BaseItemStack getItem(int index) {
        return this.inventory.item(index);
    }

    /**
     * Used to set the type of inventory.
     *
     * @param type The type of inventory.
     * @return This instance.
     */
    public @NotNull Inventory setType(@NotNull InventoryType type) {
        this.inventory.type(type);
        return this;
    }

    /**
     * Used to set the title of the inventory with a legacy string.
     *
     * @param title The title to set.
     * @return This inventory.
     */
    public @NotNull Inventory setTitle(@NotNull String title) {
        this.inventory.title(title);
        return this;
    }

    /**
     * Used to append the item to the inventory
     * into the slots the item specifies.
     *
     * @param item The item to place into the inventory.
     * @return This instance.
     */
    public @NotNull Inventory setItem(@NotNull InventoryItem item) {
        for (int slot : item.getSlotList()) {
            this.inventory.item(slot, item.convertToItemStack());
        }
        return this;
    }

    /**
     * Used to open the inventory.
     *
     * @return This instance.
     */
    public @NotNull Inventory open(@NotNull Player player) {
        return this;
    }
}
