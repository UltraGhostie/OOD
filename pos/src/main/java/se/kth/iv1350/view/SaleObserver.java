package se.kth.iv1350.view;

import se.kth.iv1350.dto.SaleDTO;

/**
 * Interface for observers.
 */
public interface SaleObserver {
    /**
     * Informs observers of change in state along with the new state.
     */
    public void stateChange(SaleDTO object);
}
