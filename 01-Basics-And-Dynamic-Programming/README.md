# Basics & Dynamic Programming

A collection of fundamental algorithmic problems demonstrating core programming concepts and dynamic programming techniques.

## Contents

| Algorithm | Complexity | Technique |
|-----------|------------|-----------|
| Find Maximum | O(n) | Linear Scan |
| Closest Pair (Brute Force) | O(n²) | Exhaustive Search |
| Subset Sum | O(n × sum) | Dynamic Programming |

## Algorithms

### Find Maximum
Simple linear scan to find the maximum element in an array.
- **Time**: O(n)
- **Space**: O(1)

### Closest Pair of Points
Brute-force approach to find the two closest points in a 2D plane.
- **Time**: O(n²)
- **Space**: O(1)
- Uses squared Euclidean distance to avoid expensive `sqrt()` operations

### Subset Sum (Dynamic Programming)
Determines whether any subset of numbers adds up to a target sum.

```
Strategy: Boolean DP array where index i is true if sum i is achievable
```

- **Time**: O(n × targetSum)
- **Space**: O(targetSum)
- Iterates backwards to prevent using the same element multiple times

#### Example
```java
AlgorithmCollection algo = new AlgorithmCollection();
int[] numbers = {3, 34, 4, 12, 5, 2};
boolean exists = algo.hasSubsetSum(9, numbers); // true (3 + 4 + 2 = 9)
```

## Files

| File | Description |
|------|-------------|
| `AlgorithmCollection.java` | All algorithm implementations |

## Concepts Demonstrated

- **Brute Force**: Exhaustive search for optimization problems
- **Dynamic Programming**: Bottom-up tabulation approach
- **Space Optimization**: 1D DP array instead of 2D table
