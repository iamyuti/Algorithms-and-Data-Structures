/**
 * Collection of fundamental algorithmic problems.
 * Highlights include Dynamic Programming for the Subset Sum problem.
 */
public class AlgorithmCollection {

    /**
     * Inner class representing a 2D Point.
     * Included here to keep the file self-contained.
     */
    public static class Point {
        double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
        public double getX() { return x; }
        public double getY() { return y; }
    }

    /**
     * Finds the maximum integer in an array.
     * Time Complexity: O(n)
     */
    public int findMax(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        for (int number : numbers) {
            if (number > max) {
                max = number;
            }
        }
        return max;
    }

    /**
     * Finds the closest pair of points in a 2D plane using a Brute-Force approach.
     * Time Complexity: O(n^2)
     * * @param points Array of points to check.
     * @return A Point array of size 2 containing the closest pair.
     */
    public Point[] findClosestPair(Point[] points) {
        if (points == null || points.length < 2) return null;

        Point[] result = new Point[2];
        // Initialize with the first two points
        result[0] = points[0];
        result[1] = points[1];
        double minDistanceSq = distanceSq(points[0], points[1]);

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double currentDistSq = distanceSq(points[i], points[j]);
                if (currentDistSq < minDistanceSq) {
                    minDistanceSq = currentDistSq;
                    result[0] = points[i];
                    result[1] = points[j];
                }
            }
        }
        return result;
    }

    /**
     * Helper to calculate squared Euclidean distance (avoids expensive sqrt).
     */
    private double distanceSq(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return (dx * dx) + (dy * dy);
    }

    /**
     * Solves the Subset Sum problem using Dynamic Programming.
     * Determines if any subset of the array adds up to the target sum.
     * * Strategy: Uses a boolean array where index i is true if a sum of i is achievable.
     * Space Complexity: O(sum)
     * Time Complexity: O(n * sum)
     * * @param targetSum The sum we are trying to reach.
     * @param numbers The set of available numbers.
     * @return true if a subset exists that sums up to targetSum.
     */
    public boolean hasSubsetSum(int targetSum, int[] numbers) {
        if (targetSum < 0) return false;

        boolean[] reachable = new boolean[targetSum + 1];
        reachable[0] = true; // Base case: sum 0 is always possible (empty set)

        for (int number : numbers) {
            // Iterate backwards to avoid using the same number multiple times for the same sum
            for (int j = targetSum; j >= number; j--) {
                if (reachable[j - number]) {
                    reachable[j] = true;
                }
            }
        }
        return reachable[targetSum];
    }
}