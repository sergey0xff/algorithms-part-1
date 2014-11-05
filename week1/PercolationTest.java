import junit.framework.TestCase;

public class PercolationTest extends TestCase {
    public void testIsOpen() {
        Percolation percolation = new Percolation(5);
        for (int i = 1; i <= 5; i++) {
            percolation.open(i, i);
        }
        for (int i = 1; i <= 5; i++) {
            assertTrue(percolation.isOpen(i, i));
        }
    }

    public void testIsFull() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(3, 3);
        assertTrue(percolation.isFull(3, 1));

        // test backwash
        assertFalse(percolation.isFull(3, 3));
    }

    public void testPercolates() {
        Percolation percolation = new Percolation(3);
        assertFalse(percolation.percolates());
        for (int i = 1; i <= 2; i++) {
            percolation.open(i, 1);
            assertFalse(percolation.percolates());
        }
        percolation.open(3, 1);
        assertTrue(percolation.percolates());
    }
}
