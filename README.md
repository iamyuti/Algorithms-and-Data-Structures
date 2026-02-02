# Algorithms & Data Structures

A comprehensive collection of algorithm implementations in Java, showcasing fundamental computer science concepts and advanced problem-solving techniques.

## Overview

This repository demonstrates proficiency in algorithm design and implementation, covering topics from dynamic programming to NP-complete problem reductions.

| Module | Topic | Key Algorithms |
|--------|-------|----------------|
| [01-Basics-And-Dynamic-Programming](./01-Basics-And-Dynamic-Programming) | Fundamentals & DP | Subset Sum, Closest Pair |
| [02-Graph-Algorithms](./02-Graph-Algorithms) | Graph Theory | Feedback Vertex Set, DFS |
| [03-Tree-Algorithms](./03-Tree-Algorithms) | Tree Structures | Binary Tree Reconstruction |
| [04-Hashing-Strategies](./04-Hashing-Strategies) | Hash Tables | Collision Resolution, Brent's Method |
| [05-SAT-Reduction](./05-SAT-Reduction) | NP-Completeness | Exact Cover → SAT Reduction |
| [06-Advanced-Dynamic-Programming](./06-Advanced-Dynamic-Programming) | 3D Dynamic Programming | Constrained LCS |

## Highlights

### Dynamic Programming
- **Subset Sum Problem**: Space-optimized O(n × sum) solution
- **Constrained LCS**: 3D DP approach with backtracking reconstruction

### Graph Algorithms
- **Feedback Vertex Set**: Greedy heuristics with pluggable scoring strategies (Strategy Pattern)
- **Cycle Detection**: DFS-based with recursion stack tracking

### Complexity Theory
- **Polynomial-Time Reduction**: Exact Cover to SAT (DIMACS CNF format)

### Hash Tables
- **Collision Resolution**: Linear, Quadratic, Double Hashing, and Brent's Method

## Tech Stack

- **Language**: Java
- **Paradigms**: Object-Oriented Design, Functional Interfaces, Strategy Pattern
- **Complexity Analysis**: Time and space complexity documented for all algorithms

## Getting Started

Each module is self-contained and can be compiled independently:

```bash
cd 01-Basics-And-Dynamic-Programming
javac AlgorithmCollection.java
```
