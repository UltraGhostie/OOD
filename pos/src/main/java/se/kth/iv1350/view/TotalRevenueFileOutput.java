package se.kth.iv1350.view;

import java.io.File;
import java.io.PrintStream;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.controller.Observable;
import se.kth.iv1350.dto.SaleDTO;

public class TotalRevenueFileOutput implements SaleObserver {
    private static TotalRevenueFileOutput INSTANCE = new TotalRevenueFileOutput();
    private File file = new File("TotalRev.txt");
    private PrintStream out;
    double totalIncome;

    private class RevenueFileOutputInitializationException extends RuntimeException {
        RevenueFileOutputInitializationException(String message)
        {
            super(message);
        }
    }

    private TotalRevenueFileOutput() throws RevenueFileOutputInitializationException
    {
        try {
            out = new PrintStream(file);
        } catch (Exception e) {
            throw new RevenueFileOutputInitializationException("Unable to initialize TotalRevenueFileOutput");
        }
        totalIncome = 0;
    }

    public void subscribeTo(Observable observable)
    {
        observable.subscribeOnFinish(this);
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
