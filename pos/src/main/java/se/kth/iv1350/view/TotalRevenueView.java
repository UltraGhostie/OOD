package se.kth.iv1350.view;

import se.kth.iv1350.dto.SaleDTO;

public class TotalRevenueView implements Observer {
    private double totalRevenue = 0;

    private static TotalRevenueView INSTANCE = new TotalRevenueView();

    private TotalRevenueView(){}

    @Override
    public void stateChange(SaleDTO saleInfo)
    {
        totalRevenue += saleInfo.totalCostBeforeDiscount - saleInfo.totalDiscount;
        System.out.println("Total revenue: " + totalRevenue);
    }

    public static TotalRevenueView getInstance()
    {
        return INSTANCE;
    }
}
