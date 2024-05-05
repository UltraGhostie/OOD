package se.kth.iv1350.model;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import se.kth.iv1350.dto.DiscountDTO;
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.view.Observer;

/**
 * Contains information about the an ongoing sale.
 */
public class Sale implements Observer{
    private int id;
    private ArrayList<Item> items;
    private double totalDiscount;
    private double payment = 0;
    
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
        if (itemInfo == null) {
            throw new InvalidParameterException();
        }
        for (Item currentItem : items) {
            if (currentItem.itemID == itemInfo.itemID) {
                currentItem.increment();
                return;
            }
        }
        Item item = new Item(itemInfo);
        items.add(item);
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
        String invalidCountMessage = "Illegal value for parameter itemCount.";
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
    }
    
    /**
     * @param payment Sets payment to given value.
     */
    public void setPayment(double payment)
    {
        this.payment = payment;
    }

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        //Stuff
    }

    /**
     * Set payment.
     * @param amount The payment amount
     * @return The amount of change
     */
    public double setPayment(double amount)
    {
        SaleDTO saleInfo = dto();
        double cost = saleInfo.totalCostBeforeDiscount - saleInfo.totalDiscount;
        if (amount - cost < 0) {
            throw new InvalidParameterException("Payment requirements not met.");
        }
        return amount - cost;
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
}
