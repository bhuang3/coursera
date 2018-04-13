import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] items;
  private int count;

  // construct an empty randomized queue
  public RandomizedQueue() {
    this.items = (Item[])new Object[2];
    this.count = 0;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return this.count == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return this.count;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (this.count == this.items.length) {
      this.resizeQueue(this.items.length * 2);
    }

    this.items[this.count] = item;
    this.count++;
  }

  // remove and return a random item
  public Item dequeue() {
    if (this.count == 0) {
      throw new NoSuchElementException();
    }

    int pos = StdRandom.uniform(0, this.count);
    Item item = this.items[pos];

    for (int i = pos + 1; i < this.count; i++) {
      this.items[i - 1] = this.items[i];
    }

    this.count--;

    if (this.count > 0 && this.count == this.items.length / 4) {
      this.resizeQueue(this.items.length / 2);
    }

    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (this.count == 0) {
      throw new NoSuchElementException();
    }

    return this.items[StdRandom.uniform(0, this.count)];
  }

  private void resizeQueue(int size) {
    Item[] newItems = (Item[])new Object[size];

    for (int i = 0; i < this.count; i++) {
      newItems[i] = this.items[i];
    }

    this.items = newItems;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {
    private int pos;
    private int[] orders;

    RandomizedQueueIterator() {
      this.pos = 0;
      this.orders = StdRandom.permutation(count);
    }

    public boolean hasNext() {
      return this.pos < this.orders.length;
    }

    public Item next() {
      if (this.pos >= this.orders.length) {
        throw new NoSuchElementException();
      }

      return items[this.orders[this.pos++]];
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
