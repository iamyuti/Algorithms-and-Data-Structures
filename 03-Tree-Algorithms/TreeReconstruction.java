import java.util.HashMap;
import java.util.Map;

/**
 * efficient algorithms to reconstruct Binary Trees from traversal arrays.
 * Uses HashMaps to achieve O(n) time complexity by avoiding linear searches
 * for root indices in the InOrder array.
 * * The resulting tree is stored in an implicit array representation
 * (index i -> left child at 2*i + 1, right child at 2*i + 2).
 */
public class TreeReconstruction {

    /**
     * Reconstructs a binary tree given its InOrder and PreOrder traversals.
     * * @param inOrderTraversal Array containing the InOrder sequence (Left, Root, Right)
     * @param preOrderTraversal Array containing the PreOrder sequence (Root, Left, Right)
     * @param reconstructedTree The output array where the tree will be stored (Heap-like structure)
     */
    public void buildFromPreOrder(int[] inOrderTraversal, int[] preOrderTraversal, int[] reconstructedTree) {
        // Optimization: Map value -> index for O(1) access
        Map<Integer, Integer> inOrderMap = new HashMap<>();
        for (int i = 0; i < inOrderTraversal.length; i++) {
            inOrderMap.put(inOrderTraversal[i], i);
        }

        buildPreHelper(inOrderTraversal, preOrderTraversal, reconstructedTree,
                0, inOrderTraversal.length - 1,
                0, preOrderTraversal.length - 1,
                0, inOrderMap);
    }

    private void buildPreHelper(int[] inOrder, int[] preOrder, int[] tree,
                                int inStart, int inEnd,
                                int preStart, int preEnd,
                                int treeIndex, Map<Integer, Integer> inMap) {

        if (inStart > inEnd || preStart > preEnd) {
            return;
        }

        // 1. Identify Root: In PreOrder, the first element is always the root
        int rootValue = preOrder[preStart];

        // Safety check to prevent array out of bounds if tree is sparse
        if (treeIndex < tree.length) {
            tree[treeIndex] = rootValue;
        }

        // 2. Find Root in InOrder: Everything left of it is the Left Subtree
        int rootIndexInInOrder = inMap.get(rootValue);
        int leftSubtreeSize = rootIndexInInOrder - inStart;

        // 3. Recurse Left
        buildPreHelper(inOrder, preOrder, tree,
                inStart, rootIndexInInOrder - 1,
                preStart + 1, preStart + leftSubtreeSize,
                2 * treeIndex + 1, inMap);

        // 4. Recurse Right
        buildPreHelper(inOrder, preOrder, tree,
                rootIndexInInOrder + 1, inEnd,
                preStart + leftSubtreeSize + 1, preEnd,
                2 * treeIndex + 2, inMap);
    }

    /**
     * Reconstructs a binary tree given its InOrder and PostOrder traversals.
     * * @param inOrderTraversal Array containing the InOrder sequence (Left, Root, Right)
     * @param postOrderTraversal Array containing the PostOrder sequence (Left, Right, Root)
     * @param reconstructedTree The output array where the tree will be stored
     */
    public void buildFromPostOrder(int[] inOrderTraversal, int[] postOrderTraversal, int[] reconstructedTree) {
        Map<Integer, Integer> inOrderMap = new HashMap<>();
        for (int i = 0; i < inOrderTraversal.length; i++) {
            inOrderMap.put(inOrderTraversal[i], i);
        }

        buildPostHelper(inOrderTraversal, postOrderTraversal, reconstructedTree,
                0, inOrderTraversal.length - 1,
                0, postOrderTraversal.length - 1,
                0, inOrderMap);
    }

    private void buildPostHelper(int[] inOrder, int[] postOrder, int[] tree,
                                 int inStart, int inEnd,
                                 int postStart, int postEnd,
                                 int treeIndex, Map<Integer, Integer> inMap) {

        if (inStart > inEnd || postStart > postEnd) {
            return;
        }

        // 1. Identify Root: In PostOrder, the LAST element is the root
        int rootValue = postOrder[postEnd];

        if (treeIndex < tree.length) {
            tree[treeIndex] = rootValue;
        }

        // 2. Find Root in InOrder
        int rootIndexInInOrder = inMap.get(rootValue);
        int leftSubtreeSize = rootIndexInInOrder - inStart;

        // 3. Recurse Left
        buildPostHelper(inOrder, postOrder, tree,
                inStart, rootIndexInInOrder - 1,
                postStart, postStart + leftSubtreeSize - 1,
                2 * treeIndex + 1, inMap);

        // 4. Recurse Right
        buildPostHelper(inOrder, postOrder, tree,
                rootIndexInInOrder + 1, inEnd,
                postStart + leftSubtreeSize, postEnd - 1,
                2 * treeIndex + 2, inMap);
    }
}