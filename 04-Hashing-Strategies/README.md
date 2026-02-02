# Hashing Strategies

Implementation of various hash table collision resolution strategies, including advanced optimization techniques.

## Collision Resolution Methods

| Category | Method | Description |
|----------|--------|-------------|
| Chaining | Separate Chaining | Linked lists at each bucket |
| Open Addressing | Linear Probing | `(h(k) + i) % m` |
| Open Addressing | Quadratic Probing | `(h(k) + 0.5i + 0.5i²) % m` |
| Open Addressing | Double Hashing | `(h₁(k) + i × h₂(k)) % m` |
| Advanced | Brent's Method | Optimizes placement during insertion |

## Algorithms

### Separate Chaining
Each bucket contains a linked list of elements.

```java
if (bucket is empty) {
    insert element
} else {
    prepend element to existing chain
}
```

### Open Addressing Probing

All elements stored directly in the table. On collision, probe for next available slot.

| Strategy | Formula | Clustering |
|----------|---------|------------|
| Linear | `(h(k) + i) % m` | Primary clustering |
| Quadratic | `(h(k) + 0.5i + 0.5i²) % m` | Reduced primary clustering |
| Double | `(h₁(k) + i × h₂(k)) % m` | Minimal clustering |

### Brent's Method (Advanced)

Optimizes **average search time** by potentially relocating existing elements during insertion.

```
Key Idea:
When inserting key K at position P (occupied by X):
- Compare: cost to move K forward vs. cost to move X forward
- If moving X is cheaper: swap K and X, then continue inserting X
```

This reduces the average probe length for lookups.

## Usage

```java
CollisionResolution cr = new CollisionResolution();

// Strategy Pattern: Pass probing strategy as lambda
cr.insert(hashTable, cr::probeLinear, key, capacity);
cr.insert(hashTable, cr::probeQuadratic, key, capacity);
cr.insert(hashTable, cr::probeDoubleHashing, key, capacity);

// Brent's optimized insertion
cr.insertBrentOptimized(hashTable, key, capacity);
```

## Files

| File | Description |
|------|-------------|
| `CollisionResolution.java` | All collision resolution algorithms |
| `HashInterfaces.java` | Supporting interfaces and classes |

## Concepts Demonstrated

- **Collision Handling**: Multiple strategies for hash collisions
- **Strategy Pattern**: Interchangeable probing algorithms
- **Optimization**: Brent's method for improved average-case performance
- **Trade-offs**: Space vs. time, clustering vs. complexity
