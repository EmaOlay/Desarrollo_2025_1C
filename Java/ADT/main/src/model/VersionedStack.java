package model;

public class VersionedStack implements Stack {

    private static final int MAX_SIZE = 1000;
    private static final int MAX_VERSIONS = 100;
    /**
     * Ejemplo mental:
     * v0   v1   v2   v3 ...
     * 0:   0    5    5    5
     * 1:  -1    0    1    0
     * 2:  -1   -1    0   -1
     * tops: -1    0    1    0
     * currentVersionIndex: indica la columna actual
     * nextVersionIndex: indica la próxima columna para una nueva versión
     */
    private final int[][] matriz = new int[MAX_SIZE][MAX_VERSIONS];
    private int currentVersionIndex = 0;
    private int nextVersionIndex = 1; // Inicializamos en 1 porque la versión 0 está vacía al principio
    private int[] tops = new int[MAX_VERSIONS]; // tops[i] = índice del elemento superior en la versión i

    /**
     * Constructor para una VersionedStack vacía.
     *
     * Precondiciones: Ninguna.
     * Postcondiciones: Se crea una nueva VersionedStack con una versión inicial vacía (versión 0).
     * El índice del tope de la versión 0 se inicializa en -1.
     * Estrategia de Implementación: Se inicializa el array `tops` y se establece `tops[0]` en -1.
     */
    public VersionedStack() {
        this.tops[0] = -1; // La versión inicial está vacía
    }

