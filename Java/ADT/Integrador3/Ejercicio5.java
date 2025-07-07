package Integrador3;

import Integrador3.model.BinaryTreeImpl;
import Integrador3.util.BinaryTreeIndexer;
import Integrador3.util.BinaryTreeOrderChecker;

public class Ejercicio5 {
    public static void main(String[] args) {
        // Crear árbol de ejemplo
        BinaryTreeImpl root = new BinaryTreeImpl(5);
        root.setIndex(0);
 
        root.addLeft(7);
        BinaryTreeImpl left = (BinaryTreeImpl) root.getLeft();
        left.setIndex(1);
 
        root.addRight(10);
        BinaryTreeImpl right = (BinaryTreeImpl) root.getRight();
        right.setIndex(2);
        
        //       5
        //     /   \
        //    7     10
        // Verificar si está ordenado según el indice
        boolean estaOrdenado = BinaryTreeOrderChecker.isOrderedByIndex(root);
        System.out.println("¿Árbol ordenado por indice? " + estaOrdenado);

        // Crear árbol de ejemplo
        BinaryTreeImpl root2 = new BinaryTreeImpl(5);
        root2.setIndex(0);
 
        root2.addLeft(4);
        BinaryTreeImpl left2 = (BinaryTreeImpl) root2.getLeft();
        left2.setIndex(1);
 
        root2.addRight(10);
        BinaryTreeImpl right2 = (BinaryTreeImpl) root2.getRight();
        right2.setIndex(2);
        
        //       5
        //     /   \
        //    4     10
        // Verificar si está ordenado según el indice
        boolean estaOrdenado2 = BinaryTreeOrderChecker.isOrderedByIndex(root2);
        System.out.println("¿Árbol ordenado por indice? " + estaOrdenado2);
    }
    
}
