import java.lang.Comparable;
import java.awt.Graphics;

public class BinarySearchTree<E extends Comparable> {
  private Node<E> root;

  public BinarySearchTree() {
    root = null;
  }

  public Node<E> getRoot() {
    return root;
  }

  public void add(E data) {
    if (root == null) {
      root = new Node<E>(data);
      return;
    }

    add(root, data);
  }

  public void add(Node<E> node, E data) {
    if (node.get().compareTo(data) > 0) {
      if (node.getLeft() != null) {
        add(node.getLeft(), data);
      } else {
        Node<E> newNode = new Node<E>(data);
        newNode.setParent(node);
        node.setLeft(newNode);
      }
    } else if (node.get().compareTo(data) < 0) {
      if (node.getRight() != null) {
        add(node.getRight(), data);
      } else {
        Node<E> newNode = new Node<E>(data);
        newNode.setParent(node);
        node.setRight(newNode);
      }
    }
  }

  public boolean contains(E data) {
    if (root == null)
      return false;
    return contains(root, data);
  }

  public boolean contains(Node<E> node, E data) {
    if (node.get().compareTo(data) > 0) {
      if (node.getLeft() != null) {
        return contains(node.getLeft(), data);
      } else {
        return false;
      }
    } else if (node.get().compareTo(data) < 0) {
      if (node.getRight() != null) {
        return contains(node.getRight(), data);
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  public String toString() {
    return InOrderString(root);
  }

  public String InOrderString(Node<E> node) {
    if (node == null)
      return "";

    String str = "";
    str += InOrderString(node.getLeft());
    str += node.get() + " ";
    str += InOrderString(node.getRight());
    return str;
  }

  public int getHeight() {
    if (root == null) {
      return 0;
    }
    return getHeight(root);
  }

  private int getHeight(Node<E> node) {
    if (node == null) {
      return -1;
    }

    int leftHeight = getHeight(node.getLeft());
    int rightHeight = getHeight(node.getRight());

    if (leftHeight > rightHeight) {
      return leftHeight + 1;
    }

    return rightHeight + 1;
  }

  public int getLevel() {
    return getHeight(root) + 1;
  }

  public void rotateRight(Node<E> node) {
    Node<E> center = node.getLeft();

    node.setRight(null);
    node.setLeft(null);

    center.setRight(node);

    Node<E> parent = node.getParent();
    if (parent == null) {
      root = center;
      return;
    } else if (node == parent.getLeft()) {
      parent.setLeft(center);
    } else {
      parent.setRight(center);
    }
  }

  public void rotateLeft(Node<E> node) {
    Node<E> center = node.getRight();

    node.setRight(null);
    node.setLeft(null);

    center.setLeft(node);

    Node<E> parent = node.getParent();

    if (parent == null) {
      root = center;
      return;
    } else if (node == parent.getLeft()) {
      parent.setLeft(center);
    } else {
      parent.setRight(center);
    }
  }

  public boolean isLeafNode(Node<E> node) {
    return (node.getLeft() == null && node.getRight() == null);
  }

  public void balance() {
    balance(root);
  }

  private void balance(Node<E> node) {
    if (node == null) {
      return;
    }

    case1ALeft(node);
    case1ARight(node);

    case1BLeft(node);
    case1BRight(node);

    case2ALeft(node);
    case2ARight(node);

    case2BLeft(node);
    case2BRight(node);

    case2CLeft(node);
    case2CRight(node);

    balance(node.getLeft());
    balance(node.getRight());
  }

  public void case1ALeft(Node<E> node) {
    if (node.getRight() == null && node.getLeft() != null &&
        node.getLeft().getRight() == null && node.getLeft().getLeft() != null &&
        isLeafNode(node.getLeft().getLeft())) {
      rotateRight(node);
    }
  }

  public void case1ARight(Node<E> node) {
    if (node.getLeft() == null && node.getRight() != null &&
        node.getRight().getLeft() == null && node.getRight().getRight() != null &&
        isLeafNode(node.getRight().getRight())) {
      rotateLeft(node);
    }
  }

  public void case1BLeft(Node<E> node) {
    if (node.getRight() == null && node.getLeft() != null &&
        node.getLeft().getLeft() == null && node.getLeft().getRight() != null &&
        isLeafNode(node.getLeft().getRight())) {
      rotateLeft(node.getLeft());
      rotateRight(node);
    }
  }

  public void case1BRight(Node<E> node) {
    if (node.getLeft() == null && node.getRight() != null &&
        node.getRight().getRight() == null && node.getRight().getLeft() != null &&
        isLeafNode(node.getRight().getLeft())) {
      rotateRight(node.getRight());
      rotateLeft(node);
    }
  }

  public void case2ALeftAction(Node<E> node) {
    Node<E> center = node.getLeft();
    node.setLeft(center.getRight());
    center.setRight(node);

    if (root == node) {
      root = center;
    }
  }

  public void case2ARightAction(Node<E> node) {
    Node<E> center = node.getRight();
    node.setRight(center.getLeft());
    center.setLeft(node);

    if (root == node) {
      root = center;
    }
  }

  public void case2ARight(Node<E> node) {
    if (node.getLeft() != null && isLeafNode(node.getLeft()) &&
        node.getRight() != null && node.getRight().getLeft() != null &&
        isLeafNode(node.getRight().getLeft()) && node.getRight().getRight() != null &&
        ((node.getRight().getRight().getLeft() != null && node.getRight().getRight().getRight() == null) ||
            (node.getRight().getRight().getLeft() == null && node.getRight().getRight().getRight() != null))) {
      case2ARightAction(node);
    }
  }

  public void case2ALeft(Node<E> node) {
    if (node.getRight() != null && isLeafNode(node.getRight()) &&
        node.getLeft() != null && node.getLeft().getRight() != null &&
        isLeafNode(node.getLeft().getRight()) && node.getLeft().getLeft() != null &&
        ((node.getLeft().getLeft().getRight() != null && node.getLeft().getLeft().getLeft() == null) ||
            (node.getLeft().getLeft().getRight() == null && node.getLeft().getLeft().getLeft() != null))) {
      case2ALeftAction(node);
    }
  }

  public void case2BLeft(Node<E> node) {
    if (node.getRight() != null && isLeafNode(node.getRight()) &&
        node.getLeft() != null && node.getLeft().getLeft() != null &&
        isLeafNode(node.getLeft().getLeft()) && node.getLeft().getRight() != null
        && node.getLeft().getRight().getLeft() == null &&
        node.getLeft().getRight().getRight() != null && isLeafNode(node.getLeft().getRight().getRight())) {
      rotateLeft(node.getLeft());
      case2ALeftAction(node);
    }
  }

  public void case2BRight(Node<E> node) {
    if (node.getLeft() != null && isLeafNode(node.getLeft()) &&
        node.getRight() != null && node.getRight().getRight() != null &&
        isLeafNode(node.getRight().getRight()) && node.getRight().getLeft() != null
        && node.getRight().getLeft().getRight() == null &&
        node.getRight().getLeft().getLeft() != null && isLeafNode(node.getRight().getLeft().getLeft())) {
      rotateRight(node.getRight());
      case2ARightAction(node);
    }
  }

  public void case2CLeft(Node<E> node) {
    if (node.getRight() != null && isLeafNode(node.getRight()) &&
        node.getLeft() != null && node.getLeft().getLeft() != null &&
        isLeafNode(node.getLeft().getLeft()) && node.getLeft().getRight() != null
        && node.getLeft().getRight().getRight() == null &&
        node.getLeft().getRight().getLeft() != null && isLeafNode(node.getLeft().getRight().getLeft())) {
      rotateRight(node.getLeft().getRight());
      rotateLeft(node.getLeft());
      case2ALeftAction(node);
    }
  }

  public void case2CRight(Node<E> node) {
    if (node.getLeft() != null && isLeafNode(node.getLeft()) &&
        node.getRight() != null && node.getRight().getRight() != null &&
        isLeafNode(node.getRight().getRight()) && node.getRight().getLeft() != null
        && node.getRight().getLeft().getLeft() == null &&
        node.getRight().getLeft().getRight() != null && isLeafNode(node.getRight().getLeft().getRight())) {
      rotateLeft(node.getRight().getLeft());
      rotateRight(node.getRight());
      case2ARightAction(node);
    }
  }

  public void clear() {
    root = null;
  }

  public void draw(Graphics g, int x, int y) {
    draw(g, root, x, y, 50);
  }

  private void draw(Graphics g, Node<E> node, int x, int y, int xOffset) {
    if (node != null) {
      g.drawString(node.get().toString(), x, y);
      if (node.getLeft() != null) {
        g.drawLine(x, y, x - xOffset, y + 50);
        draw(g, node.getLeft(), x - xOffset, y + 50, xOffset / 2);
      }
      if (node.getRight() != null) {
        g.drawLine(x, y, x + xOffset, y + 50);
        draw(g, node.getRight(), x + xOffset, y + 50, xOffset / 2);
      }
    }
  }
}