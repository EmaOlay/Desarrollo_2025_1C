package TestManual;

import model.FlatDequeueMutable;
import util.FlatDequeueUtil;

public class FlatDequeueUtil_Test {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas para FlatDequeueUtil:");

        testReverse();
        testPrint();
        testCopy();
        testMapInPlace();
        testFindFirst();
        testAllMatch();
        testCount();
        testGetN();
    }

    public static void testReverse() {
        System.out.println("\nEjecutando prueba: testReverse");
        FlatDequeueMutable queue1 = new FlatDequeueMutable();
        FlatDequeueUtil.reverse(queue1);
        reportResult("testReverse - Caso 1 (cola vacía)", queue1.isEmpty());

        FlatDequeueMutable queue2 = new FlatDequeueMutable();
        queue2.add(1);
        FlatDequeueUtil.reverse(queue2);
        reportResult("testReverse - Caso 2 (un elemento)", queue2.poll() == 1 && queue2.isEmpty());

        FlatDequeueMutable queue3 = new FlatDequeueMutable();
        queue3.add(1);
        queue3.add(2);
        queue3.add(3);
        FlatDequeueUtil.reverse(queue3);
        reportResult("testReverse - Caso 3 (múltiples elementos)",
                     queue3.poll() == 3 && queue3.poll() == 2 && queue3.poll() == 1 && queue3.isEmpty());
    }

    public static void testPrint() {
        System.out.println("\nEjecutando prueba: testPrint");
        FlatDequeueMutable queue = new FlatDequeueMutable();
        queue.add(1);
        queue.add(2);
        // No podemos verificar directamente la System.out.println,
        // pero podemos verificar que la cola no se vacía.
        int initialSize = queue.size();
        FlatDequeueUtil.print(queue);
        reportResult("testPrint - Caso 1 (múltiples elementos)", queue.size() == initialSize);
    }

    public static void testCopy() {
        System.out.println("\nEjecutando prueba: testCopy");
        FlatDequeueMutable queue1 = new FlatDequeueMutable();
        FlatDequeueMutable copy1 = FlatDequeueUtil.copy(queue1);
        reportResult("testCopy - Caso 1 (cola vacía)", copy1 != null && copy1.isEmpty());

        FlatDequeueMutable queue2 = new FlatDequeueMutable();
        queue2.add(1);
        queue2.add(2);
        FlatDequeueMutable copy2 = FlatDequeueUtil.copy(queue2);
        boolean copyCorrect = copy2 != null && copy2.poll() == 1 && copy2.poll() == 2 && copy2.isEmpty();
        boolean originalUnchanged = queue2.poll() == 1 && queue2.poll() == 2 && queue2.isEmpty();
        reportResult("testCopy - Caso 2 (múltiples elementos)", copyCorrect && originalUnchanged);
    }

    public static void testMapInPlace() {
        System.out.println("\nEjecutando prueba: testMapInPlace");
        FlatDequeueMutable queue1 = new FlatDequeueMutable();
        FlatDequeueUtil.mapInPlace(queue1, n -> n + 1);
        reportResult("testMapInPlace - Caso 1 (cola vacía)", queue1.isEmpty());

        FlatDequeueMutable queue2 = new FlatDequeueMutable();
        queue2.add(1);
        queue2.add(2);
        queue2.add(3);
        FlatDequeueUtil.mapInPlace(queue2, n -> n + 1);
        reportResult("testMapInPlace - Caso 2 (múltiples elementos)",
                     queue2.poll() == 2 && queue2.poll() == 3 && queue2.poll() == 4 && queue2.isEmpty());
    }

    public static void testFindFirst() {
        System.out.println("\nEjecutando prueba: testFindFirst");
        FlatDequeueMutable queue1 = new FlatDequeueMutable();
        Integer result1 = FlatDequeueUtil.findFirst(queue1, 1);
        reportResult("testFindFirst - Caso 1 (cola vacía)", result1 == null);

        FlatDequeueMutable queue2 = new FlatDequeueMutable();
        queue2.add(1);
        queue2.add(3);
        queue2.add(2);
        Integer result2 = FlatDequeueUtil.findFirst(queue2, 3);
        boolean originalUnchanged2 = queue2.poll() == 1 && queue2.poll() == 3 && queue2.poll() == 2 && queue2.isEmpty();
        reportResult("testFindFirst - Caso 2 (elemento encontrado)", result2 != null && result2 == 3 && originalUnchanged2);

        FlatDequeueMutable queue3 = new FlatDequeueMutable();
        queue3.add(1);
        queue3.add(2);
        Integer result3 = FlatDequeueUtil.findFirst(queue3, 3);
        boolean originalUnchanged3 = queue3.poll() == 1 && queue3.poll() == 2 && queue3.isEmpty();
        reportResult("testFindFirst - Caso 3 (elemento no encontrado)", result3 == null && originalUnchanged3);
    }

    public static void testGetN() {
        System.out.println("\nEjecutando prueba: testGetN");
    
        FlatDequeueMutable queue1 = new FlatDequeueMutable();
        try {
            FlatDequeueUtil.getN(queue1, 0);
            reportResult("testGetN - Caso 1 (cola vacía)", false); // Debería lanzar excepción
        } catch (RuntimeException e) {
            reportResult("testGetN - Caso 1 (cola vacía)", e.getMessage().equals("No se puede obtener la posicion indicada."));
        }
    
        FlatDequeueMutable queue2 = new FlatDequeueMutable();
        queue2.add(10);
        queue2.add(20);
        queue2.add(30);
        try {
            int result2 = FlatDequeueUtil.getN(queue2, 1);
            boolean originalUnchanged2 = queue2.poll() == 10 && queue2.poll() == 20 && queue2.poll() == 30 && queue2.isEmpty();
            reportResult("testGetN - Caso 2 (posición válida)", result2 == 20 && originalUnchanged2);
        } catch (RuntimeException e) {
            reportResult("testGetN - Caso 2 (posición válida)", false);
        }
    
        FlatDequeueMutable queue3 = new FlatDequeueMutable();
        queue3.add(5);
        try {
            int get_res = FlatDequeueUtil.getN(queue3, 0);
            boolean originalUnchanged3 = queue3.poll() == 5 && queue3.isEmpty();
            reportResult("testGetN - Caso 3 (posición cero)", get_res == 5 && originalUnchanged3);
        } catch (RuntimeException e) {
            reportResult("testGetN - Caso 3 (posición cero)", false);
        }
    
        FlatDequeueMutable queue4 = new FlatDequeueMutable();
        queue4.add(1);
        queue4.add(2);
        try {
            FlatDequeueUtil.getN(queue4, 2);
            reportResult("testGetN - Caso 4 (posición fuera de rango)", false); // Debería lanzar excepción
        } catch (RuntimeException e) {
            reportResult("testGetN - Caso 4 (posición fuera de rango)", e.getMessage().equals("No se puede obtener la posicion indicada."));
        }
    
        FlatDequeueMutable queue5 = new FlatDequeueMutable();
        queue5.add(100);
        queue5.add(200);
        queue5.add(300);
        queue5.add(400);
        try {
            int result5 = FlatDequeueUtil.getN(queue5, 3);
            boolean originalUnchanged5 = queue5.poll() == 100 && queue5.poll() == 200 && queue5.poll() == 300 && queue5.poll() == 400 && queue5.isEmpty();
            reportResult("testGetN - Caso 5 (última posición)", result5 == 400 && originalUnchanged5);
        } catch (RuntimeException e) {
            reportResult("testGetN - Caso 5 (última posición)", false);
        }
    
        try {
            FlatDequeueUtil.getN(null, 0);
            reportResult("testGetN - Caso 6 (cola nula)", false); // Debería lanzar excepción
        } catch (RuntimeException e) {
            reportResult("testGetN - Caso 6 (cola nula)", e.getMessage().equals("No se puede obtener la posicion indicada."));
        }
    }

    public static void testAllMatch() {
        System.out.println("\nEjecutando prueba: testAllMatch");
        FlatDequeueMutable queue1 = new FlatDequeueMutable();
        boolean result1 = FlatDequeueUtil.allMatch(queue1, n -> n > 0);
        reportResult("testAllMatch - Caso 1 (cola vacía)", result1);

        FlatDequeueMutable queue2 = new FlatDequeueMutable();
        queue2.add(1);
        queue2.add(2);
        boolean result2 = FlatDequeueUtil.allMatch(queue2, n -> n > 0);
        boolean originalUnchanged2 = queue2.poll() == 1 && queue2.poll() == 2 && queue2.isEmpty();
        reportResult("testAllMatch - Caso 2 (todos coinciden)", result2 && originalUnchanged2);

        FlatDequeueMutable queue3 = new FlatDequeueMutable();
        queue3.add(1);
        queue3.add(-1);
        boolean result3 = FlatDequeueUtil.allMatch(queue3, n -> n > 0);
        boolean originalUnchanged3 = queue3.poll() == 1 && queue3.poll() == -1 && queue3.isEmpty();
        reportResult("testAllMatch - Caso 3 (no todos coinciden)", !result3 && originalUnchanged3);
    }

    public static void testCount() {
        System.out.println("\nEjecutando prueba: testCount");
        FlatDequeueMutable queue1 = new FlatDequeueMutable();
        int count1 = FlatDequeueUtil.count(queue1, n -> n % 2 == 0);
        reportResult("testCount - Caso 1 (cola vacía)", count1 == 0);

        FlatDequeueMutable queue2 = new FlatDequeueMutable();
        queue2.add(1);
        queue2.add(2);
        queue2.add(3);
        int count2 = FlatDequeueUtil.count(queue2, n -> n % 2 == 0);
        boolean originalUnchanged2 = queue2.poll() == 1 && queue2.poll() == 2 && queue2.poll() == 3 && queue2.isEmpty();
        reportResult("testCount - Caso 2 (múltiples elementos)", count2 == 1 && originalUnchanged2);
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