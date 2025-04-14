package model;

public class VersionedQueue implements Queue {

    private final VersionedStack stackForEnqueue;
    private final VersionedStack stackForDequeue;

    /**
     * Constructor para una VersionedQueue.
     * Utiliza dos VersionedStack internos para implementar la funcionalidad de la
     * cola.
     */
    public VersionedQueue() {
        this.stackForEnqueue = new VersionedStack();
        this.stackForDequeue = new VersionedStack();
    }

    /**
     * Agrega un elemento al final de la cola, creando una nueva versión de las
     * pilas internas.
     *
     * Precondiciones: Ninguna.
     *
     * Postcondiciones: El elemento `a` se agrega al final de la cola en la nueva
     * versión.
     * Se crea una nueva versión de `stackForEnqueue`.
     *
     * Estrategia de Implementación:
     * 1. Simplemente agregar el elemento al `stackForEnqueue`. Los elementos se
     * encolan en la primera pila.
     */
    @Override
    public void add(int a) {
        stackForEnqueue.add(a);
    }

    /**
     * Remueve el elemento del frente de la cola, creando una nueva versión de las
     * pilas internas.
     *
     * Precondiciones: La cola no debe estar vacía (`!isEmpty()`).
     *
     * Postcondiciones: El elemento del frente de la cola se remueve en la nueva
     * versión.
     * Se crean nuevas versiones de `stackForEnqueue` y/o `stackForDequeue` si es
     * necesario.
     *
     * Estrategia de Implementación:
     * 1. Si `stackForDequeue` está vacía, transferir todos los elementos de
     * `stackForEnqueue` a `stackForDequeue` invirtiendo el orden (simulando el
     * comportamiento de una cola).
     * 2. Remover el elemento de la cima de `stackForDequeue`. Este será el elemento
     * del frente de la cola.
     * 3. Si la cola estaba vacía, lanzar una `RuntimeException`.
     */
    @Override
    public void remove() {
        if (isEmpty()) {
            throw new RuntimeException("Can't dequeue from an empty queue.");
        }
        // Si la pila para desencolar está vacía, transferir elementos de la pila para
        // encolar
        if (stackForDequeue.isEmpty()) {
            transferEnqueueToDequeue();
        }
        stackForDequeue.remove();
    }

    /**
     * Verifica si la cola está vacía en la versión actual de las pilas internas.
     *
     * Precondiciones: Ninguna.
     *
     * Postcondiciones: Devuelve `true` si ambas pilas internas están vacías en su
     * versión actual, `false` en caso contrario.
     *
     * Estrategia de Implementación:
     * 1. Verificar si ambas `stackForEnqueue` y `stackForDequeue` están vacías en
     * su versión actual.
     */
    @Override
    public boolean isEmpty() {
        return stackForEnqueue.isEmpty() && stackForDequeue.isEmpty();
    }

    /**
     * Obtiene el elemento del frente de la cola en la versión actual de las pilas
     * internas.
     *
     * Precondiciones: La cola no debe estar vacía (`!isEmpty()`).
     *
     * Postcondiciones: Devuelve el elemento del frente de la cola en la versión
     * actual. La cola no se modifica.
     *
     * Estrategia de Implementación:
     * 1. Si `stackForDequeue` está vacía, transferir todos los elementos de
     * `stackForEnqueue` a `stackForDequeue` invirtiendo el orden.
     * 2. Obtener el elemento de la cima de `stackForDequeue`. Este será el elemento
     * del frente de la cola.
     * 3. Si la cola estaba vacía, lanzar una `RuntimeException`.
     */
    @Override
    public int getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("Can't get first element of an empty queue.");
        }
        // Si la pila para desencolar está vacía, transferir elementos de la pila para
        // encolar
        if (stackForDequeue.isEmpty()) {
            transferEnqueueToDequeue();
        }
        return stackForDequeue.getTop();
    }

    /**
     *      * Método auxiliar para transferir elementos de `stackForEnqueue` a
     * `stackForDequeue` invirtiendo el orden.
     *      * Esto simula el comportamiento FIFO de una cola utilizando dos pilas
     * LIFO.
     *      
     */
    private void transferEnqueueToDequeue() {
        VersionedStack tempStack = new VersionedStack();
        // Pasar todos los elementos de enqueue a una pila temporal (invirtiendo el
        // orden)
        while (!stackForEnqueue.isEmpty()) {
            tempStack.add(stackForEnqueue.getTop());
            stackForEnqueue.remove();
        }
        // Pasar los elementos de la pila temporal a dequeue (manteniendo el orden FIFO)
        while (!tempStack.isEmpty()) {
            stackForDequeue.add(tempStack.getTop());
            tempStack.remove();
        }
        // No necesitamos volver a una versión anterior de stackForEnqueue.
        // La pila se vació intencionalmente para la transferencia.
    }
}