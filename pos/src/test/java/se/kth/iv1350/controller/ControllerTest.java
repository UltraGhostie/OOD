package se.kth.iv1350.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.integration.Integration;
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
        String failMessage = "Sale not reset correctly.";
        controller.startSale();
        assertNotNull(saleInfo);
        try {
            controller.scanItem(validItemID);
            controller.startSale();
            for (ItemDTO item : saleInfo.items) {
                fail(failMessage);
            }
        assertTrue(true);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    /**
     * Checks that scanning an item adds it to the current sale.
     */
    @Test
    public void scanValidItemTest()
    {
        int validItemID = 1;
        String failMessage = "No item with id " + validItemID + " found";
        try {
            controller.scanItem(validItemID);
            assertNotNull(saleInfo);
            for (ItemDTO item : saleInfo.items) {
                if (item.itemID == validItemID) {
                    assertTrue(true);
                    return;
                }
            }
            fail(failMessage);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    /**
     * Checks that scanning an invalid item throws an error.
     */
    @Test
    public void scanInvalidItemTest()
    {
        int invalidItemID = -1234;
        try {
            controller.scanItem(invalidItemID);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Checks that setCount works as intended.
     */
    @Test
    public void setCountTest()
    {
        int validItemID = 1;
        int validCount = 5;
        String wrongCount = "Item count not equal to validCount (" + validCount +")";
        controller.subscribeOnUpdate(this);
        try {
            controller.scanItem(1);
            controller.setCount(validItemID, validCount);
            for (ItemDTO item : saleInfo.items) {
                if (item.count != 5) {
                    fail(wrongCount);
                }
            }
            assertTrue(true);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    /**
     * Checks that setCount throws exception correctly when an invalid count is entered.
     */
    @Test
    public void setInvalidCountTest()
    {
        int invalidItemCount = 0;
        int validItemID = 1;
        String success = "Managed to set item count to illegal value";
        controller.subscribeOnUpdate(this);
        try {
            try {
                controller.scanItem(validItemID);
            } catch (Exception e) {
                fail(e.toString());
            }
            controller.setCount(validItemID, invalidItemCount);
            fail(success);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Checks that setCount throws exception correctly when an invalid item is entered.
     */
    @Test
    public void setCountInvalidItemTest()
    {
        int validCount = 5;
        int invalidItemID = -1;
        String success = "Managed to set an invalid items count";
        controller.subscribeOnUpdate(this);
        try {
            try {
                controller.scanItem(1);
            } catch (Exception e) {
                fail(e.toString());
            }
            controller.setCount(invalidItemID, validCount);
            fail(success);
        } catch (Exception e) {
            assertTrue(true);
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
        String saleNotNull = "Sale not emptied.";
        try {
            controller.scanItem(knownItemID);
            controller.setCount(knownItemID, itemCount);
            controller.enterPayment(amount);
            double change = saleInfo.change;
            assertEquals(expectedChange, change, acceptableDelta);
            try {
                controller.scanItem(knownItemID);
                fail(saleNotNull);
            } catch (Exception e) {
                assertTrue(true);
            }
        } catch (Exception e) {
            fail(e.toString());
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
        String failMessage = "Bad payment accepted.";
        try {
            try {
                controller.scanItem(knownItemID);
            } catch (Exception e) {
                fail(e.toString());
            }
            controller.enterPayment(amount);
            fail(failMessage);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
