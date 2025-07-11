package model;

public class StackImpl {
    private Object[] elements;
    private int top; // Index of the top element
    private static final int DEFAULT_CAPACITY = 10;

    public StackImpl() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.top = -1; // Stack is empty
    }

    // Preconditions: none
    public void push(Object item) {
        if (top == elements.length - 1) {
            resize();
        }
        elements[++top] = item;
    }

    // Preconditions: stack is not empty
    public Object pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot pop.");
        }
        Object item = elements[top];
        elements[top--] = null; // Dereference to aid garbage collection
        return item;
    }

    // Preconditions: stack is not empty
    public Object peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty. Cannot peek.");
        }
        return elements[top];
    }

    // Preconditions: none
    public boolean isEmpty() {
        return top == -1;
    }

    // Preconditions: none
    public int size() {
        return top + 1;
    }

    private void resize() {
        Object[] newElements = new Object[elements.length * 2];
        for (int i = 0; i <= top; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }
}