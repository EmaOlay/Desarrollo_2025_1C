package Integrador3;

import Integrador3.model.BinaryTreeImpl;
import Integrador3.util.BinaryTreeIndexer;

public class Ejercicio4 {
    public static void main(String[] args) {
        BinaryTreeImpl rootNode = new BinaryTreeImpl(50);
        rootNode.addLeft(25);
        rootNode.addRight(75);
        ((BinaryTreeImpl) rootNode.getLeft()).addLeft(10);
        ((BinaryTreeImpl) rootNode.getLeft()).addRight(30);
        ((BinaryTreeImpl) rootNode.getRight()).addLeft(60);
        ((BinaryTreeImpl) rootNode.getRight()).addRight(90);
        //          50
        //      25 ------75
        // 10 -- 30   60 -- 90
        // Asignar índices en Inorder
        BinaryTreeIndexer.indexInorder(rootNode);
        
        // Verificarlos
        int targetIndex = 3; // Suponiendo que sabes qué índice tiene qué nodo
        Integer foundValue = BinaryTreeIndexer.findElementByIndexOptimized(rootNode, targetIndex);

        if (foundValue != null) {
            System.out.println("El valor en el indice " + targetIndex + " es: " + foundValue);
        } else {
            System.out.println("No se encontró ningún nodo con el indice " + targetIndex);
        }

        // Verificarlos
        int targetIndex2 = 2; // Suponiendo que sabes qué indice tiene qué nodo
        Integer foundValue2 = BinaryTreeIndexer.findElementByIndexOptimized(rootNode, targetIndex2);

        if (foundValue2 != null) {
            System.out.println("El valor en el indice " + targetIndex2 + " es: " + foundValue2);
        } else {
            System.out.println("No se encontró ningún nodo con el indice " + targetIndex2);
        }
    }
    
}
