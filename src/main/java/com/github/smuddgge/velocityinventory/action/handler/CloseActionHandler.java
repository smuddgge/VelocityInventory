package com.github.smuddgge.velocityinventory.action.handler;

import com.github.smuddgge.velocityinventory.Inventory;
import com.github.smuddgge.velocityinventory.action.ActionHandler;
import com.github.smuddgge.velocityinventory.action.ActionResult;
import com.github.smuddgge.velocityinventory.action.action.CloseAction;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.api.inventory.InventoryClose;
import org.jetbrains.annotations.NotNull;

public class CloseActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onOpen(@NotNull Player player, @NotNull Inventory inventory) {
        return new ActionResult();
    }

    @Override
    public @NotNull ActionResult onClose(@NotNull InventoryClose inventoryClose, @NotNull Inventory inventory) {
        ActionResult result = new ActionResult();

        for (CloseAction closeAction : inventory.getAction(CloseAction.class)) {
            ActionResult tempResult = closeAction.onClose(inventoryClose, inventory);
            if (tempResult.hasChanged()) result = tempResult;
        }

        return result;
    }

    @Override
    public @NotNull ActionResult onClick(@NotNull InventoryClick inventoryClick, @NotNull Inventory inventory) {
        return new ActionResult();
    }
}
