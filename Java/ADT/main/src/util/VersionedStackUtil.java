package util;

import model.StaticStack;
import model.VersionedStack;

public class VersionedStackUtil {

    private VersionedStackUtil() {
        // Constructor privado para evitar la instanciación de la clase de utilidad
    }

    /**
     * Convierte una instancia de VersionedStack a una instancia de StaticStack.
     * La conversión toma la versión actual de la VersionedStack y la copia a la StaticStack.
     *
     * @param versionedStack La instancia de VersionedStack a convertir.
     * @return Una nueva instancia de StaticStack con los elementos de la versión actual
     * de la VersionedStack.
     */
    public static StaticStack mapToStaticStack(VersionedStack versionedStack) {
        int currentVersionIndex = versionedStack.getCurrentVersion();
        int topIndex = versionedStack.getTop();
        StaticStack staticStack = new StaticStack(topIndex + 1); // Inicializar con el tamaño adecuado

        VersionedStack tempStack = new VersionedStack();
        // Pasar todos los elementos de enqueue a una pila temporal (invirtiendo el
        // orden)




        /*
         * 
         * 
         * 
         * A REVISAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRR
         */
        while (!versionedStack.isEmpty()) {
            tempStack.add(versionedStack.getTop());
            versionedStack.remove();
        }
        // Pasar los elementos de la pila temporal a dequeue (manteniendo el orden FIFO)
        while (!tempStack.isEmpty()) {
            stackForDequeue.add(tempStack.getTop());
            tempStack.remove();
        }

        for (int i = 0; i <= topIndex; i++) {
            staticStack.add(versionedStack.add(versionedStack.getTop()));
            versionedStack.remove();
        }
        return staticStack;
    }

    /**
     * Convierte una instancia de StaticStack a una instancia de VersionedStack.
     * La conversión crea una nueva VersionedStack con una única versión que contiene
     * todos los elementos del StaticStack.
     *
     * @param staticStack La instancia de StaticStack a convertir.
     * @return Una nueva instancia de VersionedStack con una versión inicial
     * que contiene los elementos del StaticStack.
     */
    public static VersionedStack mapToVersionedStack(StaticStack staticStack) {
        VersionedStack versionedStack = new VersionedStack();
        int count = staticStack.getCount();
        // Para mantener el orden de la pila, los elementos del StaticStack
        // se agregan a la VersionedStack en el mismo orden en que están en el array.
        // Esto creará una única versión en VersionedStack.
        for (int i = 0; i < count; i++) {
            try {
                // Acceder al array interno de StaticStack directamente (mala práctica general,
                // pero necesario aquí sin métodos de acceso directos)
                java.lang.reflect.Field arrayField = StaticStack.class.getDeclaredField("array");
                arrayField.setAccessible(true);
                int[] array = (int[]) arrayField.get(staticStack);
                versionedStack.add(array[i]);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error al acceder al array interno de StaticStack", e);
            }
        }
        return versionedStack;
    }
}