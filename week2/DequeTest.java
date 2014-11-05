import junit.framework.TestCase;

public class DequeTest extends TestCase {
    public void testAddFirst() {
        Deque<Integer> deq = new Deque<>();
        for (int i = 0; i < 10; i++) {
            deq.addFirst(i);
        }
        assertEquals(10, deq.size());
    }

    public void testAddLast() {
        Deque<Integer> deq = new Deque<>();
        for (int i = 0; i < 10; i++) {
            deq.addLast(i);
        }
        assertEquals(10, deq.size());
    }

    public void testIntermixedAddFirstAddLast() {
        Deque<Integer> deq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            deq.addFirst(5);
        }
        for (int i = 0; i < 5; i++) {
            deq.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            deq.addFirst(i);
        }
        for (int i = 0; i < 5; i++) {
            deq.addLast(i);
        }
        assertEquals(20, deq.size());

        deq = new Deque<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                deq.addFirst(i);
            } else {
                deq.addLast(i);
            }
        }
        assertEquals(20, deq.size());
    }

    public void testIntermixedAddFirstRemoveLast() {
        Deque<Integer> deq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            deq.addFirst(i);
        }
        for (int i = 0; i < 5; i++) {
            deq.removeLast();
        }
        assertEquals(0, deq.size());

        deq = new Deque<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                deq.addFirst(i);
            } else {
                deq.removeLast();
            }
        }
        assertEquals(0, deq.size());
    }

    public void testIntermixedAddFirstRemoveFirst() {
        Deque<Integer> deq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            deq.addFirst(i);
        }
        for (int i = 0; i < 5; i++) {
            deq.removeFirst();
        }
        assertEquals(0, deq.size());

        deq = new Deque<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                deq.addFirst(i);
            } else {
                deq.removeFirst();
            }
        }
        assertEquals(0, deq.size());
    }

    public void testIntermixedAddLastRemoveLast() {
        Deque<Integer> deq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            deq.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            deq.removeLast();
        }
        assertEquals(0, deq.size());

        deq = new Deque<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                deq.addLast(i);
            } else {
                deq.removeLast();
            }
        }
        assertEquals(0, deq.size());
    }

    public void testIntermixedAddLastRemoveFirst() {
        Deque<Integer> deq = new Deque<>();
        for (int i = 0; i < 5; i++) {
            deq.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            deq.removeFirst();
        }
        assertEquals(0, deq.size());

        deq = new Deque<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                deq.addLast(i);
            } else {
                deq.removeFirst();
            }
        }
        assertEquals(0, deq.size());
    }
}
