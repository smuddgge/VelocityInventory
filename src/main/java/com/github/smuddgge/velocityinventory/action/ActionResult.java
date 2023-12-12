package com.github.smuddgge.velocityinventory.action;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a inventory action result.
 */
public class ActionResult {

    private boolean changeCancelled = false;
    private boolean cancel = false;

    /**
     * Used to change the state of the event.
     *
     * @param cancel True to cancel the event.
     * @return This instance.
     */
    public @NotNull ActionResult setCancelled(boolean cancel) {
        this.changeCancelled = true;
        this.cancel = cancel;
        return this;
    }

    /**
     * Used to check if the set cancelled should be set true.
     *
     * @return True if the event should be cancelled.
     */
    public boolean isCancelTrue() {
        return this.changeCancelled && this.cancel;
    }

    /**
     * Used to check if the set cancelled should be set false.
     *
     * @return True if the event should not be cancelled.
     */
    public boolean isCancelFalse() {
        return this.changeCancelled && !this.cancel;
    }

    /**
     * Used to check if the result has been changed.
     *
     * @return If the result has been changed.
     */
    public boolean hasChanged() {
        return this.changeCancelled;
    }
}
