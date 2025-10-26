package graph;

import java.util.Objects;

/**
 * Represents an edge in a weighted undirected graph.
 * Used in the Graph data structure for MST algorithms.
 */
public class Edge implements Comparable<Edge> {
    private final int source;
    private final int destination;
    private final int weight;

    /**
     * Creates an edge with specified source, destination, and weight.
     *
     * @param source      The source vertex
     * @param destination The destination vertex
     * @param weight      The weight (cost) of the edge
     */
    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public int getSource() {
        return source;
    }

    public int getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * Compares edges based on their weight for sorting.
     *
     * @param other The edge to compare with
     * @return Negative if this edge is lighter, positive if heavier, 0 if equal
     */
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return weight == edge.weight &&
                ((source == edge.source && destination == edge.destination) ||
                        (source == edge.destination && destination == edge.source));
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(source, destination),
                Math.max(source, destination), weight);
    }

    @Override
    public String toString() {
        return String.format("Edge(%d-%d, weight=%d)", source, destination, weight);
    }

    /**
     * Returns a formatted string for JSON output.
     *
     * @return JSON-friendly representation
     */
    public String toJsonFormat() {
        return String.format("{\"from\": %d, \"to\": %d, \"cost\": %d}",
                source, destination, weight);
    }
}