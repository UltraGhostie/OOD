package se.kth.iv1350.model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.kth.iv1350.dto.ItemDTO;

/**
 * Unit tests for Item.
 */
public class ItemTest {
    static int itemID;
    static String name;
    static int cost;
    static double vat;
    static String description;
    static ItemDTO itemDTO;
    static Item item;

    /**
     * Initializes all static values
     */
    @BeforeClass
    public static void init()
    {
        itemID = 1;
        name = "name";
        cost = 1;
        vat = 0.06;
        description = "description";
        itemDTO = new ItemDTO(itemID, name, cost, vat, description);
    }

    /** 
     * Sets the item reference to a new item
    */
    @Before
    public void before()
    {
        item = new Item(itemDTO);
    }

    /**
     * Clears up the item reference
     */
    @After
    public void after()
    {
        item = null;
    }

    /*
     * Clears up the static references
     */
    @AfterClass
    public static void cleanup()
    {
        description = null;
        itemDTO = null;
        System.gc();
    }

    /**
     * Checks that the constructor works correctly
     */
    @Test
    public void dtoConstructorTest()
    {
        assertItemEquals(item);
    }

    /**
     * Checks that the getter count() works correctly
     */
    @Test
    public void countTest()
    {
        int expected = 1;
        assertEquals(expected, item.count());
    }

    /**
     * Checks that the setter setCount() works correctly
     */
    @Test
    public void setCountTest()
    {
        int expected = 5;
        item.setCount(expected);
        assertEquals(expected, item.count());
    }

    /**
     * Checks that the incrementer function works correctly
     */
    @Test
    public void incrementTest()
    {
        int expected = 2;
        item.increment();
        assertEquals(expected, item.count());
    }

    private void assertItemEquals(Item item)
    {
        double acceptableDelta = 0.1;
        assertEquals(itemID, item.itemID);
        assertEquals(name, item.name);
        assertEquals(cost, item.cost, acceptableDelta);
        assertEquals(vat, item.vat, acceptableDelta);
        assertEquals(description, item.description);
    }
}
