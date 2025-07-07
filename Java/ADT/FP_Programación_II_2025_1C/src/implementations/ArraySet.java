// implementations/ArraySet.java
package implementations;

import interfaces.Set;

public class ArraySet implements Set {
    private int[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ArraySet() {
        this.elements = new int[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // Precondicion: Ninguna.
    // Complejidad: O(N) en el peor caso (revisar si existe y añadir al final),
    //             O(N) si se redimensiona.
    @Override
    public void add(int a) {
        if (!contains(a)) {
            if (size == elements.length) {
                resize();
            }
            elements[size] = a;
            size++;
        }
    }

    // Precondicion: Ninguna.
    // Complejidad: O(N) en el peor caso (encontrar el elemento y desplazar).
    @Override
    public void remove(int a) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == a) {
                // Mover el ultimo elemento al lugar del eliminado
                elements[i] = elements[size - 1];
                size--;
                return;
            }
        }
    }

    // Precondicion: Que el conjunto no este vacio.
    // Complejidad: O(1).
    @Override
    public int choose() {
        if (isEmpty()) {
            throw new IllegalStateException("Set is empty, cannot choose an element.");
        }
        return elements[0];
    }

    // Complejidad: O(1).
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Complejidad: O(N).
    public boolean contains(int a) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == a) {
                return true;
            }
        }
        return false;
    }

    // Complejidad: O(N).
    public int size() {
        return size;
    }

    // Complejidad: O(N).
    private void resize() {
        int newCapacity = elements.length * 2;
        int[] newElements = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}