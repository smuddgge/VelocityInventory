package com.github.smuddgge.velocityinventory.action.action;

import com.github.smuddgge.velocityinventory.Inventory;
import com.github.smuddgge.velocityinventory.action.Action;
import com.github.smuddgge.velocityinventory.action.ActionResult;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import org.jetbrains.annotations.NotNull;

public interface ClickAction extends Action {

    @NotNull ActionResult onClick(@NotNull InventoryClick inventoryClick, @NotNull Inventory inventory);
}
