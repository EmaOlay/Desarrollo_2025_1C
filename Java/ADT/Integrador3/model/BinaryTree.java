package Integrador3.model;

public interface BinaryTree {

    int getRoot();
    BinaryTree getLeft();
    BinaryTree getRight();
    void addLeft(int a);
    void addRight(int a);

    int getIndex();
    void setIndex(int index);
    
    void removeLeft();
    void removeRight();

}
