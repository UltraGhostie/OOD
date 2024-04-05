package se.kth.iv1350.view;

import org.junit.After;
import org.junit.Before;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.integration.Integration;

/**
 * Unit test for View.
 */
public class ViewTest {
    static Controller controller;
    static View view;

    /**
     * Initializes temporary variables.
     */
    @Before
    public void init()
    {
        controller = Controller.getInstance();
        controller.startSale();
        view = new View(controller);
    }

    /**
     * Empties the temporary variables.
     */
    @After
    public void clean()
    {
        view = null;
        controller = null;
    }
}
