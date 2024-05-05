package se.kth.iv1350.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import se.kth.iv1350.dto.DiscountDTO;
import se.kth.iv1350.dto.ItemDTO;
import se.kth.iv1350.dto.SaleDTO;
import se.kth.iv1350.model.Item;

/**
 * Unit tests for Integration.
 */
public class IntegrationTest {
    static Integration integration;

    /**
     * Initializes temporary variables.
     */
    @BeforeClass
    public static void init()
    {
        integration = Integration.getInstance();
    }

    /**
     * Clears up temporary variables.
     */
    @AfterClass
    public static void cleanup()
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
            fail();
        }
    }

    /**
     * Checks that error is thrown when no item is found.
     */
    @Test
    public void lookupInvalidIDTest()
    {
        int invalidID = -1234;
        try {
            ItemDTO item = integration.lookup(invalidID);
            fail();
        } catch (InvalidParameterException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail();
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
        int totalCost = 60;
        ItemDTO item1 = new ItemDTO(1, null, 20, 0, null);
        ItemDTO item2 = new ItemDTO(2, null, 20, 0, null);
        ItemDTO item3 = new ItemDTO(3, null, 20, 0, null);
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(item1));
        items.add(new Item(item2));
        items.add(new Item(item3));
        SaleDTO saleDTO = new SaleDTO(totalCost, items, null, customerID, 0);
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
        SaleDTO saleDTO = new SaleDTO.SaleDTOBuilder(0).build();
        integration.recordSale(saleDTO);
    }

    /**
     * Checks that the removeInventory() function executes.
     */
    @Test
    public void removeInventoryTest()
    {
        SaleDTO saleDTO = new SaleDTO.SaleDTOBuilder(0).build();
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
