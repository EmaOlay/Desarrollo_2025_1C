// utils/BinaryTreeUtil.java
package utils;

import interfaces.BinaryTree;

public class BinaryTreeUtil {

    // Precondicion: El arbol no debe ser nulo. Existe una rama mas larga.
    // Complejidad: O(N) donde N es el numero de nodos del arbol.
    // La estrategia es la siguiente:
    // 1. Encontrar la longitud de la rama más larga del árbol utilizando un recorrido DFS recursivo.
    // 2. Realizar otro recorrido DFS recursivo, esta vez pasando la longitud de la rama más larga.
    // 3. En cada nodo, verificar si el nodo actual está en un camino que forma una de las ramas más largas.
    //    Para esto, calculamos la longitud de la rama que pasa por el nodo actual y verificamos si coincide
    //    con la longitud máxima global.
    public static boolean isOnLongestBranch(BinaryTree tree, int value) {
        if (tree == null) {
            return false;
        }
        // Step 1: Find the maximum depth of the tree
        int maxDepth = getMaxDepth(tree);

        // Step 2 & 3: Check if the value is on a path that matches the maxDepth
        return isOnLongestBranchRecursive(tree, value, 0, maxDepth);
    }

    // Precondicion: El arbol no debe ser nulo.
    // Complejidad: O(N) donde N es el numero de nodos del arbol.
    private static int getMaxDepth(BinaryTree tree) {
        if (tree == null) {
            return -1; // -1 para que la hoja tenga profundidad 0
        }
        int leftDepth = getMaxDepth(tree.getLeft());
        int rightDepth = getMaxDepth(tree.getRight());
        return 1 + (leftDepth > rightDepth ? leftDepth : rightDepth);
    }

    // Precondicion: El arbol no debe ser nulo.
    // Complejidad: O(N) donde N es el numero de nodos del arbol.
    private static boolean isOnLongestBranchRecursive(BinaryTree tree, int value, int currentDepth, int maxDepth) {
        if (tree == null) {
            return false;
        }

        // Check if current node is the target value
        if (tree.getRoot() == value) {
            // If the current node is the target, we need to check if the path from
            // this node to a leaf can form a longest branch.
            // This means currentDepth + (maxDepth from this node down) should equal maxDepth.
            // We can simplify: if this node is the target, we just need to verify that
            // it's part of *any* path that could be a longest branch.
            // A node is on the longest branch if there's a path from the root through this node
            // to a leaf, and that path's length is `maxDepth`.
            // The remaining depth from this node to a leaf must be `maxDepth - currentDepth`.
            return getMaxDepth(tree) == (maxDepth - currentDepth);
        }

        // Recursively check left and right subtrees
        boolean foundInLeft = isOnLongestBranchRecursive(tree.getLeft(), value, currentDepth + 1, maxDepth);
        if (foundInLeft) {
            return true;
        }
        boolean foundInRight = isOnLongestBranchRecursive(tree.getRight(), value, currentDepth + 1, maxDepth);
        return foundInRight;
    }
}