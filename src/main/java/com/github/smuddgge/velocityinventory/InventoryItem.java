package com.github.smuddgge.velocityinventory;

import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import com.github.smuddgge.velocityinventory.action.action.ClickAction;
import com.github.smuddgge.velocityinventory.indicator.ItemStackConvertable;
import com.github.smuddgge.velocityinventory.slot.SlotManager;
import com.github.smuddgge.velocityinventory.slot.SlotType;
import dev.simplix.protocolize.api.item.BaseItemStack;
import dev.simplix.protocolize.api.item.ItemFlag;
import dev.simplix.protocolize.api.item.ItemStack;
import dev.simplix.protocolize.data.ItemType;
import dev.simplix.protocolize.data.inventory.InventoryType;
import net.kyori.adventure.text.Component;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.Tag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents an item that can be
 * put in the {@link Inventory}.
 */
public class InventoryItem implements ItemStackConvertable, ConfigurationConvertable<InventoryItem> {

    private final @NotNull ItemStack itemStack;
    private @NotNull List<Integer> slots;
    private final @NotNull List<ClickAction> clickActionList;

    /**
     * Used to create an inventory item from an item stack.
     *
     * @param itemStack The instance of an item stack.
     */
    public InventoryItem(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
        this.slots = new ArrayList<>();
        this.clickActionList = new ArrayList<>();
    }

    public InventoryItem(@NotNull BaseItemStack baseItemStack) {
        this.itemStack = new ItemStack(baseItemStack);
        this.slots = new ArrayList<>();
        this.clickActionList = new ArrayList<>();
    }

