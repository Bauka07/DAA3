package utils;

import graph.Graph;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Utility class for reading graph data from JSON files.
 */
public class JsonReader {

    /**
     * Reads multiple graphs from a JSON file.
     *
     * @param filename Path to the JSON file
     * @return List of Graph objects
     * @throws IOException If file reading fails
     */
    public static List<Graph> readGraphsFromJson(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(content, JsonObject.class);
        JsonArray graphsArray = jsonObject.getAsJsonArray("graphs");

        List<Graph> graphs = new ArrayList<>();

        for (JsonElement graphElement : graphsArray) {
            JsonObject graphObj = graphElement.getAsJsonObject();

            String name = graphObj.get("name").getAsString();
            int vertices = graphObj.get("vertices").getAsInt();
            JsonArray edgesArray = graphObj.getAsJsonArray("edges");

            Graph graph = new Graph(vertices);

            for (JsonElement edgeElement : edgesArray) {
                JsonObject edgeObj = edgeElement.getAsJsonObject();
                int from = edgeObj.get("from").getAsInt();
                int to = edgeObj.get("to").getAsInt();
                int cost = edgeObj.get("cost").getAsInt();

                graph.addEdge(from, to, cost);
            }

            graphs.add(graph);
            System.out.println("Loaded graph: " + name + " (" + vertices +
                    " vertices, " + graph.getEdgeCount() + " edges)");
        }

        return graphs;
    }

    /**
     * Reads a single graph from a JSON file.
     *
     * @param filename Path to the JSON file
     * @return Graph object
     * @throws IOException If file reading fails
     */
    public static Graph readSingleGraphFromJson(String filename) throws IOException {
        List<Graph> graphs = readGraphsFromJson(filename);
        return graphs.isEmpty() ? null : graphs.get(0);
    }
}