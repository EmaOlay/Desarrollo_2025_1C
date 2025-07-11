package model;

public class SetImpl implements Set {
    private int[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public SetImpl() {
        this.elements = new int[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Preconditions: none
    // Complexity: O(N) in worst case (element not present, resize needed)
    @Override
    public void add(int element) {
        if (contains(element)) {
            return; // Element already exists
        }
        if (size == elements.length) {
            resize();
        }
        elements[size++] = element;
    }

    // Preconditions: none
    // Complexity: O(N) in worst case (element found at start, requires shifting)
    @Override
    public void remove(int element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element) {
                // Shift elements to the left
                for (int j = i; j < size - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                size--;
                return;
            }
        }
    }

    // Preconditions: none
    // Complexity: O(N) in worst case (element at end or not present)
    @Override
    public boolean contains(int element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element) {
                return true;
            }
        }
        return false;
    }

    // Preconditions: none
    // Complexity: O(1)
    @Override
    public int size() {
        return size;
    }

    // Preconditions: none
    // Complexity: O(1)
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Preconditions: none
    // Complexity: O(N) to copy elements
    @Override
    public int[] toArray() {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = elements[i];
        }
        return result;
    }

    private void resize() {
        int[] newElements = new int[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}