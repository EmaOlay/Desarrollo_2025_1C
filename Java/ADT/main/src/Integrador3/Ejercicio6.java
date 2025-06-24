package Integrador3;
 
import Integrador3.model.BinaryTreeImpl;
import Integrador3.util.BinaryTreeIndexer;
import Integrador3.util.BinaryTreeLevelWalker;
 
public class Ejercicio6 {
    public static void main(String[] args) {
        // Crear árbol de prueba
        BinaryTreeImpl root = new BinaryTreeImpl(10);
        root.addLeft(5);
        root.addRight(20);
 
        BinaryTreeImpl left = (BinaryTreeImpl) root.getLeft();
        left.addLeft(3);
        left.addRight(7);
 
        BinaryTreeImpl right = (BinaryTreeImpl) root.getRight();
        right.addRight(25);
        right.addLeft(23);

        BinaryTreeImpl right2 = (BinaryTreeImpl) right.getRight();
        right2.addRight(35);
        right2.addLeft(33);

        // Estructura del árbol:
        //        10
        //       /  \
        //      5       20
        //     / \     /  \
        //    3   7   23  25
        //               / \    
        //              33  35
        // Paso 1: Asignar índices usando recorrido preorden (ejercicio 3)
        BinaryTreeIndexer.indexPreorder(root);
 
        // Paso 2: Recorrer el árbol por niveles usando esos índices
        System.out.println("Recorrido por niveles:");
        BinaryTreeLevelWalker.recorrerPorNiveles(root);
    }
}