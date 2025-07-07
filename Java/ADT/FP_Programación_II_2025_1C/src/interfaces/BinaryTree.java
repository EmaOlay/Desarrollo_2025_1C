// interfaces/BinaryTree.java
package interfaces;

public interface BinaryTree {
    int getRoot();
    BinaryTree getLeft();
    BinaryTree getRight();
    void removeLeft();
    void removeRight();
    void addLeft(int a);
    void addRight(int a);
}