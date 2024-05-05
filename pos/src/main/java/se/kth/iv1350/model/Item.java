package se.kth.iv1350.model;

import se.kth.iv1350.dto.ItemDTO;

/**
 * The Item class represents an item/ware/good type that is to be sold.
 */
public class Item {
    public final int itemID;
    public final double cost;
    public final double vat;
    public final String name;
    public final String description;
    private int count;

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
        return new ItemDTO.ItemDTOBuilder(itemID).setCost(cost).setDescription(description).setName(name).setVat(vat).setCount(count).build();
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

    /**
     * Sets the count of this item to a non-negative, non-zero integer
     * @param count The amount of this item.
     */
    public void setCount(int count)
    {
        this.count = count;
    }
}
