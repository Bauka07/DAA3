package algorithms;

import graph.Edge;
import graph.Graph;
import models.MSTResult;

import java.util.*;

/**
 * Implementation of Kruskal's algorithm for finding Minimum Spanning Tree.
 * Time Complexity: O(E log E) due to sorting edges.
 */
public class KruskalAlgorithm {
    private long operationCount;

    /**
     * Finds the MST using Kruskal's algorithm.
     *
     * @param graph The input graph
     * @return MSTResult containing the MST edges and statistics
     */
    public MSTResult findMST(Graph graph) {
        operationCount = 0;
        long startTime = System.nanoTime();

        int vertices = graph.getVertices();
        List<Edge> mstEdges = new ArrayList<>();

        // Get all edges and sort them by weight
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
        operationCount += edges.size() * Math.log(edges.size()); // Sorting complexity

        // Create Union-Find data structure
        UnionFind uf = new UnionFind(vertices);

        int totalCost = 0;

        // Process edges in sorted order
        for (Edge edge : edges) {
            operationCount++; // Processing edge

            int sourceRoot = uf.find(edge.getSource());
            int destRoot = uf.find(edge.getDestination());
            operationCount += 2; // Two find operations

            // If including this edge doesn't create a cycle
            if (sourceRoot != destRoot) {
                operationCount++; // Comparison
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                uf.union(edge.getSource(), edge.getDestination());
                operationCount++; // Union operation

                // If we have V-1 edges, we're done
                if (mstEdges.size() == vertices - 1) {
                    break;
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;

        return new MSTResult("Kruskal's Algorithm", mstEdges, totalCost,
                vertices, graph.getEdgeCount(), operationCount, executionTime);
    }

    /**
     * Union-Find (Disjoint Set Union) data structure with path compression
     * and union by rank optimizations.
     */
    private class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];

            // Initialize each element as its own parent
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        /**
         * Finds the root of the set containing x with path compression.
         *
         * @param x The element
         * @return The root of the set
         */
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
                operationCount++; // Path compression operation
            }
            return parent[x];
        }

        /**
         * Unites the sets containing x and y using union by rank.
         *
         * @param x First element
         * @param y Second element
         */
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                // Union by rank
                operationCount++; // Comparison
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }

        /**
         * Checks if x and y are in the same set.
         *
         * @param x First element
         * @param y Second element
         * @return true if in same set, false otherwise
         */
        public boolean connected(int x, int y) {
            operationCount++; // Connected check
            return find(x) == find(y);
        }
    }
}