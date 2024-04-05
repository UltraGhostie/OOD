package se.kth.iv1350.view;

import java.io.File;
import java.io.PrintStream;

import se.kth.iv1350.dto.SaleDTO;

public class TotalRevenueFileOutput implements Observer {
    private static TotalRevenueFileOutput INSTANCE = new TotalRevenueFileOutput();
    private File file = new File("TotalRev.txt");
    private PrintStream out;

    private TotalRevenueFileOutput()
    {
        try {
            out = new PrintStream(file);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        out.println(saleInfo.toString());
    }

    public static TotalRevenueFileOutput getInstance()
    {
        return INSTANCE;
    }
}
