package util;

import model.FlatDequeueMutable;

public class FlatDequeueUtil {

    private FlatDequeueUtil() {
        // Constructor privado para evitar la instanciación
    }
    /**
     * Devuelvo el elemento en position siendo position 0 index
     * 
     */
    public static int getN(FlatDequeueMutable queue,int position) {
        if (queue == null || queue.isEmpty() || queue.size() <= position) {
            throw new RuntimeException("No se puede obtener la posicion indicada.");
        }
        int result = -1;
        for (int i = 0 ; i< queue.size();i++){
            int element = queue.poll();
            if (i == position) {
                result = element;
            }
            queue.add(element); // re-enqueue
        }
        return result;
    }
    /**
     * Invierte el orden de los elementos en una FlatDequeueMutable in-place
     *
     * Precondiciones:
     * - `queue` no debe ser null.
     *
     * Postcondiciones:
     * - El orden de los elementos dentro de `queue` estará invertido.
     * - Si la cola estaba vacía o tenía un solo elemento, permanecerá sin cambios.
     *
     * Estrategia de Implementación:
     * 1. Se utiliza una cola auxiliar (`auxiliaryQueue`) para almacenar
     * temporalmente los elementos.
     * 2. Se extraen todos los elementos de la cola original y se añaden a la cola
     * auxiliar (manteniendo el orden original).
     * 3. Luego, se itera `size` veces. En cada iteración, se mueve el último
     * elemento de la cola auxiliar al frente,
     * y luego se extrae ese elemento del frente de la cola auxiliar y se añade al
     * final de la cola original.
     * Esto efectivamente invierte el orden.
     */
    public static void reverse(FlatDequeueMutable queue) {
        if (queue == null || queue.isEmpty() || queue.size() == 1) {
            return;
        }
        int size = queue.size();
        FlatDequeueMutable auxiliaryQueue = new FlatDequeueMutable();

        // Pasar todos los elementos a la cola auxiliar
        for (int i = 0; i < size; i++) {
            auxiliaryQueue.add(queue.poll());
        }

        // Volver a añadir los elementos a la cola original en orden inverso
        for (int i = 0; i < size; i++) {
            // Mover todos los elementos excepto el último al frente
            for (int j = 0; j < auxiliaryQueue.size() - 1; j++) {
                auxiliaryQueue.add(auxiliaryQueue.poll());
            }
            // El último elemento ahora está al frente, lo volvemos a añadir a la cola
            // original
            queue.add(auxiliaryQueue.poll());
        }
    }

    /**
     * Imprime los elementos de la FlatDequeueMutable en el orden en que se
     * encuentran.
     *
     * Precondiciones:
     * - `queue` no debe ser null.
     *
     * Postcondiciones:
     * - Los elementos de la cola se habrán impreso en la consola en su orden
     * actual.
     * - La cola `queue` permanecerá inalterada.
     *
     * Estrategia de Implementación:
     * 1. Se obtiene el tamaño de la cola.
     * 2. Se itera sobre los elementos de la cola `size` veces.
     * 3. En cada iteración, se extrae el elemento del frente de la cola, se imprime
     * en la consola,
     * y luego se vuelve a añadir al final de la cola para preservar el orden y el
     * contenido.
     */
    public static void print(FlatDequeueMutable queue) {
        String resultado = "";
        if (queue == null || queue.isEmpty()) {
            return;
        }
        int size = queue.size();
        FlatDequeueMutable aux = copy(queue);
        for (int i = 0; i < size; i++) {
            int element = aux.poll();
            resultado = resultado+element + ",";
        }
        System.out.println(resultado.substring(0, resultado.length()-1));
    }

