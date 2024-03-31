package se.kth.iv1350.dto;

/**
 * A class for transferring data about discounts.
 */
public class DiscountDTO {
    public final double customerDiscount;
    public final double totalDiscount;
    public final int flatDiscount;

    public DiscountDTO(double customerDiscount, double totalDiscount, int flatDiscount)
    {
        this.customerDiscount = customerDiscount;
        this.totalDiscount = totalDiscount;
        this.flatDiscount = flatDiscount;
    }
}
