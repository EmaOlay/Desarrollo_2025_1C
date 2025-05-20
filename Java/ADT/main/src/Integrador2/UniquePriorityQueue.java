package Integrador2;

public class UniquePriorityQueue implements PriorityQueue {
 
    private static final int MAX_SIZE = 1000;
    private int[] priorities = new int[MAX_SIZE];
    private int[] values = new int[MAX_SIZE];
    private int size = 0;
 
    @Override
    public void add(int priority, int value) {
        // Verificar si la prioridad ya existe
        for (int i = 0; i < size; i++) {
            if (priorities[i] == priority) {
                System.out.println("Prioridad duplicada no permitida: " + priority);
                return;
            }
        }
 
        // Insertar manteniendo el orden por prioridad
        int i = size - 1;
        while (i >= 0 && priorities[i] > priority) {
            priorities[i + 1] = priorities[i];
            values[i + 1] = values[i];
            i--;
        }
        priorities[i + 1] = priority;
        values[i + 1] = value;
        size++;
    }
 
    @Override
    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        else {
            for (int i = 1; i < size; i++) {
                priorities[i - 1] = priorities[i];
                values[i - 1] = values[i];
            }
            size--;
        }
    }
 
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
 
    @Override
    public int getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return values[0];
    }
 
    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return priorities[0];
    }
}

// Método add
// Verificación de duplicado:
// Recorre el arreglo de prioridades para ver si ya existe.
// Costo: O(n)
// Inserción ordenada:
// Desplaza elementos para mantener el orden por prioridad.
// Costo: O(n)
// Complejidad total de add():
// O(n) + O(n) = O(n)
// Metodo remove: desplaza elementos → O(n)
