package se.kth.iv1350.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.dto.DiscountDTO;
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.ItemDTO.ItemDTOBuilder;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.dto.SaleDTO.SaleDTOBuilder;
import se.kth.iv1350.model.Item;

/**
 * Unit tests for Integration.
 */
public class IntegrationTest {
    Integration integration;

    /**
     * Initializes temporary variables.
     */
    @Before 
    public void init()
    {
        integration = Integration.getInstance();
    }

    /**
     * Clears up temporary variables.
     */
    @After
    public void cleanup()
    {
        integration = null;
    }

    /**
     * Checks that a valid item is returned when found.
     */
    @Test
    public void lookupValidIDTest()
    {
        int validID = 1;
        String expectedName = "foo";
        double expectedCost = 1;
        double expectedVat = 0.06;
        String expectedDescription = "bar";
        double acceptableDelta = 0.1;

        try {
            ItemDTO item = integration.lookup(validID);
            assertEquals(validID, item.itemID);
            assertEquals(expectedName, item.name);
            assertEquals(expectedCost, item.cost, acceptableDelta);
            assertEquals(expectedVat, item.vat, acceptableDelta);
            assertEquals(expectedDescription, item.description);
        } catch (Exception e) {
            fail(e.getMessage() + "\n" + e.getStackTrace());
        }
    }

    /**
     * Checks that null is returned when no item is found.
     */
    @Test
    public void lookupInvalidIDTest()
    {
        int invalidID = -1234;

        try {
            ItemDTO item = integration.lookup(invalidID);
            fail("Found invalid item.");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Checks that timeout works.
     */
    @Test
    public void timeoutTest() {
        int timeoutID = -1;

        try {
            ItemDTO item = integration.lookup(timeoutID);
            fail("Request did not timeout.");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * Checks that a discount request is correctly returned.
     */
    @Test
    public void discountRequestTest()
    {
        double expectedCustomerDiscount = 0.2;
        double expectedTotalDiscount = 0.1;
        int expectedFlatDiscount = 15;
        int customerID = 1;
        int saleID = 0;
        ItemDTO item1 = new ItemDTOBuilder(1).setCost(20).build();
        ItemDTO item2 = new ItemDTOBuilder(2).setCost(20).build();
        ItemDTO item3 = new ItemDTOBuilder(3).setCost(20).build();
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(item1));
        items.add(new Item(item2));
        items.add(new Item(item3));
        SaleDTO saleDTO = new SaleDTOBuilder(saleID).setCustomerID(customerID).setItems(items).build();
        DiscountDTO discountInfo = integration.discountRequest(saleDTO);
        assertEquals(expectedCustomerDiscount, discountInfo.customerDiscount, 0.1);
        assertEquals(expectedTotalDiscount, discountInfo.totalDiscount, 0.1);
        assertEquals(expectedFlatDiscount, discountInfo.flatDiscount);
    }

    /**
     * Checks that the recordSale() function executes.
     */
    @Test
    public void recordSaleTest()
    {
        int saleID = 0;
        SaleDTO saleDTO = new SaleDTOBuilder(saleID).build();
        integration.recordSale(saleDTO);
    }

    /**
     * Checks that the removeInventory() function executes.
     */
    @Test
    public void removeInventoryTest()
    {
        int saleID = 0;
        SaleDTO saleDTO = new SaleDTOBuilder(saleID).build();
        integration.removeInventory(saleDTO);
    }

    /**
     * Checks that the updateRegister() function executes.
     */
    @Test
    public void updateRegisterTest()
    {
        int amount = 15;
        int negativeAmount = -amount;
        integration.updateRegister(amount);
        integration.updateRegister(negativeAmount);
    }
} 