    /**
     * Used to create an inventory item of type AIR.
     */
    public InventoryItem() {
        this.itemStack = new ItemStack(ItemType.AIR);
        this.slots = new ArrayList<>();
        this.clickActionList = new ArrayList<>();
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

    public @NotNull String getName() {
        return this.itemStack.displayName(true);
    }

    public @NotNull List<String> getLore() {
        return this.itemStack.lore(true);
    }

    public @NotNull ItemType getMaterial() {
        return this.itemStack.itemType();
    }

    public int getAmount() {
        return this.itemStack.amount();
    }

    public int getDurability() {
        return this.itemStack.durability();
    }

    public @NotNull List<ItemFlag> getFlags() {
        return new ArrayList<>(this.itemStack.itemFlags());
    }

    public @NotNull List<String> getFlagsAsStringList() {
        List<ItemFlag> list = this.getFlags();
        List<String> stringList = new ArrayList<>();
        for (ItemFlag itemFlag : list) {
            stringList.add(itemFlag.name());
        }
        return stringList;
    }

    private int getHideFlags() {
        return this.itemStack.hideFlags();
    }

    public @NotNull CompoundTag getNbt() {
        return this.itemStack.nbtData();
    }

    public @NotNull ConfigurationSection getNbtAsSection() {
        CompoundTag nbt = this.getNbt();
        ConfigurationSection section = this.getNbtAsSection(
                nbt,
                new MemoryConfigurationSection(new HashMap<>())
        );
        return section;
    }

    private @NotNull ConfigurationSection getNbtAsSection(@NotNull CompoundTag nbt, @NotNull ConfigurationSection section) {
        for (Map.Entry<String, Tag<?>> entry : nbt) {

            // Check if its compound tag.
            if (entry.getValue().toString().contains("CompoundTag")) {
                section.set(
                        entry.getKey(),
                        this.getNbtAsSection(nbt.getCompoundTag(
                                        entry.getKey()),
                                new MemoryConfigurationSection(new HashMap<>())
                        ).getMap()
                );
                continue;
            }

            // Set the value.
            section.set(entry.getKey(), entry.getValue().toString());
        }

        return section;
    }

    /**
     * Used to set the name of the item.
     * If the name is null, it will do nothing.
     *
     * @param name The instance of the name to set.
     * @return This instance.
     */
    public @NotNull InventoryItem setName(@Nullable String name) {
        if (name == null) return this;
        this.itemStack.displayName(MessageParser.parseToComponent(name, null));
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
        List<String> list = new ArrayList<>(Arrays.asList(lore));
        List<Component> parsedList = new ArrayList<>();
        for (String line : list) {
            parsedList.add(MessageParser.parseToComponent(line, null));
        }

        this.itemStack.lore(parsedList, false);
        return this;
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
        this.itemStack.lore(Arrays.asList(lore), false);
        return this;
    }

    public @NotNull InventoryItem addLore(@NotNull List<String> lore) {
        List<Component> loreList = this.itemStack.lore(false);
        lore.forEach(string -> loreList.add(MessageParser.parseToComponent(string, null)));
        this.itemStack.lore(loreList, true);
        return this;
    }

    public @NotNull InventoryItem addLore(@NotNull String lore) {
        List<String> loreList = new ArrayList<>();
        loreList.add(lore);
        return this.addLore(loreList);
    }

    public @NotNull InventoryItem setMaterial(@NotNull ItemType type) {
        this.itemStack.itemType(type);
        return this;
    }

    public @NotNull InventoryItem setAmount(int amount) {
        this.itemStack.amount(Byte.parseByte(String.valueOf(amount)));
        return this;
    }

    public @NotNull InventoryItem setDurability(int durability) {
        this.itemStack.durability(Short.parseShort(String.valueOf(durability)));
        return this;
    }

    public @NotNull InventoryItem setFlag(@NotNull ItemFlag flag, boolean active) {
        this.itemStack.itemFlag(flag, active);
        return this;
    }

    public @NotNull InventoryItem setFlags(@NotNull List<String> flags) {
        for (String flag : flags) {
            this.setFlag(ItemFlag.valueOf(flag.toUpperCase()), true);
        }
        return this;
    }

    public @NotNull InventoryItem setHideFlags(int hideFlags) {
        this.itemStack.hideFlags(hideFlags);
        return this;
    }

    public @NotNull InventoryItem setNBT(@NotNull CompoundTag nbt) {
        this.itemStack.nbtData(nbt);
        return this;
    }

    public @NotNull InventoryItem setNBT(@NotNull ConfigurationSection section) {
        CompoundTag nbt = this.getNBT(section, new CompoundTag());
        this.setNBT(nbt);
        return this;
    }

    private @NotNull CompoundTag getNBT(@NotNull ConfigurationSection section, @NotNull CompoundTag nbt) {
        for (Map.Entry<String, Object> entry : section.getMap().entrySet()) {
            if (entry.getValue() instanceof Map) {
                nbt.put(
                        entry.getKey(),
                        this.getNBT(
                                section.getSection(entry.getKey()),
                                new CompoundTag()
                        )
                );
            }
            nbt.putString(entry.getKey(), entry.getValue().toString());
        }
        return nbt;
    }

    public int getCustomModelData() {
        return this.itemStack.nbtData().getInt("CustomModelData");
    }

    public @NotNull InventoryItem setCustomModelData(int customModelData) {
        this.itemStack.nbtData().putInt("CustomModelData", customModelData);
        return this;
    }

    public @NotNull List<Integer> getSlots() {
        return this.slots;
    }

    public @NotNull InventoryItem setSlots(@NotNull List<Integer> slots) {
        this.slots = slots;
        return this;
    }

    /**
     * Used to add specific slots based on the type of inventory.
     * This will use the {@link SlotManager} to parse the slots.
     *
     * @param slotString The slot string, for example "center0".
     *                   See {@link SlotType} implementations for options.
     * @param type       The type of inventory.
     * @return This instance.
     */
    public @NotNull InventoryItem addSlots(@NotNull String slotString, @NotNull InventoryType type) {
        this.slots.addAll(SlotManager.parseSlot(slotString, type));
        return this;
    }

    public @NotNull InventoryItem addSlots(int slot) {
        this.slots.add(slot);
        return this;
    }

    public @NotNull InventoryItem addSlots(int... slots) {
        for (int slot : slots) {
            this.slots.add(slot);
        }
        return this;
    }

    public @NotNull InventoryItem addSlots(@NotNull List<Integer> slots) {
        this.slots.addAll(slots);
        return this;
    }

    /**
     * Adds slots from start to end including start and end.
     *
     * @param start The start slot.
     * @param end   The end slot.
     * @return This instance.
     */
    public @NotNull InventoryItem addSlots(int start, int end) {
        IntStream.range(start, end + 1).forEachOrdered(this::addSlots);
        return this;
    }

    /**
     * Used to get the list of click actions for this item.
     *
     * @return The list of click actions.
     */
    public @NotNull List<ClickAction> getClickActionList() {
        return this.clickActionList;
    }

    /**
     * Used to add a click action to this item.
     *
     * @param clickAction The instance of the click action.
     * @return This instance.
     */
    public @NotNull InventoryItem addClickAction(@NotNull ClickAction clickAction) {
        this.clickActionList.add(clickAction);
        return this;
    }

    @Override
    public @NotNull ItemStack convertToItemStack() {
        return this.itemStack.deepClone();
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new HashMap<>());

        section.set("name", this.getName());
        section.set("lore", this.getLore());
        section.set("material", this.getMaterial().name());
        section.set("amount", this.getAmount());
        section.set("durability", this.getDurability());
        section.set("flags", this.getFlagsAsStringList());
        section.set("hide_flags", this.getHideFlags());
        section.set("nbt", this.getNbtAsSection());

        return section;
    }

    @Override
    public @NotNull InventoryItem convert(@NotNull ConfigurationSection section) {
        InventoryItem item = new InventoryItem();

        item.setName(section.getString("name"));
        item.setLore(section.getListString("lore"));
        item.setMaterial(ItemType.valueOf(section.getString("material").toUpperCase()));
        item.setAmount(section.getInteger("amount"));
        item.setDurability(section.getInteger("durability"));
        item.setFlags(section.getListString("flags"));
        item.setHideFlags(section.getInteger("hide_flags"));
        item.setNBT(section.getSection("nbt"));

        return item;
    }
}
