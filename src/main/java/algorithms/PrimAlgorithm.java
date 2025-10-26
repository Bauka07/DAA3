package algorithms;

import graph.Edge;
import graph.Graph;
import models.MSTResult;

import java.util.*;

/**
 * Implementation of Prim's algorithm for finding Minimum Spanning Tree.
 * Time Complexity: O(E log V) using a priority queue.
 */
public class PrimAlgorithm {
    private long operationCount;

    /**
     * Finds the MST using Prim's algorithm.
     *
     * @param graph The input graph
     * @return MSTResult containing the MST edges and statistics
     */
    public MSTResult findMST(Graph graph) {
        operationCount = 0;
        long startTime = System.nanoTime();

        int vertices = graph.getVertices();
        List<Edge> mstEdges = new ArrayList<>();
        boolean[] inMST = new boolean[vertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // Check if graph is connected
        if (!graph.isConnected()) {
            long endTime = System.nanoTime();
            double executionTime = (endTime - startTime) / 1_000_000.0;
            return new MSTResult("Prim's Algorithm", new ArrayList<>(), 0,
                    vertices, graph.getEdgeCount(), operationCount, executionTime);
        }

        // Start from vertex 0
        inMST[0] = true;
        operationCount++; // Marking vertex as visited

        // Add all edges from vertex 0
        for (Edge edge : graph.getAdjacentEdges(0)) {
            pq.offer(edge);
            operationCount++; // Insert into priority queue
        }

        int totalCost = 0;

        while (!pq.isEmpty() && mstEdges.size() < vertices - 1) {
            Edge edge = pq.poll();
            operationCount++; // Remove from priority queue

            int dest = edge.getDestination();

            // If destination is already in MST, skip this edge
            operationCount++; // Comparison
            if (inMST[dest]) {
                continue;
            }

            // Add edge to MST
            mstEdges.add(edge);
            totalCost += edge.getWeight();
            inMST[dest] = true;
            operationCount++; // Marking vertex as visited

            // Add all edges from the newly added vertex
            for (Edge adjacentEdge : graph.getAdjacentEdges(dest)) {
                operationCount++; // Checking adjacent edge
                if (!inMST[adjacentEdge.getDestination()]) {
                    pq.offer(adjacentEdge);
                    operationCount++; // Insert into priority queue
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;

        return new MSTResult("Prim's Algorithm", mstEdges, totalCost,
                vertices, graph.getEdgeCount(), operationCount, executionTime);
    }

    /**
     * Alternative implementation using an array-based approach (O(V^2)).
     * Useful for dense graphs or when a simpler implementation is needed.
     *
     * @param graph The input graph
     * @return MSTResult containing the MST edges and statistics
     */
    public MSTResult findMSTArrayBased(Graph graph) {
        operationCount = 0;
        long startTime = System.nanoTime();

        int vertices = graph.getVertices();
        int[] parent = new int[vertices];
        int[] key = new int[vertices];
        boolean[] inMST = new boolean[vertices];

        // Initialize all keys as infinite
        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        // Start with vertex 0
        key[0] = 0;

        for (int count = 0; count < vertices - 1; count++) {
            // Find minimum key vertex not yet in MST
            int u = -1;
            int minKey = Integer.MAX_VALUE;

            for (int v = 0; v < vertices; v++) {
                operationCount++; // Comparison
                if (!inMST[v] && key[v] < minKey) {
                    minKey = key[v];
                    u = v;
                }
            }

            if (u == -1) break; // Graph is disconnected

            inMST[u] = true;
            operationCount++; // Marking vertex

            // Update key values of adjacent vertices
            for (Edge edge : graph.getAdjacentEdges(u)) {
                int v = edge.getDestination();
                int weight = edge.getWeight();
                operationCount++; // Checking adjacent edge

                if (!inMST[v] && weight < key[v]) {
                    key[v] = weight;
                    parent[v] = u;
                    operationCount++; // Update operation
                }
            }
        }

        // Construct MST edges
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        for (int i = 1; i < vertices; i++) {
            if (parent[i] != -1) {
                // Find the actual edge
                for (Edge edge : graph.getAdjacentEdges(i)) {
                    if (edge.getDestination() == parent[i]) {
                        mstEdges.add(new Edge(parent[i], i, key[i]));
                        totalCost += key[i];
                        break;
                    }
                }
            }
        }

        long endTime = System.nanoTime();
        double executionTime = (endTime - startTime) / 1_000_000.0;

        return new MSTResult("Prim's Algorithm (Array-based)", mstEdges, totalCost,
                vertices, graph.getEdgeCount(), operationCount, executionTime);
    }
}