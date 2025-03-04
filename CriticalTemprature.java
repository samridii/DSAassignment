// Question 1a:
// Finding the Critical Temperature

class CriticalTemperature {
    // Function to calculate the minimum number of attempts required
    public static int minMeasurements(int thermometers, int levels) {
        int[][] dp = new int[thermometers + 1][levels + 1]; // DP table
        int attempts = 0; // Number of attempts required

        // Loop until we can measure all levels
        while (dp[thermometers][attempts] < levels) {
            attempts++;
            for (int i = 1; i <= thermometers; i++) {
                // Formula to update DP table: Using previous results
                dp[i][attempts] = dp[i - 1][attempts - 1] + dp[i][attempts - 1] + 1;
            }
        }
        return attempts;
    }

    public static void main(String[] args) {
        // Example Test Cases
        System.out.println("Example 1:");
        System.out.println("Input: Thermometers = 1, Levels = 5");
        System.out.println("Output: " + minMeasurements(1, 5)); // Expected Output: 5

        System.out.println("\nExample 2:");
        System.out.println("Input: Thermometers = 2, Levels = 10");
        System.out.println("Output: " + minMeasurements(2, 10)); // Expected Output: 4

        System.out.println("\nExample 3:");
        System.out.println("Input: Thermometers = 3, Levels = 20");
        System.out.println("Output: " + minMeasurements(3, 20)); // Expected Output: 5

        System.out.println("\nExample 4:");
        System.out.println("Input: Thermometers = 4, Levels = 30");
        System.out.println("Output: " + minMeasurements(4, 30)); // Expected Output: 5

    }
}