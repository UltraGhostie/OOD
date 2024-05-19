package se.kth.iv1350.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Logger {
    private static String defaultPath = "logdump.txt";
    private static Logger INSTANCE = new Logger();
    private PrintStream stream;

    private class LoggerInitializationException extends RuntimeException {
        LoggerInitializationException(String message)
        {
            super(message);
        }
    }

    private Logger() throws LoggerInitializationException {
        try {
            setPath(defaultPath);
        } catch (Exception e) {
            throw new LoggerInitializationException("Unable to initialize logger: " + e.toString());
        }
    }

    /**
     * @param filePath The filepath for the output of the logger
     * @throws FileNotFoundException If file is inaccessible.
     * @throws SecurityException If a security manager is present and checkWrite(file.getPath()) denies write access to the file.
     */
    public void setPath(String filePath) throws FileNotFoundException, SecurityException
    {
        try {
            File file = new File(filePath);
            stream = new PrintStream(file);
        } catch (Exception e) {
            throw e;
        }
    }

    public void log(Exception e)
    {
        e.printStackTrace(stream);
    }

    public static Logger getInstance()
    {
        return INSTANCE;
    }
}
