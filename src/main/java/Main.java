import algorithms.*;
import graph.Graph;
import models.MSTResult;
import utils.*;

import java.io.IOException;
import java.util.*;

/**
 * Main class for the City Transportation Network MST optimization.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== City Transportation Network Optimization ===\n");

        // Input files to process
        String[] inputFiles = {
                "src/main/resources/input_small.json",
                "src/main/resources/input_medium.json",
                "src/main/resources/input_large.json"
        };

        List<Map<String, MSTResult>> allResults = new ArrayList<>();

        // Process each input file
        for (String inputFile : inputFiles) {
            System.out.println("\n--- Processing: " + inputFile + " ---");

            try {
                List<Graph> graphs = JsonReader.readGraphsFromJson(inputFile);

                for (Graph graph : graphs) {
                    processGraph(graph, allResults);
                }

            } catch (IOException e) {
                System.err.println("Error reading file " + inputFile + ": " + e.getMessage());
            }
        }

        // Write results to output files
        try {
            JsonWriter.writeResultsToJson(allResults, "src/main/resources/output.json");
            JsonWriter.writeResultsToCSV(allResults, "src/main/resources/results.csv");
        } catch (IOException e) {
            System.err.println("Error writing results: " + e.getMessage());
        }

        System.out.println("\n=== Processing Complete ===");
        printSummary(allResults);
    }

    /**
     * Processes a single graph with both algorithms.
     *
     * @param graph      The graph to process
     * @param allResults List to store results
     */
    private static void processGraph(Graph graph, List<Map<String, MSTResult>> allResults) {
        System.out.println("\n" + graph);

        // Check if graph is connected
        if (!graph.isConnected()) {
            System.out.println("WARNING: Graph is not connected! MST cannot be found.");
            return;
        }

        Map<String, MSTResult> results = new HashMap<>();

        // Run Prim's Algorithm
        PrimAlgorithm primAlgorithm = new PrimAlgorithm();
        MSTResult primResult = primAlgorithm.findMST(graph);
        results.put("prim", primResult);
        System.out.println(primResult);

        // Run Kruskal's Algorithm
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm();
        MSTResult kruskalResult = kruskalAlgorithm.findMST(graph);
        results.put("kruskal", kruskalResult);
        System.out.println(kruskalResult);

        // Compare results
        compareResults(primResult, kruskalResult);

        allResults.add(results);
    }

    /**
     * Compares results from Prim's and Kruskal's algorithms.
     *
     * @param primResult    Result from Prim's algorithm
     * @param kruskalResult Result from Kruskal's algorithm
     */
    private static void compareResults(MSTResult primResult, MSTResult kruskalResult) {
        System.out.println("\n=== Algorithm Comparison ===");

        boolean costsMatch = primResult.getTotalCost() == kruskalResult.getTotalCost();
        System.out.println("Total costs match: " + (costsMatch ? "✓ YES" : "✗ NO"));

        if (!costsMatch) {
            System.out.println("  Prim's cost: " + primResult.getTotalCost());
            System.out.println("  Kruskal's cost: " + kruskalResult.getTotalCost());
        }

        System.out.println("\nExecution Time:");
        System.out.printf("  Prim's: %.3f ms\n", primResult.getExecutionTimeMs());
        System.out.printf("  Kruskal's: %.3f ms\n", kruskalResult.getExecutionTimeMs());

        double timeDiff = Math.abs(primResult.getExecutionTimeMs() - kruskalResult.getExecutionTimeMs());
        String faster = primResult.getExecutionTimeMs() < kruskalResult.getExecutionTimeMs()
                ? "Prim's" : "Kruskal's";
        System.out.printf("  %s was faster by %.3f ms\n", faster, timeDiff);

        System.out.println("\nOperation Count:");
        System.out.printf("  Prim's: %d operations\n", primResult.getOperationCount());
        System.out.printf("  Kruskal's: %d operations\n", kruskalResult.getOperationCount());

        long opDiff = Math.abs(primResult.getOperationCount() - kruskalResult.getOperationCount());
        String fewer = primResult.getOperationCount() < kruskalResult.getOperationCount()
                ? "Prim's" : "Kruskal's";
        System.out.printf("  %s performed %d fewer operations\n", fewer, opDiff);
    }

    /**
     * Prints a summary of all results.
     *
     * @param allResults List of all results
     */
    private static void printSummary(List<Map<String, MSTResult>> allResults) {
        System.out.println("\n=== Overall Summary ===");
        System.out.println("Total graphs processed: " + allResults.size());

        int primFaster = 0;
        int kruskalFaster = 0;
        int primFewerOps = 0;
        int kruskalFewerOps = 0;

        for (Map<String, MSTResult> results : allResults) {
            if (results.containsKey("prim") && results.containsKey("kruskal")) {
                MSTResult prim = results.get("prim");
                MSTResult kruskal = results.get("kruskal");

                if (prim.getExecutionTimeMs() < kruskal.getExecutionTimeMs()) {
                    primFaster++;
                } else {
                    kruskalFaster++;
                }

                if (prim.getOperationCount() < kruskal.getOperationCount()) {
                    primFewerOps++;
                } else {
                    kruskalFewerOps++;
                }
            }
        }

        System.out.println("\nSpeed Comparison:");
        System.out.printf("  Prim's faster: %d times\n", primFaster);
        System.out.printf("  Kruskal's faster: %d times\n", kruskalFaster);

        System.out.println("\nOperation Efficiency:");
        System.out.printf("  Prim's fewer operations: %d times\n", primFewerOps);
        System.out.printf("  Kruskal's fewer operations: %d times\n", kruskalFewerOps);
    }
}