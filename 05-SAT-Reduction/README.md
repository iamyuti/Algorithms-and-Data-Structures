# SAT Reduction

Implementation of **polynomial-time reduction** from the **Exact Cover** problem to **Boolean Satisfiability (SAT)**.

## Problem Statement

### Exact Cover
> Given a universe of elements U = {1, 2, ..., n} and a collection of sets S₁, S₂, ..., Sₘ,  
> select a sub-collection where **every element appears exactly once**.

### Example
```
Universe: {1, 2, 3, 4}
Sets:
  S₁ = {1, 2}
  S₂ = {2, 3}
  S₃ = {3, 4}
  S₄ = {1, 4}

Solution: {S₁, S₃} → covers {1,2} ∪ {3,4} = {1,2,3,4} ✓
```

## Reduction to SAT

### Variable Mapping
Each set Sᵢ corresponds to a boolean variable xᵢ:
- `xᵢ = true` → Set Sᵢ is selected
- `xᵢ = false` → Set Sᵢ is not selected

### CNF Clauses

Two types of constraints converted to **Conjunctive Normal Form (CNF)**:

| Constraint | Meaning | CNF Clause |
|------------|---------|------------|
| **At Least One** | Every element must be covered | `(x₁ ∨ x₂ ∨ ...)` for sets containing element |
| **At Most One** | No element covered twice | `(¬xᵢ ∨ ¬xⱼ)` for every pair covering same element |

### DIMACS Format Output

```
p cnf 4 7
1 4 0        ← Element 1 in S₁ or S₄
1 2 0        ← Element 2 in S₁ or S₂
2 3 0        ← Element 3 in S₂ or S₃
3 4 0        ← Element 4 in S₃ or S₄
-1 -2 0      ← S₁ and S₂ both contain element 2
...
```

## Usage

```java
ExactCoverReductor reducer = new ExactCoverReductor();

boolean[][] setMatrix = {
    {true, true, false, false},  // S₁ = {1, 2}
    {false, true, true, false},  // S₂ = {2, 3}
    {false, false, true, true},  // S₃ = {3, 4}
    {true, false, false, true}   // S₄ = {1, 4}
};

boolean[] chosenSets = new boolean[4];
boolean hasSolution = reducer.findExactCover(setMatrix, satSolver, chosenSets);
```

## Files

| File | Description |
|------|-------------|
| `ExactCoverReductor.java` | Reduction algorithm with DIMACS generation |
| `SatSolver.java` | Interface for SAT solver integration |

## Concepts Demonstrated

- **NP-Completeness**: Polynomial reduction between NP-complete problems
- **CNF Transformation**: Boolean constraints to DIMACS format
- **Problem Solving**: Using SAT solvers for combinatorial problems
- **Theoretical CS**: Practical application of complexity theory
