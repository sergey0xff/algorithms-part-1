import java.util.LinkedList;

public class Board {
    private int N;
    private int[][] blocks;
    private int emptyI = -1;
    private int emptyJ = -1;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        N = blocks.length;
        this.blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.blocks[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    emptyI = i;
                    emptyJ = j;
                }
            }
        }
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = blocks[i][j];
                if (val == 0) continue;

                if (val != rowColumnToExpectedValue(i, j)) result += 1;
            }
        }
        return result;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = blocks[i][j];
                if (val == 0) continue;

                if (val != rowColumnToExpectedValue(i, j)) {
                    int[] rowColumn = valueToRowColumn(val);
                    result += Math.abs(rowColumn[0] - i);
                    result += Math.abs(rowColumn[1] - j);
                }
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = blocks[i][j];
                if (val == 0) continue;
                if (val != rowColumnToExpectedValue(i, j))return false;
            }
        }
        return true;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        Board board = new Board(blocks);

        int i, j, i2, j2;

        if (emptyI == 0) {
            i = 1;
            i2 = 1;
        } else {
            i = emptyI - 1;
            i2 = i;
        }

        j = emptyJ;

        if (j == 0) {
            j2 = 1;
        } else {
            j2 = j - 1;
        }

        int tmp = board.blocks[i][j];
        board.blocks[i][j] = board.blocks[i2][j2];
        board.blocks[i2][j2] = tmp;

        return board;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (getClass() != y.getClass()) return false;

        Board other = (Board) y;
        if (N != other.N) return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != other.blocks[i][j]) return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> boards = new LinkedList<>();

        int[][] newBlocks;

        // top adjacent block
        if (emptyI - 1 >= 0) {
            newBlocks = blocksCopy(blocks);
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI - 1][emptyJ];
            newBlocks[emptyI - 1][emptyJ] = 0;
            boards.add(new Board(newBlocks));
        }

        // bottom adjacent block
        if (emptyI + 1 < N) {
            newBlocks = blocksCopy(blocks);
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI + 1][emptyJ];
            newBlocks[emptyI + 1][emptyJ] = 0;
            boards.add(new Board(newBlocks));
        }

        // left adjacent block
        if (emptyJ - 1 >= 0) {
            newBlocks = blocksCopy(blocks);
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI][emptyJ - 1];
            newBlocks[emptyI][emptyJ - 1] = 0;
            boards.add(new Board(newBlocks));
        }

        // right adjacent block
        if (emptyJ + 1 < N) {
            newBlocks = blocksCopy(blocks);
            newBlocks[emptyI][emptyJ] = newBlocks[emptyI][emptyJ + 1];
            newBlocks[emptyI][emptyJ + 1] = 0;
            boards.add(new Board(newBlocks));
        }

        return boards;
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N).append("\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // expected value for  row, column (i, j) indexes
    private int rowColumnToExpectedValue(int i, int j) {
        return i * N + j + 1;
    }

    // row, column (i, j) indexes for value
    private int[] valueToRowColumn(int val) {
        val -= 1;
        assert val > -1;
        int[] rowColumn = new int[2];
        rowColumn[0] = val / N;
        rowColumn[1] = val - (rowColumn[0] * N);
        return rowColumn;
    }

    private int[][] blocksCopy(int[][] blocks) {
        int[][] newBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            System.arraycopy(blocks[i], 0, newBlocks[i], 0, N);
        }
        return newBlocks;
    }

    public static void main(String[] args) {
        int[][] blocks2 = new int[][] {
                {3, 0, 4},
                {2, 5, 8},
                {7, 1, 6}
        };
        Board board2 = new Board(blocks2);
        System.out.println(board2.twin());
    }
}
