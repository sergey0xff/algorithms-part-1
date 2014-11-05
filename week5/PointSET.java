import java.util.LinkedList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> result = new LinkedList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;

        Point2D nearest = set.first();
        double nearestDistance = p.distanceTo(nearest);

        for (Point2D q : set) {
            double distance = p.distanceTo(q);
            if (distance < nearestDistance) {
                nearest = q;
                nearestDistance = distance;
            }
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}