package se.kth.iv1350.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

import se.kth.iv1350.dto.SaleDTO;

/**
 * Contains information about the an ongoing sale.
 */
public class Sale {
    private int id;
    private ArrayList<Item> items;
    private Integer customerID;
    private Integer totalDiscount;
    
    /**
     * Initializes a new object of type sale.
     */
    public Sale()
    {
        this.id = generateID();
        this.items = new ArrayList<Item>();
    }
    
    /**
     * Returns a new SaleDTO object containing the information of this Sale object.
     * @return A filled SaleDTO.
     */
    public SaleDTO dto()
    {
        return new SaleDTO(id, items, null, customerID, id);
    }

    /**
     * Calculates the total cost of items in this sale instance including taxes.
     * @return The total cost as an int.
     */
    private int totalCost()
    {
        return 0;
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
        items.add(item);
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

    private int generateID()
    {
        int id = 0;
        Random random = new Random();
        id = random.nextInt();
        return id;
    }
}
