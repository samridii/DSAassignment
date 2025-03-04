//Question 2a.
//Determine the minimum number of rewards you need to distribute to the employees.

public class EmployeeRewards {
    // Function to calculate the minimum number of rewards
    public static int minRewards(int[] ratings) {
        int n = ratings.length;
        int[] rewards = new int[n];

        // Step 1: Initialize all rewards to 1
        for (int i = 0; i < n; i++) {
            rewards[i] = 1;
        }

        // Step 2: Left-to-right pass
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                rewards[i] = rewards[i - 1] + 1;
            }
        }

        // Step 3: Right-to-left pass
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                rewards[i] = Math.max(rewards[i], rewards[i + 1] + 1);
            }
        }

        // Step 4: Sum all rewards
        int totalRewards = 0;
        for (int reward : rewards) {
            totalRewards += reward;
        }

        return totalRewards;
    }

    public static void main(String[] args) {
        // Example 1: Simple case with increasing ratings
        int[] ratings1 = {1, 2, 3, 4, 5};
        System.out.println("Example 1 Output: " + minRewards(ratings1)); // Output: 15

        // Example 2: Simple case with decreasing ratings
        int[] ratings2 = {5, 4, 3, 2, 1};
        System.out.println("Example 2 Output: " + minRewards(ratings2)); // Output: 15

        // Example 3: Mixed ratings with peaks and valleys
        int[] ratings3 = {1, 3, 2, 1, 4, 3, 2};
        System.out.println("Example 3 Output: " + minRewards(ratings3)); // Output: 13

        // Example 4: All employees have the same rating
        int[] ratings4 = {2, 2, 2, 2, 2};
        System.out.println("Example 4 Output: " + minRewards(ratings4)); // Output: 5

    }
}