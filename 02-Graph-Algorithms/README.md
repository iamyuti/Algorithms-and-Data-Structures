# Graph Algorithms

Implementation of the **Feedback Vertex Set (FVS)** problem solver using greedy algorithms and multiple heuristic strategies.

## Problem Statement

> **Feedback Vertex Set**: Given a directed graph, find a minimal set of vertices whose removal makes the graph acyclic.

This is an NP-hard optimization problem with applications in:
- Deadlock detection and prevention
- Circuit design
- Dependency resolution

## Implementation

### Core Components

| Component | Purpose |
|-----------|---------|
| `FeedbackVertexSetSolver` | Main solver with DFS cycle detection and greedy removal |
| `Graph` | Interface abstracting graph operations |
| `Heuristic` | Functional interface for pluggable scoring strategies |

## Algorithms

### Cycle Detection (DFS)
Uses depth-first search with a recursion stack to detect back-edges.
- **Time**: O(V + E)
- **Space**: O(V)

### Greedy FVS Solver
Repeatedly removes the highest-scoring vertex until the graph becomes acyclic.

```
while graph has cycles:
    vertex = argmax(heuristic(v) for v in vertices)
    remove vertex from graph
    add vertex to feedback set
```

## Heuristic Strategies

Three interchangeable scoring functions (Strategy Pattern):

| Heuristic | Formula | Rationale |
|-----------|---------|-----------|
| Degree Sum | in + out | Prioritizes highly connected nodes |
| Degree Product | in × out | Targets "hub" nodes with flow through them |
| Balanced Flow | in + out − 0.3×\|in − out\| | Penalizes imbalanced nodes |

### Usage Example
```java
FeedbackVertexSetSolver solver = new FeedbackVertexSetSolver();

// Use any heuristic via method reference
solver.solve(graph, solver::scoreByDegreeProduct, resultArray);
```

## Files

| File | Description |
|------|-------------|
| `FeedbackVertexSetSolver.java` | DFS cycle detection + greedy solver |
| `Graph.java` | Graph interface (decoupled from implementation) |
| `Heuristic.java` | Functional interface for heuristics |

## Concepts Demonstrated

- **Graph Traversal**: DFS with cycle detection
- **Greedy Algorithms**: Heuristic-driven optimization
- **Design Patterns**: Strategy Pattern via functional interfaces
- **Interface Segregation**: Clean abstraction of graph operations
