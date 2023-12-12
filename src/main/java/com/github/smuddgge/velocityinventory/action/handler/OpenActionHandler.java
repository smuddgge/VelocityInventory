package com.github.smuddgge.velocityinventory.action.handler;

import com.github.smuddgge.velocityinventory.Inventory;
import com.github.smuddgge.velocityinventory.action.ActionHandler;
import com.github.smuddgge.velocityinventory.action.ActionResult;
import com.github.smuddgge.velocityinventory.action.action.OpenAction;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.api.inventory.InventoryClose;
import org.jetbrains.annotations.NotNull;

public class OpenActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onOpen(@NotNull Player player, @NotNull Inventory inventory) {
        ActionResult result = new ActionResult();

        for (OpenAction openAction : inventory.getAction(OpenAction.class)) {
            ActionResult tempResult = openAction.onOpen(player, inventory);
            if (tempResult.hasChanged()) result = tempResult;
        }

        return result;
    }

    @Override
    public @NotNull ActionResult onClose(@NotNull InventoryClose inventoryClose, @NotNull Inventory inventory) {
        return null;
    }

    @Override
    public @NotNull ActionResult onClick(@NotNull InventoryClick inventoryClick, @NotNull Inventory inventory) {
        return null;
    }
}
