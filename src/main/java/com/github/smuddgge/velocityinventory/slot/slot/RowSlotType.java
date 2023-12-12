package com.github.smuddgge.velocityinventory.slot.slot;

import com.github.smuddgge.velocityinventory.slot.SlotType;
import dev.simplix.protocolize.data.inventory.InventoryType;

import java.util.stream.IntStream;

public class RowSlotType implements SlotType {

    @Override
    public boolean match(String slot) {
        return slot.matches("^row[0-9]$");
    }

    @Override
    public int[] parse(String slot, InventoryType inventoryType) {
        int row = Integer.parseInt(slot.replace("row", ""));
        return IntStream.range((9 * row), 9 + (9 * row)).toArray();
    }
}
