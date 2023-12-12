package com.github.smuddgge.velocityinventory.action;

import com.github.smuddgge.velocityinventory.Inventory;
import com.github.smuddgge.velocityinventory.action.handler.ClickActionHandler;
import com.github.smuddgge.velocityinventory.action.handler.CloseActionHandler;
import com.github.smuddgge.velocityinventory.action.handler.OpenActionHandler;
import com.velocitypowered.api.proxy.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an action manager.
 * Used to handle the actions.
 */
public class ActionManager {

    private final @NotNull List<ActionHandler> actionHandlerList;

    /**
     * Used to create an action manager.
     *
     * @param inventory The instance of the inventory
     *                  the action manager is for.
     */
    public ActionManager(@NotNull Inventory inventory) {
        this.actionHandlerList = new ArrayList<>();
        this.actionHandlerList.add(new OpenActionHandler());
        this.actionHandlerList.add(new ClickActionHandler());
        this.actionHandlerList.add(new CloseActionHandler());

        inventory.getBaseInventory().onClick(inventoryClick -> {
            ActionResult result = new ActionResult();

            for (ActionHandler actionHandler : this.actionHandlerList) {
                ActionResult tempResult = actionHandler.onClick(inventoryClick, inventory);
                if (tempResult.hasChanged()) result = tempResult;
            }

            if (result.isCancelTrue()) inventoryClick.cancelled(true);
        });
    }

    /**
     * Called when the inventory is opened.
     *
     * @param player    The instance of the player.
     * @param inventory The instance of the inventory.
     * @return The result of the actions.
     */
    public @NotNull ActionResult onOpen(@NotNull Player player, @NotNull Inventory inventory) {
        ActionResult result = new ActionResult();

        for (ActionHandler actionHandler : this.actionHandlerList) {
            ActionResult tempResult = actionHandler.onOpen(player, inventory);
            if (tempResult.hasChanged()) result = tempResult;
        }

        return result;
    }
}
