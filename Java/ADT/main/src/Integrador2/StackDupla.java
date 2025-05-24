package Integrador2;

public class StackDupla {
    private Dupla[] data = new Dupla[100];
    private int top = -1;
 
    public void add(Dupla d) {
        if (top < data.length - 1) {
            top++;
            data[top] = d;
        }
    }
 
    public void remove() {
        if (!isEmpty()) {
            top--;
        }
    }
 
    public Dupla getTop() {
        if (isEmpty()) {
            return null;
        } else {
            return data[top];
        }
    }
 
    public boolean isEmpty() {
        return top == -1;
    }
}