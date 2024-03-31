package se.kth.iv1350.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import se.kth.iv1350.dto.ItemDTO;

public class ItemTest {
    static int itemID;
    static String name;
    static int cost;
    static double vat;
    static String description;

    @BeforeClass
    public static void init()
    {
        itemID = 1;
        name = "name";
        cost = 1;
        vat = 0.06;
        description = "description";
    }

    @Test
    public void regularConstructorTest()
    {
        Item item = new Item(itemID, name, cost, vat, description);
        assertItemEquals(item);
    }

    @Test
    public void dtoConstructorTest()
    {
        ItemDTO itemDTO = new ItemDTO(itemID, name, cost, vat, description);
        Item item = new Item(itemDTO);
        assertItemEquals(item);
    }

    @Test
    public void countTest()
    {
        int expected = 1;
        Item item = new Item(itemID, name, cost, vat, description);
        assertEquals(expected, item.count());
    }

    @Test
    public void setCountTest()
    {
        int expected = 5;
        Item item = new Item(expected, name, expected, expected, description);
        item.setCount(expected);
        assertEquals(expected, item.count());
    }

    @Test
    public void incrementTest()
    {
        int expected = 2;
        Item item = new Item(itemID, name, cost, vat, description);
        item.increment();
        assertEquals(expected, item.count());
    }

    private void assertItemEquals(Item item)
    {
        assertEquals(itemID, item.itemID);
        assertEquals(name, item.name);
        assertEquals(cost, item.cost);
        assertEquals(vat, item.vat, 1);
        assertEquals(description, item.description);
    }
}
