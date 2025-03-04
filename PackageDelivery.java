import java.util.*;

public class PackageDelivery {
    public static void main(String[] args) {
        // Test case 1
        int[] packages1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println("Output: " + minRoadsToCollectPackages(packages1, roads1)); // Expected Output: 2

        // Test case 2 (Different from the original)
        int[] packages2 = {0, 1, 0, 1, 0, 1, 0, 1};
        int[][] roads2 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}};
        System.out.println("Output: " + minRoadsToCollectPackages(packages2, roads2)); // Expected Output: 6
    }

    public static int minRoadsToCollectPackages(int[] packages, int[][] roads) {
        int n = packages.length;
        Map<Integer, List<Integer>> graph = new HashMap<>();

        // Build the adjacency list
        for (int[] road : roads) {
            graph.computeIfAbsent(road[0], k -> new ArrayList<>()).add(road[1]);
            graph.computeIfAbsent(road[1], k -> new ArrayList<>()).add(road[0]);
        }

        Set<Integer> visited = new HashSet<>();
        return dfs(0, -1, graph, packages, visited);
    }

    private static int dfs(int node, int parent, Map<Integer, List<Integer>> graph, int[] packages, Set<Integer> visited) {
        visited.add(node);
        int roadsUsed = 0;

        for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            if (neighbor == parent) continue; // Avoid going back directly
            if (!visited.contains(neighbor)) {
                roadsUsed += dfs(neighbor, node, graph, packages, visited);
            }
        }

        // If a package exists at this node or any child node required traversal, count the road
        if ((packages[node] == 1 || roadsUsed > 0) && parent != -1) {
            return roadsUsed + 2; // Moving to the node and coming back
        }
        return roadsUsed;
    }
}