    /**
     * Devuelve una copia superficial de la FlatDequeueMutable.
     *
     * Precondiciones:
     * - `queue` puede ser null.
     *
     * Postcondiciones:
     * - Se devolverá una nueva instancia de `FlatDequeueMutable` que contiene los
     * mismos elementos
     * que la cola original en el mismo orden.
     * - Si `queue` es null, se devolverá null.
     * - La cola original `queue` permanecerá inalterada.
     *
     * Estrategia de Implementación:
     * 1. Si la cola de entrada es null, se devuelve null.
     * 2. Se crea una nueva instancia de `FlatDequeueMutable` (`copy`).
     * 3. Se obtiene el tamaño de la cola original.
     * 4. Se itera sobre los elementos de la cola original `size` veces.
     * 5. En cada iteración, se extrae un elemento de la cola original, se añade a
     * la copia,
     * y luego se vuelve a añadir al final de la cola original para mantenerla
     * intacta.
     * 6. Se devuelve la cola `copy`.
     */
    public static FlatDequeueMutable copy(FlatDequeueMutable queue) {
        if (queue == null) {
            return null;
        }
        FlatDequeueMutable copy = new FlatDequeueMutable();
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int element = queue.poll();
            copy.add(element);
            queue.add(element); // Mantener la cola original
        }
        return copy;
    }

    /**
     * Elimina todos los elementos de la FlatDequeueMutable que cumplen una
     * condición definida por IntCondition.
     * Esta operación se realiza in-place sin estructuras externas.
     *
     * Precondiciones:
     * - `queue` no debe ser null.
     *
     * Postcondiciones:
     * - La cola `queue` contendrá solo los elementos que **no** cumplen la
     * `condition`.
     * - El orden relativo de los elementos que se conservan se mantendrá.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la cola es null o si la cola está vacía. En
     * esos casos, no se hace nada.
     * 2. Se crea una cola auxiliar (`tempQueue`) para almacenar los elementos que
     * **no** cumplen la `condition`.
     * 3. Se itera sobre la cola original hasta que esté vacía.
     * 4. En cada iteración, se extrae un elemento de la cola original.
     * 5. Se compara el valor del elemento con la `condition`.
     * 6. Si el elemento **no** cumple la `condition`, se añade a la `tempQueue`.
     * 7. Después de procesar todos los elementos de la cola original, se vacía la
     * cola original.
     * 8. Se transfieren todos los elementos desde la `tempQueue` de vuelta a la
     * cola original.
     */
    public static void filterInPlace(FlatDequeueMutable queue, int condition) {
        if (queue == null || queue.isEmpty()) {
            return;
        }

        FlatDequeueMutable tempQueue = new FlatDequeueMutable();
        int initialSize = queue.size(); // Guardar el tamaño inicial para la iteración

        for (int i = 0; i < initialSize; i++) {
            int element = queue.poll();
            // Comparamos el valor usando la condición
            if (!(element==condition)) { // Invertimos la lógica para mantener los que NO cumplen
                tempQueue.add(element);
            }
        }

        // Limpiar la cola original
        while (!queue.isEmpty()) {
            queue.poll();
        }

        // Re-añadir los elementos que NO cumplieron la condición
        while (!tempQueue.isEmpty()) {
            queue.add(tempQueue.poll());
        }
    }

    /**
     * Aplica una función a cada elemento de la FlatDequeueMutable in-place
     * sin estructuras externas.
     */
    public static void mapInPlace(FlatDequeueMutable queue, java.util.function.IntUnaryOperator mapper) {
        if (queue == null || queue.isEmpty()) {
            return;
        }
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int element = queue.poll();
            queue.add(mapper.applyAsInt(element));
        }
    }

    /**
     * Busca el primer elemento en la FlatDequeueMutable que cumpla un predicado
     * sin estructuras externas.
     * Devuelve el elemento si se encuentra, o null si no. La cola no se modifica.
     */
    public static Integer findFirst(FlatDequeueMutable queue, int number) {
        if (queue == null || queue.isEmpty()) {
            return null;
        }
        int size = queue.size();
        Integer foundElement = null;
        for (int i = 0; i < size; i++) {
            int element = queue.poll();
            if (element == number) {
                foundElement = element;
            }
            queue.add(element); // Re-enqueue
        }
        return foundElement;
    }

    /**
     * Verifica si todos los elementos de la FlatDequeueMutable cumplen un predicado
     * sin estructuras externas.
     */
    public static boolean allMatch(FlatDequeueMutable queue, java.util.function.IntPredicate predicate) {
        if (queue == null || queue.isEmpty()) {
            return true;
        }
        int size = queue.size();
        boolean allMatch = true;
        for (int i = 0; i < size; i++) {
            int element = queue.poll();
            if (!predicate.test(element)) {
                allMatch = false;
            }
            queue.add(element); // Re-enqueue
            if (!allMatch) {
                break;
            }
        }
        return allMatch;
    }

    /**
     * Cuenta el número de elementos en la FlatDequeueMutable que cumplen un
     * predicado
     * sin estructuras externas.
     */
    public static int count(FlatDequeueMutable queue, java.util.function.IntPredicate predicate) {
        if (queue == null || queue.isEmpty()) {
            return 0;
        }
        int size = queue.size();
        int count = 0;
        for (int i = 0; i < size; i++) {
            int element = queue.poll();
            if (predicate.test(element)) {
                count++;
            }
            queue.add(element); // Re-enqueue
        }
        return count;
    }
}