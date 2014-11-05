import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Brute {
    private static void solve(List<Point> points) {
        int length = points.size();

        Collections.sort(points);

        HashSet<TreeSet<Point>> solutions = new HashSet<>();

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                double slope1 = points.get(i).slopeTo(points.get(j));
                for (int k = j + 1; k < length; k++) {
                    double slope2 = points.get(j).slopeTo(points.get(k));
                    if (slope1 != slope2) continue;
                    for (int l = k + 1; l < length; l++) {
                        double slope3 = points.get(k).slopeTo(points.get(l));
                        if (slope2 == slope3) {
                            TreeSet<Point> solution = new TreeSet<>();
                            solution.add(points.get(i));
                            solution.add(points.get(j));
                            solution.add(points.get(k));
                            solution.add(points.get(l));
                            solutions.add(solution);
                        }
                    }
                }
            }
        }

        displaySolutions(solutions);
    }

    private static void displaySolutions(Set<TreeSet<Point>> solutions) {
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

    private static ArrayList<Point> readPoints(String filename) throws FileNotFoundException {
        Scanner in = new Scanner(new File(filename));
        int n = in.nextInt();
        ArrayList<Point> points = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            points.add(new Point(in.nextInt(), in.nextInt()));
        }
        return points;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "inputs/collinear/equidistant.txt";
        if (args.length != 0) {
            fileName = args[0];
        }
        ArrayList<Point> points = readPoints(fileName);
        if (points.size() > 3) {
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
