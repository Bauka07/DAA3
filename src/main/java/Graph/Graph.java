package graph;

import java.util.*;

/**
 * Represents a weighted undirected graph using adjacency list representation.
 * This implementation is optimized for MST algorithms (Prim's and Kruskal's).
 */
public class Graph {
    private final int vertices;
    private final List<Edge> edges;
    private final Map<Integer, List<Edge>> adjacencyList;

    /**
     * Creates a graph with the specified number of vertices.
     *
     * @param vertices Number of vertices in the graph
     */
    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();

        // Initialize adjacency list for all vertices
        for (int i = 0; i < vertices; i++) {
            adjacencyList.put(i, new ArrayList<>());
        }
    }

    /**
     * Adds an undirected edge to the graph.
     *
     * @param source      Source vertex
     * @param destination Destination vertex
     * @param weight      Weight of the edge
     */
    public void addEdge(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        edges.add(edge);

        // Add to adjacency list (undirected graph)
        adjacencyList.get(source).add(edge);
        adjacencyList.get(destination).add(new Edge(destination, source, weight));
    }

    /**
     * Gets all edges in the graph.
     *
     * @return List of all edges
     */
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * Gets all edges adjacent to a specific vertex.
     *
     * @param vertex The vertex
     * @return List of adjacent edges
     */
    public List<Edge> getAdjacentEdges(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    /**
     * Gets the number of vertices in the graph.
     *
     * @return Number of vertices
     */
    public int getVertices() {
        return vertices;
    }

    /**
     * Gets the number of edges in the graph.
     *
     * @return Number of edges
     */
    public int getEdgeCount() {
        return edges.size();
    }

    /**
     * Checks if the graph is connected using BFS.
     *
     * @return true if the graph is connected, false otherwise
     */
    public boolean isConnected() {
        if (vertices == 0) return true;

        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(0);
        visited[0] = true;
        int visitedCount = 1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (Edge edge : adjacencyList.get(current)) {
                int neighbor = edge.getDestination();
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                    visitedCount++;
                }
            }
        }

        return visitedCount == vertices;
    }

    /**
     * Gets the density of the graph.
     * Density = (2 * edges) / (vertices * (vertices - 1))
     *
     * @return Graph density as a percentage
     */
    public double getDensity() {
        if (vertices <= 1) return 0.0;
        double maxEdges = vertices * (vertices - 1.0) / 2.0;
        return (edges.size() / maxEdges) * 100.0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Graph: %d vertices, %d edges, %.2f%% density\n",
                vertices, edges.size(), getDensity()));
        sb.append("Edges:\n");
        for (Edge edge : edges) {
            sb.append("  ").append(edge).append("\n");
        }
        return sb.toString();
    }

    /**
     * Creates a deep copy of this graph.
     *
     * @return A new Graph instance with the same structure
     */
    public Graph copy() {
        Graph newGraph = new Graph(this.vertices);
        for (Edge edge : this.edges) {
            newGraph.addEdge(edge.getSource(), edge.getDestination(), edge.getWeight());
        }
        return newGraph;
    }

    /**
     * Validates if an MST result is valid for this graph.
     *
     * @param mstEdges List of edges in the MST
     * @return true if valid MST, false otherwise
     */
    public boolean validateMST(List<Edge> mstEdges) {
        // MST should have exactly V-1 edges
        if (mstEdges.size() != vertices - 1) {
            return false;
        }

        // Check if MST is acyclic and connected using Union-Find
        UnionFind uf = new UnionFind(vertices);
        for (Edge edge : mstEdges) {
            if (uf.find(edge.getSource()) == uf.find(edge.getDestination())) {
                return false; // Cycle detected
            }
            uf.union(edge.getSource(), edge.getDestination());
        }

        // Check if all vertices are in the same component
        int root = uf.find(0);
        for (int i = 1; i < vertices; i++) {
            if (uf.find(i) != root) {
                return false;
            }
        }

        return true;
    }

    /**
     * Union-Find (Disjoint Set) data structure for cycle detection.
     */
    private static class UnionFind {
        private final int[] parent;
        private final int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX != rootY) {
                // Union by rank
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
    }
}