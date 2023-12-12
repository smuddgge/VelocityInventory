package com.github.smuddgge.velocityinventory;

import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.data.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Represents a customisable inventory.
 */
public class Inventory implements ConfigurationConvertable<Inventory> {

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

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new HashMap<>());

        section.set("type", this.getType().name());
        section.set("title", this.getTitle());

        for (Map.Entry<Integer, BaseItemStack> entry : this.inventory.items().entrySet()) {
            section.getSection("items").set(
                    String.valueOf(entry.getKey()),
                    new InventoryItem(entry.getValue()).convert()
            );
        }

        return section;
    }

    @Override
    public @NotNull Inventory convert(@NotNull ConfigurationSection section) {
        
        this.setType(InventoryType.valueOf(section.getString("type", "GENERIC_9X3").toUpperCase()));
        this.setTitle(section.getString("title"));

        for (String key : section.getSection("items").getKeys()) {
            InventoryItem item = new InventoryItem();
            item.convert(section.getSection(key));
            item.addSlots(key, this.getType());
            this.setItem(item);
        }

        return this;
    }
}
