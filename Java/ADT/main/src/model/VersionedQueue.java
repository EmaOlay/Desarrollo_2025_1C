package model;

public class VersionedQueue implements Queue {

    private final VersionedStack stackForEnqueue;
    private final VersionedStack stackForDequeue;

    public VersionedQueue() {
        this.stackForEnqueue = new VersionedStack();
        this.stackForDequeue = new VersionedStack();
    }


    @Override
    public void add(int a) {
        stackForEnqueue.add(a);
    }


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


    @Override
    public boolean isEmpty() {
        return stackForEnqueue.isEmpty() && stackForDequeue.isEmpty();
    }


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