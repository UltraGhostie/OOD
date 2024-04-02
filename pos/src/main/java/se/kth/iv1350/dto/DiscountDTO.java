package se.kth.iv1350.dto;

/**
 * An immutable class for transferring data relevant to discounts.
 */
public class DiscountDTO {
    public final double customerDiscount;
    public final double totalDiscount;
    public final int flatDiscount;

    /**
     * Initializes a new object of type DiscountDTO.
     * @param customerDiscount The discount specific to a customer.
     * @param totalDiscount The total discount in percentage depending on the total cost of the sale.
     * @param flatDiscount The flat discount depending on the specific items in the sale.
     */
    public DiscountDTO(double customerDiscount, double totalDiscount, int flatDiscount)
    {
        this.customerDiscount = customerDiscount;
        this.totalDiscount = totalDiscount;
        this.flatDiscount = flatDiscount;
    }
}
