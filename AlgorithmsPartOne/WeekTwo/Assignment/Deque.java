import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
  private Node head;
  private int count;

  // construct an empty deque
  public Deque() {
    this.head = null;
    this.count = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return this.size() == 0;
  }

  // return the number of items on the deque
  public int size() {
    return this.count;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node node = new Node(item);

    if (this.head == null) {
      this.head = node;
      this.head.next = this.head;
      this.head.prev = this.head;
    } else {
      node.prev = this.head.prev;
      node.next = this.head;
      node.prev.next = node;
      this.head.prev = node;
      this.head = node;
    }

    this.count++;
  }

  // add the item to the end
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    Node node = new Node(item);

    if (this.head == null) {
      this.head = node;
      this.head.next = this.head;
      this.head.prev = this.head;
    } else {
      node.prev = this.head.prev;
      node.next = this.head;
      node.prev.next = node;
      this.head.prev = node;
    }

    this.count++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Node node = this.head;

    if (this.count == 1) {
      this.head = null;
    } else {
      this.head.prev.next = this.head.next;
      this.head.next.prev = this.head.prev;
      this.head = this.head.next;
    }

    node.next = null;
    node.prev = null;

    this.count--;

    return node.item;
  }

  // remove and return the item from the end
  public Item removeLast() {
    if (this.isEmpty()) {
      throw new NoSuchElementException();
    }

    Node node = this.head.prev;

    if (this.count == 1) {
      this.head = null;
    } else {
      this.head.prev = node.prev;
      this.head.prev.next = this.head;
    }

    node.next = null;
    node.prev = null;

    this.count--;

    return node.item;
  }

  // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new DequeIterator<Item>();
  }

  private class DequeIterator<Item> implements Iterator<Item> {
    private Node node = head;
    private int curr = 1;

    public boolean hasNext() {
      return this.curr <= count;
    }

    public Item next() {
      if (this.curr > count) {
        throw new NoSuchElementException();
      }

      this.curr++;
      node = node.next;

      return node.prev.item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  private class Node {
    private Item item;
    private Node next;
    private Node prev;

    Node(Item item) {
      this.item = item;
    }
  }
}