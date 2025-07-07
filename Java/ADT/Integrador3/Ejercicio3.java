package Integrador3;

import Integrador3.model.BinaryTreeImpl;
import Integrador3.util.BinaryTreeIndexer;

public class Ejercicio3 {
    public static void main(String[] args) {
        BinaryTreeImpl root = new BinaryTreeImpl(10);
        root.addLeft(5);
        root.addRight(20);
        ((BinaryTreeImpl) root.getLeft()).addLeft(3);
        ((BinaryTreeImpl) root.getLeft()).addRight(7);
        //     10
        // 5 ------20
        // 3 -- 7
        // Asignar índices en preorden
        BinaryTreeIndexer.indexPreorder(root);
        
        // Verificarlos
        System.out.println("Raiz: " + root.getRoot() + " - indice: " + root.getIndex());
        System.out.println("Izquierdo: " + root.getLeft().getRoot() + " - indice: " + root.getLeft().getIndex());
        System.out.println("Derecho: " + root.getRight().getRoot() + " - indice: " + root.getRight().getIndex());
        System.out.println("Izq. del izquierdo: " + root.getLeft().getLeft().getRoot() + " - indice: " + root.getLeft().getLeft().getIndex());
        System.out.println("Der. del izquierdo: " + root.getLeft().getRight().getRoot() + " - indice: " + root.getLeft().getRight().getIndex());
    }
    
}
