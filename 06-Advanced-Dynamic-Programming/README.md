# Advanced Dynamic Programming

Implementation of the **Constrained Longest Common Subsequence (CLCS)** problem using 3-dimensional dynamic programming.

## Problem Statement

> Find the **longest common subsequence** of two strings `s1` and `s2` that **must contain** a constraint pattern `P` as a subsequence.

### Example
```
s1 = "ABCDEF"
s2 = "AEBDCF"
P  = "BD"

Standard LCS: "ABCDF" (length 5)
Constrained LCS: "ABDF" (length 4, contains "BD")
```

## Algorithm

### 3D Dynamic Programming Table

| Dimension | Represents |
|-----------|------------|
| `k` | Characters of pattern P matched so far (0 to \|P\|) |
| `i` | Position in string s1 (0 to \|s1\|) |
| `j` | Position in string s2 (0 to \|s2\|) |

```
dp[k][i][j] = length of longest common subsequence using
              s1[0..i-1] and s2[0..j-1] that contains P[0..k-1]
```

### Transition Rules

```java
if (s1[i-1] == s2[j-1]) {
    if (current_char == P[k-1]) {
        // Advance both LCS and constraint
        dp[k][i][j] = dp[k-1][i-1][j-1] + 1
    } else {
        // Regular LCS extension
        dp[k][i][j] = dp[k][i-1][j-1] + 1
    }
}
// Always consider skipping characters
dp[k][i][j] = max(dp[k][i][j], dp[k][i-1][j], dp[k][i][j-1])
```

### Complexity

| Metric | Value |
|--------|-------|
| Time | O(n × m × k) |
| Space | O(n × m × k) |

Where n = \|s1\|, m = \|s2\|, k = \|P\|

## Features

| Method | Purpose |
|--------|---------|
| `computeDynamicProgrammingTable()` | Builds the 3D DP table |
| `backtrackingCLCS()` | Reconstructs the optimal solution |
| `isFeasible()` | Validates a solution |

## Usage

```java
ConstrainedLCSSolver solver = new ConstrainedLCSSolver();

char[] s1 = "ABCDEF".toCharArray();
char[] s2 = "AEBDCF".toCharArray();
char[] pattern = "BD".toCharArray();

// Step 1: Build DP table
int[][][] dp = solver.computeDynamicProgrammingTable(s1, s2, pattern);

// Step 2: Backtrack to find solution
char[] result = solver.backtrackingCLCS(s1, s2, pattern, dp);

// Step 3: Verify solution
boolean valid = solver.isFeasible(s1, s2, pattern, result);
```

## Files

| File | Description |
|------|-------------|
| `ConstrainedLCSSolver.java` | Complete CLCS implementation |

## Concepts Demonstrated

- **Multi-dimensional DP**: 3D state space with careful initialization
- **State Transitions**: Complex dependency between constraint and LCS progress
- **Backtracking**: Reconstructing the optimal solution from DP table
- **Subsequence Theory**: Combining multiple subsequence constraints
