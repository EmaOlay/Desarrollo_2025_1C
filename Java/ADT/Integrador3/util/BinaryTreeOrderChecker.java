package Integrador3.util;
 
import Integrador3.model.BinaryTreeImpl;
 
public class BinaryTreeOrderChecker {
 
    private static class NodoValor {
        int index;
        int value;
    }
 
    /**
     * Asume que no hay más de 100 nodos.
     */
    public static boolean isOrderedByIndex(BinaryTreeImpl root) {
        NodoValor[] nodos = new NodoValor[100]; // lista auxiliar de nodos vistos
        int[] count = new int[1];
 
        collectNodes(root, nodos, count);
 
        // Ordenar nodos por índice usando bubble sort simple
        for (int i = 0; i < count[0] - 1; i++) {
            for (int j = 0; j < count[0] - i - 1; j++) {
                if (nodos[j].index > nodos[j + 1].index) {
                    NodoValor temp = nodos[j];
                    nodos[j] = nodos[j + 1];
                    nodos[j + 1] = temp;
                }
            }
        }
 
        // Verificar si los valores están ordenados en base al índice
        for (int i = 0; i < count[0] - 1; i++) {
            if (nodos[i].value > nodos[i + 1].value) {
                return false;
            }
        }
 
        return true;
    }
 
    private static void collectNodes(BinaryTreeImpl node, NodoValor[] nodos, int[] count) {
        if (node == null) return;
 
        NodoValor nv = new NodoValor();
        nv.index = node.getIndex();
        nv.value = node.getRoot();
        nodos[count[0]] = nv;
        count[0]++;
 
        collectNodes((BinaryTreeImpl) node.getLeft(), nodos, count);
        collectNodes((BinaryTreeImpl) node.getRight(), nodos, count);
    }
}