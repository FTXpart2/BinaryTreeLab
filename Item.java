public class Item implements Comparable<Item> {
    private String name;
    private double price;

    public Item(String name, double price) {
        if (name.length() > 7) {
            throw new IllegalArgumentException("Name must be at most 7 characters long");
        }
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(Item other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}