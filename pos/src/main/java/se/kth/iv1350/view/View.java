package se.kth.iv1350.view;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.dto.SaleDTO;

/**
 * Handles data input and output between the user and the controller.
 */
public class View implements Observer{
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

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        this.saleInfo = saleInfo;
        outputSale();
    }

    private void testCase()
    {
        int validItem = 1;
        int invalidItem = -1;
        int count = 15;
        int customerID = 1;
        
        controller.startSale();
        scanItem(validItem);
        scanItem(validItem);
        controller.setCount(validItem, count);
        scanItem(invalidItem);
        controller.discountRequest(customerID);
        payment = 20;
        change = controller.enterPayment(payment);
    }

    private void scanItem(int itemID)
    {
        String invalidIDMessage = "Invalid Item ID: " + itemID;
        SaleDTO saleDTO;
        try {
            controller.scanItem(itemID);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    private void outputSale()
    {
        out(saleInfo.toString());
        if (payment > 0) {
            out("Paid: " + payment);
            out("Change: " + change);
        }
    }

    private void out(String string)
    {
        System.out.println(string);
    }
}
