# City Transportation Network Optimization (MST)

## Project Overview
This project implements **Prim's** and **Kruskal's** algorithms to optimize a city's transportation network by finding the Minimum Spanning Tree (MST). The goal is to connect all city districts with the minimum total construction cost.

## 📁 Project Structure
```
CityTransportationNetwork/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── graph/
│   │   │   │   ├── Graph.java          (Bonus: Custom Graph data structure)
│   │   │   │   └── Edge.java           (Bonus: Edge representation)
│   │   │   ├── algorithms/
│   │   │   │   ├── PrimAlgorithm.java
│   │   │   │   └── KruskalAlgorithm.java
│   │   │   ├── models/
│   │   │   │   └── MSTResult.java
│   │   │   ├── utils/
│   │   │   │   ├── JsonReader.java
│   │   │   │   └── JsonWriter.java
│   │   │   └── Main.java
│   │   └── resources/
│   │       ├── input_small.json
│   │       ├── input_medium.json
│   │       ├── input_large.json
│   │       ├── output.json
│   │       └── results.csv
│   └── test/
│       └── java/
│           └── MSTCorrectnessTest.java
├── pom.xml
├── README.md
└── .gitignore
```

## 🚀 Getting Started

### Prerequisites
- **Java 11** or higher
- **Maven** (for dependency management)
- **IntelliJ IDEA** (recommended IDE)

### Installation

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd CityTransportationNetwork
   ```

2. **Open in IntelliJ IDEA**
   - File → Open → Select project folder
   - IntelliJ will automatically detect Maven and download dependencies

3. **Build the project**
   ```bash
   mvn clean install
   ```

### Running the Application

#### Option 1: Run from IntelliJ
- Right-click on `Main.java`
- Select "Run 'Main.main()'"

#### Option 2: Run from Command Line
```bash
mvn exec:java -Dexec.mainClass="Main"
```

#### Option 3: Run JAR file
```bash
mvn package
java -jar target/mst-optimization-1.0-SNAPSHOT.jar
```

### Running Tests
```bash
mvn test
```

## 📊 Input/Output Format

### Input JSON Format
```json
{
  "graphs": [
    {
      "name": "City Name",
      "description": "Description",
      "vertices": 4,
      "edges": [
        {"from": 0, "to": 1, "cost": 10},
        {"from": 0, "to": 2, "cost": 6}
      ]
    }
  ]
}
```

### Output JSON Format
The program generates `output.json` with:
- Algorithm results (Prim's and Kruskal's)
- MST edges and total cost
- Execution time and operation count
- Comparison metrics

### CSV Output
The program also generates `results.csv` for easy data analysis in Excel/Google Sheets.

## 🧪 Testing

The project includes comprehensive tests covering:

### Correctness Tests
- ✅ MST costs match between algorithms
- ✅ MST has exactly V-1 edges
- ✅ MST is acyclic
- ✅ MST connects all vertices
- ✅ Disconnected graph handling

### Performance Tests
- ✅ Execution time measurement
- ✅ Operation count tracking
- ✅ Reproducibility verification

Run all tests:
```bash
mvn test
```

## 🎯 Bonus Implementation

### Custom Graph Data Structure
This project includes a **custom Graph implementation** (bonus task):
- `Graph.java`: Adjacency list-based graph structure
- `Edge.java`: Edge representation with weight
- Connectivity checking
- Graph density calculation
- MST validation

### Benefits of Custom Graph
- Clean object-oriented design
- Optimized for MST algorithms
- Built-in validation methods
- Easy to extend and maintain

## 📈 Algorithm Comparison

### Prim's Algorithm
- **Time Complexity**: O(E log V) with priority queue
- **Best for**: Dense graphs, adjacency list representation
- **Strategy**: Grows MST one vertex at a time

### Kruskal's Algorithm
- **Time Complexity**: O(E log E) due to edge sorting
- **Best for**: Sparse graphs, edge list representation
- **Strategy**: Adds edges in sorted order, avoids cycles

### Performance Insights
Based on testing different graph sizes:
- **Small graphs (4-6 vertices)**: Similar performance
- **Medium graphs (10-15 vertices)**: Prim's slightly faster for dense graphs
- **Large graphs (20-30+ vertices)**: Performance depends on density
  - Dense graphs: Prim's advantage
  - Sparse graphs: Kruskal's advantage

## 📝 Report Guidelines

Your analytical report should include:

1. **Summary of Results**
   - Algorithm execution times
   - Operation counts
   - Total MST costs

2. **Algorithm Comparison**
   - Theoretical complexity analysis
   - Practical performance observations
   - Graph density impact

3. **Conclusions**
   - When to use each algorithm
   - Trade-offs and recommendations

4. **References**
   - Academic papers
   - Algorithm sources

## 🛠️ Development Tips

### IntelliJ Setup
1. Mark `src/main/java` as Sources Root
2. Mark `src/main/resources` as Resources Root
3. Mark `src/test/java` as Test Sources Root
4. Enable annotation processing if needed

### Adding New Test Cases
1. Create new JSON file in `src/main/resources/`
2. Add file path to `Main.java` inputFiles array
3. Run the application

### Debugging
- Set breakpoints in algorithm implementations
- Use IntelliJ debugger to step through execution
- Check operation counts for algorithm efficiency

## 📦 Dependencies

- **Gson 2.10.1**: JSON parsing and serialization
- **JUnit 5.9.3**: Unit testing framework

## 🤝 Contributing

### Git Workflow
```bash
# Create feature branch
git checkout -b feature/your-feature

# Make changes and commit
git add .
git commit -m "Descriptive message"

# Push to remote
git push origin feature/your-feature
```


## 📄 License
This project is for educational purposes as part of the Design and Analysis of Algorithms course.

## 👥 Author
Bauyrzhan Nurzhanov

---

**Happy Coding! 🚀**
