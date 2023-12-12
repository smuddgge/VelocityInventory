package com.github.smuddgge.velocityinventory.action.action;

import com.github.smuddgge.velocityinventory.Inventory;
import com.github.smuddgge.velocityinventory.action.Action;
import com.github.smuddgge.velocityinventory.action.ActionResult;
import dev.simplix.protocolize.api.inventory.InventoryClose;
import org.jetbrains.annotations.NotNull;

public interface CloseAction extends Action {

    @NotNull ActionResult onClose(@NotNull InventoryClose inventoryClose, @NotNull Inventory inventory);
}
