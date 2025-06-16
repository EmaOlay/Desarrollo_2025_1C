package Integrador2;


public class StackUtils {
 
     public static void addToBottomIfNotExists(Stack2 stack, int element) {
        if (contains(stack, element)) {
            return; // Ya existe, no lo agregamos
        }
 
        addToBottom(stack, element);
    }
 
    private static void addToBottom(Stack2 stack, int element) {
        if (stack.isEmpty()) {
            stack.add(element);
            return;
        }
 
        int top = stack.getTop();
        stack.remove();
 
        addToBottom(stack, element);
 
        stack.add(top);
    }
 
    // Método auxiliar para verificar si un elemento está en la pila
    private static boolean contains(Stack2 stack, int element) {
        if (stack.isEmpty()) {
            return false;
        }
 
        int top = stack.getTop();
        stack.remove();
 
        boolean found = top == element || contains(stack, element);
 
        stack.add(top); // reconstruimos la pila
 
        return found;
    }
}