// Question 1b.
//Determine the kth lowest combined return that can be achieved

import java.util.PriorityQueue;

public class LowestReturn {

    // Function to find the kth lowest combined return
    public static int findLowestReturn(int[] returns1, int[] returns2, int k) {
        // Min-heap to store the combined returns
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // Iterate through all possible combinations of returns1 and returns2
        for (int r1 : returns1) {
            for (int r2 : returns2) {
                minHeap.offer(r1 + r2); // Add the combined return to the heap
            }
        }

        // Extract the kth smallest element from the heap
        int result = 0;
        for (int i = 0; i < k; i++) {
            result = minHeap.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        // Example 1
        int[] returns1_1 = {2, 5};
        int[] returns2_1 = {3, 4};
        int k1 = 2;
        System.out.println("1 Output: " + findLowestReturn(returns1_1, returns2_1, k1)); // Output: 8

        // Example 2
        int[] returns1_2 = {-4, -2, 0, 3};
        int[] returns2_2 = {2, 4};
        int k2 = 6;
        System.out.println("2 Output: " + findLowestReturn(returns1_2, returns2_2, k2)); // Output: 0
    }
}
