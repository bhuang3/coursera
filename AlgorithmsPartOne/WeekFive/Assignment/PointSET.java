import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
  private final SET<Point2D> set;

  // construct an empty set of points
  public PointSET() {
    this.set = new SET<Point2D>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return this.set.isEmpty();
  }

  // number of points in the set
  public int size() {
    return this.set.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    this.set.add(p);
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }

    return this.set.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    StdDraw.clear();
    StdDraw.setPenRadius(0.01);

    for (Point2D p : this.set) {
      StdDraw.point(p.x(), p.y());
    }
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }

    SET<Point2D> points = new SET<Point2D>();

    for (Point2D p : this.set) {
      if (rect.contains(p)) {
        points.add(p);
      }
    }

    return points;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D that) {
    if (that == null) {
      throw new IllegalArgumentException();
    }

    double min = Double.POSITIVE_INFINITY;
    double distance = 0;
    Point2D point = null;

    for (Point2D p : this.set) {
      distance = p.distanceSquaredTo(that);

      if (Double.compare(distance, min) < 0) {
        min = distance;
        point = p;
      }
    }

    return point;
  }

}