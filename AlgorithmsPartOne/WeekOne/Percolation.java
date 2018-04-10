import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private WeightedQuickUnionUF uf1;
  private WeightedQuickUnionUF uf2;
  private boolean[][] opened;
  private int openedSites;
  private int size;
  private int top;
  private int bottom;

  // create n-by-n grid, with all sites blocked
  public Percolation(int n) {
    this.size = n;
    this.openedSites = 0;
    this.opened = new boolean[n][n];
    this.uf1 = new WeightedQuickUnionUF(n * n + 2);
    this.uf2 = new WeightedQuickUnionUF(n * n + 1);
    this.top = n * n;
    this.bottom = n * n + 1;
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col) {
    if (this.isOpen(row, col)) {
      return;
    }

    int pos = this.getPosition(row, col);

    this.opened[row - 1][col - 1] = true;
    this.openedSites++;

    // Top
    if (row == 1) {
      this.uf1.union(pos, top);
      this.uf2.union(pos, top);
    }

    // Bottom
    if (col == this.size) {
      this.uf1.union(pos, bottom);
    }

    // union with up
    if (row >= 2 && this.isOpen(row - 1, col)) {
      this.uf1.union(pos, this.getPosition(row - 1, col));
      this.uf2.union(pos, this.getPosition(row - 1, col));
    }

    // union with down
    if (row <= this.size - 1 && this.isOpen(row + 1, col)) {
      this.uf1.union(pos, this.getPosition(row + 1, col));
      this.uf2.union(pos, this.getPosition(row + 1, col));
    }

    // union with left
    if (col >= 2 && this.isOpen(row, col - 1)) {
      this.uf1.union(pos, this.getPosition(row, col - 1));
      this.uf2.union(pos, this.getPosition(row, col - 1));
    }

    // union with right
    if (col <= this.size - 1 && this.isOpen(row, col + 1)) {
      this.uf1.union(pos, this.getPosition(row, col + 1));
      this.uf2.union(pos, this.getPosition(row, col + 1));
    }
  }

  // is site (row, col) open?
  public boolean isOpen(int row, int col) {
    return this.opened[row - 1][col - 1];
  }

  // is site (row, col) full?
  public boolean isFull(int row, int col) {
    return this.uf2.connected(top, this.getPosition(row, col));
  }

  // number of open sites
  public int numberOfOpenSites() {
    return this.openedSites;
  }

  // does the system percolate?
  public boolean percolates() {
    return this.uf1.connected(top, bottom);
  }

  private int getPosition(int row, int col) {
    return (row - 1) * this.size + (col - 1);
  }
}