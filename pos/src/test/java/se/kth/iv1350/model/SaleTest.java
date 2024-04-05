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
import se.kth.iv1350.view.Observer;

/**
 * Unit tests for Sale.
 */
public class SaleTest implements Observer{
    Sale sale;
    Item item;
    SaleDTO saleInfo;

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        this.saleInfo = saleInfo;
    }

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
        ItemDTO itemDTO = new ItemDTO.ItemDTOBuilder(itemId).setCost(cost).setDescription(description).setName(name).setVat(vat).build();
        item = new Item(itemDTO);
        Controller.getInstance().subscribeOnUpdate(this);
    }

    /**
     * Cleans up the internal variables.
     */
    @After
    public void cleanup()
    {
        sale = null;
        item = null;
        saleInfo = null;
    }

    /**
     * Checks that the contains() function returns false correctly.
     */
    @Test
    public void containsFalseTest()
    {
        int badId = -124;
        sale.add(item);
        assertFalse(sale.contains(badId));
    }

    /**
     * Checks that the add() function and contains() function are consistent
     */
    @Test
    public void addTest()
    {
        sale.add(item);
        assertTrue(sale.contains(item.itemID));
    }

    /**
     * Checks that the setCount function works with a correct value.
     */
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
    
    /**
     * Checks that the setCount function does not work with a bad value.
     */
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

    /**
     * Checks that scanning an item twice works correctly.
     */
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
        Controller controller = Controller.getInstance();
        controller.startSale();
        sale.add(item);
        sale.setCount(knownItemID, itemCount);
        sale.applyDiscount(discountInfo);
        assertEquals(expectedDiscount, sale.dto().totalDiscount, 1);
    }
}
