package se.kth.iv1350.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.integration.Integration;

/**
 * Unit test for View.
 */
public class ViewTest {
    static Controller controller;
    static View view;
    static PrintStream standard;
    static PrintStream fileStream;
    static File temp;

    /**
     * Initializes temporary variables.
     */
    @Before
    public void init()
    {
        controller = new Controller(new Integration());
        controller.startSale();
        view = new View(controller);
        temp = new File("./test.txt");
        standard = System.out;
        try {
            temp.createNewFile();
            fileStream = new PrintStream(temp);
            System.setOut(fileStream);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Empties the temporary variables.
     */
    @After
    public void clean()
    {
        view = null;
        controller = null;
        fileStream.close();
        System.setOut(standard);
        fileStream = null;
        System.gc();
        temp.delete();
    }
}
