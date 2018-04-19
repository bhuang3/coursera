import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
  private Point[] points;
  private List<LineSegment> segments;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    this.points = points;
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

    for (Point point : this.points) {
      List<Point> list = Arrays.asList(this.points);
      Collections.sort(list, point.slopeOrder());

      Point start = list.get(1);
      int count = 1;
      double slope = point.slopeTo(start);

      for (int i = 2; i < list.size(); i++) {
        if (slope == point.slopeTo(list.get(i))) {
          count++;
        } else {
          if (count >= 3) {
            this.segments.add(new LineSegment(start, list.get(i - 1)));
            start = list.get(i - 1);
            count = 1;
            slope = point.slopeTo(list.get(i));
          }
        }
      }

      if (count >= 3) {
        this.segments.add(new LineSegment(start, list.get(list.size() - 1)));
      }
    }
  }
}