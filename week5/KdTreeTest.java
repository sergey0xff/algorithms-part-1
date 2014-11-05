import junit.framework.TestCase;

import java.util.LinkedList;

public class KdTreeTest extends TestCase {
    private KdTree tree;

    public void setUp() {
        tree = new KdTree();
    }

    public void tearDown() {
        tree = null;
    }

    public void testSize() {
        assertEquals(0, tree.size());
        for (int i = 1; i <= 10; i++) {
            tree.insert(new Point2D(i / 100.0, i / 100.0));
            assertEquals(tree.size(), i);
        }
        assertEquals(10, tree.size());
    }

    public void testSizePointsWithEqualsX() {
        tree.insert(new Point2D(0.1, 0.1));
        tree.insert(new Point2D(0.1, 0.5));
        assertEquals(2, tree.size());
    }

    public void testPointsAreUnique() {
        assertEquals(0, tree.size());
        for (int i = 0; i < 10; i++) {
            tree.insert(new Point2D(0.123, 0.123));
        }
        assertEquals(1, tree.size());
    }

    public void testContains() {
        for (int i = 0; i < 10; i++) {
            tree.insert(new Point2D(i / 100.0, i / 100.0));
        }
        for (int i = 0; i < 10; i++) {
            assertTrue(tree.contains(new Point2D(i / 100.0, i / 100.0)));
        }
        for (int i = 10; i < 20; i++) {
            assertFalse(tree.contains(new Point2D(i / 100.0, i / 100.0)));
        }
    }

    public void testRange() {
        for (int i = 0; i < 10; i++) {
            tree.insert(new Point2D(i / 10.0, i / 10.0));
        }
        LinkedList<Point2D> expectedRange = new LinkedList<>();
        for (int i = 0; i <= 5; i++) {
            expectedRange.add(new Point2D(i / 10.0, i / 10.0));
        }
        Iterable<Point2D> range = tree.range(new RectHV(0, 0, 0.5, 0.5));
        assertEquals(expectedRange, range);
        assertEquals(new LinkedList<Point2D>(), tree.range(new RectHV(0.98, 0.98, 0.98, 0.98)));
    }

    public void testNearest() {
        for (int i = 0; i < 10; i++) {
            tree.insert(new Point2D(i / 10.0, i / 10.0));
        }

        for (int i = 0; i < 10; i++) {
            Point2D p = new Point2D(i / 10.0, i / 10.0);
            Point2D nearest = tree.nearest(p);
            assertEquals(p, nearest);
        }
    }
}