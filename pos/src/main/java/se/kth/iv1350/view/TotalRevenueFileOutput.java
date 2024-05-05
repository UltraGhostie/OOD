package se.kth.iv1350.view;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.dto.SaleDTO;

public class TotalRevenueFileOutput implements Observer {
    private static TotalRevenueFileOutput INSTANCE = new TotalRevenueFileOutput();
    private File file = new File("TotalRev.txt");
    private PrintStream out;
    private Scanner scanner;
    double totalIncome;

    private class InitializationException extends RuntimeException {
        InitializationException(String message)
        {
            super(message);
        }
    }

    private TotalRevenueFileOutput()
    {
        try {
            out = new PrintStream(file);
            scanner = new Scanner(file);
        } catch (Exception e) {
            throw new InitializationException("Unable to initialize TotalRevenueFileOutput");
        }
        totalIncome = 0;
    }

    public void subscribeTo(Controller controller)
    {
        controller.subscribeOnPayment(this);
    }

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        totalIncome += saleInfo.totalCostBeforeDiscount-saleInfo.totalDiscount;
        out.println(totalIncome);
    }

    public static TotalRevenueFileOutput getInstance()
    {
        return INSTANCE;
    }
}
