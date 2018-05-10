import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {
  private Node root;
  private int count;

  // construct an empty set of points
  public KdTree() {
    this.root = null;
    this.count = 0;
  }

  // is the set empty?
  public boolean isEmpty() {
    return this.root == null;
  }

  // number of points in the set
  public int size() {
    return this.count;
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    if (this.contains(p)) {
      return;
    }

    this.root = this.insert(this.root, p);
    this.root.horizontal = false;
    this.count++;
  }

  private Node insert(Node node, Point2D p) {
    if (node == null) {
      return new Node(p);
    } else {
      if (node.horizontal) {
        if (Double.compare(node.point.y(), p.y()) > 0) {
          node.child2 = this.insert(node.child2, p);
          node.child2.horizontal = !node.horizontal;
        } else {
          node.child1 = this.insert(node.child1, p);
          node.child1.horizontal = !node.horizontal;
        }
      } else {
        if (Double.compare(node.point.x(), p.x()) > 0) {
          node.child1 = this.insert(node.child1, p);
          node.child1.horizontal = !node.horizontal;
        } else {
          node.child2 = this.insert(node.child2, p);
          node.child2.horizontal = !node.horizontal;
        }
      }

      return node;
    }
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    Node node = this.root;

    while (node != null) {
      if (node.point.compareTo(p) == 0) {
        return true;
      }

      if (node.horizontal) {
        node = Double.compare(node.point.y(), p.y()) > 0 ? node.child2 : node.child1;
      } else {
        node = Double.compare(node.point.x(), p.x()) > 0 ? node.child1 : node.child2;
      }
    }

    return false;
  }

  // draw all points to standard draw
  public void draw() {
    StdDraw.clear();

    this.draw(this.root, new RectHV(0, 0, 1, 1));
  }

  private void draw(Node node, RectHV rect) {
    if (node == null) {
      return;
    }

    Point2D point = node.point;

    StdDraw.setPenRadius(0.01);

    if (node.horizontal) {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(rect.xmin(), point.y(), rect.xmax(), point.y());

      this.draw(node.child1, new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax()));
      this.draw(node.child2, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y()));
    } else {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(point.x(), rect.ymin(), point.x(), rect.ymax());

      this.draw(node.child1, new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax()));
      this.draw(node.child2, new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax()));
    }
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }

    SET<Point2D> points = new SET<Point2D>();

    this.range(rect, this.root, points);

    return points;
  }

  private void range(RectHV rect, Node node, SET<Point2D> points) {
    if (node == null) {
      return;
    }

    if (rect.contains(node.point)) {
      points.add(node.point);

      range(rect, node.child1, points);
      range(rect, node.child2, points);
    } else {
      if (node.horizontal) {
        if (Double.compare(node.point.y(), rect.ymax()) >= 0) {
          range(rect, node.child2, points);
        } else {
          range(rect, node.child1, points);
        }
      } else {
        if (Double.compare(node.point.x(), rect.xmax()) >= 0) {
          range(rect, node.child1, points);
        } else {
          range(rect, node.child2, points);
        }
      }
    }
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    return this.nearest(this.root, p);
  }

  private Point2D nearest(Node node, Point2D p) {
    if (node == null) {
      return null;
    }

    double min = p.distanceSquaredTo(node.point);

    if (node.horizontal) {
      if (Double.compare(node.point.y(), p.y()) > 0) {
        Point2D p2 = nearest(node.child2, p);

        if (p2 != null && Double.compare(p.distanceSquaredTo(p2), min) <= 0) {
          return p2;
        } else {
          Point2D p1 = nearest(node.child1, p);

          if (p1 != null && Double.compare(p.distanceSquaredTo(p1), min) <= 0) {
            return p1;
          }
        }
      } else {
        Point2D p1 = nearest(node.child1, p);

        if (p1 != null && Double.compare(p.distanceSquaredTo(p1), min) <= 0) {
          return p1;
        } else {
          Point2D p2 = nearest(node.child2, p);

          if (p2 != null && Double.compare(p.distanceSquaredTo(p2), min) <= 0) {
            return p2;
          }
        }
      }
    } else {
      if (Double.compare(node.point.x(), p.x()) > 0) {
        Point2D p1 = nearest(node.child1, p);

        if (p1 != null && Double.compare(p.distanceSquaredTo(p1), min) <= 0) {
          return p1;
        } else {
          Point2D p2 = nearest(node.child2, p);

          if (p2 != null && Double.compare(p.distanceSquaredTo(p2), min) <= 0) {
            return p2;
          }
        }
      } else {
        Point2D p2 = nearest(node.child2, p);

        if (p2 != null && Double.compare(p.distanceSquaredTo(p2), min) < 0) {
          return p2;
        } else {
          Point2D p1 = nearest(node.child1, p);

          if (p1 != null && Double.compare(p.distanceSquaredTo(p1), min) < 0) {
            return p1;
          }
        }
      }
    }

    return node.point;
  }

  private class Node {
    private final Point2D point;
    private boolean horizontal;
    private Node child1;
    private Node child2;

    Node(Point2D point) {
      this.point = point;
      this.horizontal = false;
      this.child1 = null;
      this.child2 = null;
    }
  }
}