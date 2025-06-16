// testmanual/FlatDequeueMutableTestManual.java
package TestManual;

import model.FlatDequeueMutable;

public class FlatDequeueMutable_Test {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas para FlatDequeueMutable:");

        testAddAndPoll();
        testIsEmpty();
        testAddAndGetFirst();
        testRemove();
        testPollOnEmptyQueue();
        testRemoveOnEmptyQueue();
        testGetFirstOnEmptyQueue();
    }

    public static void testAddAndPoll() {
        System.out.println("\nEjecutando prueba: testAddAndPoll");
        FlatDequeueMutable queue = new FlatDequeueMutable();
        queue.add(1);
        queue.add(2);

        boolean test1Passed = queue.poll() == 1;
        boolean test2Passed = queue.poll() == 2;
        boolean test3Failed = false;
        try {
            queue.poll();
        } catch (RuntimeException e) {
            test3Failed = e.getMessage().equals("Queue is Empty");
        }

        reportResult("testAddAndPoll - Caso 1 (poll primero)", test1Passed);
        reportResult("testAddAndPoll - Caso 2 (poll segundo)", test2Passed);
        reportResult("testAddAndPoll - Caso 3 (poll después de vaciar)", test3Failed);
    }

    public static void testIsEmpty() {
        System.out.println("\nEjecutando prueba: testIsEmpty");
        FlatDequeueMutable queue = new FlatDequeueMutable();

        boolean test1Passed = queue.isEmpty();
        queue.add(1);
        boolean test2Passed = !queue.isEmpty();
        queue.poll();
        boolean test3Passed = queue.isEmpty();

        reportResult("testIsEmpty - Caso 1 (inicialmente vacío)", test1Passed);
        reportResult("testIsEmpty - Caso 2 (después de agregar)", test2Passed);
        reportResult("testIsEmpty - Caso 3 (después de poll)", test3Passed);
    }

    public static void testAddAndGetFirst() {
        System.out.println("\nEjecutando prueba: testAddAndGetFirst");
        FlatDequeueMutable queue = new FlatDequeueMutable();
        queue.add(5);
        boolean test1Passed = queue.getFirst() == 5;
        queue.add(10);
        boolean test2Passed = queue.getFirst() == 5; // El primero no debería cambiar
        queue.poll();
        boolean test3Passed = queue.getFirst() == 10;

        reportResult("testAddAndGetFirst - Caso 1 (primer elemento)", test1Passed);
        reportResult("testAddAndGetFirst - Caso 2 (después de agregar más)", test2Passed);
        reportResult("testAddAndGetFirst - Caso 3 (después de poll)", test3Passed);
    }

    public static void testRemove() {
        System.out.println("\nEjecutando prueba: testRemove");
        FlatDequeueMutable queue = new FlatDequeueMutable();
        queue.add(10);
        queue.add(20);
        queue.remove();
        boolean test1Passed = !queue.isEmpty() && queue.getFirst() == 10 && queue.size() == 1;
        queue.remove();
        boolean test2Passed = queue.isEmpty() && queue.size() == 0;
        boolean test3Failed = false;
        try {
            queue.remove();
        } catch (RuntimeException e) {
            test3Failed = e.getMessage().equals("Queue is Empty");
        }

        reportResult("testRemove - Caso 1 (remover un elemento)", test1Passed);
        reportResult("testRemove - Caso 2 (remover el último elemento)", test2Passed);
        reportResult("testRemove - Caso 3 (remover de cola vacía)", test3Failed);
    }

    public static void testPollOnEmptyQueue() {
        System.out.println("\nEjecutando prueba: testPollOnEmptyQueue");
        FlatDequeueMutable queue = new FlatDequeueMutable();
        boolean testFailed = false;
        try {
            queue.poll();
        } catch (RuntimeException e) {
            testFailed = e.getMessage().equals("Queue is Empty");
        }
        reportResult("testPollOnEmptyQueue - Caso 1 (poll en cola vacía)", testFailed);
    }

    public static void testRemoveOnEmptyQueue() {
        System.out.println("\nEjecutando prueba: testRemoveOnEmptyQueue");
        FlatDequeueMutable queue = new FlatDequeueMutable();
        boolean testFailed = false;
        try {
            queue.remove();
        } catch (RuntimeException e) {
            testFailed = e.getMessage().equals("Queue is Empty");
        }
        reportResult("testRemoveOnEmptyQueue - Caso 1 (remove en cola vacía)", testFailed);
    }

    public static void testGetFirstOnEmptyQueue() {
        System.out.println("\nEjecutando prueba: testGetFirstOnEmptyQueue");
        FlatDequeueMutable queue = new FlatDequeueMutable();
        boolean testFailed = false;
        try {
            queue.getFirst();
        } catch (RuntimeException e) {
            testFailed = e.getMessage().equals("No se puede obtener el primero de una cola vacía.");
        }
        reportResult("testGetFirstOnEmptyQueue - Caso 1 (getFirst en cola vacía)", testFailed);
    }

    // Método utilitario para reportar los resultados de cada caso de prueba
    public static void reportResult(String testName, boolean passed) {
        if (passed) {
            System.out.println("[PASSED] " + testName);
        } else {
            System.out.println("[FAILED] " + testName);
        }
    }
}