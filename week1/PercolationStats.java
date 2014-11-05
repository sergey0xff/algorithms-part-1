public class PercolationStats {
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;
    private double results[];
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0) throw new IllegalArgumentException("N should be greater than 0");
        if (T <= 0) throw new IllegalArgumentException("T should be greater than 0");

        this.T = T;
        results = new double[T];

        for (int i = 0; i < T; i++) {
            results[i] = monteCarloSimulation(N);
        }

        calculateStats();
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi;
    }

    private double monteCarloSimulation(int N) {
        double openedSites = 0;
        Percolation percolation = new Percolation(N);

        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, N + 1);
            int col = StdRandom.uniform(1, N + 1);

            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                openedSites++;
            }
        }
        return openedSites / (N * N);
    }

    private void calculateStats() {
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceLo = mean - (1.96 * stddev / Math.sqrt(T));
        confidenceHi = mean + (1.96 * stddev / Math.sqrt(T));
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() +
            ", " + stats.confidenceHi());
    }
}