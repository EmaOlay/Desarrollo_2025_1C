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

    @Override
    public boolean isEmpty() {
        return tops[currentVersionIndex] == -1;
    }
}