package se.kth.iv1350.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.dto.DiscountDTO;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.integration.Integration;

public class SaleTest {
    Sale sale;
    Item item;

    @Before
    public void init()
    {
        sale = new Sale();
        item = new Item(1, "test", 1, 0.06, "test");
    }

    @After
    public void cleanup()
    {
        sale = null;
        item = null;
    }

    @Test
    public void containsFalseTest()
    {
        int badId = -124;
        sale.add(item);
        assertFalse(sale.contains(badId));
    }

    @Test
    public void addTest()
    {
        sale.add(item);
        assertTrue(sale.contains(item.itemID));
    }

    @Test
    public void addBadID()
    {
        int badId = -23;
        try {
            sale.add(badId);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void setGoodCountTest()
    {
        int goodCount = 5;
        sale.add(item);
        sale.setCount(item.itemID, goodCount);
        for (Item item : sale.dto().items) {
            assertEquals(goodCount, item.count());
        }
    }
    
    @Test
    public void setBadCountTest()
    {
        int goodCount = -5;
        sale.add(item);
        try {
            sale.setCount(item.itemID, goodCount);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void addAnother()
    {
        int oneItem = 1;
        int twoItems = 2;
        sale.add(item);
        for (Item item : sale.dto().items) {
            assertEquals(oneItem, item.count());
        }
        sale.add(item);
        for (Item item : sale.dto().items) {
            assertEquals(twoItems, item.count());
        }
    }

    @Test
    public void dtoTest()
    {
        SaleDTO saleInfo = sale.dto();
        assertNotNull(saleInfo.saleID);
        assertNotNull(saleInfo.items);
        assertNotNull(saleInfo.totalDiscount);
        assertNotNull(saleInfo.totalCostBeforeDiscount);
        assertNotNull(saleInfo.dateTime);
        assertNull(saleInfo.customerID);
    }

    @Test
    public void applyDiscountTest()
    {
        int knownItemID = 1;
        double customerDiscount = 0.2;
        double totalDiscount = 0.1;
        int flatDiscount = 15;
        int itemCount = 60;
        double expectedDiscount = flatDiscount + (totalDiscount*itemCount) + (customerDiscount*itemCount);
        DiscountDTO discountInfo = new DiscountDTO(customerDiscount, totalDiscount, flatDiscount);
        Controller controller = new Controller(new Integration());
        controller.startSale();
        SaleDTO itemContainer = controller.scanItem(knownItemID);
        Item item = itemContainer.items.get(0);
        sale.add(item);
        sale.setCount(knownItemID, itemCount);
        sale.applyDiscount(discountInfo);
        assertEquals(expectedDiscount, sale.dto().totalDiscount, 1);
    }
}
