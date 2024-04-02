package se.kth.iv1350.inventory;

import java.util.ArrayList;

import se.kth.iv1350.dto.SaleDTO;

/**
 * Substitute for an external inventory DB system. 
 * Contains information about dummy items and methods to manipulate and access it.
 */
public class Inventory {
    private ArrayList<InvItem> list;
    private InvItem dummy;

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
     */
    public String lookup(int itemID)
    {
        for (InvItem item : list) {
            if (item.id == itemID) {
                String info = "id:"+itemID+",name:"+item.name+",cost:"+item.cost+",vat"+item.vat+",description:"+item.description;
                return info;
            }
        }
        return null;
    }

    /**
     * Updates inventory by removing the items
     */
    public void remove(SaleDTO saleInfo)
    {
        return;
    }
}
