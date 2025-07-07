// implementations/BinaryTreeImpl.java
package implementations;

import interfaces.BinaryTree;

public class BinaryTreeImpl implements BinaryTree {
    private int root;
    private BinaryTree left;
    private BinaryTree right;

    public BinaryTreeImpl(int root) {
        this.root = root;
        this.left = null;
        this.right = null;
    }

    // Precondicion: Ninguna.
    // Complejidad: O(1).
    @Override
    public int getRoot() {
        return root;
    }

    // Precondicion: Ninguna.
    // Complejidad: O(1).
    @Override
    public BinaryTree getLeft() {
        return left;
    }

    // Precondicion: Ninguna.
    // Complejidad: O(1).
    @Override
    public BinaryTree getRight() {
        return right;
    }

    // Precondicion: Ninguna.
    // Complejidad: O(1).
    @Override
    public void removeLeft() {
        this.left = null;
    }

    // Precondicion: Ninguna.
    // Complejidad: O(1).
    @Override
    public void removeRight() {
        this.right = null;
    }

    // Precondicion: Que el nodo izquierdo no este ya ocupado.
    // Complejidad: O(1).
    @Override
    public void addLeft(int a) {
        if (this.left != null) {
            throw new IllegalStateException("Left child already exists.");
        }
        this.left = new BinaryTreeImpl(a);
    }

    // Precondicion: Que el nodo derecho no este ya ocupado.
    // Complejidad: O(1).
    @Override
    public void addRight(int a) {
        if (this.right != null) {
            throw new IllegalStateException("Right child already exists.");
        }
        this.right = new BinaryTreeImpl(a);
    }
}