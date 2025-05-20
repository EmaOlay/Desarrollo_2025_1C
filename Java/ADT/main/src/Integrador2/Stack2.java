package Integrador2;


public class Stack2 {
    private static final int MAX_SIZE = 10000;

    private int[] array;
    private int count;

    // Terms Rewriting System -> TeReSe

    public Stack2() { // C_1 + C_2 + C_3 + C_4 + C_5 entonces la complejidad es O(1)
        array = new int[MAX_SIZE];
        count = 0;
    }


    public void add(int a) {
        if(count == MAX_SIZE) {
            throw new RuntimeException("Stack is full");
        }
        array[count] = a;
        count++;
    }

    public void remove() {
        if(this.isEmpty()) {
            throw new RuntimeException("No se puede desapilar una pila vacía.");
        }
        count--;
    }

    public int getTop() {
        if(this.isEmpty()) {
            throw new RuntimeException("No se puede obtener el tope de una pila vacía.");
        }
        return this.array[count - 1];
    }

    public boolean isEmpty() {
        return count == 0;
    }
}