package se.kth.iv1350.model;
import se.kth.iv1350.view.SaleObserver;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import se.kth.iv1350.controller.Observable;
import se.kth.iv1350.dto.DiscountDTO;
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;

/**
 * Contains information about the an ongoing sale.
 */
public class Sale implements Observable {
    private int id;
    private ArrayList<Item> items;
    private double totalDiscount;
    private double payment = 0;
    private ArrayList<SaleObserver> onFinishSubscribers = new ArrayList<>();
    private ArrayList<SaleObserver> onUpdateSubscribers = new ArrayList<>();
    
    /**
     * Initializes a new object of type sale.
     */
    public Sale()
    {
        this.id = generateID();
        this.items = new ArrayList<Item>();
        totalDiscount = 0;
    }
    
    /**
     * Returns a new SaleDTO object containing the information of this Sale object.
     * @return A filled SaleDTO.
     */
    public SaleDTO dto()
    {
        LocalDateTime dateTime = LocalDateTime.now();
        return new SaleDTO.SaleDTOBuilder(id).setDateTime(dateTime).setItems(items).setTotalDiscount(totalDiscount).setPayment(payment).build();
    }

    /**
     * Checks if sale contains an item with the id itemID.
     * @param itemID The id that is searched for.
     * @return True if an item is found, otherwise false.
     */
    private boolean contains(int itemID)
    {
        boolean found = true;
        for (Item item : items) {
            if (item.itemID == itemID) {
                return found;
            }
        }
        return !found;
    }

    /**
     * Adds the item to the sale
     * @param itemInfo The item to add to the sale.
     */
    public void add(ItemDTO itemInfo)
    {        
        Item newItem = new Item(itemInfo);

        for (Item item : items) {
            if (item.itemID == newItem.itemID) {
                item.increment();
                return;
            }
        }
        items.add(newItem);
        updateOnUpdate();
    }

    /**
     * Sets the count of the item with id itemID to the non-zero, non-negative integer count.
     * @param itemID The unique id of the item to be changed.
     * @param itemCount The amount of the item. Non-zero, non-negative.
     * @throws InvalidParameterException If count is below 1 or an item with the given id is not in sale.
     */
    public void setCount(int itemID, int itemCount)
    {
        int oneItem = 1;
        String invalidCountMessage = "Illegal value for parameter itemCount (tried to set item to " + itemCount + ").";
        String invalidItemMessage = "No item with id " + id + " in sale.";
        if (itemCount < oneItem) {
            throw new InvalidParameterException(invalidCountMessage);
        }
        if (!contains(itemID)) {
            throw new InvalidParameterException(invalidItemMessage);
        }

        for (Item item : items) {
            if (itemID == item.itemID) {
                item.setCount(itemCount);
            }
        }
        updateOnUpdate();
    }

    /**
     * Applies discount based on discountInfo to totalDiscount.
     * @param discountInfo A DTO containing information about discounts for this sale.
     */
    public void applyDiscount(DiscountDTO discountInfo)
    {
        totalDiscount += discountInfo.flatDiscount;
        totalDiscount += discountInfo.customerDiscount * totalCost();
        totalDiscount += discountInfo.totalDiscount * totalCost();
        updateOnUpdate();
    }

    /**
     * Set payment. Presumes the action ends the sale and thus fires the onFinish event.
     * @param amount The payment amount
     * @return The amount of change
     */
    public void setPayment(double amount) throws InvalidParameterException
    {
        SaleDTO saleInfo = dto();
        double cost = saleInfo.totalCostBeforeDiscount - saleInfo.totalDiscount;
        double change = amount - cost;
        if (change < 0) {
            throw new InvalidParameterException("Payment requirements not met.");
        }
        this.payment = amount;
        updateOnFinish();
        updateOnUpdate();
    }

    private int totalCost()
    {
        int total = 0;
        for (Item item : items) {
            total += item.cost * item.count();
        }
        return total;
    }

    private int generateID()
    {
        int id = 0;
        Random random = new Random();
        id = Math.abs(random.nextInt());
        return id;
    }

    /**
     * @param observer Adds observer as a subscriber to the event OnPayment.
     */
    @Override
    public void subscribeOnFinish(SaleObserver observer)
    {
        this.onFinishSubscribers.add(observer);
        observer.stateChange(dto());
    }

    /**
     * @param observer Adds observer as a subscriber to the event OnUpdate.
     */
    @Override
    public void subscribeOnUpdate(SaleObserver observer)
    {
        this.onUpdateSubscribers.add(observer);
        observer.stateChange(dto());
    }

    private void updateOnUpdate()
    {
        for (SaleObserver observer : onUpdateSubscribers) {
            observer.stateChange(dto());
        }
    }

    private void updateOnFinish()
    {
        for (SaleObserver observer : onFinishSubscribers) {
            observer.stateChange(dto());
        }
    }
}
