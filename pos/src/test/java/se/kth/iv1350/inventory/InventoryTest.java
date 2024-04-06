package se.kth.iv1350.inventory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for Inventory.
 */
public class InventoryTest {
    Inventory inventory;

    /**
     * Initializes temporary variables.
     */
    @Before
    public void init()
    {
        inventory = new Inventory();
    }

    /**
     * Clears up temporary variables.
     */
    @After
    public void cleanup()
    {
        inventory = null;
    }

    /**
     * Checks that an item with a valid id is added.
     */
    @Test
    public void validIDLookupTest()
    {
        int validID = 1;
        try {
            inventory.lookup(validID);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * Checks that an item with an invalid id is returned as null.
     */
    @Test
    public void invalidIDLookupTest()
    {
        int invalidID = -1234;
        try {
            inventory.lookup(invalidID);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}
