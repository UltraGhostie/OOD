package se.kth.iv1350.controller;

import se.kth.iv1350.model.*;

import java.util.ArrayList;

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

    /**
     * Attempts to set the count of the item with the id itemID to the non-zero, non-negative itemCount.
     * Returns null if unsuccessfull.
     * @param itemID The id of the item to be changed.
     * @param itemCount The new count of the item.
     */
    public SaleDTO setCount(int itemID, int itemCount)
    {
        try {
            currentSale.setCount(itemID, itemCount);
            return currentSale.dto();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Send a request to the discount system to retrieve discount data about the current sale.
     * @param customerID The id of the customer.
     * @return A new instance of SaleDTO containing previous data as well as discount data.
     */
    public SaleDTO discountRequest(int customerID)
    {
        ArrayList<Integer> itemIDList = new ArrayList<Integer>();
        int totalCost = 0;
        for (Item item : currentSale.dto().items) {
            itemIDList.add(item.itemID);
            totalCost += item.cost * item.count();
        }
        int[] itemIDArray = new int[itemIDList.size()];
        for (int i = 0; i < itemIDList.size(); i++) {
            itemIDArray[i] = itemIDList.get(i);
        }
        DiscountDTO discountInfo = integration.discountRequest(customerID, itemIDArray, totalCost);
        currentSale.applyDiscount(discountInfo);
        return currentSale.dto();
    }
}
