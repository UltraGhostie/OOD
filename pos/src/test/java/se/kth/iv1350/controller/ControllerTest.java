package se.kth.iv1350.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.integration.Integration;
import se.kth.iv1350.model.Item;
import se.kth.iv1350.view.Observer;

/**
 * Unit tests for Controller.
 */
public class ControllerTest implements Observer{
    static Integration integration = Integration.getInstance();
    static Controller controller = Controller.getInstance();
    SaleDTO saleInfo;

    public void stateChange(SaleDTO saleInfo)
    {
        this.saleInfo = saleInfo;
    }

    /**
     * Initializes new temporary variables.
     */
    @Before
    public void init()
    {
        controller.startSale();
        controller.subscribeOnUpdate(this);
        saleInfo = null;
    }

    /**
     * Clears up temporary variables.
     */
    @After
    public void cleanup()
    {
        saleInfo = null;
    }

    @AfterClass
    public static void cleanerup()
    {
        controller = null;
        integration = null;
    }

    /**
     * Checks that startSale() creates a new sale.
     */
    @Test
    public void startSaleTest()
    {
        int validItemID = 1;
        controller.startSale();
        assertNotNull(saleInfo);
        controller.scanItem(validItemID);
        controller.startSale();
        for (Item item : saleInfo.items) {
            fail();
        }
        assertTrue(true);
    }

    /**
     * Checks that scanning an item adds it to the current sale.
     */
    @Test
    public void scanValidItemTest()
    {
        int validItemID = 1;
        String failMessage = "No item with id " + validItemID + " found";
        controller.scanItem(validItemID);
        assertNotNull(saleInfo);
        for (Item item : saleInfo.items) {
            if (item.itemID == validItemID) {
                return;
            }
        }
        fail(failMessage);
    }

    /**
     * Checks that scanning an invalid item returns null.
     */
    @Test
    public void scanInvalidItemTest()
    {
        int invalidItemID = -1234;
        try {
            controller.scanItem(invalidItemID);
            fail();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * Checks that enterPayment returns the correct value and empties sale.
     */
    @Test
    public void enterPaymentTest()
    {
        double amount = 200;
        int knownItemID = 1;
        double itemCost = 1;
        int itemCount = 10;
        double itemVat = 1.06;
        double acceptableDelta = 0.1;
        double expectedChange = amount - (itemCost*itemCount*itemVat);
        controller.scanItem(knownItemID);
        controller.setCount(knownItemID, itemCount);
        assertEquals(expectedChange, controller.enterPayment(amount), acceptableDelta);
        try {
            controller.scanItem(knownItemID);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void enterPaymentBadAmountTest()
    {
        double amount = 0;
        int knownItemID = 1;
        double itemCost = 1;
        double acceptableDelta = 0.1;
        controller.scanItem(knownItemID);
        try {
            controller.enterPayment(amount);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
