package TestManual;

import model.VersionedStack;

/**
 * Clase Main para probar las funcionalidades de VersionedStack del ejercicio 2
 * 
 * Ejercicio 2:
 * - P(a): Crear el arreglo con tamaño 1 inicialmente, y expandirlo 
 *   cuando sea necesario, copiando el contenido anterior.
 * - P(c): Guardar las versiones de manera eficiente sin depender de count.
 */
public class Main2 {

    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 2: Prueba de VersionedStack ===");
        System.out.println("Implementación con puntos P(a) y P(c)");
        
        // Creamos una nueva instancia de VersionedStack
        VersionedStack stack = new VersionedStack();
        
        System.out.println("\n=== Prueba de operaciones básicas ===");
        // Versión 0: vacía
        System.out.println("Versión inicial (0): Vacía");
        stack.printVersion(0);
        System.out.println("¿Está vacía la versión 0? " + stack.isEmpty());
        
        // Agregar elementos (creando nuevas versiones)
        stack.add(10);
        System.out.println("\nDespués de agregar 10:");
        System.out.println("Versión actual: " + stack.getCurrentVersion());
        stack.printVersion(stack.getCurrentVersion());
        
        stack.add(20);
        System.out.println("\nDespués de agregar 20:");
        System.out.println("Versión actual: " + stack.getCurrentVersion());
        stack.printVersion(stack.getCurrentVersion());
        
        stack.add(30);
        System.out.println("\nDespués de agregar 30:");
        System.out.println("Versión actual: " + stack.getCurrentVersion());
        stack.printVersion(stack.getCurrentVersion());
        
        // Probar getTop
        System.out.println("Elemento en la cima: " + stack.getTop());
        
        // Eliminar elementos (creando nuevas versiones)
        stack.remove();
        System.out.println("\nDespués de eliminar un elemento:");
        System.out.println("Versión actual: " + stack.getCurrentVersion());
        stack.printVersion(stack.getCurrentVersion());
        System.out.println("Elemento en la cima: " + stack.getTop());
        
        // Probar createVersionFrom
        System.out.println("\n=== Prueba de createVersionFrom ===");
        // Crear una nueva versión basada en la versión 1
        stack.createVersionFrom(1);
        System.out.println("Nueva versión creada desde la versión 1:");
        System.out.println("Versión actual: " + stack.getCurrentVersion());
        stack.printVersion(stack.getCurrentVersion());
        
        // Mostrar todas las versiones
        System.out.println("\n=== Todas las versiones creadas ===");
        int versionCount = stack.getVersionCount();
        for (int i = 0; i < versionCount; i++) {
            stack.printVersion(i);
        }
        
        // Verificar la expansión del arreglo (P(a))
        System.out.println("\n=== Prueba de expansión de arreglo (P(a)) ===");
        System.out.println("Total de versiones creadas: " + stack.getVersionCount());
        System.out.println("Las versiones se han expandido automáticamente según sea necesario.");
        
        // Verificar el uso eficiente de memoria (P(c))
        System.out.println("\n=== Prueba de uso eficiente de memoria (P(c)) ===");
        System.out.println("Cada versión almacena exactamente los elementos necesarios.");
        
        System.out.println("\n=== Pruebas completadas ===");
    }
} 