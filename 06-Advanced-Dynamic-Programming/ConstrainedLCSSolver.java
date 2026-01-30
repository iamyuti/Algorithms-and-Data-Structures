import java.util.Arrays;

/**
 * Solves the Constrained Longest Common Subsequence (CLCS) problem.
 * Finds the longest common subsequence of two strings (s1, s2) that must also
 * contain a specific constraint pattern (P) as a subsequence.
 * * Approach: 3-Dimensional Dynamic Programming.
 * Time Complexity: O(n * m * k) where n, m are string lengths and k is pattern length.
 */
public class ConstrainedLCSSolver {

    /**
     * Checks if a given solution is valid.
     * 1. Must be a subsequence of s1 and s2.
     * 2. Must contain the constraint pattern as a subsequence.
     */
    public boolean isFeasible(char[] s1, char[] s2, char[] constraint, char[] solution) {
        // Check if solution is subsequence of s1 and s2
        if (!isSubsequence(solution, s1) || !isSubsequence(solution, s2)) {
            return false;
        }

        // Check if constraint is a subsequence of solution
        return isSubsequence(constraint, solution);
    }

    /**
     * Helper: Checks if 'potentialSub' is a subsequence of 'text'.
     */
    private boolean isSubsequence(char[] potentialSub, char[] text) {
        int i = 0; // index for potentialSub
        int j = 0; // index for text
        while (i < potentialSub.length && j < text.length) {
            if (potentialSub[i] == text[j]) {
                i++;
            }
            j++;
        }
        return i == potentialSub.length;
    }

    /**
     * Computes the 3D Dynamic Programming table.
     * dimensions: [k (pattern index)][i (s1 index)][j (s2 index)]
     */
    public int[][][] computeDynamicProgrammingTable(char[] s1, char[] s2, char[] constraint) {
        int n = s1.length;
        int m = s2.length;
        int r = constraint.length;

        // DP table initialized with Integer.MIN_VALUE to represent unreachable states
        int[][][] dp = new int[r + 1][n + 1][m + 1];

        // Initialize layer k=0 (base LCS without constraint requirement)
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[0][i][j] = 0; // Base case: 0 length constraint satisfied
            }
        }

        // Initialize other layers with MIN_VALUE
        for (int k = 1; k <= r; k++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= m; j++) {
                    dp[k][i][j] = Integer.MIN_VALUE;
                }
            }
        }

        // Build the table
        for (int k = 0; k <= r; k++) {
            for (int i = 0; i <= n; i++) {
                for (int j = 0; j <= m; j++) {
                    if (i == 0 || j == 0) {
                        // Already handled by initialization, but kept for logic clarity
                        continue;
                    }

                    int val = Integer.MIN_VALUE;

                    if (s1[i - 1] == s2[j - 1]) {
                        char currentChar = s1[i - 1];

                        // Case 1: The current char matches the constraint character we need next
                        if (k > 0 && currentChar == constraint[k - 1]) {
                            int prev = dp[k - 1][i - 1][j - 1];
                            if (prev != Integer.MIN_VALUE) {
                                val = prev + 1;
                            }
                        }
                        // Case 2: Standard LCS extension (stay in same k-layer)
                        else {
                            int prev = dp[k][i - 1][j - 1];
                            if (prev != Integer.MIN_VALUE) {
                                val = prev + 1;
                            }
                        }
                    }

                    // Maximize with omitting characters from s1 or s2
                    int maxPrev = Math.max(dp[k][i - 1][j], dp[k][i][j - 1]);

                    dp[k][i][j] = Math.max(val, maxPrev);
                }
            }
        }
        return dp;
    }

    /**
     * Backtracks to find the optimal string from the DP table.
     */
    public char[] backtrackingCLCS(char[] s1, char[] s2, char[] constraint, int[][][] dp) {
        StringBuilder reversedSolution = new StringBuilder();
        int i = s1.length;
        int j = s2.length;
        int k = constraint.length;

        // If the final state is unreachable, no solution exists
        if (dp[k][i][j] < 0) {
            return new char[0];
        }

        while (i > 0 && j > 0) {
            // Case: Match found
            if (s1[i - 1] == s2[j - 1]) {
                // Did this match advance the constraint level?
                if (k > 0 && s1[i - 1] == constraint[k - 1] &&
                        dp[k][i][j] == dp[k - 1][i - 1][j - 1] + 1) {

                    reversedSolution.append(s1[i - 1]);
                    i--; j--; k--;
                }
                // Or was it a regular LCS match?
                else if (dp[k][i][j] == dp[k][i - 1][j - 1] + 1) {
                    reversedSolution.append(s1[i - 1]);
                    i--; j--;
                }
                // Movement logic if not a match used for score
                else if (dp[k][i][j] == dp[k][i - 1][j]) {
                    i--;
                } else {
                    j--;
                }
            }
            // Case: No match, move in direction of max score
            else if (dp[k][i][j] == dp[k][i - 1][j]) {
                i--;
            } else {
                j--;
            }
        }

        return reversedSolution.reverse().toString().toCharArray();
    }
}