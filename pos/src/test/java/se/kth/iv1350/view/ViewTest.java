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

public class ViewTest {
    static Controller controller;
    static PrintStream standard;
    static File outFile;
    static PrintStream out;

    @BeforeClass
    public static void initClass()
    {
        outFile = new File("test.txt");
        standard = System.out;
    }

    @Before
    public void init()
    {
        controller = new Controller(new Integration());
        controller.startSale();
        try {
            outFile.createNewFile();
            out = new PrintStream(outFile);
            System.setOut(out);
        } catch (Exception e) {
            System.out.println("Could not create file test.txt, aborting test");
            System.exit(0);
        }
    }

    @After
    public void clean()
    {
        controller = null;
        System.setOut(standard);
        out.close();
        outFile.delete();
    }

    @Test
    public void validConstructionTest()
    {
        try {
            View view = new View(controller);
        } catch (Exception e) {
            assertTrue(false);
        }
        assertTrue(true);
    }

    @Test
    public void startSaleTest()
    {
        View view = new View(controller);
        try {
            view.startSale();
        } catch (Exception e) {
            fail();
        }
        assertTrue(true);
    }

    @Test
    public void scanValidItemTest()
    {
        int validID = 1;
        String validIDMessage = "Success!";

        View view = new View(controller);
        try {
            view.scanItem(validID);
        } catch (Exception e) {
            fail();
        }

        try {
            Scanner scanner = new Scanner(outFile);
            String fileContents = readTestFile(scanner);
            assertLastLineEquals(validIDMessage, fileContents);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void scanInvalidItemTest()
    {
        int invalidID = -1234;
        String invalidIDMessage = "Invalid item id";

        View view = new View(controller);
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

    String readTestFile(Scanner scanner)
    {
        String fileContents = new String();
        while (scanner.hasNextLine()) {
            fileContents += "\n"+scanner.nextLine();
        }
        return fileContents;
    }

    void assertLastLineEquals(String expected, String actual)
    {
        String[] split = actual.trim().split("\\n");
        String lastString = split[split.length-1];
        assertEquals(expected.trim(), actual.trim());
    }
}
