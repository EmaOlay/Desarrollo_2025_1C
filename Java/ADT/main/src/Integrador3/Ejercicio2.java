package Integrador3;
 
import Integrador3.model.BinaryTreeImpl;
import Integrador3.util.BinaryTreeHelper;
 
public class Ejercicio2 {
    public static void main(String[] args) {
        // Crear nodo raíz
        BinaryTreeImpl root = new BinaryTreeImpl(10);
        root.setIndex(0);
 
        // Hijo izquierdo
        root.addLeft(5);
        BinaryTreeImpl left = (BinaryTreeImpl) root.getLeft();
        left.setIndex(1);
 
        // Hijo derecho
        root.addRight(15);
        BinaryTreeImpl right = (BinaryTreeImpl) root.getRight();
        right.setIndex(2);
 
        // Hijo izquierdo del izquierdo
        left.addLeft(3);
        BinaryTreeImpl leftLeft = (BinaryTreeImpl) left.getLeft();
        leftLeft.setIndex(3);
 
        // Nodo con índice repetido (error intencional para probar)
        left.addRight(7);
        BinaryTreeImpl leftRight = (BinaryTreeImpl) left.getRight();
        leftRight.setIndex(2); // <- ERROR: índice duplicado con el derecho de raíz
 
        // Verificar si los índices son válidos y únicos
        boolean valid = BinaryTreeHelper.checkUniqueIndexes(root);
        System.out.println("¿Los indice son validos y unicos? " + valid);
    }
}