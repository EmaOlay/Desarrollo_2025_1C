package util;

import model.Node;
import model.StackImpl;

public class BinaryTreeOrderChecker {

    // Preconditions: root is not null
    public static boolean isIdentityMatrixTree(Node root) {
        if (root == null) {
            return false;
        }

        StackImpl nodeStack = new StackImpl();
        Node tempCurrent = root;
        int[] traversedValues = new int[BinaryTreeHelper.getTotalNodes(root)]; // Store values to check later
        int valueIndex = 0;

        while (tempCurrent != null || !nodeStack.isEmpty()) {
            while (tempCurrent != null) {
                nodeStack.push(tempCurrent);
                tempCurrent = tempCurrent.getLeft();
            }
            tempCurrent = (Node) nodeStack.pop();
            traversedValues[valueIndex++] = tempCurrent.getValue(); // Collect the value
            tempCurrent = tempCurrent.getRight();
        }

        StackImpl s1 = new StackImpl();
        StackImpl s2 = new StackImpl();
        s1.push(root);

        int[] levelOrderValues = new int[BinaryTreeHelper.getTotalNodes(root)];
        int levelIndex = 0;

        while (!s1.isEmpty()) {
            Node node = (Node) s1.pop();
            s2.push(node); // Push to second stack to reverse order

            if (node.getLeft() != null) {
                s1.push(node.getLeft());
            }
            if (node.getRight() != null) {
                s1.push(node.getRight());
            }
        }

        // s2 now has nodes in level order (reversed, so pop to get correct order)
        while (!s2.isEmpty()) {
            Node node = (Node) s2.pop();
            levelOrderValues[levelIndex++] = node.getValue();
        }

        StackImpl s_nodes = new StackImpl();
        StackImpl s_values = new StackImpl(); // To store values in correct order

        s_nodes.push(root);

        while (!s_nodes.isEmpty()) {
            Node currentNode = (Node) s_nodes.pop();
            s_values.push(currentNode); // Push to s_values to reverse order later

            // Push right child first, then left, so left is popped first from s_nodes
            if (currentNode.getRight() != null) {
                s_nodes.push(currentNode.getRight());
            }
            if (currentNode.getLeft() != null) {
                s_nodes.push(currentNode.getLeft());
            }
        }

        int totalNodes = BinaryTreeHelper.getTotalNodes(root);
        int matrixDimension = (int) Math.sqrt(totalNodes);

        if (matrixDimension * matrixDimension != totalNodes) {
            // Not a square matrix based on node count, cannot be an identity matrix
            return false;
        }

        // Re-pop from s_values to get the correct level-order sequence
        int[] actualValues = new int[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            actualValues[i] = ((Node) s_values.pop()).getValue();
        }

        for (int i = 0; i < totalNodes; i++) {
            int row = i / matrixDimension;
            int col = i % matrixDimension;

            if (row == col) { // Diagonal element
                if (actualValues[i] != 1) return false;
                } else { // Off-diagonal element
                if (actualValues[i] != 0) return false;
            }
        }

        return true;
    }
}