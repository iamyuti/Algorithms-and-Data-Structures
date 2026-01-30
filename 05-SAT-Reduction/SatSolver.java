/**
 * Interface for a boolean satisfiability solver.
 * Expects input in DIMACS CNF format.
 */
public interface SatSolver {
    /**
     * Solves the given boolean formula.
     * @param dimacsInput The problem description in DIMACS format.
     * @return A string containing the satisfying assignment (literals separated by spaces),
     * or an empty string/error message if unsatisfiable.
     */
    String solve(String dimacsInput);
}