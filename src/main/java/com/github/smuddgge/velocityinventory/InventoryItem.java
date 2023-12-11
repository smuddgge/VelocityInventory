package com.github.smuddgge.velocityinventory;

import com.github.smuddgge.velocityinventory.indicator.ItemStackConvertable;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an item that can be
 * put in the {@link Inventory}.
 */
public class InventoryItem implements ItemStackConvertable {

    private final @NotNull ItemStack itemStack;
    private final @NotNull List<Integer> slots;

    /**
     * Used to create an inventory item from an item stack.
     *
     * @param itemStack The instance of an item stack.
     */
    public InventoryItem(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.slots = new ArrayList<>();
    }

    /**
     * Used to create an inventory item of type AIR.
     */
    public InventoryItem() {
        this.itemStack = new ItemStack(ItemType.AIR);
        this.slots = new ArrayList<>();
    }

    @Override
    public @NotNull ItemStack convertToItemStack() {
        return this.itemStack;
    }

    /**
     * Used to get the list of slots the
     * item will be placed into.
     *
     * @return The list of slots.
     */
    public @NotNull List<Integer> getSlotList() {
        return this.slots;
    }

    public @NotNull InventoryItem setName(@NotNull String name) {
        this.itemStack.displayName(name);
        return this;
    }

    public @NotNull InventoryItem setName(@NotNull Component component) {
        this.itemStack.displayName(component);
        return this;
    }

    public @NotNull InventoryItem setLore(@NotNull List<String> lore) {
        this.itemStack.lore(lore, true);
        return this;
    }

    public @NotNull InventoryItem setLore(@NotNull String... lore) {
        return this.setLore(lore);
    }

    public @NotNull InventoryItem setLore(@NotNull String lore) {
        List<String> loreList = new ArrayList<>();
        loreList.add(lore);
        return this.setLore(loreList);
    }

    public @NotNull InventoryItem setLoreComponent(@NotNull List<Component> lore) {
        this.itemStack.lore(lore, false);
        return this;
    }

    public @NotNull InventoryItem setLoreComponent(@NotNull Component lore) {
        List<Component> loreList = new ArrayList<>();
        loreList.add(lore);
        return this.setLoreComponent(loreList);
    }

    public @NotNull InventoryItem setLoreComponent(@NotNull Component... lore) {
        return this.setLoreComponent(lore);
    }

    public @NotNull InventoryItem addLore(@NotNull List<String> lore) {
        List<String> loreList = this.itemStack.lore(true);
        loreList.addAll(lore);
        this.itemStack.lore(loreList, true);
        return this;
    }

    public @NotNull InventoryItem addLore(@NotNull String lore) {
        List<String> loreList = new ArrayList<>();
        loreList.add(lore);
        return this.addLore(loreList);
    }
}
