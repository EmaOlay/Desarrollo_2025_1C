// testmanual/StaticListTestManual.java
package TestManual;

import model.StaticList;

public class StaticListTestManual {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas manuales para StaticList:");

        testAdd();
        testRemove();
        testGetFirst();
        testGetLast();
        testGet();
        testInsert();
        testDelete();
        testIsEmpty();
        testSize();
        testFind();
        testExceptions();
    }

    public static void testAdd() {
        System.out.println("\nEjecutando prueba: testAdd");
        StaticList list = new StaticList(3);
        list.add(10);
        reportResult("testAdd - Caso 1 (agregar primer elemento)", list.size() == 1 && list.get(0) == 10);
        list.add(20);
        reportResult("testAdd - Caso 2 (agregar segundo elemento)", list.size() == 2 && list.get(1) == 20);
        list.add(30);
        reportResult("testAdd - Caso 3 (agregar tercer elemento)", list.size() == 3 && list.get(2) == 30);
        try {
            list.add(40);
            reportResult("testAdd - Caso 4 (agregar a lista llena)", false);
        } catch (IllegalStateException e) {
            reportResult("testAdd - Caso 4 (agregar a lista llena)", e.getMessage().equals("La lista está llena."));
        }
    }

    public static void testRemove() {
        System.out.println("\nEjecutando prueba: testRemove");
        StaticList list = new StaticList(3);
        list.add(10);
        list.add(20);
        list.remove();
        reportResult("testRemove - Caso 1 (remover un elemento)", list.size() == 1 && list.getFirst() == 10);
        list.remove();
        reportResult("testRemove - Caso 2 (remover el último elemento)", list.isEmpty());
        try {
            list.remove();
            reportResult("testRemove - Caso 3 (remover de lista vacía)", false);
        } catch (IllegalStateException e) {
            reportResult("testRemove - Caso 3 (remover de lista vacía)", e.getMessage().equals("La lista está vacía."));
        }
    }

    public static void testGetFirst() {
        System.out.println("\nEjecutando prueba: testGetFirst");
        StaticList list = new StaticList(3);
        try {
            list.getFirst();
            reportResult("testGetFirst - Caso 1 (lista vacía)", false);
        } catch (IllegalStateException e) {
            reportResult("testGetFirst - Caso 1 (lista vacía)", e.getMessage().equals("La lista está vacía."));
        }
        list.add(5);
        reportResult("testGetFirst - Caso 2 (un elemento)", list.getFirst() == 5);
        list.add(10);
        reportResult("testGetFirst - Caso 3 (múltiples elementos)", list.getFirst() == 5);
    }

    public static void testGetLast() {
        System.out.println("\nEjecutando prueba: testGetLast");
        StaticList list = new StaticList(3);
        try {
            list.getLast();
            reportResult("testGetLast - Caso 1 (lista vacía)", false);
        } catch (IllegalStateException e) {
            reportResult("testGetLast - Caso 1 (lista vacía)", e.getMessage().equals("La lista está vacía."));
        }
        list.add(5);
        reportResult("testGetLast - Caso 2 (un elemento)", list.getLast() == 5);
        list.add(10);
        reportResult("testGetLast - Caso 3 (múltiples elementos)", list.getLast() == 10);
        list.add(20);
        reportResult("testGetLast - Caso 4 (tres elementos)", list.getLast() == 20);
    }

    public static void testGet() {
        System.out.println("\nEjecutando prueba: testGet");
        StaticList list = new StaticList(3);
        list.add(10);
        list.add(20);
        list.add(30);
        reportResult("testGet - Caso 1 (posición 0)", list.get(0) == 10);
        reportResult("testGet - Caso 2 (posición 1)", list.get(1) == 20);
        reportResult("testGet - Caso 3 (posición 2)", list.get(2) == 30);
        try {
            list.get(-1);
            reportResult("testGet - Caso 4 (posición negativa)", false);
        } catch (IndexOutOfBoundsException e) {
            reportResult("testGet - Caso 4 (posición negativa)", e.getMessage().equals("Posición inválida: -1"));
        }
        try {
            list.get(3);
            reportResult("testGet - Caso 5 (posición fuera de límite)", false);
        } catch (IndexOutOfBoundsException e) {
            reportResult("testGet - Caso 5 (posición fuera de límite)", e.getMessage().equals("Posición inválida: 3"));
        }
    }

    public static void testInsert() {
        System.out.println("\nEjecutando prueba: testInsert");
        StaticList list = new StaticList(4);
        list.add(10);
        list.add(30);
        list.insert(20, 1);
        reportResult("testInsert - Caso 1 (insertar en medio)", list.size() == 3 && list.get(0) == 10 && list.get(1) == 20 && list.get(2) == 30);
        list.insert(5, 0);
        reportResult("testInsert - Caso 2 (insertar al inicio)", list.size() == 4 && list.get(0) == 5 && list.get(1) == 10 && list.get(2) == 20 && list.get(3) == 30);
        try {
            list.insert(40, 5);
            reportResult("testInsert - Caso 3 (insertar fuera de límite superior)", false);
        } catch (IndexOutOfBoundsException e) {
            reportResult("testInsert - Caso 3 (insertar fuera de límite superior)", e.getMessage().equals("Posición inválida para inserción: 5"));
        }
        try {
            list.insert(0, -1);
            reportResult("testInsert - Caso 4 (insertar fuera de límite inferior)", false);
        } catch (IndexOutOfBoundsException e) {
            reportResult("testInsert - Caso 4 (insertar fuera de límite inferior)", e.getMessage().equals("Posición inválida para inserción: -1"));
        }
        StaticList fullList = new StaticList(3);
        fullList.add(1);
        fullList.add(2);
        fullList.add(3);
        try {
            fullList.insert(4, 3);
            reportResult("testInsert - Caso 5 (insertar en lista llena)", false);
        } catch (IllegalStateException e) {
            reportResult("testInsert - Caso 5 (insertar en lista llena)", e.getMessage().equals("La lista está llena."));
        }
    }

    public static void testDelete() {
        System.out.println("\nEjecutando prueba: testDelete");
        StaticList list = new StaticList(3);
        list.add(10);
        list.add(20);
        list.add(30);
        list.delete(1);
        reportResult("testDelete - Caso 1 (eliminar del medio)", list.size() == 2 && list.get(0) == 10 && list.get(1) == 30);
        list.delete(0);
        reportResult("testDelete - Caso 2 (eliminar del inicio)", list.size() == 1 && list.get(0) == 30);
        list.delete(0);
        reportResult("testDelete - Caso 3 (eliminar el último)", list.isEmpty());
        try {
            list.delete(0);
            reportResult("testDelete - Caso 4 (eliminar de lista vacía)", false);
        } catch (IllegalStateException e) {
            reportResult("testDelete - Caso 4 (eliminar de lista vacía)", e.getMessage().equals("La lista está vacía."));
        }
        StaticList list2 = new StaticList(3);
        list2.add(1);
        try {
            list2.delete(-1);
            reportResult("testDelete - Caso 5 (posición negativa)", false);
        } catch (IndexOutOfBoundsException e) {
            reportResult("testDelete - Caso 5 (posición negativa)", e.getMessage().equals("Posición inválida: -1"));
        }
        try {
            list2.delete(1);
            reportResult("testDelete - Caso 6 (posición fuera de límite)", false);
        } catch (IndexOutOfBoundsException e) {
            reportResult("testDelete - Caso 6 (posición fuera de límite)", e.getMessage().equals("Posición inválida: 1"));
        }
    }

    public static void testIsEmpty() {
        System.out.println("\nEjecutando prueba: testIsEmpty");
        StaticList list = new StaticList();
        reportResult("testIsEmpty - Caso 1 (inicialmente vacía)", list.isEmpty());
        list.add(1);
        reportResult("testIsEmpty - Caso 2 (después de agregar)", !list.isEmpty());
        list.remove();
        reportResult("testIsEmpty - Caso 3 (después de remover)", list.isEmpty());
    }

    public static void testSize() {
        System.out.println("\nEjecutando prueba: testSize");
        StaticList list = new StaticList();
        reportResult("testSize - Caso 1 (inicialmente)", list.size() == 0);
        list.add(1);
        reportResult("testSize - Caso 2 (después de agregar uno)", list.size() == 1);
        list.add(2);
        reportResult("testSize - Caso 3 (después de agregar dos)", list.size() == 2);
        list.remove();
        reportResult("testSize - Caso 4 (después de remover uno)", list.size() == 1);
    }

    public static void testFind() {
        System.out.println("\nEjecutando prueba: testFind");
        StaticList list = new StaticList(3);
        list.add(10);
        list.add(20);
        list.add(30);
        reportResult("testFind - Caso 1 (elemento al inicio)", list.find(10) == 0);
        reportResult("testFind - Caso 2 (elemento en medio)", list.find(20) == 1);
        reportResult("testFind - Caso 3 (elemento al final)", list.find(30) == 2);
        reportResult("testFind - Caso 4 (elemento no encontrado)", list.find(40) == -1);
        StaticList emptyList = new StaticList();
        reportResult("testFind - Caso 5 (buscar en lista vacía)", emptyList.find(5) == -1);
    }

    public static void testExceptions() {
        System.out.println("\nEjecutando prueba: testExceptions (casos borde)");
        StaticList emptyList = new StaticList();
        try {
            emptyList.getFirst();
            reportResult("testExceptions - Caso 1 (getFirst en lista vacía)", false);
        } catch (IllegalStateException e) {
            reportResult("testExceptions - Caso 1 (getFirst en lista vacía)", e.getMessage().equals("La lista está vacía."));
        }
        try {
            emptyList.getLast();
            reportResult("testExceptions - Caso 2 (getLast en lista vacía)", false);
        } catch (IllegalStateException e) {
            reportResult("testExceptions - Caso 2 (getLast en lista vacía)", e.getMessage().equals("La lista está vacía."));
        }
        try {
            emptyList.get(0);
            reportResult("testExceptions - Caso 3 (get en lista vacía)", false);
        } catch (IndexOutOfBoundsException e) {
            reportResult("testExceptions - Caso 3 (get en lista vacía)", e.getMessage().equals("Posición inválida: 0"));
        }
        try {
            emptyList.delete(0);
            reportResult("testExceptions - Caso 4 (delete en lista vacía)", false);
        } catch (IllegalStateException e) {
            reportResult("testExceptions - Caso 4 (delete en lista vacía)", e.getMessage().equals("La lista está vacía."));
        }
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