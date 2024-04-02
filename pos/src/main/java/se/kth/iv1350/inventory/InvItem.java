package se.kth.iv1350.inventory;

class InvItem {
    int id;
    String name;
    int cost;
    double vat;
    String description;
    
    InvItem(int id, String name, int cost, double vat, String description)
    {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.vat = vat;
        this.description = description;
    }
}
