package Integrador3;
 
import Integrador3.model.BinaryTreeImpl;
import Integrador3.util.BinaryTreeHelper;
 
public class Ejercicio1 {
    public static void main(String[] args) {
        // Inicia Ej 1################################# ///////////
        // Crear nodo raíz con valor 10
        BinaryTreeImpl root = new BinaryTreeImpl(10);
        root.setIndex(0);
 
        // Agregar hijos
        root.addLeft(5);
        root.getLeft().setIndex(1);
 
        root.addRight(15);
        root.getRight().setIndex(2);
 
        // Agregar nietos
        BinaryTreeImpl leftChild = (BinaryTreeImpl) root.getLeft();
        leftChild.addLeft(3);
        leftChild.getLeft().setIndex(3);
 
        leftChild.addRight(7);
        leftChild.getRight().setIndex(4);
 
        // Imprimir nodos e índices
        System.out.println("Raiz: valor = " + root.getRoot() + ", indice = " + root.getIndex());
        System.out.println("Izquierdo: valor = " + root.getLeft().getRoot() + ", indice = " + root.getLeft().getIndex());
        System.out.println("Derecho: valor = " + root.getRight().getRoot() + ", indice = " + root.getRight().getIndex());
        System.out.println("Izquierdo del izquierdo: valor = " + root.getLeft().getLeft().getRoot() + ", indice = " + root.getLeft().getLeft().getIndex());
        System.out.println("Derecho del izquierdo: valor = " + root.getLeft().getRight().getRoot() + ", indice = " + root.getLeft().getRight().getIndex());
        // Fin Ej 1################################# ///////////
    }
}