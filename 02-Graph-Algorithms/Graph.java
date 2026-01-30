/**
 * Interface defining the essential operations required for the Feedback Vertex Set solver.
 * Decouples the algorithm from specific graph libraries (like JGraphT).
 */
public interface Graph {

    /**
     * Returns all vertices currently in the graph.
     */
    int[] getVertices();

    /**
     * Returns the outgoing neighbors of a given vertex.
     * Essential for Depth-First Search (DFS).
     */
    int[] getSuccessors(int vertex);

    /**
     * Returns the number of incoming edges for a vertex.
     */
    int inDegree(int vertex);

    /**
     * Returns the number of outgoing edges for a vertex.
     */
    int outDegree(int vertex);

    /**
     * Removes a vertex and all incident edges from the graph.
     */
    void removeVertex(int vertex);
}