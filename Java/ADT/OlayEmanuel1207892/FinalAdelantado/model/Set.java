package model;

// Custom interface for Set, since java.util.Set is forbidden
public interface Set {
    void add(int element);
    void remove(int element);
    boolean contains(int element);
    int size();
    boolean isEmpty();
    int[] toArray(); // To allow iterating or processing the elements outside
}