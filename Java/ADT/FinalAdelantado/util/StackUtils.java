package util;

import model.StackImpl;

public class StackUtils {

    public static void printStack(StackImpl stack) {
        System.out.print("[");
        StackImpl tempStack = new StackImpl();
        while (!stack.isEmpty()) {
            Object item = stack.pop();
            System.out.print(item + (stack.isEmpty() ? "" : ", "));
            tempStack.push(item);
        }
        // Restore the original stack
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
        System.out.println("]");
    }
}