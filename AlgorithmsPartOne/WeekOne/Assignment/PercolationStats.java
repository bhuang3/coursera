import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] thresholds;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }

    this.thresholds = new double[trials];
    this.percolate(n, trials);
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(this.thresholds);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(this.thresholds);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return this.mean() - 1.96 / Math.sqrt(this.thresholds.length);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mean() + 1.96 / Math.sqrt(this.thresholds.length);
  }

  private void percolate(int n, int trials) {
    for (int i = 0; i < trials; i++) {
      Percolation perc = new Percolation(n);

      while (!perc.percolates()) {
        int row = StdRandom.uniform(1, n + 1);
        int col = StdRandom.uniform(1, n + 1);

        perc.open(row, col);
      }

      this.thresholds[i] = perc.numberOfOpenSites() * 1.0 / (n * n);
    }
  }

  // test client (described below)
  public static void main(String[] args) {
    PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

    System.out.printf("mean                    = %f\n", ps.mean());
    System.out.printf("stddev                  = %f\n", ps.stddev());
    System.out.printf("95%% confidence interval = [%f, %f]\n", ps.confidenceLo(), ps.confidenceHi());
  }
}