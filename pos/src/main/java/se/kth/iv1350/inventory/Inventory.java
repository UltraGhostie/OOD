package se.kth.iv1350.inventory;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import se.kth.iv1350.dto.SaleDTO;

/**
 * Substitute for an external inventory DB system. 
 * Contains information about dummy items and methods to manipulate and access it.
 */
public class Inventory {
    private ArrayList<InvItem> list;
    private InvItem dummy;

    /**
     * Thrown when a valid id does not have a matching item.
     */
    public class ItemNotFoundException extends Exception {
        public ItemNotFoundException() {}

        public ItemNotFoundException(String message)
        {
            super(message);
        }
    }

    /**
     * Initializes a new instance of the inventory class. Used as a substitute to an API for a database.
     */
    public Inventory()
    {
        this.list = new ArrayList<InvItem>();
        this.dummy = new InvItem(1, "foo", 1, 0.06, "bar");
        list.add(dummy);
    }

    /**
     * Queries database about the information of the item with the id itemID.
     * @param itemID The id of the item whose information is wanted.
     * @return The information in the format attribute:value with comma seperation between attributes.
     * Null if no item is found.
     * @throws TimeoutException If server is unable to be reached.
     * @throws InvalidParameterException If an invalid id is entered.
     * @throws ItemNotFoundException If an item with id id is not found.
     */
    public String lookup(int itemID) throws ItemNotFoundException, TimeoutException, InvalidParameterException
    {
        if (itemID == -1) {
            throw new TimeoutException("Server could not be reached.");
        }
        if (itemID < -1) {
            throw new InvalidParameterException("Item ids are always positive.");
        }
        for (InvItem item : list) {
            if (item.id == itemID) {
                String info = "id:"+itemID+",name:"+item.name+",cost:"+item.cost+",vat:"+item.vat+",description:"+item.description;
                return info;
            }
        }
        throw new ItemNotFoundException("No item with id: " + itemID + " found.");
    }

    /**
     * Updates inventory by removing the items
     */
    public void remove(SaleDTO saleInfo)
    {
        return;
    }
}
