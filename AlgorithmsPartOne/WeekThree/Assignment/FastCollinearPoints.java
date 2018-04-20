import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
  private Point[] points;
  private List<LineSegment> segments;
  private List<Double> slopes;
  private List<Point> starts;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    this.points = points;
    this.segments = new ArrayList<LineSegment>();
    this.slopes = new ArrayList<Double>();
    this.starts = new ArrayList<Point>();

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

    for (Point point : this.points) {
      List<Point> list = clonePoints(this.points);
      Collections.sort(list, point.slopeOrder());

      int count = 1;
      Point start = list.get(1);
      double slope1 = point.slopeTo(start);
      double slope2 = 0.0;

      // this.slopes.clear();

      for (int i = 2; i < list.size(); i++) {
        slope2 = point.slopeTo(list.get(i));

        if (slope1 == slope2) {
          count++;
        } else {
          if (count >= 3) {
            this.addSegment(slope1, start.compareTo(point) < 0 ? start : point, list.get(i - 1));
          }

          start = list.get(i);
          count = 1;
          slope1 = slope2;
        }
      }

      if (count >= 3) {
        this.addSegment(slope1, start.compareTo(point) < 0 ? start : point, list.get(list.size() - 1));
      }
    }
  }

  private void addSegment(double slope, Point p1, Point p2) {
    int index = this.slopes.indexOf(slope);

    if (index < 0 || 
      (this.starts.get(index).compareTo(p1) != 0 && this.starts.get(index).compareTo(p2) != 0)) {
      this.slopes.add(slope);
      this.starts.add(p1);
      this.segments.add(new LineSegment(p1, p2));
    }
  }

  private List<Point> clonePoints(Point[] points) {
    List<Point> list = new ArrayList<Point>();

    for (Point point : points) {
      list.add(point);
    }

    return list;
  }
}