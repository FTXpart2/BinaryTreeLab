import java.lang.Comparable;

public class Node<E> {
  private E data;
  private Node<E> left, right, parent;

  public Node(E data) {
    this.data = data;
    this.left = null;
    this.right = null;
    this.parent = null;
  }

  public E get() {
    return data;
  }

  public Node<E> getLeft() {
    return left;
  }

  public Node<E> getRight() {
    return right;
  }

  public Node<E> getParent() {
    return parent;
  }

  public void setParent(Node<E> x) {
    parent = x;
  }

  public void setLeft(Node<E> left) {
    this.left = left;
  }

  public void setRight(Node<E> right) {
    this.right = right;
  }
}