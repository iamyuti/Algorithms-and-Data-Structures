/**
 * Solves the Exact Cover problem via Polynomial-Time Reduction to SAT (Boolean Satisfiability).
 * * Problem: Given a universe of elements and a collection of sets, select a sub-collection
 * such that every element appears exactly once.
 * * Approach:
 * 1. Transform the input into a CNF boolean formula (DIMACS format).
 * 2. Feed the formula into a SAT Solver.
 * 3. Interpret the SAT Solver's result to identify the chosen sets.
 */
public class ExactCoverReductor {

    /**
     * Tries to find an exact cover for the given sets.
     * * @param setMatrix A boolean matrix where setMatrix[i][j] is true if Set i contains Element j.
     * @param solver The external SAT solver to use.
     * @param chosenSets Output array. After execution, chosenSets[i] will be true if Set i is part of the solution.
     * @return true if an exact cover exists, false otherwise.
     */
    public boolean findExactCover(boolean[][] setMatrix, SatSolver solver, boolean[] chosenSets) {
        if (setMatrix == null || setMatrix.length == 0) {
            return false;
        }

        int numSets = setMatrix.length;       // Corresponds to SAT Variables
        int numElements = setMatrix[0].length; // Corresponds to Constraints

        // Step 1: Reduce Exact Cover to SAT (generate CNF clauses)
        String dimacsInput = generateDimacsCNF(setMatrix, numElements, numSets);

        if (dimacsInput.isEmpty()) {
            return false; // Constraint generation failed (e.g., impossible configuration)
        }

        // Step 2: Solve using external SAT Solver
        String result = solver.solve(dimacsInput);

        if (result == null || result.isEmpty() || result.startsWith("Parsing error")) {
            return false; // Unsatisfiable
        }

        // Step 3: Map SAT result back to Exact Cover solution
        // Reset output array
        for (int i = 0; i < chosenSets.length; i++) {
            chosenSets[i] = false;
        }

        // Parse literals (e.g., "1 -2 3 0")
        String[] literals = result.trim().split("\\s+");
        for (String literal : literals) {
            try {
                int value = Integer.parseInt(literal);
                // DIMACS format: "0" marks end of line, ignore it
                if (value != 0) {
                    // Positive literal (e.g., "5") means Set 5 is chosen.
                    // Negative literal (e.g., "-2") means Set 2 is NOT chosen.
                    if (value > 0) {
                        chosenSets[value - 1] = true; // Adjust 1-based index to 0-based
                    }
                }
            } catch (NumberFormatException e) {
                // Ignore non-integer tokens
            }
        }
        return true;
    }

    /**
     * Generates the DIMACS CNF string.
     * Constraints:
     * 1. "At least one": Every element must be in at least one chosen set.
     * 2. "At most one": No element can be in more than one chosen set (Pairwise exclusion).
     */
    private String generateDimacsCNF(boolean[][] setMatrix, int numElements, int numSets) {
        StringBuilder dimacs = new StringBuilder();

        // Header: p cnf <vars> <clauses>
        dimacs.append(String.format("p cnf %d %d\n", numSets, calculateTotalClauses(setMatrix, numElements, numSets)));

        // Constraint 1: At LEAST one set must cover element u
        for (int u = 0; u < numElements; u++) {
            StringBuilder clause = new StringBuilder();
            boolean hasLiterals = false;

            for (int i = 0; i < numSets; i++) {
                if (setMatrix[i][u]) {
                    clause.append(i + 1).append(" "); // Variable index (1-based)
                    hasLiterals = true;
                }
            }

            if (hasLiterals) {
                clause.append("0\n"); // End of clause
                dimacs.append(clause);
            } else {
                return ""; // Impossible: Element u is not in ANY set
            }
        }

        // Constraint 2: At MOST one set can cover element u
        // For every pair of sets (i, j) that both contain element u, add clause (-i OR -j)
        for (int u = 0; u < numElements; u++) {
            for (int i = 0; i < numSets; i++) {
                if (setMatrix[i][u]) {
                    for (int j = i + 1; j < numSets; j++) {
                        if (setMatrix[j][u]) {
                            // Mutually exclusive: NOT (i AND j)  <=>  (NOT i OR NOT j)
                            dimacs.append(-(i + 1)).append(" ").append(-(j + 1)).append(" 0\n");
                        }
                    }
                }
            }
        }
        return dimacs.toString();
    }

    /**
     * Helper to pre-calculate the number of clauses for the DIMACS header.
     */
    private int calculateTotalClauses(boolean[][] setMatrix, int numElements, int numSets) {
        int numClauses = numElements; // Start with "At least one" clauses (1 per element)

        // Add "At most one" clauses (n*(n-1)/2 for overlapping sets)
        for (int u = 0; u < numElements; u++) {
            int setsContainingElement = 0;
            for (int i = 0; i < numSets; i++) {
                if (setMatrix[i][u]) {
                    setsContainingElement++;
                }
            }
            // Pairwise combinations: k over 2
            numClauses += (setsContainingElement * (setsContainingElement - 1)) / 2;
        }
        return numClauses;
    }
}