package se.kth.iv1350.integration;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.kth.iv1350.dto.ItemDTO;

public class IntegrationTest {
    Integration integration;

    @Before 
    public void init()
    {
        integration = new Integration();
    }

    @After
    public void cleanup()
    {
        integration = null;
    }

    @Test
    public void lookupValidIDTest()
    {
        int validID = 1;

        ItemDTO item = integration.lookup(validID);
        assertEquals(1, item.itemID);
    }

    @Test
    public void lookupInvalidIDTest()
    {
        int invalidID = -1234;

        ItemDTO item = integration.lookup(invalidID);
        assertNull(item);
    }
}
