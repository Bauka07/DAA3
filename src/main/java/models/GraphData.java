package models;

/**
 * Data class for storing graph metadata from JSON input.
 * Optional helper class for JSON deserialization.
 */
public class GraphData {
    private String name;
    private String description;
    private int vertices;
    private EdgeData[] edges;

    public GraphData() {
    }

    public GraphData(String name, String description, int vertices, EdgeData[] edges) {
        this.name = name;
        this.description = description;
        this.vertices = vertices;
        this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVertices() {
        return vertices;
    }

    public void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public EdgeData[] getEdges() {
        return edges;
    }

    public void setEdges(EdgeData[] edges) {
        this.edges = edges;
    }

    /**
     * Inner class representing edge data from JSON.
     */
    public static class EdgeData {
        private int from;
        private int to;
        private int cost;

        public EdgeData() {
        }

        public EdgeData(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
    }
}