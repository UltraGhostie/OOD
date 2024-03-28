package se.kth.iv1350.controller;

import se.kth.iv1350.model.*;
import se.kth.iv1350.dto.*;
import se.kth.iv1350.integration.Integration;

/**
 * The controller executes actions when prompted by a view.
 */
public class Controller {
    private Integration integration;
    private Sale currentSale;

    /**
     * Initializes a new object of the controller type.
     * @param integration The integration used to communicate with external systems.
     */
    public Controller(Integration integration)
    {
        this.integration = integration;
    }

    /**
     * Creates a new sale.
     * @return A new empty SaleDTO object.
     */
    public SaleDTO startSale()
    {
        Sale newSale = new Sale();
        this.currentSale = newSale;
        return newSale.dto();
    }

    /**
     * Adds an item with the itemID if it is found. If the itemID is invalid returns null.
     * @param itemID The unique id of the item to find.
     * @return A new SaleDTO containing information about the updated sale or null if item is not found.
     */
    public SaleDTO scanItem(int itemID)
    {
        if (currentSale.contains(itemID)) {
            currentSale.add(itemID);
            return currentSale.dto();
        }

        ItemDTO itemInfo = integration.lookup(itemID);
        if (itemInfo == null) {
            return null;
        }

        Item item = new Item(itemInfo);
        currentSale.add(item);
        return currentSale.dto();
    }
}
