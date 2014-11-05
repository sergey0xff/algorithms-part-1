/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    // x coordinate
    private final int x;

    // y coordinate
    private final int y;

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (x == that.x) return Double.POSITIVE_INFINITY;
        if (y == that.y) return 0;

        double dy = that.y - y;
        double dx = that.x - x;
        return dy / dx;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            if (p1 == null || p2 == null) throw new NullPointerException();
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 > slope2) return 1;
            if (slope1 < slope2) return -1;
            return 0;
        }
    }

    // unit test
    public static void main(String[] args) {

    }
}