// interfaces/Graph.java
package interfaces;

public interface Graph {
    void addNode(int node);
    void removeNode(int node);
    Set getNodes();
    void addEdge(int from, int to, int weight);
    void removeEdge(int from, int to);
    boolean edgeExists(int from, int to);
    int weight(int from, int to);
}