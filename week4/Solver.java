import java.util.Comparator;
import java.util.LinkedList;

public class Solver {
    private int moves;
    private Iterable<Board> solution;

    private Comparator<Node> manhattanComparator = new Comparator<Node>() {
        @Override
        public int compare(Node a, Node b) {
            return a.board.manhattan() + a.moves - (b.board.manhattan() + b.moves);
        }
    };

    // search node data type
    private static class Node {
        private final Node previousNode;
        private final Board board;
        private int moves; // number of moves made to get the Node

        private Node(Board board) {
            this.board = board;
            this.previousNode = null;
        }

        private Node(Board board, Node previousNode) {
            this.board = board;
            this.previousNode = previousNode;
            this.moves = previousNode.moves + 1;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solve(initial);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        return solution;
    }

    private void solve(Board initial) {
        MinPQ<Node> pq = new MinPQ<>(manhattanComparator);
        MinPQ<Node> twinPQ = new MinPQ<>(manhattanComparator);

        pq.insert(new Node(initial));
        twinPQ.insert(new Node(initial.twin()));

        while (true) {
            Node node = step(pq);
            Node twinNode = step(twinPQ);

            if (twinNode.board.isGoal()) {
                moves = -1;
                break;
            }

            if (node.board.isGoal()) {
                moves = node.moves;
                solution = getSolution(node);
                break;
            }
        }
    }

    private Iterable<Board> getSolution(Node last) {
        LinkedList<Board> solution = new LinkedList<>();
        while (last != null) {
            solution.addFirst(last.board);
            last = last.previousNode;
        }
        return solution;
    }

    private Node step(MinPQ<Node> pq) {
        Node node = pq.delMin();
        for (Board board : node.board.neighbors()) {
            if (node.previousNode == null || !node.previousNode.board.equals(board)) {
                pq.insert(new Node(board, node));
            }
        }
        return node;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        String filename = "inputs/8puzzle/puzzle02.txt";
        if (args.length > 0) filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}