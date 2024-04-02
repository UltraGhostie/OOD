package se.kth.iv1350.discount;

/**
 * Substitute for the discount DB system.
 */
public class Discount {
    
    /**
     * Retrieves the personal percentage discount to be removed from the total price.
     * @param customerID The id of the customer.
     * @return A double that represents the percentage of the total price that should be removed.
     */
    public double customerDiscount(int customerID)
    {
        return checkCustomerDiscount(customerID);
    }

    /**
     * Retrieves the total cost percentage discount to be removed from the total price.
     * @param totalCost The total cost of the sale.
     * @return A double that represents the percentage of the total price that should be removed.
     */
    public double totalCostDiscount(int totalCost)
    {
        return checkTotalDiscounts(totalCost);
    }

    /**
     * Retrieves the total item discount to be removed from the total price.
     * @param itemIDs the ids of the items to be checked..
     * @return An int representing the flat amount to be removed from the total price.
     */
    public int flatDiscount(int[] itemIDs)
    {
        return checkItemDiscounts(itemIDs);
    }

    private double checkCustomerDiscount(int customerID)
    {
        if (customerID == 1) {
            return 0.20;
        }
        return 0;
    }

    private double checkTotalDiscounts(int totalCost)
    {
        if (totalCost > 50) {
            return 0.1;
        }
        return 0;
    }

    private int checkItemDiscounts(int[] itemIDs)
    {
        int total = 0;
        int discount = 5;
        int[] validIDs = { 1, 2, 3 };
        for (int i : itemIDs) {
            for (int j : validIDs) {
                if (i==j) {
                    total += discount;
                }
            }
        }
        return total;
    }
}
