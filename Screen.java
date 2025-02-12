import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Screen extends JFrame {
    private BinarySearchTree<Item> tree;
    private JTextArea itemList;
    private JLabel treeInfo;

    public Screen(BinarySearchTree<Item> tree) {
        this.tree = tree;
        setTitle("Binary Search Tree Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton searchButton = new JButton("Search Item");
        JButton deleteButton = new JButton("Delete Item");

        controlPanel.add(addButton);
        controlPanel.add(searchButton);
        controlPanel.add(deleteButton);

        add(controlPanel, BorderLayout.NORTH);

        itemList = new JTextArea();
        itemList.setEditable(false);
        add(new JScrollPane(itemList), BorderLayout.EAST);

        treeInfo = new JLabel("Tree Info: ");
        add(treeInfo, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addItem());
        searchButton.addActionListener(e -> searchItem());
        deleteButton.addActionListener(e -> deleteItem());

        // Initialize the tree with at least 4 items
        tree.add(new Item("Apple", 1.0));
        tree.add(new Item("Book", 2.0));
        tree.add(new Item("Bread", 1.5));
        tree.add(new Item("Milk", 2.5));

        updateDisplay();
        setVisible(true);
    }

    private void addItem() {
        String name = JOptionPane.showInputDialog("Enter item name:");
        if (name != null && !name.trim().isEmpty()) {
            try {
                double price = Double.parseDouble(JOptionPane.showInputDialog("Enter item price:"));
                tree.add(new Item(name, price));
                rebalanceTree();
                updateDisplay();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid price input.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchItem() {
        String name = JOptionPane.showInputDialog("Enter item name to search:");
        if (name != null && !name.trim().isEmpty()) {
            Item searchItem = new Item(name, 0); // Assuming price is not relevant for search
            int[] passes = {0};
            if (containsWithPasses(tree.getRoot(), searchItem, passes)) {
                JOptionPane.showMessageDialog(this, "Item found: " + name + " in " + passes[0] + " passes", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Item not found.", "Search Result", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean containsWithPasses(Node<Item> node, Item data, int[] passes) {
        if (node == null) {
            return false;
        }
        passes[0]++;
        if (data.compareTo(node.get()) < 0) {
            return containsWithPasses(node.getLeft(), data, passes);
        } else if (data.compareTo(node.get()) > 0) {
            return containsWithPasses(node.getRight(), data, passes);
        } else {
            return true;
        }
    }

    private void deleteItem() {
        String name = JOptionPane.showInputDialog("Enter item name to delete:");
        if (name != null && !name.trim().isEmpty()) {
            Item deleteItem = new Item(name, 0); // Assuming price is not relevant for deletion
            tree.remove(deleteItem);
            rebalanceTree();
            updateDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void rebalanceTree() {
        List<Item> sortedItems = new ArrayList<>();
        inorderTraversal(tree.getRoot(), sortedItems);
        tree.clear();
        buildBalancedTree(sortedItems, 0, sortedItems.size() - 1);
    }

    private void inorderTraversal(Node<Item> node, List<Item> items) {
        if (node != null) {
            inorderTraversal(node.getLeft(), items);
            items.add(node.get());
            inorderTraversal(node.getRight(), items);
        }
    }

    private void buildBalancedTree(List<Item> items, int start, int end) {
        if (start > end) {
            return;
        }
        int mid = (start + end) / 2;
        tree.add(items.get(mid));
        buildBalancedTree(items, start, mid - 1);
        buildBalancedTree(items, mid + 1, end);
    }

    private void updateDisplay() {
        itemList.setText(getItemsString());
        treeInfo.setText("Height: " + tree.getHeight() + " | Size: " + tree.getLevel());
        repaint();
    }

    private String getItemsString() {
        List<Item> items = new ArrayList<>();
        inorderTraversal(tree.getRoot(), items);
        StringBuilder sb = new StringBuilder();
        for (Item item : items) {
            sb.append(item.getName()).append(": ").append(item.getPrice()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (tree.getRoot() != null) {
            drawTree(g, tree.getRoot(), getWidth() / 2, 100, 100);
        }
    }

    private void drawTree(Graphics g, Node<Item> node, int x, int y, int xOffset) {
        if (node != null) {
            g.drawString(node.get().toString(), x, y);
            if (node.getLeft() != null) {
                g.drawLine(x, y, x - xOffset, y + 100);
                drawTree(g, node.getLeft(), x - xOffset, y + 100, xOffset / 2);
            }
            if (node.getRight() != null) {
                g.drawLine(x, y, x + xOffset, y + 100);
                drawTree(g, node.getRight(), x + xOffset, y + 100, xOffset / 2);
            }
        }
    }
}
