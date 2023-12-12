package com.github.smuddgge.velocityinventory.action.action;

import com.github.smuddgge.velocityinventory.Inventory;
import com.github.smuddgge.velocityinventory.action.Action;
import com.github.smuddgge.velocityinventory.action.ActionResult;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an open action.
 * Called when the inventory is opened.
 */
public interface OpenAction extends Action {

    /**
     * Called when the inventory is opened.
     *
     * @param player    The instance of the player.
     * @param inventory The instance of the inventory.
     */
    @NotNull ActionResult onOpen(@NotNull Player player, @NotNull Inventory inventory);
}
