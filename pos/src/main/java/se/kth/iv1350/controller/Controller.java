package se.kth.iv1350.controller;

import se.kth.iv1350.model.*;
import se.kth.iv1350.view.Observer;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import se.kth.iv1350.dto.*;
import se.kth.iv1350.integration.Integration;

/**
 * The controller executes actions when prompted by a view.
 */
public class Controller {
    private Integration integration;
    static Controller INSTANCE = new Controller();
    private Sale currentSale;
    private ArrayList<Observer> onPaymentSubscribers = new ArrayList<>();
    private ArrayList<Observer> onUpdateSubscribers = new ArrayList<>();

    /**
     * Initializes a new object of the controller type.
     * @param integration The integration used to communicate with external systems.
     */
    private Controller()
    {
        this.integration = Integration.getInstance();
    }

    public static Controller getInstance()
    {
        return INSTANCE;
    }
    /**
     * Creates a new sale.
     * @return A new empty SaleDTO object.
     */
    public void startSale()
    {
        Sale newSale = new Sale();
        this.currentSale = newSale;
        updateOnUpdate();
    }

    /**
     * Adds an item with the itemID if it is found. If the itemID is invalid returns null.
     * @param itemID The unique id of the item to find.
     * @return A new SaleDTO containing information about the updated sale or null if item is not found.
     * @throws TimeoutException If server is unable to be reached.
     * @throws InvalidParameterException If an invalid id is entered or there is no item with id itemID.
     */
    public void scanItem(int itemID) throws TimeoutException, InvalidParameterException
    {
        if (currentSale.contains(itemID)) {
            currentSale.increment(itemID);
            return;
        }
        try {  
            ItemDTO itemInfo = integration.lookup(itemID);
            Item item = new Item(itemInfo);
            currentSale.add(item);
            updateOnUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Attempts to set the count of the item with the id itemID to the non-zero, non-negative itemCount.
     * @param itemID The id of the item to be changed.
     * @param itemCount The new count of the item.
     * @throws InvalidParameterException If count is below 1 or an item with the given id is not in sale.
     */
    public void setCount(int itemID, int itemCount) throws InvalidParameterException
    {
        try {
            currentSale.setCount(itemID, itemCount);
            updateOnUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Send a request to the discount system to retrieve discount data about the current sale.
     * @param customerID The id of the customer.
     */
    public void discountRequest(int customerID)
    {
        currentSale.applyDiscount(integration.discountRequest(currentSale.dto()));
        updateOnUpdate();
    }

    /**
     * Completes the sale by adding payment and change to sale.
     * @param amount The payment given by the customer.
     */
    public void enterPayment(double amount)
    {
        SaleDTO saleInfo = currentSale.dto();
        String badPaymentMessage = "Payment amount is lower than cost.";
        double cost = saleInfo.totalCostBeforeDiscount - saleInfo.totalDiscount;
        if (amount - cost < 0) {
            throw new InvalidParameterException(badPaymentMessage);
        }
        currentSale.setPayment(amount);
        saleInfo = currentSale.dto();
        integration.removeInventory(saleInfo);
        integration.recordSale(saleInfo);
        integration.updateRegister(amount);
        updateOnPayment();
        updateOnUpdate();
        currentSale = null;
        return;
    }

    /**
     * @param observer Adds observer as a subscriber to the event OnPayment.
     */
    public void subscribeOnPayment(Observer observer)
    {
        this.onPaymentSubscribers.add(observer);
    }

    /**
     * @param observer Adds observer as a subscriber to the event OnUpdate.
     */
    public void subscribeOnUpdate(Observer observer)
    {
        this.onUpdateSubscribers.add(observer);
    }

    private void updateOnUpdate()
    {
        for (Observer observer : onUpdateSubscribers) {
            observer.stateChange(currentSale.dto());
        }
    }

    private void updateOnPayment()
    {
        for (Observer observer : onPaymentSubscribers) {
            observer.stateChange(currentSale.dto());
        }
    }
}
