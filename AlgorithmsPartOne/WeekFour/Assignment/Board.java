import java.util.ArrayList;
import java.util.List;

public class Board {
  private final int[][] blocks;
  private int hammingPriority;
  private int manhattanPriority;
  private int emptyX;
  private int emptyY;

  // construct a board from an n-by-n array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks) {
    this.blocks = blocks.clone();

    this.initBoard();
  }

  // board dimension n
  public int dimension() {
    return this.blocks.length;
  }

  // number of blocks out of place
  public int hamming() {
    return this.hammingPriority;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() {
    return this.manhattanPriority;
  }

  // is this board the goal board?
  public boolean isGoal() {
    return this.hammingPriority == 0 && this.manhattanPriority == 0;
  }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin() {
    int n = this.blocks.length;
    int[][] twinBlocks = this.cloneBlocks(this.blocks);
    int x = -1;
    int y = -1;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (twinBlocks[i][j] != 0) {
          if (x == -1) {
            x = i;
            y = j;
          } else {
            swap(twinBlocks, i, j, x, y);

            return new Board(twinBlocks);
          }
        }
      }
    }

    return null;
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;

    Board that = (Board) y;

    if (this.blocks.length != that.blocks.length || this.blocks[0].length != that.blocks[0].length) {
      return false;
    }

    for (int i = 0; i < this.blocks.length; i++) {
      for (int j = 0; j < this.blocks.length; j++) {
        if (this.blocks[i][j] != that.blocks[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    List<Board> boards = new ArrayList<Board>();
    int n = this.blocks.length;

    // top
    if (this.emptyX - 1 >= 0) {
      int[][] top = this.cloneBlocks(this.blocks);
      swap(top, this.emptyX, this.emptyY, this.emptyX - 1, this.emptyY);
      boards.add(new Board(top));
    }

    // bottom
    if (this.emptyX + 1 < n) {
      int[][] bottom = this.cloneBlocks(this.blocks);
      swap(bottom, this.emptyX, this.emptyY, this.emptyX + 1, this.emptyY);
      boards.add(new Board(bottom));
    }

    // left
    if (this.emptyY - 1 >= 0) {
      int[][] left = this.cloneBlocks(this.blocks);
      swap(left, this.emptyX, this.emptyY, this.emptyX, this.emptyY - 1);
      boards.add(new Board(left));
    }

    // right
    if (this.emptyY + 1 < n) {
      int[][] right = this.cloneBlocks(this.blocks);
      swap(right, this.emptyX, this.emptyY, this.emptyX, this.emptyY + 1);
      boards.add(new Board(right));
    }

    return boards;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int n = this.blocks.length;

    sb.append(n).append("\n");

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        sb.append(String.format("%2d ", this.blocks[i][j]));
      }

      sb.append("\n");
    }

    return sb.toString();
  }

  private void initBoard() {
    int hamming = 0;
    int manhattan = 0;
    int n = this.blocks.length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int expect = i * n + j + 1;
        int actual = blocks[i][j];

        if (blocks[i][j] == 0) {
          emptyX = i;
          emptyY = j;
        } else if (expect != actual) {
          actual--;
          hamming++;
          manhattan += Math.abs(actual / n - i) + Math.abs(actual % n - j);
        }
      }
    }

    this.hammingPriority = hamming;
    this.manhattanPriority = manhattan;
  }

  private int[][] cloneBlocks(int[][] b) {
    int n = b.length;
    int[][] newBlocks = new int[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        newBlocks[i][j] = b[i][j];
      }
    }

    return newBlocks;
  }

  private void swap(int[][] b, int i1, int j1, int i2, int j2) {
    int temp = b[i1][j1];

    b[i1][j1] = b[i2][j2];
    b[i2][j2] = temp;
  }
}