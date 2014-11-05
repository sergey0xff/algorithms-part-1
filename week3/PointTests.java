import junit.framework.TestCase;

public class PointTests extends TestCase {
    private static final double DELTA = 1e-10;

    public void testSlopeTo() {
        Point p;
        Point q;

        p = new Point(267, 131);
        q = new Point(418, 381);
        Point r = new Point(135, 337);
        assertEquals(1.6556291390728477, p.slopeTo(q), DELTA);
        assertEquals(-1.5606060606060606, p.slopeTo(r), DELTA);
    }
}
