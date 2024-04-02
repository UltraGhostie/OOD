package se.kth.iv1350.startup;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for Main.
 */
public class MainTest 
{
    /**
     * Tests that Main.main runs.
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        String ignoredArgument1 = "Hello ";
        String ignoredArgument2 = "World!";
        String[] ignoredArguments = { ignoredArgument1, ignoredArgument2 };
        Main.main(ignoredArguments);
        assertTrue( true );
    }
}
