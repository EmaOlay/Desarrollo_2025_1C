package Integrador3.util;
 
import Integrador3.model.BinaryTreeImpl;
 
public class BinaryTreeLevelWalker {
 
    /**

     * Recorre un árbol binario por niveles (BFS) en forma recursiva

     * utilizando únicamente los índices preorden asignados previamente.

     * @param root raíz del árbol ya indexado

     */

    public static void recorrerPorNiveles(BinaryTreeImpl root) {

        int altura = obtenerAltura(root);

        for (int nivel = 0; nivel < altura; nivel++) {
            System.out.println("Nivel " + nivel + ":");
            recorrerNivel(root, nivel);

        }

    }
 
    private static int obtenerAltura(BinaryTreeImpl node) {

        if (node == null) return 0;

        int izq = obtenerAltura((BinaryTreeImpl) node.getLeft());

        int der = obtenerAltura((BinaryTreeImpl) node.getRight());

        return 1 + Math.max(izq, der);

    }
 
    private static void recorrerNivel(BinaryTreeImpl node, int nivel) {

        if (node == null) return;
 
        if (nivel == 0) {

            System.out.print("Valor: " + node.getRoot() + " - Indice: " + node.getIndex() + "\n");

        } else {

            recorrerNivel((BinaryTreeImpl) node.getLeft(), nivel - 1);

            recorrerNivel((BinaryTreeImpl) node.getRight(), nivel - 1);

        }

    }

}

 