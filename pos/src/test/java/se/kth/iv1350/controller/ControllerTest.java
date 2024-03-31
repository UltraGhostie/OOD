package se.kth.iv1350.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.integration.Integration;
import se.kth.iv1350.model.Item;

public class ControllerTest {
    static Integration integration;
    static Controller controller;

    @Before
    public void init()
    {
        integration = new Integration();
        controller = new Controller(integration);
        controller.startSale();
    }

    @After
    public void cleanup()
    {
        integration = null;
        controller = null;
    }

    @Test
    public void startSaleTest()
    {
        SaleDTO saleData = controller.startSale();
        assertNotNull(saleData);
    }

    @Test
    public void scanValidItemTest()
    {
        int validItemID = 1;
        String failMessage = "No item with id " + validItemID + " found";
        SaleDTO saleInfo = controller.scanItem(validItemID);
        assertNotNull(saleInfo);
        for (Item item : saleInfo.items) {
            if (item.itemID == validItemID) {
                return;
            }
        }
        fail(failMessage);
    }

    @Test
    public void scanInvalidItemTest()
    {
        int invalidItemID = -1234;
        SaleDTO shouldBeNull = controller.scanItem(invalidItemID);
        assertNull(shouldBeNull);
    }
}
