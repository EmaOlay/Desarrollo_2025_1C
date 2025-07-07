// interfaces/SearchBinaryTree.java
package interfaces;

public interface SearchBinaryTree {
    int getRoot();
    SearchBinaryTree getLeft();
    SearchBinaryTree getRight();
    void removeLeft();
    void removeRight();
    void add(int a);
}