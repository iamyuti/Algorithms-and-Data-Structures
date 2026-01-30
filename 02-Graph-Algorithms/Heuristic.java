/**
 * Functional interface for defining heuristic strategies.
 * Used to score vertices to determine which one should be removed next.
 */
public interface Heuristic {

    /**
     * Evaluates a vertex based on a specific heuristic logic.
     * @param g The graph context.
     * @param vertex The vertex to evaluate.
     * @return A score (higher means more likely to be removed).
     */
    double eval(Graph g, int vertex);
}