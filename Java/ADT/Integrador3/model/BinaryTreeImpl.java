package Integrador3.model;
 
/**
* Implementacion simple de BinaryTree,
* con referencias directas a hijos izquierdo y derecho.
*/
public class BinaryTreeImpl implements BinaryTree {
 
    private int root;
    private int index; // NUEVO: indice del nodo
    private BinaryTree left;
    private BinaryTree right;
 
    /**
     * Construye un arbol con un unico nodo raiz.
     * @param root valor a almacenar en la raiz
     */
    public BinaryTreeImpl(int root) {
        this.root = root;
        this.index = -1; // valor por defecto, sin asignar
        this.left = null;
        this.right = null;
    }
 
    @Override
    public int getRoot() {
        return this.root;
    }
 
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
 
    @Override
    public BinaryTree getLeft() {
        return this.left;
    }
 
    @Override
    public BinaryTree getRight() {
        return this.right;
    }
 
    @Override
    public void addLeft(int a) {
        this.left = new BinaryTreeImpl(a);
    }
 
    @Override
    public void addRight(int a) {
        this.right = new BinaryTreeImpl(a);
    }
 
    @Override
    public void removeLeft() {
        this.left = null;
    }
 
    @Override
    public void removeRight() {
        this.right = null;
    }
}