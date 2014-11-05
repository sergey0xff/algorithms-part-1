import junit.framework.TestCase;

import static org.junit.Assert.assertNotEquals;

public class BoardTests extends TestCase {
    public void testEquals() {
        int[][] blocks = new int[][]{
            {0, 1},
            {2, 3},
        };

        int[][] blocks2 = new int[][]{
            {5, 3},
            {4, 8},
        };

        int[][] blocksEmpty = new int[0][0];

        Board a = new Board(blocks);
        Board b = new Board(blocks);
        Board c = new Board(blocks2);
        Board empty1 = new Board(blocksEmpty);
        Board empty2 = new Board(blocksEmpty);

        assertEquals(a, b);
        assertEquals(a, a);
        assertEquals(empty1, empty2);
        assertNotEquals(a, empty1);
        assertNotEquals(a, c);
        assertNotEquals(a, null);
    }

    public void testIsGoal() {
        int[][] blocks1 = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        int[][] blocks2 = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        };

        Board a = new Board(blocks1);
        Board b = new Board(blocks2);
        assertTrue(a.isGoal());
        assertFalse(b.isGoal());
    }

    public void testTwin() {
        int[][] blocks1 = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board = new Board(blocks1);
        for (int i = 0; i < 20; i++) {
            assertNotEquals(board, board.twin());
        }

        int[][] blocks2 = new int[][] {
                {0, 1},
                {2, 3},
        };
        Board board2 = new Board(blocks2);
        for (int i = 0; i < 20; i++) {
            assertNotEquals(board2, board2.twin());
        }
    }

    public void testNeighbours() {
        int[][] blocks1 = new int[][]{
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 5}
        };
        Board board1 = new Board(blocks1);
        int count = 0;
        for (Board b : board1.neighbors()) count += 1;
        assertEquals(4, count);

        int[][] blocks2 = new int[][]{
                {0, 2, 3},
                {4, 1, 6},
                {7, 8, 5}
        };
        Board board2 = new Board(blocks2);
        count = 0;
        for (Board b : board2.neighbors()) count += 1;
        assertEquals(2, count);

        int[][] blocks3 = new int[][]{
                {3, 2, 0},
                {4, 1, 6},
                {7, 8, 5}
        };
        Board board3 = new Board(blocks3);
        count = 0;
        for (Board b : board3.neighbors()) count += 1;
        assertEquals(2, count);

        int[][] blocks4 = new int[][]{
                {3, 2, 5},
                {4, 1, 6},
                {7, 8, 0}
        };
        Board board4 = new Board(blocks4);
        count = 0;
        for (Board b : board4.neighbors()) count += 1;
        assertEquals(2, count);

        int[][] blocks5 = new int[][]{
                {3, 2, 5},
                {4, 1, 6},
                {0, 8, 7}
        };
        Board board5 = new Board(blocks5);
        count = 0;
        for (Board b : board5.neighbors()) count += 1;
        assertEquals(2, count);
    }

    public void testHamming() {
        int[][] blocks1 = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board1 = new Board(blocks1);
        assertEquals(0, board1.hamming());

        int[][] blocks2 = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        };
        Board board2 = new Board(blocks2);
        assertEquals(1, board2.hamming());

        int[][] blocks3 = new int[][] {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board3 = new Board(blocks3);
        assertEquals(5, board3.hamming());
    }

    public void testManhattan() {
        int[][] blocks1 = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board board1 = new Board(blocks1);
        assertEquals(0, board1.manhattan());

        int[][] blocks2 = new int[][] {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        };
        Board board2 = new Board(blocks2);
        assertEquals(1, board2.manhattan());

        int[][] blocks3 = new int[][] {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board3 = new Board(blocks3);
        assertEquals(10, board3.manhattan());
    }
}