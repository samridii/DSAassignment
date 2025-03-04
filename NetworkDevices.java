//Question 3a
//Determine the minimum total cost to connect all devices in the network.

import java.util.*;
public class NetworkDevices{

    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        // Create a list to store all edges (connections + module options)
        List<int[]> edges = new ArrayList<>();

        // Add edges for module installations 
        for (int i = 0; i < n; i++) {
            edges.add(new int[]{0, i + 1, modules[i]}); // Connect device i+1 to virtual node 0 with cost modules[i]
        }

        // Add edges for given connections
        for (int[] connection : connections) {
            int device1 = connection[0];
            int device2 = connection[1];
            int cost = connection[2];
            edges.add(new int[]{device1, device2, cost});
        }

        // Sort edges by cost (ascending order)
        edges.sort((a, b) -> a[2] - b[2]);

        // Initialize Union-Find data structure
        int[] parent = new int[n + 1]; // Parent array for Union-Find
        for (int i = 0; i <= n; i++) {
            parent[i] = i; // Each node is its own parent initially
        }

        int totalCost = 0; // Total cost of the MST
        int edgesUsed = 0; // Number of edges used in the MST

        // Kruskal's algorithm 
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int cost = edge[2];

            int parentU = find(parent, u);
            int parentV = find(parent, v);

            // If u and v are not in the same set, add this edge to the MST
            if (parentU != parentV) {
                totalCost += cost;
                edgesUsed++;
                union(parent, parentU, parentV); // Union the sets
            }

            // Stop when we have used n edges 
            if (edgesUsed == n) {
                break;
            }
        }

        return totalCost;
    }

    // Find function for Union-Find
    private static int find(int[] parent, int node) {
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]); // Path compression
        }
        return parent[node];
    }

    // Union function for Union-Find
    private static void union(int[] parent, int u, int v) {
        parent[v] = u;
    }

    public static void main(String[] args) {
        // Example input
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};

        // Find the minimum total cost
        int result = minCostToConnectDevices(n, modules, connections);
        System.out.println("Minimum Total Cost: " + result); // Output: 3
    }
}
