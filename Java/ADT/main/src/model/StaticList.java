package model;

public class StaticList implements List {

    private static final int DEFAULT_CAPACITY = 300;
    private int[] elements;
    private int size;

    public StaticList() {
        this(DEFAULT_CAPACITY);
    }

    public StaticList(int capacity) {
        this.elements = new int[capacity];
        this.size = 0;
    }

    @Override
    public void add(int element) {
        if (size == elements.length) {
            throw new IllegalStateException("La lista está llena.");
        }
        elements[size++] = element;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("La lista está vacía.");
        }
        size--;
        // Opcional: elements[size] = 0; // Para liberar la referencia al objeto (si fueran objetos)
    }

    @Override
    public int getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("La lista está vacía.");
        }
        return elements[0];
    }

    @Override
    public int getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("La lista está vacía.");
        }
        return elements[size - 1];
    }

    @Override
    public int get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + position);
        }
        return elements[position];
    }

    @Override
    public void insert(int element, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Posición inválida para inserción: " + position);
        }
        if (size == elements.length) {
            throw new IllegalStateException("La lista está llena.");
        }
        for (int i = size; i > position; i--) {
            elements[i] = elements[i - 1];
        }
        elements[position] = element;
        size++;
    }

    @Override
    public void delete(int position) {
        if (isEmpty()) {
            throw new IllegalStateException("La lista está vacía.");
        }
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Posición inválida: " + position);
        }
        for (int i = position; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--;
        // Opcional: elements[size] = 0; // Para liberar la referencia al objeto (si fueran objetos)
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int find(int element) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == element) {
                return i;
            }
        }
        return -1;
    }
}