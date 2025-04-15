// testmanual/VersionedQueueManualTest.java
package TestManual;

import model.VersionedQueue;

public class Main3 {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas manuales para VersionedQueue:");

        testEnqueueAndGetFirst();
        testDequeue();
        testIsEmpty();
    }

    public static void testEnqueueAndGetFirst() {
        System.out.println("\nEjecutando prueba: testEnqueueAndGetFirst");
        VersionedQueue queue = new VersionedQueue();

        // Versión 0: Vacía
        reportResult("testEnqueueAndGetFirst - Versión 0: isEmpty", queue.isEmpty());

        queue.add(10);
        // Versión 1: [10] (frente)
        reportResult("testEnqueueAndGetFirst - Versión 1: getFirst", queue.getFirst() == 10);
        reportResult("testEnqueueAndGetFirst - Versión 1: isEmpty", !queue.isEmpty());

        queue.add(20);
        // Versión 2: [10] (frente), [20] (final)
        reportResult("testEnqueueAndGetFirst - Versión 2: getFirst", queue.getFirst() == 10);

        queue.add(30);
        // Versión 3: [10] (frente), [20], [30] (final)
        reportResult("testEnqueueAndGetFirst - Versión 3: getFirst", queue.getFirst() == 10);
    }

    public static void testDequeue() {
        System.out.println("\nEjecutando prueba: testDequeue");
        VersionedQueue queue = new VersionedQueue();

        try {
            queue.remove();
            reportResult("testDequeue - Versión 0: dequeue en cola vacía", false); // Debería lanzar excepción
        } catch (RuntimeException e) {
            reportResult("testDequeue - Versión 0: dequeue en cola vacía (excepción)", e.getMessage().equals("Can't dequeue from an empty queue."));
        }

        queue.add(5);  // Versión 1: [5]
        queue.add(10); // Versión 2: [5, 10]
        queue.remove(); // Versión 3: [10] (frente)
        reportResult("testDequeue - Versión 1: isEmpty", !queue.isEmpty());

        queue.remove(); // Versión 4: []
        reportResult("testDequeue - Versión 2: isEmpty después de segundo dequeue", queue.isEmpty());

        try {
            queue.remove(); // Versión 5 (intento de dequeue en vacía)
            reportResult("testDequeue - Versión 3: dequeue en cola vacía", false); // Debería lanzar excepción
        } catch (RuntimeException e) {
            reportResult("testDequeue - Versión 3: dequeue en cola vacía (excepción)", e.getMessage().equals("Can't dequeue from an empty queue."));
        }

        // Prueba con múltiples enqueue antes de dequeue
        VersionedQueue queue2 = new VersionedQueue();
        queue2.add(1);
        queue2.add(2);
        queue2.add(3);
        queue2.remove();
        reportResult("testDequeue - Versión 4 (queue2): getFirst", queue2.getFirst() == 2);
        queue2.remove();
        queue2.remove();
        reportResult("testDequeue - Versión 5 (queue2): isEmpty", queue2.isEmpty());
    }

    public static void testIsEmpty() {
        System.out.println("\nEjecutando prueba: testIsEmpty");
        VersionedQueue queue = new VersionedQueue();

        // Versión 0:
        reportResult("testIsEmpty - Versión 0: inicialmente vacía", queue.isEmpty());

        queue.add(1); // Versión 1: [1]
        reportResult("testIsEmpty - Versión 1: después de add", !queue.isEmpty());

        queue.remove(); // Versión 2: []
        reportResult("testIsEmpty - Versión 2: después de remove", queue.isEmpty());

        queue.add(5);  // Versión 3: [5]
        queue.add(10); // Versión 4: [5, 10]
        queue.remove(); // Versión 5: [10]
        reportResult("testIsEmpty - Versión 5: después de un add y un remove", !queue.isEmpty());
        queue.remove(); // Versión 6: []
        reportResult("testIsEmpty - Versión 6: después de dos add y dos remove", queue.isEmpty());
    }

    public static void reportResult(String testName, boolean passed) {
        if (passed) {
            System.out.println("[PASSED] " + testName);
        } else {
            System.out.println("[FAILED] " + testName);
        }
    }
}