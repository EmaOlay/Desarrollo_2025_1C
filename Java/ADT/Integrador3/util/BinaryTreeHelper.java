package Integrador3.util;
 
import Integrador3.model.BinaryTreeImpl;
 
public class BinaryTreeHelper {
 
    /**
     * Verifica que los indices de los nodos del arbol sean unicos y esten asignados.
     * Usa un arreglo simple para registrar los indices encontrados.
     * @param root Nodo raiz del arbol.
     * @return true si los indices son validos y unicos.
     */
    public static boolean checkUniqueIndexes(BinaryTreeImpl root) {
        // Arreglo para guardar hasta 100 indices vistos (ajustar si es necesario)
        int[] seenIndexes = new int[100];
        int[] count = new int[1]; // contador de indices insertados, paso la direccion de memoria para poder modificarlo
        return checkIndexesRecursively(root, seenIndexes, count);
    }
 
    private static boolean checkIndexesRecursively(BinaryTreeImpl node, int[] seenIndexes, int[] count) {
        if (node == null) {
            return true;
        }
 
        int index = node.getIndex();
 
        if (index == -1) {
            System.out.println("Error: nodo con indice sin asignar (-1). Valor del nodo: " + node.getRoot());
            return false;
        }
 
        // Verificar si ya fue registrado
        for (int i = 0; i < count[0]; i++) {
            if (seenIndexes[i] == index) {
                System.out.println("Error: indice repetido (" + index + ") en nodo con valor: " + node.getRoot());
                return false;
            }
        }
 
        // Registrar indice
        seenIndexes[count[0]] = index;
        count[0]++;
 
        return checkIndexesRecursively((BinaryTreeImpl) node.getLeft(), seenIndexes, count) &&
               checkIndexesRecursively((BinaryTreeImpl) node.getRight(), seenIndexes, count);
    }
}