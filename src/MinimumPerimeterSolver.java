import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

public class MinimumPerimeterSolver {

    private MinimumPerimeterSolver() {}

    public static double findMinimumPerimeter(Point[] points) {
        if (points == null || points.length < 3) {
            return Double.POSITIVE_INFINITY;
        }

        Point[] ptsByX = points.clone();
        Point[] ptsByY = points.clone();

        Arrays.sort(ptsByX, (p1, p2) -> Integer.compare(p1.x(), p2.x()));
        Arrays.sort(ptsByY, (p1, p2) -> Integer.compare(p1.y(), p2.y()));

        return solve(ptsByX, ptsByY);
    }

    private static double solve(Point[] ptsByX, Point[] ptsByY) {
        int n = ptsByX.length;

        if (n < 3) {
            return Double.POSITIVE_INFINITY;
        }

        if (n == 3) {
            return perimeter(ptsByX[0], ptsByX[1], ptsByX[2]);
        }

        int mid = n / 2;
        Point midPoint = ptsByX[mid];

        Point[] leftByX = Arrays.copyOfRange(ptsByX, 0, mid);
        Point[] rightByX = Arrays.copyOfRange(ptsByX, mid, n);

        // Track exact point instances allocated to the left branch
        Set<Point> leftSet = Collections.newSetFromMap(new IdentityHashMap<>());
        for (Point p : leftByX) {
            leftSet.add(p);
        }

        // Partition Y array using exact reference identity (prevents bounds bugs on duplicate coordinates)
        Point[] leftByY = new Point[leftByX.length];
        Point[] rightByY = new Point[rightByX.length];

        int l = 0, r = 0;
        for (Point p : ptsByY) {
            if (leftSet.contains(p)) {
                leftByY[l++] = p;
            } else {
                rightByY[r++] = p;
            }
        }

        // Recursive calls
        double dLeft = solve(leftByX, leftByY);
        double dRight = solve(rightByX, rightByY);
        double delta = Math.min(dLeft, dRight);

        // GUARD FIX: If delta is infinite, we cannot filter or compare against delta / 2
        if (Double.isInfinite(delta)) {
            // Check if all current n points across the boundary form a valid candidate
            if (n <= 16) {
                for (int i = 0; i < n; i++) {
                    for (int j = i + 1; j < n; j++) {
                        for (int k = j + 1; k < n; k++) {
                            delta = Math.min(delta, perimeter(ptsByY[i], ptsByY[j], ptsByY[k]));
                        }
                    }
                }
            }
            return delta;
        }

        // Filter Y-ordered strip within delta/2 of median line
        List<Point> strip = new ArrayList<>();
        double halfDelta = delta / 2.0;
        for (Point p : ptsByY) {
            if (Math.abs(p.x() - midPoint.x()) < halfDelta) {
                strip.add(p);
            }
        }

        // Check candidate triangles in strip
        int stripSize = strip.size();
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip.get(j).y() - strip.get(i).y()) < halfDelta; j++) {
                for (int k = j + 1; k < stripSize && (strip.get(k).y() - strip.get(i).y()) < halfDelta; k++) {
                    double p = perimeter(strip.get(i), strip.get(j), strip.get(k));
                    if (p < delta) {
                        delta = p;
                        halfDelta = delta / 2.0; // Tighten bound dynamically
                    }
                }
            }
        }

        return delta;
    }

    private static double perimeter(Point p1, Point p2, Point p3) {
        return p1.distanceTo(p2) + p2.distanceTo(p3) + p3.distanceTo(p1);
    }
}