package model;

public class VersionedStack implements Stack {

    private static final int MAX_SIZE = 1000;
    private static final int MAX_VERSIONS = 100;
    /**
     * Ejemplo mental:
     * v0  v1  v2  v3 ...
     * 0:  0   5   5   5
     * 1: -1   0   1   0
     * 2: -1  -1   0  -1
     * tops: -1   0   1   0
     * currentVersionIndex: indica la columna actual
     * nextVersionIndex: indica la próxima columna para una nueva versión
     */
    private final int[][] matriz = new int[MAX_SIZE][MAX_VERSIONS];
    private int currentVersionIndex = 0;
    private int nextVersionIndex = 1; // Inicializamos en 1 porque la versión 0 está vacía al principio
    private int[] tops = new int[MAX_VERSIONS]; // tops[i] = índice del elemento superior en la versión i

    public VersionedStack() {
        this.tops[0] = -1; // La versión inicial está vacía
    }

    public VersionedStack(int size) {
        // El tamaño aquí se refiere al tamaño máximo de la pila, que ya está definido por MAX_SIZE
        // Ignoramos el argumento size para mantener la consistencia con MAX_SIZE
        this.tops[0] = -1;
    }

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

    @Override
    public int getTop() {
        if (this.isEmpty()) {
            throw new RuntimeException("Cannot get top of an empty stack.");
        }
        return this.matriz[tops[currentVersionIndex]][currentVersionIndex];
    }

    public int getTop(int version) {
        if (this.isEmpty()) {
            throw new RuntimeException("Cannot get top of an empty stack.");
        }
        return this.matriz[tops[version]][version];
    }

    @Override
    public boolean isEmpty() {
        return tops[currentVersionIndex] == -1;
    }

    public void printVersion(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido o versión eliminada: " + versionIndex);
        }
        System.out.print("Versión " + versionIndex + ": [");
        for (int i = 0; i <= this.getTop(); i++) {
            System.out.print(this.matriz[i][versionIndex]);
            if (i < this.getTop()) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public int getCurrentVersion() {
        return this.currentVersionIndex;
    }

    /**
     * Crea una nueva versión de la VersionedStack a partir de una versión existente.
     * La nueva versión se convierte en la versión actual de la pila.
     *
     * Precondiciones:
     * - `stack` no debe ser null.
     * - `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (0-based).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila que es una copia de la versión especificada.
     * - La `currentVersionIndex` del `stack` se actualiza para apuntar a la nueva versión creada.
     * - Si el índice de la versión es inválido o se alcanza el límite de versiones, se lanza una excepción.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la pila es null. Si lo es, se lanza una excepción.
     * 2. Se verifica si el `versionIndex` está dentro del rango válido de versiones. Si no lo está, se lanza una excepción `IndexOutOfBoundsException`.
     * 3. Se verifica si se ha alcanzado el límite máximo de versiones. Si es así, se lanza una `RuntimeException`.
     * 4. Se obtiene el estado (los elementos y el tope) de la versión especificada.
     * 5. Se copia este estado a la siguiente posición disponible en la matriz de versiones.
     * 6. Se actualiza el `tops` array para la nueva versión.
     * 7. Se actualiza el `currentVersionIndex` para que apunte a la nueva versión.
     * 8. Se incrementa el `nextVersionIndex`.
     */
    public void createVersionFrom(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }
    
        int topOfSourceVersion = this.tops[versionIndex]; // Obtener el tope de la versión específica
    
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