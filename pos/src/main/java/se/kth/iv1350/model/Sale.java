package se.kth.iv1350.model;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

import se.kth.iv1350.dto.DiscountDTO;
import se.kth.iv1350.dto.SaleDTO;

/**
 * Contains information about the an ongoing sale.
 */
public class Sale {
    private int id;
    private ArrayList<Item> items;
    private Integer customerID;
    private double totalDiscount;
    
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
        return new SaleDTO(id, items, dateTime, customerID, totalDiscount);
    }

    /**
     * Checks if sale contains an item with the id itemID.
     * @param itemID The id that is searched for.
     * @return True if an item is found, otherwise false.
     */
    public boolean contains(int itemID)
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
     * Adds a new item to the sale.
     * @param item The item to be added to the sale.
     */
    public void add(Item item)
    {
        try {
            add(item.itemID);
        } catch (Exception e) {
            items.add(item);
        }
    }

    /**
     * Increases the count of the item with id itemID. Throws a new InvalidParameterException if no item with
     * id itemID found.
     * @param itemID The id of the item to increase count of.
     */
    public void add(int itemID)
    {
        for (Item item : items) {
            if (item.itemID == itemID) {
                item.increment();
                return;
            }
        }
        throw new InvalidParameterException("No item with id: " + itemID + " found");
    }

    /**
     * Sets the count of the item with id itemID to the non-zero, non-negative integer count.
     * Throws an InvalidParameterException if itemID is not found or count is below 1.
     * @param itemID The unique id of the item to be changed.
     * @param itemCount The amount of the item. Non-zero, non-negative.
     */
    public void setCount(int itemID, int itemCount)
    {
        int oneItem = 1;
        if (itemCount < oneItem || !contains(itemID)) {
            throw new InvalidParameterException();
        }

        for (Item item : items) {
            if (itemID == item.itemID) {
                item.setCount(itemCount);
            }
        }
    }

    public void applyDiscount(DiscountDTO discountInfo)
    {
        totalDiscount += discountInfo.flatDiscount;
        totalDiscount += discountInfo.customerDiscount * totalCost();
        totalDiscount += discountInfo.totalDiscount * totalCost();
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
