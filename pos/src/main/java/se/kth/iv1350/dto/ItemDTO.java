package se.kth.iv1350.dto;

/**
 * A data wrapper for transferring information about items.
 */
public class ItemDTO {
    public final int itemID;
    public final String name;
    public final int cost;
    public final float vat;
    public final String description;

    /**
     * Instantiates a new object of the ItemDTO type containing the information of an item.
     */
    public ItemDTO(int itemID, String name, int cost, float vat, String description)
    {
        this.itemID = itemID;
        this.name = name;
        this.cost = cost;
        this.vat = vat;
        this.description = description;
    }
}
