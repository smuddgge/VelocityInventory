package com.github.smuddgge.velocityinventory;

import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import com.github.smuddgge.velocityinventory.action.Action;
import com.github.smuddgge.velocityinventory.action.ActionManager;
import com.github.smuddgge.velocityinventory.action.ActionResult;
import com.github.smuddgge.velocityinventory.action.action.ClickAction;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.api.player.ProtocolizePlayer;
import dev.simplix.protocolize.data.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a customisable inventory.
 */
public class Inventory implements ConfigurationConvertable<Inventory> {

    private final @NotNull dev.simplix.protocolize.api.inventory.Inventory inventory;
    private @NotNull List<Action> actionList;
    private final @NotNull Map<ClickAction, List<Integer>> clickActionMap;
    private final @NotNull ActionManager actionManager;

    /**
     * Used to create a new instance of an inventory.
     *
     * @param type The type of inventory to create.
     */
    public Inventory(@NotNull InventoryType type) {
        this.inventory = new dev.simplix.protocolize.api.inventory.Inventory(type);
        this.actionList = new ArrayList<>();
        this.clickActionMap = new HashMap<>();
        this.actionManager = new ActionManager(this);
    }

    /**
     * Used to get the base inventory that is being used.
     * The protocolize inventory.
     *
     * @return This instance.
     */
    public @NotNull dev.simplix.protocolize.api.inventory.Inventory getBaseInventory() {
        return this.inventory;
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
     * Used to get the action list for a specific type of action.
     *
     * @param type The type of action.
     * @param <T>  The type of action.
     * @return The list of actions.
     */
    @SuppressWarnings("unchecked")
    public @NotNull <T> List<T> getAction(@NotNull Class<T> type) {
        List<T> list = new ArrayList<>();
        for (Action action : this.actionList) {
            if (!type.isInstance(action)) continue;
            list.add((T) action);
        }
        return list;
    }

    public @Nullable List<Integer> getClickActionSlots(@NotNull ClickAction action) {
        return this.clickActionMap.get(action);
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

        for (ClickAction clickAction : item.getClickActionList()) {
            this.addAction(clickAction);
            this.clickActionMap.put(clickAction, item.getSlots());
        }
        return this;
    }

    /**
     * Used to add an action that will be triggered depending on
     * what type of action it is.
     *
     * @param action The instance of the action.
     * @return This instance.
     */
    public @NotNull Inventory addAction(@NotNull Action action) {
        this.actionList.add(action);
        return this;
    }

    /**
     * Used to remove all the inventory actions.
     *
     * @return This instance.
     */
    public @NotNull Inventory removeActions() {
        this.actionList = new ArrayList<>();
        return this;
    }

    /**
     * Used to remove all the items from the inventory.
     *
     * @return This instance.
     */
    public @NotNull Inventory clearInventory() {

        // Loop though all the items and remove them.
        for (Map.Entry<Integer, BaseItemStack> entry : this.inventory.items().entrySet()) {
            this.inventory.removeItem(entry.getKey());
        }

        return this;
    }

    /**
     * Used to open the inventory.
     *
     * @return This instance.
     */
    public @NotNull Inventory open(@NotNull Player player) {

        // Run the open action.
        ActionResult result = this.actionManager.onOpen(player, this);
        if (result.isCancelTrue()) return this;

        ProtocolizePlayer protocolizePlayer = Protocolize.playerProvider().player(player.getUniqueId());
        protocolizePlayer.openInventory(this.inventory);
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
