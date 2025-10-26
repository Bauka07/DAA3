package utils;

import com.google.gson.*;
import graph.Edge;
import models.MSTResult;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Utility class for writing MST results to JSON files.
 */
public class JsonWriter {

    /**
     * Writes MST results to a JSON file.
     *
     * @param results   List of MST results
     * @param filename  Output filename
     * @throws IOException If file writing fails
     */
    public static void writeResultsToJson(List<Map<String, MSTResult>> results,
                                          String filename) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray resultsArray = new JsonArray();

        int graphIndex = 0;
        for (Map<String, MSTResult> resultMap : results) {
            JsonObject graphResult = new JsonObject();
            graphResult.addProperty("graph_id", graphIndex++);

            // Prim's result
            if (resultMap.containsKey("prim")) {
                MSTResult primResult = resultMap.get("prim");
                JsonObject primObj = createResultObject(primResult);
                graphResult.add("prim", primObj);
            }

            // Kruskal's result
            if (resultMap.containsKey("kruskal")) {
                MSTResult kruskalResult = resultMap.get("kruskal");
                JsonObject kruskalObj = createResultObject(kruskalResult);
                graphResult.add("kruskal", kruskalObj);
            }

            // Comparison
            if (resultMap.containsKey("prim") && resultMap.containsKey("kruskal")) {
                MSTResult primResult = resultMap.get("prim");
                MSTResult kruskalResult = resultMap.get("kruskal");

                JsonObject comparison = new JsonObject();
                comparison.addProperty("costs_match",
                        primResult.getTotalCost() == kruskalResult.getTotalCost());
                comparison.addProperty("cost_difference",
                        Math.abs(primResult.getTotalCost() - kruskalResult.getTotalCost()));
                comparison.addProperty("time_difference_ms",
                        Math.abs(primResult.getExecutionTimeMs() - kruskalResult.getExecutionTimeMs()));
                comparison.addProperty("operation_difference",
                        Math.abs(primResult.getOperationCount() - kruskalResult.getOperationCount()));

                String fasterAlgorithm = primResult.getExecutionTimeMs() < kruskalResult.getExecutionTimeMs()
                        ? "Prim" : "Kruskal";
                comparison.addProperty("faster_algorithm", fasterAlgorithm);

                graphResult.add("comparison", comparison);
            }

            resultsArray.add(graphResult);
        }

        JsonObject output = new JsonObject();
        output.add("results", resultsArray);

        Files.write(Paths.get(filename),
                gson.toJson(output).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Results written to: " + filename);
    }

    /**
     * Creates a JSON object from an MSTResult.
     *
     * @param result The MST result
     * @return JsonObject representation
     */
    private static JsonObject createResultObject(MSTResult result) {
        JsonObject obj = new JsonObject();
        obj.addProperty("algorithm", result.getAlgorithmName());
        obj.addProperty("total_cost", result.getTotalCost());
        obj.addProperty("vertex_count", result.getVertexCount());
        obj.addProperty("original_edge_count", result.getOriginalEdgeCount());
        obj.addProperty("mst_edge_count", result.getMstEdges().size());
        obj.addProperty("operation_count", result.getOperationCount());
        obj.addProperty("execution_time_ms", result.getExecutionTimeMs());

        JsonArray edgesArray = new JsonArray();
        for (Edge edge : result.getMstEdges()) {
            JsonObject edgeObj = new JsonObject();
            edgeObj.addProperty("from", edge.getSource());
            edgeObj.addProperty("to", edge.getDestination());
            edgeObj.addProperty("cost", edge.getWeight());
            edgesArray.add(edgeObj);
        }
        obj.add("mst_edges", edgesArray);

        return obj;
    }

    /**
     * Writes results to a CSV file for easy comparison.
     *
     * @param results   List of MST results
     * @param filename  Output CSV filename
     * @throws IOException If file writing fails
     */
    public static void writeResultsToCSV(List<Map<String, MSTResult>> results,
                                         String filename) throws IOException {
        StringBuilder csv = new StringBuilder();
        csv.append("Graph_ID,Algorithm,Total_Cost,Vertices,Original_Edges,MST_Edges,")
                .append("Operations,Execution_Time_ms\n");

        int graphId = 0;
        for (Map<String, MSTResult> resultMap : results) {
            if (resultMap.containsKey("prim")) {
                MSTResult result = resultMap.get("prim");
                csv.append(String.format("%d,%s,%d,%d,%d,%d,%d,%.3f\n",
                        graphId, "Prim", result.getTotalCost(), result.getVertexCount(),
                        result.getOriginalEdgeCount(), result.getMstEdges().size(),
                        result.getOperationCount(), result.getExecutionTimeMs()));
            }

            if (resultMap.containsKey("kruskal")) {
                MSTResult result = resultMap.get("kruskal");
                csv.append(String.format("%d,%s,%d,%d,%d,%d,%d,%.3f\n",
                        graphId, "Kruskal", result.getTotalCost(), result.getVertexCount(),
                        result.getOriginalEdgeCount(), result.getMstEdges().size(),
                        result.getOperationCount(), result.getExecutionTimeMs()));
            }
            graphId++;
        }

        Files.write(Paths.get(filename), csv.toString().getBytes(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("CSV results written to: " + filename);
    }
}