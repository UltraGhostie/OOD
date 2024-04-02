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
    }


    private void outputSale()
    {
        String string;

        string = "Sale id: " + saleInfo.saleID;
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



        printItems();
        
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
        
        string = item.name + "*" + item.count() + ", " + item.cost + "*" + item.vat + "*" + item.count();
    }

    private void out(String string)
    {
        System.out.println(string);
    }
}
