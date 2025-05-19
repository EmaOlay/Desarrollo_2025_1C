package Integrador2;


public class SpecialPriorityQueue implements PriorityQueue {
 
    private static final int MAX_SIZE = 1000;
    private int[] priorities = new int[MAX_SIZE];
    private int[] values = new int[MAX_SIZE];
    private int size = 0;
 
    @Override
    public void add(int priority, int value) {
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
 
    public boolean hasPeak() {
        if (size < 3) return false;
 
        int left = 1;
        int right = size - 2;
        
        while (left <= right) {
            int mid = (left + right) / 2;
            int prev = values[mid - 1];
            int curr = values[mid];
            int next = values[mid + 1];
 
            if (curr > prev && curr > next) {
                return true;
            } else if (curr < next) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}

// Justificación
 
// El método hasPeak() no modifica la estructura de la cola, 
// solo lee los valores ya ordenados por prioridad. 
// Por lo tanto, su complejidad depende únicamente de:
// El número de elementos (n) en la cola.
// La estrategia de búsqueda utilizada.
// Por ende: 
// - El if que chequea el tamaño para la condición que haya pico: O(C)
// - La inicialización de left y right: O(C)
// - La búsqueda binaria tiene una complejidad de O(log n)

// TOTAL hasPeak(): 
// O(C) + O(C) + O(log n) = O(log n)
