package se.kth.iv1350.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.integration.Integration;

/**
 * Unit tests for Controller.
 */
public class ControllerTest {
    static Integration integration;
    static Controller controller;

    /**
     * Initializes new temporary variables.
     */
    @Before
    public void init()
    {
        integration = new Integration();
        controller = new Controller(integration);
        controller.startSale();
    }

    /**
     * Clears up temporary variables.
     */
    @After
    public void cleanup()
    {
        integration = null;
        controller = null;
    }

    /**
     * Checks that startSale() creates a new sale.
     */
    @Test
    public void startSaleTest()
    {
        int validItemID = 1;
        SaleDTO saleData = controller.startSale();
        assertNotNull(saleData);
        controller.scanItem(validItemID);
        saleData = controller.startSale();
        for (ItemDTO item : saleData.items) {
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
        SaleDTO saleInfo = controller.scanItem(validItemID);
        assertNotNull(saleInfo);
        for (ItemDTO item : saleInfo.items) {
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
        SaleDTO shouldBeNull = controller.scanItem(invalidItemID);
        assertNull(shouldBeNull);
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
            SaleDTO saleInfo = controller.scanItem(knownItemID);
            assertNull(saleInfo);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Checks that enterPayment does not accept an invalid payment amount.
     */
    @Test
    public void enterPaymentBadAmountTest()
    {
        double amount = 0;
        int knownItemID = 1;
        controller.scanItem(knownItemID);
        try {
            controller.enterPayment(amount);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
