package model;

public class VersionedStack implements Stack {
    /**
     * Clase interna para almacenar cada version de la pila
     */
    private static class Version {
        private int[] elements; // Almacena los elementos de esta version
        private int size;       // Número actual de elementos en esta version

        public Version() {
            this.elements = new int[0]; // Inicialmente sin elementos
            this.size = 0;
        }

        public Version(Version other) {
            // Copia los elementos de otra version
            this.size = other.size;
            this.elements = new int[this.size]; // Solo usamos el espacio necesario
            System.arraycopy(other.elements, 0, this.elements, 0, this.size);
        }

        /**
         * Agrega un elemento a la cima de esta version.
         *
         * Precondiciones: Ninguna.
         * Postcondiciones: El elemento dado se agrega a la cima de esta version,
         * aumentando el tamaño en uno. Se crea un nuevo array
         * para contener los elementos.
         * Estrategia de implementación:
         * 1. Crear un nuevo array con tamaño size + 1.
         * 2. Copiar los elementos del array actual al nuevo array.
         * 3. Agregar el nuevo elemento al final del nuevo array.
         * 4. Actualizar la referencia 'elements' para que apunte al nuevo array.
         * 5. Incrementar el tamaño.
         *
         * @param element El elemento a agregar.
         */
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

        /**
         * Elimina y devuelve el elemento de la cima de esta version.
         *
         * Precondiciones: La version no debe estar vacía (size > 0).
         * Postcondiciones: El elemento de la cima se elimina y se devuelve.
         * El tamaño de la version disminuye en uno. Se crea un
         * nuevo array para contener los elementos restantes.
         * Estrategia de implementación:
         * 1. Verificar si el tamaño es mayor que 0. Si no, lanzar una RuntimeException.
         * 2. Almacenar el último elemento del array actual (el elemento de la cima).
         * 3. Crear un nuevo array con tamaño size - 1.
         * 4. Copiar los elementos del array actual al nuevo array, excluyendo el último.
         * 5. Actualizar la referencia 'elements' para que apunte al nuevo array.
         * 6. Decrementar el tamaño.
         * 7. Devolver el elemento eliminado.
         *
         * @return El elemento eliminado de la cima.
         * @throws RuntimeException Si la version está vacía.
         */
        public int remove() {
            if (this.size <= 0) {
                throw new RuntimeException("Cannot remove from an empty version");
            }
            int removed = this.elements[this.size - 1];

            // Creamos un nuevo arreglo con un elemento menos
            int[] newElements = new int[this.size - 1];
            // Copiamos los elementos existentes excepto el ultimo
            System.arraycopy(this.elements, 0, newElements, 0, this.size - 1);
            // Actualizamos el arreglo y el tamaño
            this.elements = newElements;
            this.size--;

            return removed;
        }

        /**
         * Obtiene el elemento de la cima de esta version sin eliminarlo.
         *
         * Precondiciones: La version no debe estar vacía (size > 0).
         * Postcondiciones: El elemento de la cima se devuelve sin modificar la version.
         * Estrategia de implementación:
         * 1. Verificar si el tamaño es mayor que 0. Si no, lanzar una RuntimeException.
         * 2. Devolver el último elemento del array.
         *
         * @return El elemento en la cima.
         * @throws RuntimeException Si la version está vacía.
         */
        public int getTop() {
            if (this.size <= 0) {
                throw new RuntimeException("Cannot get top of an empty version");
            }
            return this.elements[this.size - 1];
        }

        /**
         * Verifica si esta version está vacía.
         *
         * Precondiciones: Ninguna.
         * Postcondiciones: Devuelve true si el tamaño es 0, false en caso contrario.
         * Estrategia de implementación:
         * 1. Comparar el tamaño con 0.
         * 2. Devolver el resultado de la comparación.
         *
         * @return true si la version está vacía, false en caso contrario.
         */
        public boolean isEmpty() {
            return this.size == 0;
        }
    }

    //Inicialmente tenemos espacio para una sola version
    private Version[] versions;
    private int currentVersionIndex;
    private int nextVersionIndex;

    /**
     * Constructor para una VersionedStack vacia.
     *
     * Precondiciones: Ninguna.
     * Postcondiciones: Se crea una nueva VersionedStack con una version inicial vacía.
     * currentVersionIndex se inicializa en 0 y nextVersionIndex en 1.
     * Estrategia de implementación:
     * 1. Inicializar el array 'versions' con un tamaño de 1.
     * 2. Crear una nueva instancia de 'Version' y asignarla a la primera posición de 'versions'.
     * 3. Establecer 'currentVersionIndex' en 0.
     * 4. Establecer 'nextVersionIndex' en 1.
     */
    public VersionedStack() {
        this.versions = new Version[1];
        this.versions[0] = new Version(); // Version inicial vacia
        this.currentVersionIndex = 0;
        this.nextVersionIndex = 1;
    }

