package Integrador3.util;

import Integrador3.model.BinaryTree; // Importa la interfaz, si se usa directamente
import Integrador3.model.BinaryTreeImpl;

public class BinaryTreeIndexer {

    /**
     * Asigna indices a los nodos de un arbol binario en un recorrido Preorden,
     * comenzando desde 0.
     *
     * @param root El nodo raíz del arbol binario.
     */
    public static void indexPreorder(BinaryTreeImpl root) {
        int[] counter = new int[1]; // Un array de un solo elemento para actuar como contador mutable
        assignIndexPreorder(root, counter);
    }

    /**
     * Método auxiliar recursivo para asignar indices en recorrido Preorden.
     *
     * @param node El nodo actual a procesar.
     * @param counter El array contador que mantiene el indice actual.
     */
    private static void assignIndexPreorder(BinaryTreeImpl node, int[] counter) {
        if (node == null) {
            return;
        }

        // 1. Visitar el nodo actual (Raíz)
        node.setIndex(counter[0]);
        counter[0]++; // Incrementar el contador para el siguiente nodo

        // 2. Recorrer el subarbol izquierdo
        // Se requiere el cast a BinaryTreeImpl ya que getLeft() devuelve BinaryTree
        assignIndexPreorder((BinaryTreeImpl) node.getLeft(), counter);

        // 3. Recorrer el subarbol derecho
        // Se requiere el cast a BinaryTreeImpl ya que getRight() devuelve BinaryTree
        assignIndexPreorder((BinaryTreeImpl) node.getRight(), counter);
    }

    // --- NUEVO: Métodos para indexar en Inorder ---

    /**
     * Asigna indices a los nodos de un arbol binario en un recorrido Inorder,
     * comenzando desde 0.
     *
     * @param root El nodo raíz del arbol binario.
     */
    public static void indexInorder(BinaryTreeImpl root) {
        int[] counter = new int[1]; // Un array de un solo elemento para actuar como contador mutable
        assignIndexInorder(root, counter);
    }

    /**
     * Método auxiliar recursivo para asignar indices en recorrido Inorder.
     *
     * @param node El nodo actual a procesar.
     * @param counter El array contador que mantiene el indice actual.
     */
    private static void assignIndexInorder(BinaryTreeImpl node, int[] counter) {
        if (node == null) {
            return;
        }

        // 1. Recorrer el subarbol izquierdo
        assignIndexInorder((BinaryTreeImpl) node.getLeft(), counter);

        // 2. Visitar el nodo actual (Raíz)
        node.setIndex(counter[0]);
        counter[0]++; // Incrementar el contador para el siguiente nodo

        // 3. Recorrer el subarbol derecho
        assignIndexInorder((BinaryTreeImpl) node.getRight(), counter);
    }

    // --- Método para encontrar elemento por indice (Optimizado para Inorder) ---

    /**
     * Busca y devuelve el valor del nodo que tiene el indice especificado en un arbol binario.
     * La búsqueda se optimiza asumiendo que los indices se asignaron previamente
     * en un recorrido Inorder. Esto permite una búsqueda similar a la de un BST.
     *
     * @param tree El nodo raíz del arbol binario indexado.
     * @param targetIndex El indice del nodo que se desea encontrar.
     * @return El valor (root) del nodo con el indice especificado, o null si el indice no se encuentra
     * o si el arbol es nulo o el indice es negativo.
     */
    public static Integer findElementByIndexOptimized(BinaryTreeImpl tree, int targetIndex) {
        // Validación básica del indice
        if (targetIndex < 0) {
            System.out.println("Error: El indice buscado no puede ser negativo.");
            return null;
        }

        // Si el arbol es nulo en cualquier punto, no se encontró el indice en este camino
        if (tree == null) {
            return null;
        }

        int currentIndex = tree.getIndex(); // Obtiene el indice del nodo actual

        // Compara el indice objetivo con el indice del nodo actual
        if (targetIndex < currentIndex) {
            // Si el indice buscado es menor, debe estar en el subarbol izquierdo.
            // Se requiere el cast a BinaryTreeImpl ya que getLeft() devuelve BinaryTree
            return findElementByIndexOptimized((BinaryTreeImpl) tree.getLeft(), targetIndex);
        } else if (targetIndex > currentIndex) {
            // Si el indice buscado es mayor, debe estar en el subarbol derecho.
            // Se requiere el cast a BinaryTreeImpl ya que getRight() devuelve BinaryTree
            return findElementByIndexOptimized((BinaryTreeImpl) tree.getRight(), targetIndex);
        } else {
            // Si targetIndex == currentIndex, hemos encontrado el nodo.
            return tree.getRoot(); // Devuelve el valor (root) del nodo
        }
    }
}