package se.kth.iv1350.view;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;

/**
 * Handles data input and output between the user and the controller.
 */
public class View implements SaleObserver{
    private Controller controller;
    private SaleDTO saleInfo;
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
        controller.subscribeOnUpdate(this);
        this.testCase();
        this.testCase();
    }

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        this.saleInfo = saleInfo;
        outputSale();
    }

    private void testCase()
    {
        int validItem = 1;
        int noServer = -1;
        int invalidItem = 0;
        int count = 15;
        int customerID = 1;
        int invalidCount = 0;
        
        payment = 0;
        controller.startSale();
        try {
            controller.scanItem(validItem);
            controller.scanItem(validItem);
            controller.setCount(validItem, count);
        } catch (Exception e) {
            log(e);
        }
        try {
            controller.setCount(validItem, invalidCount);
        } catch (InvalidParameterException e) {
            log(e);
            out("An error has occured and has been logged: Tried to set count to an invalid amount.");
        }
        try {
            controller.scanItem(noServer);
        } catch (TimeoutException e) {
            log(e);
            out("An error has occured and has been logged: Server could not be reached.");
        } catch (InvalidParameterException e) {
            log(e);
            out("An error has occured and has been logged: No item with scanned id could be found.");
        }
        try {
            controller.scanItem(invalidItem);
        } catch (InvalidParameterException e) {
            log(e);
            out("An error has occured and has been logged: No item with scanned id could be found.");
        } catch (TimeoutException e) {
            log(e);
            out("An error has occured and has been logged: Server could not be reached.");
        }
        controller.discountRequest(customerID);
        payment = 20;
        controller.enterPayment(payment);
    }

    private void outputSale()
    {
        
        out(toString(saleInfo));
        out("");
    }

    public String toString(SaleDTO saleInfo)
    {
        int saleID = saleInfo.saleID;
        LocalDateTime dateTime = saleInfo.dateTime;
        List<ItemDTO> items = saleInfo.items;
        double cost = saleInfo.totalCostBeforeDiscount;
        double discount = saleInfo.totalDiscount;
        double change = saleInfo.change;
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
    private void out(String string)
    {
        System.out.println(string);
    }

    private void log(Exception e)
    {
        Logger.getInstance().log(e);
    }
}
