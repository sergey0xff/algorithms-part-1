import java.util.LinkedList;

public class KdTree {
    private Node root;  // root of the KdTree
    private int size;

    // KdTree helper node data type
    private static class Node {
        private Point2D p;
        private boolean vertical;  // used to compare points by x or y coordinate
        private Node left, right;  // links to left and right subtrees
        private RectHV rect;       // rectangle associated with node and its subtrees

        private Node(Point2D p, boolean vertical) {
            this.p = p;
            this.vertical = vertical;
        }

        public int compareToPoint(Point2D q, boolean vertical) {
            if (vertical) return Point2D.X_ORDER.compare(p, q);
            return Point2D.Y_ORDER.compare(p, q);
        }

        public int compareToPoint(Point2D q) {
            return compareToPoint(q, vertical);
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkPointBounds(p);
        root = insert(root, p, null);
    }

    private Node insert(Node x, Point2D p, Node parent) {
        if (x == null) return createNewNode(p, parent);

        int cmp = x.compareToPoint(p);
        if (cmp > 0) {
            x.left = insert(x.left, p, x);
        }
        else if (cmp < 0) {
            x.right = insert(x.right, p, x);
        } else if (x.compareToPoint(p, !x.vertical) != 0) {
            x.right = insert(x.right, p, x); // points lie on the same line
        }

        return x;
    }

    private Node createNewNode(Point2D p, Node parent) {
        Node node = new Node(p, true);
        if (parent != null) node.vertical = !parent.vertical;

        // creating rectangle
        if (parent == null) {
            node.rect = new RectHV(0, 0, 1, 1);
        } else {
            double xMin = parent.rect.xmin();
            double xMax = parent.rect.xmax();
            double yMin = parent.rect.ymin();
            double yMax = parent.rect.ymax();

            if (parent.vertical) {
                if (parent.compareToPoint(p) > 0) {
                    xMax = parent.p.x();
                } else {
                    xMin = parent.p.x();
                }
            } else {
                if (parent.compareToPoint(p, false) > 0) {
                    yMax = parent.p.y();
                } else {
                    yMin = parent.p.y();
                }
            }
            node.rect = new RectHV(xMin, yMin, xMax, yMax);
        }

        ++size;
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkPointBounds(p);
        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        while (x != null) {
            int cmp = x.compareToPoint(p);
            if (cmp > 0) x = x.left;
            else if (cmp < 0) x = x.right;
            // if points lie on the same line then by convention move to the right node
            else if (x.compareToPoint(p, !x.vertical) != 0) x = x.right;
            else return true;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.show(0);
        Iterable<Node> nodes = allNodes();

        StdDraw.setPenRadius(.003);
        for (Node n : nodes) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(n.p.x(), n.p.y());

            if (n.vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
            }
        }
        StdDraw.show(50);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> result = new LinkedList<>();
        if (isEmpty()) return result;

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.removeFirst();
            if (rect.contains(node.p))
                result.add(node.p);
            if (node.left != null && rect.intersects(node.left.rect))
                queue.add(node.left);
            if (node.right != null && rect.intersects(node.right.rect))
                queue.add(node.right);
        }

        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) return null;

        Point2D nearest = root.p;
        double nearestDistance = root.p.distanceSquaredTo(p);

        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.removeFirst();
            double distance = node.p.distanceSquaredTo(p);

            if (distance < nearestDistance) {
                nearest = node.p;
                nearestDistance = distance;
            }

            if (node.left != null &&
                    node.left.rect.distanceSquaredTo(p) < nearestDistance) {
                queue.add(node.left);
            }

            if (node.right != null &&
                    node.right.rect.distanceSquaredTo(p) < nearestDistance) {
                queue.add(node.right);
            }
        }

        return nearest;
    }

    private Iterable<Node> allNodes() {
        LinkedList<Node> nodes = new LinkedList<>();
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (node != null) {
                nodes.add(node);
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        return nodes;
    }

    private void checkPointBounds(Point2D p) {
        if (p.x() < 0 || p.x() > 1 || p.y() < 0 || p.y() > 1)
            throw new IllegalArgumentException("Invalid point: " + p.toString());
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.range(new RectHV(0, 0, 0.1, 0.1));
        tree.nearest(new Point2D(0.1, 0.1));
        tree.contains(new Point2D(0.1, 0.1));
        tree.size();
        tree.isEmpty();
    }
}
