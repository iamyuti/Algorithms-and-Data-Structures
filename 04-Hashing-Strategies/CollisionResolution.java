/**
 * Demonstrates various Collision Resolution strategies for Hash Tables.
 * Includes implementation of Open Addressing (Linear, Quadratic, Double Hashing)
 * and the advanced Brent's Method for optimization.
 */
public class CollisionResolution {

    // --- Part 1: Chaining ---

    /**
     * Inserts an element using Separate Chaining (Linked List at bucket index).
     * Handles collisions by appending the new element to the existing chain.
     */
    public void insertChaining(HashTableWithChaining table, ChainElement element, int capacity) {
        int key = element.getKey();
        int position = key % capacity;

        if (table.containsNoChainElement(position)) {
            table.insertChainElement(element, position);
        } else {
            // Collision: Link new element to the existing head
            ChainElement currentHead = table.get(position);
            element.setNext(currentHead);
            table.replaceChainElement(element, position);
        }
    }

    // --- Part 2: Probing Strategies ---

    /**
     * Linear Probing Strategy.
     * Formula: (h(k) + i) % m
     */
    public int probeLinear(int key, int i, int capacity) {
        return ((key % capacity) + i) % capacity;
    }

    /**
     * Quadratic Probing Strategy.
     * Formula: (h(k) + 0.5*i + 0.5*i^2) % m
     * Reduces primary clustering compared to linear probing.
     */
    public int probeQuadratic(int key, int i, int capacity) {
        return (int) ((key % capacity) + 0.5 * i + 0.5 * i * i) % capacity;
    }

    /**
     * Double Hashing Strategy.
     * Uses a second hash function to calculate the step size.
     * Formula: (h1(k) + i * h2(k)) % m
     */
    public int probeDoubleHashing(int key, int i, int capacity) {
        int h1 = key % capacity;
        int h2 = 1 + (key % 5); // Secondary hash function (must be non-zero)
        return (h1 + i * h2) % capacity;
    }

    /**
     * Generic Insert method using a provided Probing Strategy (Strategy Pattern).
     */
    public void insert(HashTable table, Probe probeStrategy, int key, int capacity) {
        for (int i = 0; i < capacity; i++) {
            int position = probeStrategy.evaluate(key, i);

            if (table.isFree(position)) {
                table.insert(key, position);
                return;
            } else if (table.get(position) == key) {
                table.replace(key, position); // Update/Overwrite
                return;
            }
        }
        // Table is full or probe sequence failed
    }

    // --- Part 3: Advanced Optimization ---

    /**
     * Brent's Method for insertion.
     * Optimizes average search time by moving existing elements during insertion
     * if moving them requires fewer total steps than placing the new element at the end of its probe sequence.
     */
    public void insertBrentOptimized(HashTable table, int key, int capacity) {
        int j = key % capacity;
        int i = 0;

        // Search for a free slot or optimization opportunity
        while (i < capacity && !table.isFree(j)) {
            int existingKey = table.get(j);

            // Calculate next steps for the NEW key
            int stepNew = 1 + (key % 5);
            int nextPosNew = (j + stepNew) % capacity;

            // Calculate next steps for the EXISTING key
            int stepExisting = 1 + (existingKey % 5);
            int nextPosExisting = (j + stepExisting) % capacity;

            // Brent's Optimization Logic:
            // Check if moving the existing key to its next position is better
            // than moving the new key to its next position.
            if (table.isFree(nextPosNew) || !table.isFree(nextPosExisting)) {
                j = nextPosNew; // Move new key forward
            } else {
                // Swap: Place new key here, push old key to its next position
                table.replace(key, j);
                key = existingKey; // The old key becomes the one to insert
                j = nextPosExisting;
            }
            i++;
        }

        if (i < capacity) {
            table.insert(key, j);
        }
    }
}