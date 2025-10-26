import algorithms.*;
import graph.*;
import models.MSTResult;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * Comprehensive test suite for MST algorithm correctness.
 */
public class MSTCorrectnessTest {

    private PrimAlgorithm primAlgorithm;
    private KruskalAlgorithm kruskalAlgorithm;

    @BeforeEach
    public void setUp() {
        primAlgorithm = new PrimAlgorithm();
        kruskalAlgorithm = new KruskalAlgorithm();
    }

    @Test
    @DisplayName("Test 1: MST costs match between algorithms")
    public void testMSTCostsMatch() {
        Graph graph = createSimpleGraph();

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost(),
                "Both algorithms should produce MSTs with the same total cost");
    }

    @Test
    @DisplayName("Test 2: MST has V-1 edges")
    public void testMSTEdgeCount() {
        Graph graph = createSimpleGraph();
        int expectedEdges = graph.getVertices() - 1;

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertEquals(expectedEdges, primResult.getMstEdges().size(),
                "Prim's MST should have V-1 edges");
        assertEquals(expectedEdges, kruskalResult.getMstEdges().size(),
                "Kruskal's MST should have V-1 edges");
    }

    @Test
    @DisplayName("Test 3: MST is acyclic")
    public void testMSTAcyclic() {
        Graph graph = createMediumGraph();

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertTrue(isAcyclic(primResult.getMstEdges(), graph.getVertices()),
                "Prim's MST should be acyclic");
        assertTrue(isAcyclic(kruskalResult.getMstEdges(), graph.getVertices()),
                "Kruskal's MST should be acyclic");
    }

    @Test
    @DisplayName("Test 4: MST connects all vertices")
    public void testMSTConnectivity() {
        Graph graph = createMediumGraph();

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertTrue(isConnected(primResult.getMstEdges(), graph.getVertices()),
                "Prim's MST should connect all vertices");
        assertTrue(isConnected(kruskalResult.getMstEdges(), graph.getVertices()),
                "Kruskal's MST should connect all vertices");
    }

    @Test
    @DisplayName("Test 5: Disconnected graph handling")
    public void testDisconnectedGraph() {
        Graph graph = new Graph(5);
        // Create two separate components
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(3, 4, 3);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertTrue(primResult.getMstEdges().size() < graph.getVertices() - 1,
                "Prim's should handle disconnected graphs");
        assertTrue(kruskalResult.getMstEdges().size() < graph.getVertices() - 1,
                "Kruskal's should handle disconnected graphs");
    }

    @Test
    @DisplayName("Test 6: Single vertex graph")
    public void testSingleVertex() {
        Graph graph = new Graph(1);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertEquals(0, primResult.getMstEdges().size());
        assertEquals(0, kruskalResult.getMstEdges().size());
        assertEquals(0, primResult.getTotalCost());
        assertEquals(0, kruskalResult.getTotalCost());
    }

    @Test
    @DisplayName("Test 7: Execution time is non-negative")
    public void testExecutionTimePositive() {
        Graph graph = createMediumGraph();

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertTrue(primResult.getExecutionTimeMs() >= 0,
                "Prim's execution time should be non-negative");
        assertTrue(kruskalResult.getExecutionTimeMs() >= 0,
                "Kruskal's execution time should be non-negative");
    }

    @Test
    @DisplayName("Test 8: Operation count is non-negative")
    public void testOperationCountPositive() {
        Graph graph = createMediumGraph();

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertTrue(primResult.getOperationCount() > 0,
                "Prim's operation count should be positive");
        assertTrue(kruskalResult.getOperationCount() > 0,
                "Kruskal's operation count should be positive");
    }

    @Test
    @DisplayName("Test 9: Complete graph MST")
    public void testCompleteGraph() {
        Graph graph = createCompleteGraph(5);

        MSTResult primResult = primAlgorithm.findMST(graph);
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);

        assertEquals(4, primResult.getMstEdges().size());
        assertEquals(4, kruskalResult.getMstEdges().size());
        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost());
    }

    @Test
    @DisplayName("Test 10: Results are reproducible")
    public void testReproducibility() {
        Graph graph = createMediumGraph();

        MSTResult primResult1 = primAlgorithm.findMST(graph);
        MSTResult primResult2 = primAlgorithm.findMST(graph);

        assertEquals(primResult1.getTotalCost(), primResult2.getTotalCost(),
                "Prim's results should be reproducible");
        assertEquals(primResult1.getMstEdges().size(), primResult2.getMstEdges().size(),
                "Prim's edge count should be reproducible");
    }

    // Helper methods

    private Graph createSimpleGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 10);
        graph.addEdge(0, 2, 6);
        graph.addEdge(0, 3, 5);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 4);
        return graph;
    }

    private Graph createMediumGraph() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 2);
        graph.addEdge(2, 3, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 6);
        return graph;
    }

    private Graph createCompleteGraph(int n) {
        Graph graph = new Graph(n);
        int weight = 1;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                graph.addEdge(i, j, weight++);
            }
        }
        return graph;
    }

    private boolean isAcyclic(List<Edge> edges, int vertices) {
        UnionFind uf = new UnionFind(vertices);
        for (Edge edge : edges) {
            if (uf.find(edge.getSource()) == uf.find(edge.getDestination())) {
                return false; // Cycle detected
            }
            uf.union(edge.getSource(), edge.getDestination());
        }
        return true;
    }

    private boolean isConnected(List<Edge> edges, int vertices) {
        UnionFind uf = new UnionFind(vertices);
        for (Edge edge : edges) {
            uf.union(edge.getSource(), edge.getDestination());
        }

        int root = uf.find(0);
        for (int i = 1; i < vertices; i++) {
            if (uf.find(i) != root) {
                return false;
            }
        }
        return true;
    }

    // Simple Union-Find for testing
    private static class UnionFind {
        private final int[] parent;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }
}