package model;

public class VersionedStack implements Stack {

    private static final int MAX_SIZE = 1000;
    private static final int MAX_VERSIONS = 100;
    /**
     * 0 0 0 // v0 tops = 1
     * 5 0 0 // v1 tops = 1
     * 5 1 0 // v2 tops = 2
     * 5 0 0 // v3 tops = 1
     */
    private final int[][] matriz;
    private int currentVersionIndex; // nuestro j
    private int nextVersionIndex; // equivalente al count
    private int tops[]; // Elemento al tope de la lista sub version
    public VersionedStack() {
        this.matriz = new int[MAX_SIZE][MAX_VERSIONS];
        currentVersionIndex = 0;
    }

    public VersionedStack(int size) {
        this.matriz = new int[size][MAX_VERSIONS];
        currentVersionIndex = 0;
    }

    @Override
    public void add(int a) {
        if(nextVersionIndex == MAX_VERSIONS) {
            throw new RuntimeException("Version limit");
        }
        if(tops[currentVersionIndex] == MAX_SIZE) {
            throw new RuntimeException("Current version limit");
        }
        // Copy
        for (int i = 0; i < tops[currentVersionIndex]; i++) {
            this.matriz[i][nextVersionIndex]=this.matriz[i][currentVersionIndex];
        }
        this.matriz[tops[currentVersionIndex]+1][nextVersionIndex]=a;
        tops[nextVersionIndex++]=tops[currentVersionIndex]++;
    }

    @Override
    public void remove() {
        if(this.isEmpty()) {
            throw new RuntimeException("Can't unstack current version.");
        }

        
        for (int i = 0; i < this.tops[this.currentVersionIndex]-1; i++) {
            this.matriz[i][nextVersionIndex]=this.matriz[i][currentVersionIndex];
        }
        this.tops[this.nextVersionIndex]=this.tops[this.currentVersionIndex]--;
    }

    @Override
    public int getTop() {
        if(this.isEmpty()) {
            throw new RuntimeException("No se puede obtener el tope de una pila vacía.");
        }
        return this.matriz[tops[currentVersionIndex]][currentVersionIndex];
    }

    @Override
    public boolean isEmpty() {
        return tops[currentVersionIndex] == 0;
    }
}
