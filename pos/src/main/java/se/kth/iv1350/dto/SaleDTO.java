package se.kth.iv1350.dto;

import se.kth.iv1350.model.Item;
import java.time.*;
import java.util.ArrayList;

/**
 * An immutable class for transferring data relevant to sales.
 */
public class SaleDTO {
    public final int saleID;
    public final LocalDateTime dateTime;
    public final Integer customerID;
    public final double totalDiscount;
    public final double totalCostBeforeDiscount;
    public final ArrayList<ItemDTO> items;

    /**
     * Initializes an empty new instance of the type SaleDTO.
     * 
     * @param saleID The unique id of the sale.
     */
    public SaleDTO(int saleID)
    {
        ArrayList<ItemDTO> emptyList = new ArrayList<ItemDTO>();
        Integer noCustomerID = null;
        int noDiscount = 0;
        int noCost = 0;

        this.saleID = saleID;
        items = emptyList;
        dateTime = LocalDateTime.now();
        customerID = noCustomerID;
        totalDiscount = noDiscount;
        totalCostBeforeDiscount = noCost;
    }

    /**
     * Initializes a filled new instance of the type SaleDTO.
     * @param saleID The unique id of the sale.
     * @param items The list of items in the sale.
     * @param dateTime The time of creation of the SaleDTO.
     * @param customerID The id of the customer in the sale. Null if unknown.
     * @param totalDiscount The total discount in currency. Null if unknown.
     */
    public SaleDTO(int saleID, ArrayList<Item> items, LocalDateTime dateTime, Integer customerID, double totalDiscount)
    {
        this.items = new ArrayList<>();
        for (Item item : items) {
            this.items.add(item.dto());
        }
        this.saleID = saleID;
        this.dateTime = dateTime;
        this.customerID = customerID;
        this.totalDiscount = totalDiscount;
        this.totalCostBeforeDiscount = calculateTotalCost();
    }

    private double calculateTotalCost()
    {
        double total = 0;
        if (items == null) {
            return total;
        }
        for (ItemDTO currentItem : items) {
            total = currentItem.cost * currentItem.count * (1+currentItem.vat);
        }
        return total;
    }
}