    /**
     * Agrega un elemento a la cima de la pila, creando una nueva versión.
     *
     * Precondiciones:
     * - La pila no debe haber alcanzado el límite máximo de versiones (`nextVersionIndex < MAX_VERSIONS`).
     * - La versión actual de la pila no debe estar llena (`tops[currentVersionIndex] < MAX_SIZE - 1`).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila con el elemento agregado en la cima.
     * - `currentVersionIndex` se actualiza para apuntar a la nueva versión.
     * - `nextVersionIndex` se incrementa.
     * - El elemento `a` se encuentra en la cima de la nueva versión.
     *
     * Estrategia de Implementación:
     * 1. Verificar si se ha alcanzado el límite de versiones. Si es así, lanzar una `RuntimeException`.
     * 2. Verificar si la versión actual está llena. Si es así, lanzar una `RuntimeException`.
     * 3. Copiar los elementos de la versión actual a la siguiente versión disponible.
     * 4. Incrementar el índice del tope para la nueva versión.
     * 5. Almacenar el elemento `a` en la cima de la nueva versión.
     * 6. Actualizar `currentVersionIndex` y `nextVersionIndex`.
     */
    @Override
    public void add(int a) {
        if (nextVersionIndex == MAX_VERSIONS) {
            throw new RuntimeException("Version limit reached");
        }
        if (tops[currentVersionIndex] == MAX_SIZE - 1) {
            throw new RuntimeException("Current version is full");
        }

        // Copy the current version to the next version
        for (int i = 0; i <= tops[currentVersionIndex]; i++) {
            this.matriz[i][nextVersionIndex] = this.matriz[i][currentVersionIndex];
        }

        // Increment the top for the new version and add the new element
        tops[nextVersionIndex] = tops[currentVersionIndex] + 1;
        this.matriz[tops[nextVersionIndex]][nextVersionIndex] = a;

        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Elimina el elemento de la cima de la pila, creando una nueva versión.
     *
     * Precondiciones:
     * - La pila no debe estar vacía (`!isEmpty()`).
     * - La pila no debe haber alcanzado el límite máximo de versiones (`nextVersionIndex < MAX_VERSIONS`).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila con el elemento de la cima eliminado.
     * - `currentVersionIndex` se actualiza para apuntar a la nueva versión.
     * - `nextVersionIndex` se incrementa.
     * - La nueva versión contiene los mismos elementos que la versión anterior, excepto el que estaba en la cima.
     *
     * Estrategia de Implementación:
     * 1. Verificar si la pila está vacía. Si es así, lanzar una `RuntimeException`.
     * 2. Verificar si se ha alcanzado el límite de versiones. Si es así, lanzar una `RuntimeException`.
     * 3. Copiar los elementos de la versión actual a la siguiente versión disponible, excluyendo el elemento de la cima.
     * 4. Decrementar el índice del tope para la nueva versión.
     * 5. Actualizar `currentVersionIndex` y `nextVersionIndex`.
     */
    @Override
    public void remove() {
        if (this.isEmpty()) {
            throw new RuntimeException("Can't unstack an empty version.");
        }

        if (nextVersionIndex == MAX_VERSIONS) {
            throw new RuntimeException("Version limit reached");
        }

        // Copy the current version to the next version (excluding the top element)
        for (int i = 0; i < tops[currentVersionIndex]; i++) {
            this.matriz[i][nextVersionIndex] = this.matriz[i][currentVersionIndex];
        }

        // Decrement the top for the new version
        tops[nextVersionIndex] = tops[currentVersionIndex] - 1;

        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Obtiene el elemento de la cima de la versión actual de la pila.
     *
     * Precondiciones: La pila no debe estar vacía (`!isEmpty()`).
     *
     * Postcondiciones: Se devuelve el elemento que se encuentra en la cima de la versión actual. La pila no se modifica.
     *
     * Estrategia de Implementación:
     * 1. Verificar si la pila está vacía. Si es así, lanzar una `RuntimeException`.
     * 2. Devolver el elemento ubicado en `matriz[tops[currentVersionIndex]][currentVersionIndex]`.
     */
    @Override
    public int getTop() {
        if (this.isEmpty()) {
            throw new RuntimeException("Cannot get top of an empty stack.");
        }
        return this.matriz[tops[currentVersionIndex]][currentVersionIndex];
    }

    /**
     * Obtiene el elemento de la cima de una versión específica de la pila.
     *
     * Precondiciones:
     * - `version` debe ser un índice válido dentro del rango de versiones existentes (`0 <= version < nextVersionIndex`).
     * - La versión especificada no debe estar vacía (`tops[version] != -1`).
     *
     * Postcondiciones: Se devuelve el elemento que se encuentra en la cima de la versión especificada. La pila no se modifica.
     *
     * Estrategia de Implementación:
     * 1. Verificar si el índice de versión es válido. Si no lo es, lanzar una `RuntimeException`.
     * 2. Verificar si la versión especificada está vacía. Si lo está, lanzar una `RuntimeException`.
     * 3. Devolver el elemento ubicado en `matriz[tops[version]][version]`.
     */
    public int getTop(int version) {
        if (version < 0 || version >= this.nextVersionIndex || tops[version] == -1) {
            throw new RuntimeException("Cannot get top of the specified version.");
        }
        return this.matriz[tops[version]][version];
    }

    /**
     * Verifica si la versión actual de la pila está vacía.
     *
     * Precondiciones: Ninguna.
     *
     * Postcondiciones: Se devuelve `true` si la versión actual no contiene elementos, `false` en caso contrario. La pila no se modifica.
     *
     * Estrategia de Implementación: Comprobar si el índice del tope de la versión actual (`tops[currentVersionIndex]`) es igual a -1.
     */
    @Override
    public boolean isEmpty() {
        return tops[currentVersionIndex] == -1;
    }

    /**
     * Imprime el contenido de una versión específica de la pila.
     *
     * Precondiciones: `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (`0 <= versionIndex < nextVersionIndex`).
     *
     * Postcondiciones: Se imprime en la consola el contenido de la versión especificada, con los elementos separados por comas y encerrados entre corchetes.
     *
     * Estrategia de Implementación:
     * 1. Verificar si el índice de versión es válido. Si no lo es, lanzar una `IndexOutOfBoundsException`.
     * 2. Iterar desde el índice 0 hasta el tope de la versión especificada (`tops[versionIndex]`).
     * 3. Imprimir cada elemento de la versión.
     * 4. Formatear la salida con corchetes y comas.
     */
    public void printVersion(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido o versión eliminada: " + versionIndex);
        }
        System.out.print("Versión " + versionIndex + ": [");
        for (int i = 0; i <= this.tops[versionIndex]; i++) {
            System.out.print(this.matriz[i][versionIndex]);
            if (i < this.tops[versionIndex]) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Obtiene el índice de la versión actual de la pila.
     *
     * Precondiciones: Ninguna.
     *
     * Postcondiciones: Se devuelve el valor de `currentVersionIndex`. La pila no se modifica.
     *
     * Estrategia de Implementación: Simplemente devolver el valor de la variable `currentVersionIndex`.
     */
    public int getCurrentVersion() {
        return this.currentVersionIndex;
    }

    /**
     * Crea una nueva versión de la VersionedStack a partir de una versión existente.
     * La nueva versión se convierte en la versión actual de la pila.
     *
     * Precondiciones:
     * - `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (`0 <= versionIndex < nextVersionIndex`).
     * - No se debe haber alcanzado el límite máximo de versiones (`nextVersionIndex < MAX_VERSIONS`).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila que es una copia de la versión especificada.
     * - La `currentVersionIndex` se actualiza para apuntar a la nueva versión creada.
     * - `nextVersionIndex` se incrementa.
     * - Si el `versionIndex` es inválido o se alcanza el límite de versiones, se lanza una excepción.
     *
     * Estrategia de Implementación:
     * 1. Verificar si el `versionIndex` está dentro del rango válido de versiones. Si no lo está, lanzar una `IndexOutOfBoundsException`.
     * 2. Verificar si se ha alcanzado el límite máximo de versiones. Si es así, lanzar una `RuntimeException`.
     * 3. Obtener el índice del tope de la versión especificada (`tops[versionIndex]`).
     * 4. Copiar los elementos desde la versión especificada a la siguiente posición disponible en la matriz de versiones.
     * 5. Actualizar el índice del tope (`tops`) para la nueva versión.
     * 6. Actualizar el `currentVersionIndex` para que apunte a la nueva versión.
     * 7. Incrementar el `nextVersionIndex`.
     */
    public void createVersionFrom(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }

        int topOfSourceVersion = this.tops[versionIndex];

        if (this.nextVersionIndex == MAX_VERSIONS) {
            throw new RuntimeException("Version limit reached");
        }

        // Copiar los elementos de la versión fuente a la nueva versión
        for (int i = 0; i <= topOfSourceVersion; i++) {
            this.matriz[i][this.nextVersionIndex] = this.matriz[i][versionIndex];
        }

        this.tops[this.nextVersionIndex] = topOfSourceVersion;
        this.currentVersionIndex = this.nextVersionIndex;
        this.nextVersionIndex++;
    }
}