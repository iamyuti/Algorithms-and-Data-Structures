# Tree Algorithms

Efficient algorithms for reconstructing binary trees from traversal sequences.

## Problem Statement

> Given **InOrder** and **PreOrder** (or **PostOrder**) traversals of a binary tree, reconstruct the original tree structure.

## Implementation

### Supported Reconstructions

| Input Traversals | Output |
|------------------|--------|
| InOrder + PreOrder | Implicit Array Tree |
| InOrder + PostOrder | Implicit Array Tree |

## Algorithm

### Key Insight

- **PreOrder**: Root is always the **first** element
- **PostOrder**: Root is always the **last** element
- **InOrder**: Elements left of root → left subtree, right of root → right subtree

### Optimization: HashMap Lookup

Instead of linear search for root position in InOrder array:
```java
// O(1) lookup instead of O(n) search
Map<Integer, Integer> inOrderMap = new HashMap<>();
for (int i = 0; i < inOrder.length; i++) {
    inOrderMap.put(inOrder[i], i);
}
```

### Complexity

| Metric | Value |
|--------|-------|
| Time | O(n) |
| Space | O(n) |

## Output Format

The reconstructed tree is stored in **implicit array representation** (heap-style):

```
Index i → Left child at 2*i + 1
       → Right child at 2*i + 2
```

### Example

```
      1
     / \
    2   3
   / \
  4   5

Array: [1, 2, 3, 4, 5, _, _]
Index:  0  1  2  3  4  5  6
```

## Usage

```java
TreeReconstruction tr = new TreeReconstruction();

int[] inOrder  = {4, 2, 5, 1, 3};
int[] preOrder = {1, 2, 4, 5, 3};
int[] tree = new int[15]; // Allocate for heap structure

tr.buildFromPreOrder(inOrder, preOrder, tree);
// tree[0] = 1 (root), tree[1] = 2, tree[2] = 3, ...
```

## Files

| File | Description |
|------|-------------|
| `TreeReconstruction.java` | PreOrder and PostOrder reconstruction |

## Concepts Demonstrated

- **Divide and Conquer**: Recursive subtree construction
- **HashMap Optimization**: O(1) index lookup
- **Implicit Tree Representation**: Array-based binary tree storage
