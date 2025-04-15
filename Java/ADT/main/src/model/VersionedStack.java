package model;

/**
 * Implementación de VersionedStack que sigue los puntos P(a) y P(c) del ejercicio 2:
 * - P(a): Crear el arreglo con tamaño 1 inicialmente, y cada vez que se crea una versión
 *   nueva, incrementar el arreglo en 1 en longitud.
 * - P(c): Como las versiones no se modifican, el tamaño del arreglo para almacenar valores
 *   no necesita ser más grande que la cantidad de elementos de la pila. Guardar las versiones
 *   sin depender de count.
 */
public class VersionedStack implements Stack {
    /**
     * Clase interna para almacenar cada versión de la pila
     */
    private static class Version {
        private int[] elements; // Almacena los elementos de esta versión
        private int size;       // Número actual de elementos en esta versión

        public Version() {
            this.elements = new int[0]; // Inicialmente sin elementos
            this.size = 0;
        }

        public Version(Version other) {
            // Copia los elementos de otra versión
            this.size = other.size;
            this.elements = new int[this.size]; // P(c): Solo usamos el espacio necesario
            System.arraycopy(other.elements, 0, this.elements, 0, this.size);
        }

        public void add(int element) {
            // Creamos un nuevo arreglo con un elemento adicional
            int[] newElements = new int[this.size + 1];
            // Copiamos los elementos existentes
            System.arraycopy(this.elements, 0, newElements, 0, this.size);
            // Agregamos el nuevo elemento
            newElements[this.size] = element;
            // Actualizamos el arreglo y el tamaño
            this.elements = newElements;
            this.size++;
        }

        public int remove() {
            if (this.size <= 0) {
                throw new RuntimeException("Cannot remove from an empty version");
            }
            int removed = this.elements[this.size - 1];
            
            // Creamos un nuevo arreglo con un elemento menos
            int[] newElements = new int[this.size - 1];
            // Copiamos los elementos existentes excepto el último
            System.arraycopy(this.elements, 0, newElements, 0, this.size - 1);
            // Actualizamos el arreglo y el tamaño
            this.elements = newElements;
            this.size--;
            
            return removed;
        }

        public int getTop() {
            if (this.size <= 0) {
                throw new RuntimeException("Cannot get top of an empty version");
            }
            return this.elements[this.size - 1];
        }

        public boolean isEmpty() {
            return this.size == 0;
        }
    }

    // P(a): Inicialmente tenemos espacio para una sola versión
    private Version[] versions;
    private int currentVersionIndex;
    private int nextVersionIndex;

    /**
     * Constructor para una VersionedStack vacía.
     * Inicializa con espacio para una sola versión (P(a))
     */
    public VersionedStack() {
        // P(a): Inicializamos el arreglo con tamaño 1
        this.versions = new Version[1];
        this.versions[0] = new Version(); // Versión inicial vacía
        this.currentVersionIndex = 0;
        this.nextVersionIndex = 1;
    }

    /**
     * Expande el arreglo de versiones cuando se necesita agregar una nueva
     */
    private void expandVersions() {
        // P(a): Creamos un nuevo arreglo con una posición más
        Version[] newVersions = new Version[this.versions.length + 1];
        // Copiamos las versiones existentes
        System.arraycopy(this.versions, 0, newVersions, 0, this.versions.length);
        // Actualizamos el arreglo
        this.versions = newVersions;
    }

    /**
     * Agrega un elemento a la cima de la pila, creando una nueva versión.
     */
    @Override
    public void add(int a) {
        // P(a): Si necesitamos más espacio, expandimos el arreglo
        if (nextVersionIndex >= versions.length) {
            expandVersions();
        }

        // Creamos una nueva versión basada en la actual
        versions[nextVersionIndex] = new Version(versions[currentVersionIndex]);
        // Agregamos el elemento a la nueva versión
        versions[nextVersionIndex].add(a);
        
        // Actualizamos los índices
        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Elimina el elemento de la cima de la pila, creando una nueva versión.
     */
    @Override
    public void remove() {
        if (this.isEmpty()) {
            throw new RuntimeException("Can't unstack an empty version.");
        }

        // P(a): Si necesitamos más espacio, expandimos el arreglo
        if (nextVersionIndex >= versions.length) {
            expandVersions();
        }

        // Creamos una nueva versión basada en la actual
        versions[nextVersionIndex] = new Version(versions[currentVersionIndex]);
        // Eliminamos el elemento superior de la nueva versión
        versions[nextVersionIndex].remove();
        
        // Actualizamos los índices
        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Obtiene el elemento de la cima de la versión actual de la pila.
     */
    @Override
    public int getTop() {
        if (this.isEmpty()) {
            throw new RuntimeException("Cannot get top of an empty stack.");
        }
        return versions[currentVersionIndex].getTop();
    }

    /**
     * Obtiene el elemento de la cima de una versión específica de la pila.
     */
    public int getTop(int version) {
        if (version < 0 || version >= this.nextVersionIndex || versions[version].isEmpty()) {
            throw new RuntimeException("Cannot get top of the specified version.");
        }
        return versions[version].getTop();
    }

    /**
     * Verifica si la versión actual de la pila está vacía.
     */
    @Override
    public boolean isEmpty() {
        return versions[currentVersionIndex].isEmpty();
    }

    /**
     * Imprime el contenido de una versión específica de la pila.
     */
    public void printVersion(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }
        
        Version version = versions[versionIndex];
        System.out.print("Versión " + versionIndex + ": [");
        
        // Imprimir los elementos en el orden de la pila (de abajo hacia arriba)
        for (int i = 0; i < version.size; i++) {
            System.out.print(version.elements[i]);
            if (i < version.size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Obtiene el índice de la versión actual de la pila.
     */
    public int getCurrentVersion() {
        return this.currentVersionIndex;
    }

    /**
     * Crea una nueva versión de la VersionedStack a partir de una versión existente.
     * La nueva versión se convierte en la versión actual de la pila.
     */
    public void createVersionFrom(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }

        // P(a): Si necesitamos más espacio, expandimos el arreglo
        if (nextVersionIndex >= versions.length) {
            expandVersions();
        }

        // Creamos una nueva versión basada en la versión indicada
        versions[nextVersionIndex] = new Version(versions[versionIndex]);
        
        // Actualizamos los índices
        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Devuelve el número total de versiones creadas
     */
    public int getVersionCount() {
        return nextVersionIndex;
    }
    public int getTopIndex(){
        return versions[currentVersionIndex].size;
    }
} 