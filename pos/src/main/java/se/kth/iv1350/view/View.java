package se.kth.iv1350.view;

import java.util.ArrayList;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.model.Item;;

/**
 * Handles data input and output between the user and the controller.
 */
public class View {
    private Controller controller;
    private SaleDTO saleInfo;
    private double change;
    private double payment;

    /**
     * Initializes a new object of type View.
     * 
     * @param controller The controller that is used to interact with other systems.
     */
    public View(Controller controller)
    {
        SaleDTO noSale = null;

        this.saleInfo = noSale;
        this.controller = controller;
        this.testCase();
    }

    private void testCase()
    {
        int validItem = 1;
        int invalidItem = -1;
        int count = 15;
        int customerID = 1;
        
        payment = 0;
        saleInfo = controller.startSale();
        outputSale();
        scanItem(validItem);
        scanItem(validItem);
        saleInfo = controller.setCount(validItem, count);
        outputSale();
        scanItem(invalidItem);
        saleInfo = controller.discountRequest(customerID);
        outputSale();
        payment = 20;
        change = controller.enterPayment(payment);
        outputSale();
    }

    private void scanItem(int itemID)
    {
        String invalidIDMessage = "Invalid Item ID: " + itemID;
        SaleDTO saleDTO;
        saleDTO = controller.scanItem(itemID);
        if (saleDTO == null) {
            out(invalidIDMessage);
            return;
        }
        saleInfo = saleDTO;
        outputSale();
    }


    private void outputSale()
    {
        String string;

        if (payment > 0) {
            string = "\nReceipt:";
            out(string);
        }

        string = "\nSale id: " + saleInfo.saleID;
        out(string);

        if (saleInfo.dateTime != null) {
            int day = saleInfo.dateTime.getDayOfMonth();
            int month = saleInfo.dateTime.getMonthValue();
            int year = saleInfo.dateTime.getYear();
            string = "Date: " + day + "-" + month + "-" + year;
            out(string);
            string = "Time: " + saleInfo.dateTime.toLocalTime().toString().split("\\.")[0];
            out(string);
        }
        
        if (saleInfo.items.size() > 0) {
            string = "Items: ";
            out(string);
            printItems();
        }
        
        double cost = saleInfo.totalCostBeforeDiscount;
        double discount = saleInfo.totalDiscount;
        if (discount != 0) {
            string = "Cost before discount: " + cost;
            out(string);

            string = "Discount: " + discount;
            out(string);
        }

        string = "Cost: " + (cost-discount);
        out(string);

        if (payment != 0) {
            string = "Paid: " + payment;
            out(string);
    
            string = "Change: " + change;
            out(string);
        }
        out("");
    }

    private void printItems()
    {
        ArrayList<Item> items = saleInfo.items;

        for (Item item : items) {
            printItem(item);
        }
    }

    private void printItem(Item item)
    {
        String string;
        
        string = item.name + "*" + item.count() + ", " + item.cost + "*" + item.count() + ", vat: " + (double)Math.round(100*item.cost*item.count()*item.vat)/100 + " (" + item.vat*100 + "%)";
        out(string);
    }

    private void out(String string)
    {
        System.out.println(string);
    }
}
