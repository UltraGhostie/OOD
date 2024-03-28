package se.kth.iv1350.view;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.dto.SaleDTO;;

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

    /**
     * Starts a new sale.
     */
    public void startSale()
    {
        saleInfo = controller.startSale();
        outputSale();
    }

    /**
     * Attempts to enter a new item into the current sale.
     * @param itemID The unique id of the item to enter into the current sale.
     */
    public void scanItem(int itemID)
    {
        SaleDTO invalidItemID = null;

        SaleDTO returnedInfo = controller.scanItem(itemID);
        if (returnedInfo == invalidItemID) {
            System.out.println("Invalid item id");
            return;
        }
        saleInfo = returnedInfo;
        outputSale();
    }

    private void outputSale()
    {
        System.out.println("Success!");
    }
}
