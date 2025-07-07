// implementations/ArrayQueue.java
package implementations;

import interfaces.Queue;

public class ArrayQueue implements Queue {
    private int[] elements;
    private int size;
    private int front; // Index of the front element
    private int rear;  // Index where the next element will be added
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayQueue() {
        this.elements = new int[DEFAULT_CAPACITY];
        this.size = 0;
        this.front = 0;
        this.rear = 0;
    }

    // Precondicion: Que la cola no este llena.
    // Complejidad: O(1) en promedio, O(N) en el peor caso si se redimensiona.
    @Override
    public void add(int a) {
        if (size == elements.length) {
            resize();
        }
        elements[rear] = a;
        rear = (rear + 1) % elements.length; // Movimiento circular
        size++;
    }

    // Precondicion: Que la cola no este vacia.
    // Complejidad: O(1).
    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty, cannot remove element.");
        }
        elements[front] = 0; // Opcional: limpiar la referencia
        front = (front + 1) % elements.length; // Movimiento circular
        size--;
    }

    // Complejidad: O(1).
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Precondicion: Que la cola no este vacia.
    // Complejidad: O(1).
    @Override
    public int getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty, no first element.");
        }
        return elements[front];
    }

    // Complejidad: O(N) donde N es el tamaño actual de la cola.
    private void resize() {
        int newCapacity = elements.length * 2;
        int[] newElements = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(front + i) % elements.length];
        }
        elements = newElements;
        front = 0;
        rear = size;
    }
}