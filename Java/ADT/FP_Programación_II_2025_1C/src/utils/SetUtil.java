// utils/SetUtil.java
package utils;

import implementations.ArraySet;
import implementations.ArrayStack;
import implementations.ArrayQueue;
import interfaces.Queue;
import interfaces.Set;
import interfaces.Stack;

public class SetUtil {

    // Precondicion: Ninguna.
    // Complejidad: O(N+M) donde N es el numero de elementos en la pila y M es el numero de elementos en la cola.
    // Estrategia:
    // 1. Crear dos conjuntos (Set) vacios.
    // 2. Iterar sobre la pila, agregando cada elemento al primer conjunto y manteniendo el orden de la pila
    //    (guardando los elementos temporalmente en otra pila para restaurar la original).
    // 3. Iterar sobre la cola, agregando cada elemento al segundo conjunto y manteniendo el orden de la cola
    //    (guardando los elementos temporalmente en otra cola para restaurar la original).
    // 4. Comparar si los dos conjuntos son iguales (mismo tamaño y todos los elementos de uno estan en el otro).
    public static boolean areStackAndQueueEqual(Stack s, Queue q) {
        if (s == null || q == null) {
            throw new IllegalArgumentException("Stack and Queue cannot be null.");
        }

        Set setFromStack = new ArraySet();
        Set setFromQueue = new ArraySet();

        // Populate setFromStack and restore original stack
        ArrayStack tempStack = new ArrayStack();
        while (!s.isEmpty()) {
            int element = s.getTop();
            s.remove();
            setFromStack.add(element);
            tempStack.add(element);
        }
        // Restore original stack
        while (!tempStack.isEmpty()) {
            s.add(tempStack.getTop());
            tempStack.remove();
        }

        // Populate setFromQueue and restore original queue
        ArrayQueue tempQueue = new ArrayQueue();
        while (!q.isEmpty()) {
            int element = q.getFirst();
            q.remove();
            setFromQueue.add(element);
            tempQueue.add(element);
        }
        // Restore original queue
        while (!tempQueue.isEmpty()) {
            q.add(tempQueue.getFirst());
            tempQueue.remove();
        }

        // Compare the two sets
        if (setFromStack.isEmpty() && setFromQueue.isEmpty()) {
            return true; // Ambos vacios, son iguales.
        }
        if (setFromStack.isEmpty() || setFromQueue.isEmpty()) {
            return false; // Uno vacio y el otro no.
        }

        // Convert one set to a temporary stack to iterate and compare
        // Note: ArraySet does not expose its internal array directly.
        // We'll use choose() and remove() to iterate, then re-add.
        ArrayStack elementsOfSetFromStack = new ArrayStack();
        while(!setFromStack.isEmpty()){
            int element = setFromStack.choose();
            setFromStack.remove(element); // CORREGIDO: Pasar el elemento a remover
            elementsOfSetFromStack.add(element);
        }

        int originalStackSetSize = 0;
        // Check if all elements in setFromStack are in setFromQueue
        while (!elementsOfSetFromStack.isEmpty()) {
            int element = elementsOfSetFromStack.getTop();
            elementsOfSetFromStack.remove();
            setFromStack.add(element); // Re-add to restore setFromStack
            originalStackSetSize++;
            if (!setFromQueue.contains(element)) {
                return false;
            }
        }

        // Check if the sizes are equal. If not, they don't contain the same elements.
        // This also implicitly checks if all elements in setFromQueue are in setFromStack
        // since we already verified the other direction.
        // ArraySet doesn't expose size, so we need to compute it.
        ArrayStack elementsOfSetFromQueue = new ArrayStack();
        while(!setFromQueue.isEmpty()){
            int element = setFromQueue.choose();
            setFromQueue.remove(element); // CORREGIDO: Pasar el elemento a remover
            elementsOfSetFromQueue.add(element);
        }

        int originalQueueSetSize = 0;
        while (!elementsOfSetFromQueue.isEmpty()) {
            int element = elementsOfSetFromQueue.getTop();
            elementsOfSetFromQueue.remove();
            setFromQueue.add(element); // Re-add to restore setFromQueue
            originalQueueSetSize++;
        }

        return originalStackSetSize == originalQueueSetSize;
    }
}