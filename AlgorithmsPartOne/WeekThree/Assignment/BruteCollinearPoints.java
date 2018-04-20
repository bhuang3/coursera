import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
  private final Point[] points;
  private final List<LineSegment> segments;

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
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

            if (slope1 == slope2 && slope2 == slope3) {
              this.segments.add(new LineSegment(this.points[i], this.points[m]));
            }
          }
        }
      }
    }
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