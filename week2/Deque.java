import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node next;
        Node prev;

        private Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node newNode = new Node(item);

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            first.prev = newNode;
            newNode.next = first;
            first = newNode;
        }

        N++;
    }

    // insert the item at the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node newNode = new Node(item);

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }

        N++;
    }

    // delete and return the item at the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;

        if (N != 1) first.prev = null;
        if (N < 3) last = first;

        N--;
        return item;
    }

    // delete and return the item at the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;

        if (N != 1) last.next = null;
        if (N < 3) first = last;

        N--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Object> deq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            deq.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            deq.removeFirst();
        }
        System.out.println();
    }
}