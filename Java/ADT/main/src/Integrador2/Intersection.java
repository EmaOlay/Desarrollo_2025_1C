package Integrador2;


public class Intersection {
 
    public static StackDupla intersection(StackDupla stack, StaticPriorityQueue queue) {
        StackDupla result = new StackDupla();
        StackDupla tempStack = new StackDupla();
        StaticPriorityQueue tempQueue;
 
        while (!stack.isEmpty()) {
            Dupla top = stack.getTop();
            stack.remove();
            tempStack.add(top);
 
            tempQueue = cloneQueue(queue);
            if (containsValue(tempQueue, top.second)) {
                result.add(top);
            }
        }
 
        // Restaurar la pila original
        while (!tempStack.isEmpty()) {
            stack.add(tempStack.getTop());
            tempStack.remove();
        }
 
        return result;
    }
 
    private static boolean containsValue(StaticPriorityQueue queue, int value) {
        StaticPriorityQueue temp = cloneQueue(queue);
        while (!temp.isEmpty()) {
            if (temp.getFirst() == value) {
                return true;
            }
            temp.remove();
        }
        return false;
    }
 
    private static StaticPriorityQueue cloneQueue(StaticPriorityQueue original) {
        StaticPriorityQueue copy = new StaticPriorityQueue();
        StaticPriorityQueue temp = new StaticPriorityQueue();
 
        while (!original.isEmpty()) {
            int val = original.getFirst();
            int pri = original.getPriority();
            original.remove();
            temp.add(pri, val);
        }
 
        while (!temp.isEmpty()) {
            int val = temp.getFirst();
            int pri = temp.getPriority();
            temp.remove();
            original.add(pri, val);
            copy.add(pri, val);
        }
 
        return copy;
    }
}