import java.util.ArrayList;
import java.util.List;

/**
 * Solves the "Feedback Vertex Set" problem using a Greedy approach.
 * The goal is to find a minimal set of vertices to remove so that the graph becomes acyclic.
 * Uses Depth-First Search (DFS) for cycle detection.
 */
public class FeedbackVertexSetSolver {

    /**
     * Depth-First Search (DFS) to detect cycles in a directed graph.
     * * @param vertex  Current vertex ID
     * @param visited Array tracking visited vertices in general
     * @param stack   Recursion stack to detect back-edges (cycles in current path)
     * @param g       The Graph structure
     * @return true if a cycle is found, false otherwise.
     */
    private boolean dfs(int vertex, boolean[] visited, boolean[] stack, Graph g) {
        visited[vertex] = true;
        stack[vertex] = true;

        int[] successors = g.getSuccessors(vertex);

        if (successors != null) {
            for (int neighbor : successors) {
                if (!visited[neighbor]) {
                    // Recursive call for unvisited neighbors
                    if (dfs(neighbor, visited, stack, g)) {
                        return true;
                    }
                } else if (stack[neighbor]) {
                    // If neighbor is in current recursion stack -> Cycle detected
                    return true;
                }
            }
        }

        stack[vertex] = false; // Backtrack
        return false;
    }

    /**
     * Checks if the entire graph contains any cycles.
     * Iterates through all components of the graph (in case it's disconnected).
     */
    public boolean isAcyclic(Graph g) {
        int[] vertices = g.getVertices();
        if (vertices == null || vertices.length == 0) {
            return true;
        }

        // Determine array size based on max ID
        int maxIDNum = Integer.MIN_VALUE;
        for (int vertex : vertices) {
            maxIDNum = Math.max(vertex, maxIDNum);
        }

        boolean[] visited = new boolean[maxIDNum + 1];
        boolean[] stack = new boolean[maxIDNum + 1];

        for (int vertex : vertices) {
            if (!visited[vertex]) {
                if (dfs(vertex, visited, stack, g)) {
                    return false; // Cycle found
                }
            }
        }
        return true;
    }

    // --- Heuristics Strategies ---

    /**
     * Heuristic 1: Sum of In-Degree and Out-Degree.
     * Prioritizes nodes that are highly connected generally.
     */
    public double scoreByDegreeSum(Graph g, int vertex) {
        int inDeg = g.inDegree(vertex);
        int outDeg = g.outDegree(vertex);

        if (inDeg == -1 || outDeg == -1) return -1;
        return inDeg + outDeg;
    }

    /**
     * Heuristic 2: Product of In-Degree and Out-Degree.
     * Prioritizes nodes that act as "hubs" (flow passing through).
     */
    public double scoreByDegreeProduct(Graph g, int vertex) {
        int inDeg = g.inDegree(vertex);
        int outDeg = g.outDegree(vertex);

        if (inDeg == -1 || outDeg == -1) return -1;
        return inDeg * outDeg;
    }

    /**
     * Heuristic 3: Balanced Flow.
     * Penalizes nodes where In-Degree and Out-Degree are very different.
     */
    public double scoreByBalancedFlow(Graph g, int vertex) {
        int inDeg = g.inDegree(vertex);
        int outDeg = g.outDegree(vertex);

        if (inDeg == -1 || outDeg == -1) return -1;
        // Logic: Total degree minus a penalty for imbalance
        return inDeg + outDeg - 0.3 * Math.abs(inDeg - outDeg);
    }

    // --- Main Solver ---

    /**
     * Executes the Greedy Algorithm.
     * Repeatedly removes the node with the highest heuristic score until the graph is acyclic.
     * * @param g The graph to process (will be modified!)
     * @param h The heuristic strategy to use (Functional Interface)
     * @param feedbackVertexSet Array to store the result
     */
    public void solve(Graph g, Heuristic h, int[] feedbackVertexSet) {
        List<Integer> currentVertices = new ArrayList<>();
        for (int vertex : g.getVertices()) {
            currentVertices.add(vertex);
        }

        int index = 0;

        while (!isAcyclic(g)) {
            int vertexToDelete = -1;
            double maxHeuristic = Double.NEGATIVE_INFINITY; // Better than Integer.MIN_VALUE for doubles

            // Find best candidate to remove
            for (int vertex : currentVertices) {
                double heuristicValue = h.eval(g, vertex);
                if (heuristicValue > maxHeuristic) {
                    maxHeuristic = heuristicValue;
                    vertexToDelete = vertex;
                }
            }

            // Remove candidate and update graph
            if (vertexToDelete != -1) {
                currentVertices.remove(Integer.valueOf(vertexToDelete));
                feedbackVertexSet[index] = vertexToDelete;
                index++;
                g.removeVertex(vertexToDelete);
            }
        }
    }
}