package model;

public class StaticStack implements Stack {

    private static final int MAX_SIZE = 10000;

    private final int[] array;
    private int count;

    public StaticStack() {
        array = new int[MAX_SIZE];
        count = 0;
    }

    public StaticStack(int a) {
        array = new int[a];
        count = 0;
    }

    @Override
    public void add(int a) {
        if(count == MAX_SIZE) {
            throw new RuntimeException("Stack is full");
        }
        array[count] = a;
        count++;
    }

    @Override
    public void remove() {
        if(this.isEmpty()) {
            throw new RuntimeException("No se puede desapilar una pila vacía.");
        }
        count--;
    }

    public int poll(){
        if(this.isEmpty()) {
            throw new RuntimeException("No se puede desapilar una pila vacía.");
        }
        int LastElement = array[this.count-1];
        this.count--;
        return LastElement;

    }
    @Override
    public int getTop() {
        if(this.isEmpty()) {
            throw new RuntimeException("No se puede obtener el tope de una pila vacía.");
        }
        return this.array[count - 1];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
}
