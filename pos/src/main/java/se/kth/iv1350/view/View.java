package se.kth.iv1350.view;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.dto.SaleDTO;

/**
 * Handles data input and output between the user and the controller.
 */
public class View implements Observer{
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
        } catch (Exception e) {
            log(e);
        }
        try {
            controller.scanItem(noServer);
        } catch (Exception e) {
            log(e);
        }
        try {
            controller.scanItem(invalidItem);
        } catch (Exception e) {
            log(e);
        }
        controller.discountRequest(customerID);
        payment = 20;
        controller.enterPayment(payment);
    }

    private void outputSale()
    {
        
        out(saleInfo.toString());
        out("");
    }

    private void out(String string)
    {
        System.out.println(string);
    }

    private void log(Exception e)
    {
        out("An error has occured and has been logged: " + e.getMessage());
        Logger.getInstance().log(e);
    }
}
