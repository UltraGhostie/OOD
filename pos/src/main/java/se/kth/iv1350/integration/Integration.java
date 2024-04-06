package se.kth.iv1350.integration;

import se.kth.iv1350.accounting.Accounting;
import se.kth.iv1350.discount.Discount;
import se.kth.iv1350.dto.DiscountDTO;
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;
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

    /**
     * Sends a request to the discount system asking for discount information.
     * @param customerID The unique id of the customer requesting discount.
     * @param itemIDs An array containing the ids of the items in the sale.
     * @param totalCost The total cost of the items in the sale, excluding vat.
     * @return An object containing the data of the varius discounts.
     */
    public DiscountDTO discountRequest(int customerID, int[] itemIDs, int totalCost)
    {
        double customerDiscount = discount.customerDiscount(customerID);
        double totalCostDiscount = discount.totalCostDiscount(totalCost);
        int flatDiscount = discount.flatDiscount(itemIDs);
        DiscountDTO discountInfo = new DiscountDTO(customerDiscount, totalCostDiscount, flatDiscount);
        return discountInfo;
    }

    /**
     * Records the sale to the external accounting system.
     * @param saleDTO The sale to be recorded.
     */
    public void recordSale(SaleDTO saleDTO)
    {
        accounting.record(saleDTO);
    }

    /**
     * Updates the register with the given amount.
     * @param amount The balance change of the register.
     */
    public void updateRegister(double amount)
    {
        register.Update(amount);
    }

    /**
     * Decreases the count of items in the external inventory system by their respective counts in the sale.
     * @param saleDTO The sale containing the items to be removed.
     */
    public void removeInventory(SaleDTO saleDTO)
    {
        inventory.remove(saleDTO);
    }

    private ItemDTO parse(String itemInfo)
    {
        int id = -1;
        String name = null;
        double cost = -1;
        double vat = -1;
        String description = null;
        String attributeSeperator = ",";
        String valueSeperator = ":";
        String[] strings = itemInfo.split(attributeSeperator);
        for (String string : strings) {
            String[] split = string.split(valueSeperator);
            String attribute = split[0];
            String value = split[1];
            switch (attribute) {
                case "id":
                    id = Integer.parseInt(value);
                    break;
                case "name":
                    name = value;
                    break;
                case "cost":
                    cost = Double.parseDouble(value);
                    break;
                case "vat":
                    vat = Double.parseDouble(value);
                    break;
                case "description":
                    description = value;
                    break;
            }
        }
        return new ItemDTO(id, name, cost, vat, description);
    }
}
