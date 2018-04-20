import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
  private final Point[] points;
  private final List<LineSegment> segments;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    this.checkNull(points);

    this.points = points.clone();
    this.segments = new ArrayList<LineSegment>();

    this.findSegments();
  }

  // the number of line segments
  public int numberOfSegments() {
    return this.segments.size();
  }

  // the line segments
  public LineSegment[] segments() {
    return this.segments.toArray(new LineSegment[this.numberOfSegments()]);
  }

  private void findSegments() {
    Arrays.sort(this.points);
    this.checkRepeat();

    for (Point point : this.points) {
      List<Point> list = clonePoints();
      Collections.sort(list, point.slopeOrder());

      int count = 1;
      Point start = list.get(1);
      double slope1 = point.slopeTo(start);
      double slope2 = 0.0;

      for (int i = 2; i < list.size(); i++) {
        slope2 = point.slopeTo(list.get(i));

        if (slope1 == slope2) {
          count++;
        } else {
          if (count >= 3) {
            this.addSegment(point, start, list.get(i - 1));
          }

          start = list.get(i);
          count = 1;
          slope1 = slope2;
        }
      }

      if (count >= 3) {
        this.addSegment(point, start, list.get(list.size() - 1));
      }
    }
  }

  private void addSegment(Point curr, Point p1, Point p2) {
    Point start = curr.compareTo(p1) < 0 ? curr : p1;
    Point end = curr.compareTo(p2) < 0 ? p2 : curr;
    LineSegment newSegment = new LineSegment(start, end);

    for (LineSegment segment : this.segments) {
      if (segment.toString().equals(newSegment.toString())) {
        return;
      }
    }

    this.segments.add(newSegment);
  }

  private List<Point> clonePoints() {
    List<Point> list = new ArrayList<Point>();

    for (Point point : this.points) {
      list.add(point);
    }

    return list;
  }

  private void checkNull(Point[] points) {
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException();
      }
    }
  }

  private void checkRepeat() {
    for (int i = 1; i < this.points.length; i++) {
      if (points[i].compareTo(points[i - 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }
  }
}