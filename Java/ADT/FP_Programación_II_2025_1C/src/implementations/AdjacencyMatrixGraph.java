// implementations/AdjacencyMatrixGraph.java
package implementations;

import interfaces.Graph;
import interfaces.Set;

public class AdjacencyMatrixGraph implements Graph {
    private int[][] adjacencyMatrix;
    private int numberOfNodes;
    private int maxNodes;
    private int[] nodeMap; // Maps node value to its index in the matrix
    private int nextAvailableIndex;

    private static final int DEFAULT_MAX_NODES = 100;

    public AdjacencyMatrixGraph(int maxNodes) {
        this.maxNodes = maxNodes;
        this.adjacencyMatrix = new int[maxNodes][maxNodes];
        this.numberOfNodes = 0;
        this.nodeMap = new int[maxNodes];
        // Initialize nodeMap with a sentinel value indicating no node is mapped
        for (int i = 0; i < maxNodes; i++) {
            nodeMap[i] = -1;
        }
        this.nextAvailableIndex = 0;
    }

    // Precondicion: El grafo no debe estar lleno. El nodo no debe existir previamente.
    // Complejidad: O(1) en promedio.
    @Override
    public void addNode(int node) {
        if (numberOfNodes >= maxNodes) {
            throw new IllegalStateException("Graph is full, cannot add more nodes.");
        }
        if (getNodeIndex(node) != -1) { // Check if node already exists
            // Node already exists, do nothing or throw an exception based on desired behavior
            return;
        }
        nodeMap[nextAvailableIndex] = node; // Store the actual node value at the next available index
        nextAvailableIndex++;
        numberOfNodes++;
    }

    // Precondicion: El nodo debe existir en el grafo.
    // Complejidad: O(N^2) donde N es el numero de nodos.
    @Override
    public void removeNode(int node) {
        int nodeIndex = getNodeIndex(node);
        if (nodeIndex == -1) {
            return; // Node not found
        }

        // Shift remaining nodes in nodeMap to fill the gap
        for (int i = nodeIndex; i < nextAvailableIndex - 1; i++) {
            nodeMap[i] = nodeMap[i + 1];
        }
        nodeMap[nextAvailableIndex - 1] = -1; // Clear the last entry
        nextAvailableIndex--;

        // Shift rows and columns in the adjacency matrix
        for (int i = nodeIndex; i < numberOfNodes - 1; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i + 1][j];
            }
        }
        for (int j = nodeIndex; j < numberOfNodes - 1; j++) {
            for (int i = 0; i < numberOfNodes; i++) {
                adjacencyMatrix[i][j] = adjacencyMatrix[i][j + 1];
            }
        }

        // Clear the last row and column (now duplicates or old data)
        for (int j = 0; j < numberOfNodes; j++) {
            adjacencyMatrix[numberOfNodes - 1][j] = 0;
        }
        for (int i = 0; i < numberOfNodes; i++) {
            adjacencyMatrix[i][numberOfNodes - 1] = 0;
        }

        numberOfNodes--;
    }

    // Precondicion: Ninguna.
    // Complejidad: O(N) donde N es el numero de nodos.
    @Override
    public Set getNodes() {
        Set nodes = new ArraySet();
        for (int i = 0; i < nextAvailableIndex; i++) {
            if (nodeMap[i] != -1) {
                nodes.add(nodeMap[i]);
            }
        }
        return nodes;
    }

    // Precondicion: 'from' y 'to' deben ser nodos existentes.
    // Complejidad: O(1).
    @Override
    public void addEdge(int from, int to, int weight) {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException("One or both nodes do not exist in the graph.");
        }
        adjacencyMatrix[fromIndex][toIndex] = weight;
    }

    // Precondicion: 'from' y 'to' deben ser nodos existentes.
    // Complejidad: O(1).
    @Override
    public void removeEdge(int from, int to) {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            return; // One or both nodes do not exist, nothing to remove
        }
        adjacencyMatrix[fromIndex][toIndex] = 0; // Assuming 0 means no edge
    }

    // Precondicion: 'from' y 'to' deben ser nodos existentes.
    // Complejidad: O(1).
    @Override
    public boolean edgeExists(int from, int to) {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            return false;
        }
        return adjacencyMatrix[fromIndex][toIndex] != 0;
    }

    // Precondicion: 'from' y 'to' deben ser nodos existentes.
    // Complejidad: O(1).
    @Override
    public int weight(int from, int to) {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex == -1 || toIndex == -1) {
            throw new IllegalArgumentException("One or both nodes do not exist in the graph.");
        }
        return adjacencyMatrix[fromIndex][toIndex];
    }

    // Precondicion: Ninguna.
    // Complejidad: O(N) donde N es el numero de nodos.
    private int getNodeIndex(int node) {
        for (int i = 0; i < nextAvailableIndex; i++) {
            if (nodeMap[i] == node) {
                return i;
            }
        }
        return -1; // Node not found
    }

    // Complejidad: O(1).
    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    // Complejidad: O(N).
    public int getNodeValue(int index) {
        if (index < 0 || index >= nextAvailableIndex || nodeMap[index] == -1) {
            throw new IndexOutOfBoundsException("Invalid node index.");
        }
        return nodeMap[index];
    }
}