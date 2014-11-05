public class Percolation {
    private int N;
    private boolean[] sites;

    private WeightedQuickUnionUF qu; // quick union with top auxiliary row
    private WeightedQuickUnionUF qu2; // quick union with top and bottom auxiliary row

    private int topAuxIdx; // quick union top auxiliary row index
    private int bottomAuxIdx; // quick union bottom auxiliary row index

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("N should be greater than 0");
        this.N = N;
        int nSquared = N * N;

        // Sites Initialization
        sites = new boolean[nSquared];

        // Quick Union initialization
        qu = new WeightedQuickUnionUF(nSquared + 1);
        qu2 = new WeightedQuickUnionUF(nSquared + 2);
        topAuxIdx = nSquared;
        bottomAuxIdx = nSquared + 1;
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        checkRowIndexesBounds(i, j);
        int siteIdx = getInternalIndex(i, j);
        sites[siteIdx] = true;

        // connect with top auxiliary
        if (i == 1) {
            qu.union(siteIdx, topAuxIdx);
            qu2.union(siteIdx, topAuxIdx);
        }

        // connect with bottom auxiliary
        if (i == N) {
            qu2.union(siteIdx, bottomAuxIdx);
        }

        // check top
        if (i > 1 && isOpen(i - 1, j)) {
            int topAdjIdx = getInternalIndex(i - 1, j);
            qu.union(siteIdx, topAdjIdx);
            qu2.union(siteIdx, topAdjIdx);
        }

        // check bottom adjacent
        if (i < N && isOpen(i + 1, j)) {
            int topAdjIdx = getInternalIndex(i + 1, j);
            qu.union(siteIdx, topAdjIdx);
            qu2.union(siteIdx, topAdjIdx);
        }

        // check left adjacent
        if (j > 1 && isOpen(i, j - 1)) {
            int topAdjIdx = getInternalIndex(i, j - 1);
            qu.union(siteIdx, topAdjIdx);
            qu2.union(siteIdx, topAdjIdx);
        }

        // check right adjacent
        if (j < N && isOpen(i, j + 1)) {
            int topAdjIdx = getInternalIndex(i, j + 1);
            qu.union(siteIdx, topAdjIdx);
            qu2.union(siteIdx, topAdjIdx);
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkRowIndexesBounds(i, j);
        return sites[getInternalIndex(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkRowIndexesBounds(i, j);
        return isOpen(i, j) &&
               qu.connected(getInternalIndex(i, j), topAuxIdx);
    }

    // does the system percolate?
    public boolean percolates() {
        return qu2.connected(topAuxIdx, bottomAuxIdx);
    }

    // Used to prevent illegal arguments in api
    private void checkRowIndexesBounds(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("row index j out of bounds");
    }

    /**
     * Converts api index i, j to internal quick union index
     * @param i [1, N]
     * @param j [1, N]
     */
    private int getInternalIndex(int i, int j) {
        return (i - 1) * N + j - 1;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}