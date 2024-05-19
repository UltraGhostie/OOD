package se.kth.iv1350.view;

import se.kth.iv1350.controller.Controller;
import se.kth.iv1350.controller.Observable;
import se.kth.iv1350.dto.SaleDTO;

public class TotalRevenueView implements SaleObserver {
    private double totalRevenue = 0;

    private static TotalRevenueView INSTANCE = new TotalRevenueView();

    private TotalRevenueView(){}

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        totalRevenue += saleInfo.totalCostBeforeDiscount - saleInfo.totalDiscount;
        System.out.println("Total revenue: " + totalRevenue + "\n");
    }

    public void subscribeTo(Observable observable)
    {
        observable.subscribeOnFinish(this);
    }

    public static TotalRevenueView getInstance()
    {
        return INSTANCE;
    }
}
