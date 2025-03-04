//2b Question: 
//Find lexicographically smallest pair (i, j) with smallest Manhattan distance.Distance = |x_coords[i] - x_coords[j]| + |y_coords[i] - y_coords[j]|

import java.util.Arrays;

public class ClosestPoint{

    // Function to find the lexicographically smallest pair of closest points
    public static int[] findClosestPair(int[] x_coords, int[] y_coords) {
        int n = x_coords.length; 
        int minDistance = Integer.MAX_VALUE; // Initialize minimum distance to a large value
        int[] result = new int[2]; // Array to store the result indices (i, j)

        // Iterate through all pairs of points
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue; // Skip the same point (distance is 0, but we need distinct points)

                // Calculate Manhattan distance between points (i) and (j)
                int distance = Math.abs(x_coords[i] - x_coords[j]) + Math.abs(y_coords[i] - y_coords[j]);

                // Check if this pair has a smaller distance or is lexicographically smaller
                if (distance < minDistance) {
                    // Update minimum distance and result indices
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                } else if (distance == minDistance) {
                    // If distance is equal, check for lexicographical order
                    if (i < result[0] || (i == result[0] && j < result[1])) {
                        // Update result indices to the lexicographically smaller pair
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }

        return result; // Return the indices of the closest pair
    }

    public static void main(String[] args) {
        // Example input
        int[] x_coords = {1, 2, 3, 2, 4}; // Array of x-coordinates
        int[] y_coords = {2, 3, 1, 2, 3}; // Array of y-coordinates

        // Find the closest pair of points
        int[] closestPair = findClosestPair(x_coords, y_coords);

        // Print the result
        System.out.println("Closest Pair Indices: " + Arrays.toString(closestPair)); // Output: [0, 3]
    }
}
