package se.kth.iv1350.dto;

import se.kth.iv1350.model.Item;
import java.time.*;
import java.util.ArrayList;
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
    public final List<ItemDTO> items;
    public final double payment;
    public final double change;

    /**
     * Initializes a filled new instance of the type SaleDTO.
     * @param saleID The unique id of the sale.
     * @param items The list of items in the sale.
     * @param dateTime The time of creation of the SaleDTO.
     * @param customerID The id of the customer in the sale. Null if unknown.
     * @param totalDiscount The total discount in currency. Null if unknown.
     */
    SaleDTO(int saleID, List<Item> items, LocalDateTime dateTime, Integer customerID, double totalDiscount, double payment)
    {
        this.items = new ArrayList<>();
        if (items != null) {
            for (Item item : items) {
                this.items.add(item.dto());
            }
        }
        this.saleID = saleID;
        this.dateTime = dateTime;
        this.customerID = customerID;
        this.totalDiscount = totalDiscount;
        this.totalCostBeforeDiscount = calculateTotalCost();
        this.payment = payment;
        this.change = payment-totalCostBeforeDiscount+totalDiscount;
    }

    public String toString()
    {
        String string = "";

        if (payment > 0) {
            string += "\nReceipt:";
        }

        string += "\nSale id: " + saleID;

        if (dateTime != null) {
            int day = dateTime.getDayOfMonth();
            int month = dateTime.getMonthValue();
            int year = dateTime.getYear();
            string += "\nDate: " + day + "-" + month + "-" + year;
            
            string += "\nTime: " + dateTime.toLocalTime().toString().split("\\.")[0];
            
        }
        
        if (items.size() > 0) {
            string += "\nItems: ";
            for (ItemDTO item : items) {
                string += "\n" + item.name + "*" + item.count + ", " + item.cost + "*" + item.count + ", vat: " + ((double)Math.round(item.cost*item.count*item.vat*100))/100 + " (" + (item.vat*100) + "%)";
            }
        }
        
        double cost = totalCostBeforeDiscount;
        double discount = totalDiscount;
        if (discount != 0) {
            string += "\nCost before discount: " + cost;
            

            string += "\nDiscount: " + discount;
            
        }

        string += "\nTotal: " + (cost-discount);
        if (payment > 0) {
            string += "\nPaid: " + payment;
            string += "\nChange: " + change;
        }

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
        double payment = 0;

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

        public SaleDTOBuilder setPayment(double payment)
        {
            this.payment = payment;
            return this;
        }

        public SaleDTO build()
        {
            return new SaleDTO(saleID, items, dateTime, customerID, totalDiscount, payment);
        }
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
