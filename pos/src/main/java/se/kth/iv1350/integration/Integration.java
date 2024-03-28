package se.kth.iv1350.integration;

import se.kth.iv1350.accounting.Accounting;
import se.kth.iv1350.discount.Discount;
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.inventory.Inventory;
import se.kth.iv1350.peripherals.Register;

/**
 * The integration class is an interface between the controller and any external systems
 */
public class Integration {
    private Inventory inventory;
    private Accounting accounting;
    private Discount discount;
    private Register register;

    /**
     * Initializes a new object of the Integration type.
     */
    public Integration()
    {
        this.inventory = new Inventory();
        this.accounting = new Accounting();
        this.discount = new Discount();
        this.register = new Register();
    }

    /**
     * Queries the inventory database for information about the item with id itemID
     * @param itemID The id that is queried.
     * @return A new ItemDTO containing information about the item.
     */
    public ItemDTO lookup(int itemID)
    {
        String information = inventory.lookup(itemID);
        
        if (information == null) {
            return null;
        }

        return parse(information);
    }

    private ItemDTO parse(String itemInfo)
    {
        int id = 1;
        String name = "Test";
        int cost = 1;
        float vat = (float)0.06;
        String description = "Hello World!";
        //Stuff
        return new ItemDTO(id, name, cost, vat, description);
    }
}
