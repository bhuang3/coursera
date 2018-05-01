import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
  private final MinPQ<SearchNode> queue;
  private final MinPQ<SearchNode> queueTwin;
  private final List<Board> boards;
  private boolean solvable;

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }

    this.queue = new MinPQ<SearchNode>(new SearchNodeComparator());
    this.queueTwin = new MinPQ<SearchNode>(new SearchNodeComparator());
    this.boards = new ArrayList<Board>();
    this.solvable = false;

    this.solve(initial);
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return this.solvable;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    if (this.isSolvable()) {
      return this.boards.size() - 1;
    } else {
      return -1;
    }
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (this.isSolvable()) {
      return this.boards;
    } else {
      return null;
    }
  }

  private void solve(Board board) {
    Board twin = board.twin();
    SearchNode min = null;
    SearchNode minTwin = null;

    this.queue.insert(new SearchNode(0, board, null));
    this.queueTwin.insert(new SearchNode(0, twin, null));

    while (true) {
      min = this.queue.delMin();
      minTwin = this.queueTwin.delMin();

      if (min.board.isGoal()) {
        this.solvable = true;
        break;
      } else {
        for (Board neighbor : min.board.neighbors()) {
          if (!this.isInQueue(min, neighbor)) {
            this.queue.insert(new SearchNode(min.moves + 1, neighbor, min));
          }
        }
      }

      if (minTwin.board.isGoal()) {
        break;
      } else {
        for (Board neighbor : minTwin.board.neighbors()) {
          if (!this.isInQueue(minTwin, neighbor)) {
            this.queueTwin.insert(new SearchNode(minTwin.moves + 1, neighbor, minTwin));
          }
        }
      }
    }

    if (this.solvable) {
      this.boards.clear();

      while (min != null) {
        this.boards.add(0, min.board);
        min = min.parent;
      }
    }
  }

  private boolean isInQueue(SearchNode node, Board board) {
    while (node != null) {
      if (board.equals(node.board)) {
        return true;
      }

      node = node.parent;
    }

    return false;
  }

  private class SearchNode {
    private final int moves;
    private final Board board;
    private final SearchNode parent;

    private SearchNode(int moves, Board board, SearchNode parent) {
      this.moves = moves;
      this.board = board;
      this.parent = parent;
    }

    public int getPriority() {
      return this.board.manhattan() + this.moves;
    }
  }

  private class SearchNodeComparator implements Comparator<SearchNode> {
    @Override
    public int compare(SearchNode node1, SearchNode node2) {
      return node1.getPriority() - node2.getPriority();
    }
  }
}