package se.kth.iv1350.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.integration.Integration;

/**
 * Unit test for View.
 */
public class ViewTest {
    static Controller controller;
    static PrintStream standard;
    static File outFile;
    static PrintStream out;
    static View view;

    /**
     * Sets standard output to a temp file used for testing.
     */
    @BeforeClass
    public static void initClass()
    {
        outFile = new File("asdhbfahsdbguaksdg.txt");
        standard = System.out;
    }

    /**
     * Initializes temporary variables and creates the testing file.
     */
    @Before
    public void init()
    {
        controller = new Controller(new Integration());
        controller.startSale();
        view = new View(controller);
        view.startSale();

        try {
            outFile.createNewFile();
            out = new PrintStream(outFile);
            System.setOut(out);
        } catch (Exception e) {
            System.out.println("Could not create file test.txt, aborting test");
            System.exit(0);
        }
    }

    /**
     * Empties the temporary variables and removes the temp file.
     */
    @After
    public void clean()
    {
        view = null;
        controller = null;
        System.setOut(standard);
        if (out != null) {
            out.close();
        }
        System.gc();
        outFile.delete();
    }

    /**
     * Checks that startSale() runs.
     */
    @Test
    public void startSaleTest()
    {
        view.startSale();
        assertTrue(true);
    }

    /**
     * Checks that scanning a valid item adds it correctly.
     */
    @Test
    public void scanValidItemTest()
    {
        int validID = 1;
        String expected = "Cost: 1.0";

        try {
            view.scanItem(validID);
        } catch (Exception e) {
            System.err.println(e);
            fail();
        }

        try {
            Scanner scanner = new Scanner(outFile);
            String fileContents = readTestFile(scanner);
            assertLastLineEquals(expected, fileContents);
        } catch (Exception e) {
            System.err.println(e);
            fail();
        }
    }

    /**
     * Checks that scanning an invalid item produces the expected output.
     */
    @Test
    public void scanInvalidItemTest()
    {
        int invalidID = -1234;
        String invalidIDMessage = "Invalid item id";

        view.scanItem(invalidID);

        try {
            Scanner scanner = new Scanner(outFile);
            String fileContents = readTestFile(scanner);
            assertLastLineEquals(invalidIDMessage, fileContents);
        } catch (Exception e) {
            System.err.println(e);
            fail();
        }
    }

    /**
     * Testing that scanning an item multiple times creates the expected output.
     */
    @Test
    public void scanItemMultipleTimesTest()
    {
        int validID = 1;
        int times = 3;


        for (int i = 0; i < times; i++) {
            view.scanItem(validID);
        }

        assertItemCount(times);
    }

    /**
     * Checks that setting the count of an item produces the expected output.
     */
    @Test
    public void setValidCountTest()
    {
        int validID = 1;
        int newCount = 5;

        view.scanItem(validID);
        view.setCount(validID, newCount);
        assertItemCount(newCount);
    }

    private void assertItemCount(int expectedCount)
    {
        try {
            Scanner scanner = new Scanner(outFile);
            String output = readTestFile(scanner);
            String[] split = output.split("\\r\\n\\r\\n");
            output = split[split.length-1];
            split = output.split("\\r\\n");

            for (String string : split) {
                if (string.contains("*")) {
                    Integer count = Integer.parseInt(string.split("*")[1].split(",")[0]);
                    assertEquals(expectedCount, (int)count);
                }
            }
        } catch (Exception e) {
            fail();
        }
    }

    private String readTestFile(Scanner scanner)
    {
        String fileContents = new String();
        while (scanner.hasNextLine()) {
            fileContents += "\n"+scanner.nextLine();
        }
        return fileContents;
    }

    private void assertLastLineEquals(String expected, String actual)
    {
        String[] split = actual.trim().split("\\n");
        String lastString = split[split.length-1];
        assertEquals(expected.trim(), lastString.trim());
    }
}
