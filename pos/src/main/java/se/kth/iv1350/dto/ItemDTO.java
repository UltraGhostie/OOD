package se.kth.iv1350.dto;

/**
 * An immutable class for transferring data relevant to items.
 */
public class ItemDTO {
    public final int itemID;
    public final String name;
    public final double cost;
    public final double vat;
    public final String description;
    public final int count;

    /**
     * Instantiates a new object of the ItemDTO type containing the information of an item.
     */
    public ItemDTO(int itemID, String name, double cost, double vat, String description)
    {
        this.itemID = itemID;
        this.name = name;
        this.cost = cost;
        this.vat = vat;
        this.description = description;
        this.count = 1;
    }

    /**
     * Instantiates a new object of the ItemDTO type containing the information of an item.
     */
    public ItemDTO(int itemID, String name, int cost, double vat, String description)
    {
        this.itemID = itemID;
        this.name = name;
        this.cost = (double)cost;
        this.vat = vat;
        this.description = description;
        this.count = 1;
    }

    /**
     * Instantiates a new object of the ItemDTO type containing the information of an item.
     */
    public ItemDTO(int itemID, String name, double cost, double vat, String description, int count)
    {
        this.itemID = itemID;
        this.name = name;
        this.cost = (double)cost;
        this.vat = vat;
        this.description = description;
        this.count = count;
    }
}
