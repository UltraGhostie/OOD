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

    /**
     * Instantiates a new object of the ItemDTO type containing the information of an item.
     */
    ItemDTO(int itemID, String name, double cost, double vat, String description)
    {
        this.itemID = itemID;
        this.name = name;
        this.cost = cost;
        this.vat = vat;
        this.description = description;
    }

    /**
     * Builder for ItemDTOs.
     */
    public static class ItemDTOBuilder {
        //Required
        int id;

        //Optional
        String name;
        double cost = 0;
        double vat = 0;
        String description;

        public ItemDTOBuilder(int id)
        {
            this.id = id;
        }

        public ItemDTOBuilder setName(String name)
        {
            this.name = name;
            return this;
        }

        public ItemDTOBuilder setCost(double cost)
        {
            this.cost = cost;
            return this;
        }

        public ItemDTOBuilder setVat(double vat)
        {
            this.vat = vat;
            return this;
        }

        public ItemDTOBuilder setDescription(String description)
        {
            this.description = description;
            return this;
        }

        public ItemDTO build()
        {
            return new ItemDTO(id, name, id, vat, description);
        }
    }
}
