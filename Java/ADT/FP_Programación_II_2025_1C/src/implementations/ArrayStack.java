// implementations/ArrayStack.java
package implementations;

import interfaces.Stack;

public class ArrayStack implements Stack {
    private int[] elements;
    private int top; // Index of the top element
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayStack() {
        this.elements = new int[DEFAULT_CAPACITY];
        this.top = -1; // Indicates an empty stack
    }

    // Precondicion: Que la pila no este llena.
    // Complejidad: O(1) en promedio, O(N) en el peor caso si se redimensiona.
    @Override
    public void add(int a) {
        if (top == elements.length - 1) {
            resize();
        }
        top++;
        elements[top] = a;
    }

    // Precondicion: Que la pila no este vacia.
    // Complejidad: O(1).
    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty, cannot remove element.");
        }
        elements[top] = 0; // Opcional: limpiar la referencia
        top--;
    }

    // Precondicion: Que la pila no este vacia.
    // Complejidad: O(1).
    @Override
    public int getTop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty, no top element.");
        }
        return elements[top];
    }

    // Complejidad: O(1).
    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    // Complejidad: O(N) donde N es el tamaño actual de la pila.
    private void resize() {
        int newCapacity = elements.length * 2;
        int[] newElements = new int[newCapacity];
        for (int i = 0; i <= top; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}