import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
  private Point[] points;
  private List<LineSegment> segments;
  private List<Double> slopes;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    this.points = points;
    this.segments = new ArrayList<LineSegment>();
    this.slopes = new ArrayList<Double>();

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
      List<Point> list = Arrays.asList(this.points);
      Collections.sort(list, point.slopeOrder());

      Point start = list.get(1);
      int count = 1;
      double slope1 = point.slopeTo(start);
      double slope2 = 0.0;

      for (int i = 2; i < list.size(); i++) {
        slope2 = point.slopeTo(list.get(i));

        if (slope1 == slope2) {
          count++;
        } else {
          if (count >= 3) {
            this.addSegment(slope1, start, list.get(i - 1));
            start = list.get(i - 1);
            count = 1;
            slope1 = slope2;
          }
        }
      }

      if (count >= 3) {
        this.addSegment(slope1, start, list.get(list.size() - 1));
      }
    }
  }

  private void addSegment(double slope, Point p1, Point p2) {
    if (this.slopes.indexOf(slope) >= 0) {
      this.slopes.add(slope);
      this.segments.add(new LineSegment(p1, p2));
    }
  }
}