    /**
     * Expande el arreglo de versiones cuando se necesita agregar una nueva.
     *
     * Precondiciones: El arreglo 'versions' está lleno (nextVersionIndex == versions.length).
     * Postcondiciones: Se crea un nuevo arreglo 'versions' con una capacidad mayor
     * (tamaño actual + 1), y las versiones existentes se copian al nuevo arreglo.
     * Estrategia de implementación:
     * 1. Crear un nuevo array 'newVersions' con un tamaño de versions.length + 1.
     * 2. Copiar todos los elementos del array 'versions' al array 'newVersions'.
     * 3. Asignar 'newVersions' a la referencia 'versions'.
     */
    private void expandVersions() {
        //  Creamos un nuevo arreglo con una posicion más
        Version[] newVersions = new Version[this.versions.length + 1];
        // Copiamos las versiones existentes
        System.arraycopy(this.versions, 0, newVersions, 0, this.versions.length);
        // Actualizamos el arreglo
        this.versions = newVersions;
    }

    /**
     * Agrega un elemento a la cima de la pila, creando una nueva version.
     *
     * Precondiciones: Ninguna.
     * Postcondiciones: Se crea una nueva version de la pila con el elemento agregado en la cima.
     * 'currentVersionIndex' se actualiza al índice de la nueva version,
     * y 'nextVersionIndex' se incrementa.
     * Estrategia de implementación:
     * 1. Verificar si 'nextVersionIndex' es igual o mayor que el tamaño del array 'versions'.
     * Si es así, llamar a 'expandVersions()' para aumentar la capacidad.
     * 2. Crear una nueva 'Version' copiando la version actual (en 'currentVersionIndex').
     * 3. Agregar el elemento 'a' a la nueva 'Version'.
     * 4. Almacenar la nueva 'Version' en el array 'versions' en el índice 'nextVersionIndex'.
     * 5. Actualizar 'currentVersionIndex' a 'nextVersionIndex'.
     * 6. Incrementar 'nextVersionIndex'.
     *
     * @param a El elemento a agregar a la pila.
     */
    @Override
    public void add(int a) {
        // Si necesitamos más espacio, expandimos el arreglo
        if (nextVersionIndex >= versions.length) {
            expandVersions();
        }

        // Creamos una nueva version basada en la actual
        versions[nextVersionIndex] = new Version(versions[currentVersionIndex]);
        // Agregamos el elemento a la nueva version
        versions[nextVersionIndex].add(a);

        // Actualizamos los indices
        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Elimina el elemento de la cima de la pila, creando una nueva version.
     *
     * Precondiciones: La pila actual no debe estar vacía (isEmpty() == false).
     * Postcondiciones: Se crea una nueva version de la pila con el elemento de la cima eliminado.
     * 'currentVersionIndex' se actualiza al índice de la nueva version,
     * y 'nextVersionIndex' se incrementa.
     * Estrategia de implementación:
     * 1. Verificar si la pila actual está vacía. Si lo está, lanzar una RuntimeException.
     * 2. Verificar si 'nextVersionIndex' es igual o mayor que el tamaño del array 'versions'.
     * Si es así, llamar a 'expandVersions()' para aumentar la capacidad.
     * 3. Crear una nueva 'Version' copiando la version actual (en 'currentVersionIndex').
     * 4. Eliminar el elemento de la cima de la nueva 'Version'.
     * 5. Almacenar la nueva 'Version' en el array 'versions' en el índice 'nextVersionIndex'.
     * 6. Actualizar 'currentVersionIndex' a 'nextVersionIndex'.
     * 7. Incrementar 'nextVersionIndex'.
     *
     * @throws RuntimeException Si la pila actual está vacía.
     */
    @Override
    public void remove() {
        if (this.isEmpty()) {
            throw new RuntimeException("Can't unstack an empty version.");
        }

        //  Si necesitamos más espacio, expandimos el arreglo
        if (nextVersionIndex >= versions.length) {
            expandVersions();
        }

        // Creamos una nueva version basada en la actual
        versions[nextVersionIndex] = new Version(versions[currentVersionIndex]);
        // Eliminamos el elemento superior de la nueva version
        versions[nextVersionIndex].remove();

        // Actualizamos los indices
        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Obtiene el elemento de la cima de la version actual de la pila.
     *
     * Precondiciones: La version actual de la pila no debe estar vacía (isEmpty() == false).
     * Postcondiciones: Devuelve el elemento en la cima de la version actual sin modificar la pila.
     * Estrategia de implementación:
     * 1. Verificar si la pila actual está vacía. Si lo está, lanzar una RuntimeException.
     * 2. Llamar al método 'getTop()' de la 'Version' actual (en 'currentVersionIndex') y devolver su resultado.
     *
     * @return El elemento en la cima de la version actual.
     * @throws RuntimeException Si la version actual está vacía.
     */
    @Override
    public int getTop() {
        if (this.isEmpty()) {
            throw new RuntimeException("Cannot get top of an empty stack.");
        }
        return versions[currentVersionIndex].getTop();
    }

    /**
     * Obtiene el elemento de la cima de una version especifica de la pila.
     *
     * Precondiciones: El índice de la version debe ser válido (>= 0 y < nextVersionIndex)
     * y la version especificada no debe estar vacía.
     * Postcondiciones: Devuelve el elemento en la cima de la version especificada sin modificar la pila.
     * Estrategia de implementación:
     * 1. Verificar si el índice de la version es válido (dentro de los límites del array 'versions').
     * 2. Verificar si la version especificada está vacía llamando a 'isEmpty()' en la 'Version' correspondiente.
     * 3. Si alguna de las condiciones anteriores no se cumple, lanzar una RuntimeException.
     * 4. Llamar al método 'getTop()' de la 'Version' en el índice especificado y devolver su resultado.
     *
     * @param version El índice de la version de la cual se quiere obtener el elemento superior.
     * @return El elemento en la cima de la version especificada.
     * @throws RuntimeException Si el índice de la version es inválido o la version está vacía.
     */
    public int getTop(int version) {
        if (version < 0 || version >= this.nextVersionIndex || versions[version].isEmpty()) {
            throw new RuntimeException("Cannot get top of the specified version.");
        }
        return versions[version].getTop();
    }

    /**
     * Verifica si la version actual de la pila está vacía.
     *
     * Precondiciones: Ninguna.
     * Postcondiciones: Devuelve true si la version actual está vacía, false en caso contrario.
     * Estrategia de implementación:
     * 1. Llamar al método 'isEmpty()' de la 'Version' actual (en 'currentVersionIndex') y devolver su resultado.
     *
     * @return true si la version actual está vacía, false en caso contrario.
     */
    @Override
    public boolean isEmpty() {
        return versions[currentVersionIndex].isEmpty();
    }

    /**
     * Imprime el contenido de una version especifica de la pila.
     *
     * Precondiciones: El índice de la version debe ser válido (>= 0 y < nextVersionIndex).
     * Postcondiciones: El contenido de la version especificada se imprime en la consola.
     * Estrategia de implementación:
     * 1. Verificar si el índice de la version es válido (dentro de los límites del array 'versions').
     * Si no es válido, lanzar una IndexOutOfBoundsException.
     * 2. Obtener la 'Version' correspondiente al índice proporcionado.
     * 3. Imprimir el índice de la version.
     * 4. Iterar a través de los elementos del array de la 'Version' e imprimirlos,
     * separándolos con comas.
     * 5. Imprimir los corchetes de apertura y cierre para representar la pila.
     *
     * @param versionIndex El índice de la version a imprimir.
     * @throws IndexOutOfBoundsException Si el índice de la version es inválido.
     */
    public void printVersion(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("indice de version inválido: " + versionIndex);
        }

        Version version = versions[versionIndex];
        System.out.print("Version " + versionIndex + ": [");

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
     * Obtiene el indice de la version actual de la pila.
     *
     * Precondiciones: Ninguna.
     * Postcondiciones: Devuelve el valor de 'currentVersionIndex'.
     * Estrategia de implementación:
     * 1. Devolver el valor de la variable 'currentVersionIndex'.
     *
     * @return El índice de la version actual.
     */
    public int getCurrentVersion() {
        return this.currentVersionIndex;
    }

    /**
     * Crea una nueva version de la VersionedStack a partir de una version existente.
     * La nueva version se convierte en la version actual de la pila.
     */
    public void createVersionFrom(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("indice de version inválido: " + versionIndex);
        }

        //  Si necesitamos más espacio, expandimos el arreglo
        if (nextVersionIndex >= versions.length) {
            expandVersions();
        }

        // Creamos una nueva version basada en la version indicada
        versions[nextVersionIndex] = new Version(versions[versionIndex]);
        
        // Actualizamos los indices
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