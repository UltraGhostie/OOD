package se.kth.iv1350.inventory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
    Inventory inventory;

    @Before
    public void init()
    {
        inventory = new Inventory();
    }

    @After
    public void cleanup()
    {
        inventory = null;
    }

    @Test
    public void validIDLookupTest()
    {
        int validID = 1;
        String item = inventory.lookup(validID);
        assertNotNull(item);
    }

    @Test
    public void invalidIDLookupTest()
    {
        int invalidID = -1234;
        String item = inventory.lookup(invalidID);
        assertNull(item);
    }
}
