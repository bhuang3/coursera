import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
  private Point[] points;
  private List<LineSegment> segments;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
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

    checkRepeat(this.points);

    double slope1 = 0.0;
    double slope2 = 0.0;
    double slope3 = 0.0;
    int size = this.points.length;

    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        slope1 = this.points[i].slopeTo(this.points[j]);

        for (int k = j + 1; k < size; k++) {
          slope2 = this.points[i].slopeTo(this.points[k]);

          for (int m = k + 1; m < size; m++) {
            slope3 = this.points[i].slopeTo(this.points[m]);

            if (slope1 == Double.NEGATIVE_INFINITY || slope2 == Double.NEGATIVE_INFINITY || slope3 == Double.NEGATIVE_INFINITY) {
              throw new IllegalArgumentException();
            } else if (slope1 == slope2 && slope2 == slope3) {
              this.segments.add(new LineSegment(this.points[i], this.points[m]));
            }
          }
        }
      }
    }
  }

  private void checkRepeat(Point[] points) {
    for (int i = 1; i < points.length; i++) {
      if (points[i].compareTo(points[i - 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }
  }
}