package util;

import model.Node;

public class BinaryTreeHelper {

    // Preconditions: node is not null for initial call
    public static int getHeight(Node node) {
        if (node == null) {
            return 0; // Height of an empty tree is 0 or -1 depending on definition. Let's use 0.
        }
        int leftHeight = getHeight(node.getLeft());
        int rightHeight = getHeight(node.getRight());
        return 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
    }

    // Preconditions: node is not null for initial call
    public static int getTotalNodes(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + getTotalNodes(node.getLeft()) + getTotalNodes(node.getRight());
    }

    // Method to build a tree from an array (example, useful for testing)
    public static Node buildTreeFromArray(int[] values, int index) {
        if (index >= values.length || values[index] == -1) { // -1 can be a marker for null
            return null;
        }
        Node node = new Node(values[index]);
        node.setLeft(buildTreeFromArray(values, 2 * index + 1));
        node.setRight(buildTreeFromArray(values, 2 * index + 2));
        return node;
    }

    // Helper for printing tree for debugging (e.g., inorder traversal)
    public static void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.getLeft());
            System.out.print(node.getValue() + " ");
            inorderTraversal(node.getRight());
        }
    }
}