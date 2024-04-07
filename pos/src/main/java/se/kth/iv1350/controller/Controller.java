package se.kth.iv1350.controller;

import se.kth.iv1350.model.Sale;
import se.kth.iv1350.dto.SaleDTO;
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
        try {
            currentSale.add(integration.lookup(itemID));
            return currentSale.dto();
        } catch (Exception e) {
            return null;
        }
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
        currentSale.applyDiscount(integration.discountRequest(currentSale.dto()));
        return currentSale.dto();
    }

    /**
     * Completes the sale and returns the amount of change to be given.
     * @param amount The payment given by the customer.
     * @return The change to be given to the customer.
     */
    public double enterPayment(double amount)
    {
        try {
            double change = currentSale.setPayment(amount);
            SaleDTO saleInfo = currentSale.dto();
            integration.removeInventory(saleInfo);
            integration.recordSale(saleInfo);
            integration.updateRegister(amount);
            currentSale = null;
            return change;
        } catch (Exception e) {
            throw e;
        }
    }
}
