package com.github.smuddgge.velocityinventory.action;

import com.github.smuddgge.velocityinventory.Inventory;
import com.velocitypowered.api.proxy.Player;
import dev.simplix.protocolize.api.inventory.InventoryClick;
import dev.simplix.protocolize.api.inventory.InventoryClose;
import org.jetbrains.annotations.NotNull;

public interface ActionHandler {

    @NotNull ActionResult onOpen(@NotNull Player player, @NotNull Inventory inventory);

    @NotNull ActionResult onClose(@NotNull InventoryClose inventoryClose, @NotNull Inventory inventory);

    @NotNull ActionResult onClick(@NotNull InventoryClick inventoryClick, @NotNull Inventory inventory);
}
