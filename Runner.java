public class Runner{

    public static void main(String[] args) {
        BinarySearchTree<Item> tree = new BinarySearchTree<>();
        tree.add(new Item("Apple", 80));
        tree.add(new Item("Book",90));
        tree.add(new Item("Monitor", 100));
        tree.add(new Item("Shoes",300));
        
        new Screen(tree);
    }
    }