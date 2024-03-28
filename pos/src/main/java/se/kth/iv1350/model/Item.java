package se.kth.iv1350.model;

import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;

/**
 * The Item class represents an item/ware/good type that is to be sold.
 */
public class Item {
    public final int itemID;
    public final int cost;
    public final float vat;
    public final String name;
    public final String description;
    private int count;

    /**
     * Initializes a new instance of the Item class representing information about a certain item type.
     * @param itemID Unique id for each item type.
     * @param name Item name.
     * @param cost Cost of item.
     * @param vat Value added tax percentage.
     * @param description Item description.
     */
    public Item(int itemID, String name, int cost, float vat, String description)
    {
        int oneItem = 1;
        this.itemID = itemID;
        this.name = name;
        this.cost = cost;
        this.vat = vat;
        this.description = description;
        this.count = oneItem;
    }

    /**
     * Initializes a new instance of the item class based on the information contained in the ItemDTO itemInfo.
     * @param itemInfo Container for the information of the item.
     */
    public Item(ItemDTO itemInfo)
    {
        int oneItem = 1;
        this.itemID = itemInfo.itemID;
        this.name = itemInfo.name;
        this.cost = itemInfo.cost;
        this.vat = itemInfo.vat;
        this.description = itemInfo.description;
        this.count = oneItem;
    }
    
    /**
     * Instantiates a new ItemDTO with information from this Item instance.
     * @return A new ItemDTO object.
     */
    public ItemDTO dto()
    {
        return new ItemDTO(itemID, name, cost, vat, description);
    }

    /**
     * Increments item count by one.
     */
    public void increment()
    {
        count++;
    }

    /**
     * Returns the count value.
     * @return Represents the amount of this item in the sale.
     */
    public int count()
    {
        return count;
    }
}
