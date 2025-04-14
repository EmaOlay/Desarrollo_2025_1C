// testmanual/VersionedStackManualTest.java
package TestManual;

import model.VersionedStack;

public class VersionedStackManualTest {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas manuales para VersionedStack:");

        testPushAndGetTop();
        testPop();
        testIsEmpty();
        // testVersionCreationOnPush();
        // testVersionCreationOnPop();
        // Falta implementar las pruebas para deleteVersion y createVersionFrom (P(a) y P(b))
        // y goToVersion (P(c) - a elección).
    }

    public static void testPushAndGetTop() {
        System.out.println("\nEjecutando prueba: testPushAndGetTop");
        VersionedStack stack = new VersionedStack(5); // Inicializamos con un tamaño máximo para esta prueba
        // Versión 0: Vacía
        reportResult("testPushAndGetTop - Versión 0: isEmpty", stack.isEmpty());

        stack.add(10);
        // Versión 1: [10]
        reportResult("testPushAndGetTop - Versión 1: getTop", stack.getTop() == 10);
        reportResult("testPushAndGetTop - Versión 1: isEmpty", !stack.isEmpty());

        stack.add(20);
        // Versión 2: [10, 20]
        reportResult("testPushAndGetTop - Versión 2: getTop", stack.getTop() == 20);

        stack.add(30);
        // Versión 3: [10, 20, 30]
        reportResult("testPushAndGetTop - Versión 3: getTop", stack.getTop() == 30);
    }

    public static void testPop() {
        System.out.println("\nEjecutando prueba: testPop");
        VersionedStack stack = new VersionedStack(5);

        try {
            stack.remove();
            reportResult("testPop - Versión 0: pop en pila vacía", false); // Debería lanzar excepción
        } catch (RuntimeException e) {
            reportResult("testPop - Versión 0: pop en pila vacía (excepción)", e.getMessage().equals("Can't unstack current version."));
        }

        stack.add(5); // Versión 1: [5]
        stack.add(10); // Versión 2: [5, 10]

        stack.remove(); // Versión 3: [5]
        reportResult("testPop - Versión 3: getTop después de pop", stack.getTop() == 5);

        stack.remove(); // Versión 4: []
        reportResult("testPop - Versión 4: isEmpty después de pop", stack.isEmpty());

        try {
            stack.remove(); // Versión 5 (intento de pop en vacía)
            reportResult("testPop - Versión 5: pop en pila vacía", false); // Debería lanzar excepción
        } catch (RuntimeException e) {
            reportResult("testPop - Versión 5: pop en pila vacía (excepción)", e.getMessage().equals("Can't unstack current version."));
        }
    }

    public static void testIsEmpty() {
        System.out.println("\nEjecutando prueba: testIsEmpty");
        VersionedStack stack = new VersionedStack(5);

        // Versión 0:
        reportResult("testIsEmpty - Versión 0: inicialmente vacía", stack.isEmpty());

        stack.add(1); // Versión 1: [1]
        reportResult("testIsEmpty - Versión 1: después de add", !stack.isEmpty());

        stack.remove(); // Versión 2: []
        reportResult("testIsEmpty - Versión 2: después de remove", stack.isEmpty());
    }

    public static void testVersionCreationOnPush() {
        System.out.println("\nEjecutando prueba: testVersionCreationOnPush");
        VersionedStack stack = new VersionedStack(5);

        // Versión 0 (inicial): []

        stack.add(1); // Versión 1: [1]
        // Para verificar manualmente, observar la estructura interna (matriz y tops) después de la operación.
        // Se espera que nextVersionIndex haya incrementado.

        stack.add(2); // Versión 2: [1, 2]
        // Verificar nuevamente la estructura interna.

        stack.add(3); // Versión 3: [1, 2, 3]
        // Verificar nuevamente.
    }

    public static void testVersionCreationOnPop() {
        System.out.println("\nEjecutando prueba: testVersionCreationOnPop");
        VersionedStack stack = new VersionedStack(5);

        stack.add(1); // Versión 1: [1]
        stack.add(2); // Versión 2: [1, 2]

        stack.remove(); // Versión 3: [1]
        // Verificar la estructura interna para asegurar que se creó una nueva versión.

        stack.remove(); // Versión 4: []
        // Verificar nuevamente la creación de una nueva versión.
    }

    // Métodos de prueba para P(a), P(b) y P(c) (goToVersion)

    // ... (Implementar testDeleteVersion, testCreateVersionFrom, testGoToVersion) ...

    public static void reportResult(String testName, boolean passed) {
        if (passed) {
            System.out.println("[PASSED] " + testName);
        } else {
            System.out.println("[FAILED] " + testName);
        }
    }
}