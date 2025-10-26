package models;

import graph.Edge;
import java.util.List;

/**
 * Stores the result of an MST algorithm execution.
 */
public class MSTResult {
    private final String algorithmName;
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final int vertexCount;
    private final int originalEdgeCount;
    private final long operationCount;
    private final double executionTimeMs;

    public MSTResult(String algorithmName, List<Edge> mstEdges, int totalCost,
                     int vertexCount, int originalEdgeCount, long operationCount,
                     double executionTimeMs) {
        this.algorithmName = algorithmName;
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.vertexCount = vertexCount;
        this.originalEdgeCount = originalEdgeCount;
        this.operationCount = operationCount;
        this.executionTimeMs = executionTimeMs;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int getOriginalEdgeCount() {
        return originalEdgeCount;
    }

    public long getOperationCount() {
        return operationCount;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n=== %s Results ===\n", algorithmName));
        sb.append(String.format("Total MST Cost: %d\n", totalCost));
        sb.append(String.format("MST Edges: %d (from %d vertices)\n",
                mstEdges.size(), vertexCount));
        sb.append(String.format("Original Graph Edges: %d\n", originalEdgeCount));
        sb.append(String.format("Operations Performed: %d\n", operationCount));
        sb.append(String.format("Execution Time: %.3f ms\n", executionTimeMs));
        sb.append("\nMST Edges:\n");
        for (Edge edge : mstEdges) {
            sb.append(String.format("  %d -> %d (cost: %d)\n",
                    edge.getSource(), edge.getDestination(), edge.getWeight()));
        }
        return sb.toString();
    }
}