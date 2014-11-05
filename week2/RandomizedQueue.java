import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int initialCapacity = 10;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[initialCapacity];
    }

    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == items.length) resize(2 * items.length);
        items[N++] = item;
    }

    // delete and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniform(N);
        Item item = items[i];
        exch(i, N - 1);
        items[N - 1] = null;
        N--;
        if (N > 0 && N == items.length / 4) resize(items.length / 2);
        return item;
    }

    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniform(N);
        return items[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        final Item[] tmp = (Item[]) new Object[N];
        System.arraycopy(items, 0, tmp, 0, N);
        StdRandom.shuffle(tmp);

        return new Iterator<Item>() {
            Item[] iteratorItems = tmp;
            int cur;

            @Override
            public boolean hasNext() {
                return cur != iteratorItems.length;
            }

            @Override
            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                return iteratorItems[cur++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, temp, 0, N);
        items = temp;
    }

    private void exch(int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

    // unit testing
    public static void main(String[] args) {

    }
}