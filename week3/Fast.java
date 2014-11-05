import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Fast {
    private static void solve(Point[] points) {
        HashSet<TreeSet<Point>> result = new HashSet<>();
        for (Point p : points) {
            List<TreeSet<Point>> groups = groupBySlope(p, points);
            for (TreeSet<Point> group : groups) {
                result.add(group);
            }
        }
        displayPoints(result);
    }

    private static List<TreeSet<Point>> groupBySlope(Point p, Point[] points) {
        // group all points by slope with point p
        HashMap<Double, TreeSet<Point>> groups = new HashMap<>();
        for (Point q : points) {
            double slope = p.slopeTo(q);
            if (!groups.containsKey(slope)) {
                groups.put(slope, new TreeSet<Point>());
            }
            groups.get(slope).add(q);
        }

        // select all groups with size > 3 and
        List<TreeSet<Point>> result = new LinkedList<>();
        for (Double key : groups.keySet()) {
            TreeSet<Point> group = groups.get(key);
            if (group.size() > 2) {
                group.add(p);
                result.add(group);
            }
        }
        return result;
    }

    private static void displayPoints(Set<TreeSet<Point>> solutions) {
        for (TreeSet<Point> solution : solutions) {
            solution.first().drawTo(solution.last());
            int i = 0;
            int size = solution.size();
            for (Point point : solution) {
                System.out.print(point);
                if (i < size - 1) {
                    System.out.print(" -> ");
                }
                i++;
            }
            System.out.println();
        }
        StdDraw.show(0);
    }

    private static Point[] readPoints(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        int n = in.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(in.nextInt(), in.nextInt());
        }
        return points;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "inputs/collinear/input8.txt";
        if (args.length != 0) {
            fileName = args[0];
        }
        Point[] points = readPoints(fileName);
        if (points.length > 3) {
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            StdDraw.show(0);
            for (Point point : points) {
                point.draw();
            }
            solve(points);
        }
    }
}
