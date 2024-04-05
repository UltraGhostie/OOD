package se.kth.iv1350.dto;

import se.kth.iv1350.model.Item;
import java.time.*;
import java.util.List;

/**
 * An immutable class for transferring data relevant to sales.
 */
public class SaleDTO {
    public final int saleID;
    public final LocalDateTime dateTime;
    public final Integer customerID;
    public final double totalDiscount;
    public final double totalCostBeforeDiscount;
    public final List<Item> items;

    /**
     * Initializes a filled new instance of the type SaleDTO.
     * @param saleID The unique id of the sale.
     * @param items The list of items in the sale.
     * @param dateTime The time of creation of the SaleDTO.
     * @param customerID The id of the customer in the sale. Null if unknown.
     * @param totalDiscount The total discount in currency. Null if unknown.
     */
    SaleDTO(int saleID, List<Item> items, LocalDateTime dateTime, Integer customerID, double totalDiscount)
    {
        this.saleID = saleID;
        this.items = items;
        this.dateTime = dateTime;
        this.customerID = customerID;
        this.totalDiscount = totalDiscount;
        this.totalCostBeforeDiscount = calculateTotalCost();
    }

    public String toString()
    {
        String string = "";

        string += "Sale id: " + saleID;

        if (dateTime != null) {
            int day = dateTime.getDayOfMonth();
            int month = dateTime.getMonthValue();
            int year = dateTime.getYear();
            string += "\nDate: " + day + "-" + month + "-" + year;
            
            string += "\nTime: " + dateTime.toLocalTime().toString().split("\\.")[0];
            
        }
        
        if (items.size() > 0) {
            string += "\nItems: ";
            for (Item item : items) {
                string += "\n" + item.name + "*" + item.count() + ", " + item.cost + "*" + item.count() + "*" + (1+item.vat);
            }
        }
        
        double cost = totalCostBeforeDiscount;
        double discount = totalDiscount;
        if (discount != 0) {
            string += "\nCost before discount: " + cost;
            

            string += "\nDiscount: " + discount;
            
        }

        string += "\nCost: " + (cost-discount);
        return string;
    }

    public static class SaleDTOBuilder {
        //Required
        int saleID;

        //Optional
        List<Item> items;
        LocalDateTime dateTime;
        Integer customerID;
        double totalDiscount = 0;

        public SaleDTOBuilder(int saleID)
        {
            this.saleID = saleID;
        }

        public SaleDTOBuilder setItems(List<Item> items)
        {
            this.items = items;
            return this;
        }

        public SaleDTOBuilder setDateTime(LocalDateTime dateTime)
        {
            this.dateTime = dateTime;
            return this;
        }

        public SaleDTOBuilder setCustomerID(int customerID)
        {
            this.customerID = customerID;
            return this;
        }
        
        public SaleDTOBuilder setTotalDiscount(double totalDiscount)
        {
            this.totalDiscount = totalDiscount;
            return this;
        }

        public SaleDTO build()
        {
            return new SaleDTO(saleID, items, dateTime, customerID, totalDiscount);
        }
    }

    private double calculateTotalCost()
    {
        double total = 0;
        if (items == null) {
            return total;
        }
        for (Item currentItem : items) {
            total = currentItem.cost * currentItem.count() * (1+currentItem.vat);
        }
        return total;
    }
}
