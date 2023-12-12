package com.github.smuddgge.velocityinventory.action.handler;

import com.github.smuddgge.velocityinventory.Inventory;
import com.github.smuddgge.velocityinventory.action.ActionHandler;
import com.github.smuddgge.velocityinventory.action.ActionResult;
import com.github.smuddgge.velocityinventory.action.action.ClickAction;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.api.inventory.InventoryClose;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClickActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onOpen(@NotNull Player player, @NotNull Inventory inventory) {
        return null;
    }

    @Override
    public @NotNull ActionResult onClose(@NotNull InventoryClose inventoryClose, @NotNull Inventory inventory) {
        return null;
    }

    @Override
    public @NotNull ActionResult onClick(@NotNull InventoryClick inventoryClick, @NotNull Inventory inventory) {
        ActionResult result = new ActionResult();

        for (ClickAction clickAction : inventory.getAction(ClickAction.class)) {
            List<Integer> slots = inventory.getClickActionSlots(clickAction);
            if (slots == null) continue;
            if (!slots.contains(inventoryClick.slot())) continue;

            ActionResult tempResult = clickAction.onClick(inventoryClick, inventory);
            if (tempResult.hasChanged()) result = tempResult;
        }

        return result;
    }
}
