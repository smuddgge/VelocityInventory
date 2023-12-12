package com.github.smuddgge.velocityinventory.slot.slot;

import com.github.smuddgge.velocityinventory.slot.SlotType;
import dev.simplix.protocolize.data.inventory.InventoryType;

public class CenterSlotType implements SlotType {

    @Override
    public boolean match(String slot) {
        return slot.matches("^center[0-9]$");
    }

    @Override
    public int[] parse(String slot, InventoryType inventoryType) {
        String argument = slot.replace("center", "");

        try {
            int col = Integer.parseInt(argument);
            return new int[]{4 + (col * 9)};

        } catch (NumberFormatException exception) {
            throw new RuntimeException(exception);
        }
    }
}
