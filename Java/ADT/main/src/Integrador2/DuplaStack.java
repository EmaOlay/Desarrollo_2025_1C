package Integrador2;

public class DuplaStack implements Stack {
 
    private static final int MAX_SIZE = 100;
    private Dupla[] stack = new Dupla[MAX_SIZE];
    private int top = -1;
 
    @Override
    public void add(Dupla d) {
        if (top < MAX_SIZE - 1) {
            stack[++top] = d;
        } else {
            System.out.println("Pila llena");
        }
    }
 
    @Override
    public void remove() {
        if (!isEmpty()) {
            top--;
        }
    }
 
    @Override
    public Dupla getTop() {
        if (!isEmpty()) {
            return stack[top];
        }
        return null;
    }
 
    @Override
    public boolean isEmpty() {
        return top == -1;
    }
}
 