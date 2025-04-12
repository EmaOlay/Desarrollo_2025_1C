package model;

public class FlatDequeueMutable implements Queue {

    private final int[] elements;
    private int front; // Índice del primer elemento en la cola
    private int rear; // Índice de la siguiente posición libre (después del último elemento)
    private int size; // Número actual de elementos en la cola
    private static final int capacity = 10000; // Capacidad máxima de la cola

    public FlatDequeueMutable() {
        this.elements = new int[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    @Override
    public void add(int element) {
        if (size == capacity) {
            throw new IllegalStateException("La cola está llena.");
        }
        elements[rear] = element;
        rear = (rear + 1) % capacity; // Manejo del circular buffer
        size++;
    }

    public int poll() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is Empty");// La cola está vacía, no hay nada que remover
        }
        int elementToRemove = elements[front];
        elements[front] = 0; // Ayuda al Garbage Collector
        front = (front + 1) % capacity; // Mueve el puntero 'front' al siguiente elemento.
        // El resultado de la suma se divide por la capacidad de la cola, y se toma el resto. 
        // Esto es crucial para implementar un buffer circular. 
        // Si front llega al final del array (capacity - 1)
        // , la siguiente vez que se ejecute esta línea,
        //  (capacity - 1 + 1) % capacity será igual a capacity % capacity, que es 0. 
        // Esto hace que front vuelva al principio del array, permitiendo reutilizar las posiciones que han quedado vacías debido a las operaciones de poll() o remove().
        size--;
        return elementToRemove;
    }

    @Override
    public void remove() {
        // Ya que poll remueve el primero hago que remove remueva el ultimo
        if (isEmpty()) {
            throw new RuntimeException("Queue is Empty");
        }
        // Calculamos el índice del último elemento
        int lastIndex = (rear - 1 + capacity) % capacity;
        elements[lastIndex] = 0; // "Removemos" el último elemento
        rear = lastIndex; // Ajustamos el puntero 'rear'
        size--;
    }

    @Override
    public int getFirst() {
        if (this.isEmpty()) {
            throw new RuntimeException("No se puede obtener el primero de una cola vacía.");
        }
        return elements[front];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // ... (El resto de tu implementación de Queue, si la tienes) ...
}