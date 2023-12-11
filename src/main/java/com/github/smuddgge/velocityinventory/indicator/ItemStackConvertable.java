package com.github.smuddgge.velocityinventory.indicator;

import dev.simplix.protocolize.api.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Indicates if the class can be converted into a item stack.
 */
public interface ItemStackConvertable {

    /**
     * Used to convert this class into an item stack.
     *
     * @return The instance of the item stack.
     */
    @NotNull ItemStack convertToItemStack();
}
