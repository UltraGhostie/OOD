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
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.integration.Integration;

/**
 * Unit tests for Sale.
 */
public class SaleTest {
    Sale sale;
    Item item;
    ItemDTO itemDTO;

    /**
     * Creates new objects for sale and item.
     */
    @Before
    public void init()
    {
        int itemId = 1;
        String name = "test";
        double cost = 1;
        double vat = 0.06;
        String description = "test";
        sale = new Sale();
        itemDTO = new ItemDTO(itemId, name, cost, vat, description);
        item = new Item(itemDTO);
    }

    /**
     * Cleans up the internal variables.
     */
    @After
    public void cleanup()
    {
        sale = null;
        item = null;
    }

    /**
     * Checks that the contains() function returns false correctly.
     */
    @Test
    public void containsFalseTest()
    {
        int badId = -124;
        sale.add(itemDTO);
        assertFalse(sale.contains(badId));
    }

    /**
     * Checks that the add() function and contains() function are consistent
     */
    @Test
    public void addTest()
    {
        sale.add(itemDTO);
        assertTrue(sale.contains(item.itemID));
    }

    /**
     * Checks that the setCount function works with a correct value.
     */
    @Test
    public void setGoodCountTest()
    {
        int goodCount = 5;
        sale.add(itemDTO);
        sale.setCount(item.itemID, goodCount);
        for (ItemDTO item : sale.dto().items) {
            assertEquals(goodCount, item.count);
        }
    }
    
    /**
     * Checks that the setCount function does not work with a bad value.
     */
    @Test
    public void setBadCountTest()
    {
        int goodCount = -5;
        sale.add(itemDTO);
        try {
            sale.setCount(item.itemID, goodCount);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Checks that scanning an item twice works correctly.
     */
    @Test
    public void addAnother()
    {
        int oneItem = 1;
        int twoItems = 2;
        sale.add(itemDTO);
        for (ItemDTO item : sale.dto().items) {
            assertEquals(oneItem, item.count);
        }
        sale.add(itemDTO);
        for (ItemDTO item : sale.dto().items) {
            assertEquals(twoItems, item.count);
        }
    }

    /**
     * Checks that the dto() function correctly creates a dto.
     */
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

    /**
     * Checks that discounts are applied correctly.
     */
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
        ItemDTO item = itemContainer.items.get(0);
        sale.add(item);
        sale.setCount(knownItemID, itemCount);
        sale.applyDiscount(discountInfo);
        assertEquals(expectedDiscount, sale.dto().totalDiscount, 1);
    }
}
