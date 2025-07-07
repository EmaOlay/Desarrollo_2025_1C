// utils/GraphUtils.java
package utils;

import implementations.ArraySet;
import implementations.ArrayStack;
import interfaces.Graph;
import interfaces.Set;
import interfaces.Stack;

public class GraphUtils {

    // Precondicion: El grafo no debe ser nulo.
    // Complejidad: O(V^3) en el peor caso (debido a la transitividad que implica 3 bucles anidados).
    public static boolean isEquivalenceRelation(Graph graph) {
        if (graph == null) {
            return false;
        }

        Set nodes = graph.getNodes();
        if (nodes.isEmpty()) {
            return true; // Un grafo vacio es trivialmente una relacion de equivalencia.
        }

        // 1. Reflexividad: Para todo vertice v, se tiene que v->v.
        // Complejidad: O(V) donde V es el numero de vertices.
        Set tempNodesReflexivity = graph.getNodes(); // Crear una copia mutable para iterar
        ArrayStack nodesStackReflexivity = new ArrayStack();
        while (!tempNodesReflexivity.isEmpty()) {
            int node = tempNodesReflexivity.choose();
            nodesStackReflexivity.add(node);
            tempNodesReflexivity.remove(node); // CORREGIDO: Pasar el nodo a remover
        }

        while (!nodesStackReflexivity.isEmpty()) {
            int node = nodesStackReflexivity.getTop();
            nodesStackReflexivity.remove();
            if (!graph.edgeExists(node, node)) {
                return false;
            }
        }

        // 2. Simetria: Para todo vertice v1 y v2, si v1->v2, entonces v2->v1.
        // Complejidad: O(V^2) donde V es el numero de vertices.
        Set tempNodesSymmetryOuter = graph.getNodes();
        ArrayStack nodesStackSymmetryOuter = new ArrayStack();
        while (!tempNodesSymmetryOuter.isEmpty()) {
            int node = tempNodesSymmetryOuter.choose();
            nodesStackSymmetryOuter.add(node);
            tempNodesSymmetryOuter.remove(node); // CORREGIDO: Pasar el nodo a remover
        }

        Set tempNodesSymmetryInner;
        ArrayStack nodesStackSymmetryInner;

        ArrayStack tempStorageOuter = new ArrayStack(); // Para restaurar la pila outer
        while (!nodesStackSymmetryOuter.isEmpty()) {
            int v1 = nodesStackSymmetryOuter.getTop();
            nodesStackSymmetryOuter.remove();
            tempStorageOuter.add(v1); // Guardar para futura restauracion

            tempNodesSymmetryInner = graph.getNodes(); // Fresh copy for inner loop
            nodesStackSymmetryInner = new ArrayStack();
            while (!tempNodesSymmetryInner.isEmpty()) {
                int node = tempNodesSymmetryInner.choose();
                nodesStackSymmetryInner.add(node);
                tempNodesSymmetryInner.remove(node); // CORREGIDO: Pasar el nodo a remover
            }

            ArrayStack tempStorageInner = new ArrayStack(); // Para restaurar la pila inner
            while (!nodesStackSymmetryInner.isEmpty()) {
                int v2 = nodesStackSymmetryInner.getTop();
                nodesStackSymmetryInner.remove();
                tempStorageInner.add(v2);

                if (graph.edgeExists(v1, v2)) {
                    if (!graph.edgeExists(v2, v1)) {
                        return false;
                    }
                }
            }
            // Restore inner stack
            while (!tempStorageInner.isEmpty()) {
                nodesStackSymmetryInner.add(tempStorageInner.getTop());
                tempStorageInner.remove();
            }
        }
        // Restore outer stack (not strictly needed as method ends, but good practice)
        while (!tempStorageOuter.isEmpty()) {
            nodesStackSymmetryOuter.add(tempStorageOuter.getTop());
            tempStorageOuter.remove();
        }


        // 3. Transitividad: Para todo vertice v1, v2, v3, si v1->v2 y v2->v3, entonces v1->v3.
        // Complejidad: O(V^3) donde V es el numero de vertices.
        Set tempNodesTransitivityV1 = graph.getNodes();
        ArrayStack nodesStackTransitivityV1 = new ArrayStack();
        while (!tempNodesTransitivityV1.isEmpty()) {
            int node = tempNodesTransitivityV1.choose();
            nodesStackTransitivityV1.add(node);
            tempNodesTransitivityV1.remove(node); // CORREGIDO: Pasar el nodo a remover
        }

        ArrayStack tempStorageV1 = new ArrayStack();
        while (!nodesStackTransitivityV1.isEmpty()) {
            int v1 = nodesStackTransitivityV1.getTop();
            nodesStackTransitivityV1.remove();
            tempStorageV1.add(v1);

            Set tempNodesTransitivityV2 = graph.getNodes();
            ArrayStack nodesStackTransitivityV2 = new ArrayStack();
            while (!tempNodesTransitivityV2.isEmpty()) {
                int node = tempNodesTransitivityV2.choose();
                nodesStackTransitivityV2.add(node);
                tempNodesTransitivityV2.remove(node); // CORREGIDO: Pasar el nodo a remover
            }

            ArrayStack tempStorageV2 = new ArrayStack();
            while (!nodesStackTransitivityV2.isEmpty()) {
                int v2 = nodesStackTransitivityV2.getTop();
                nodesStackTransitivityV2.remove();
                tempStorageV2.add(v2);

                Set tempNodesTransitivityV3 = graph.getNodes();
                ArrayStack nodesStackTransitivityV3 = new ArrayStack();
                while (!tempNodesTransitivityV3.isEmpty()) {
                    int node = tempNodesTransitivityV3.choose();
                    nodesStackTransitivityV3.add(node);
                    tempNodesTransitivityV3.remove(node); // CORREGIDO: Pasar el nodo a remover
                }

                ArrayStack tempStorageV3 = new ArrayStack();
                while (!nodesStackTransitivityV3.isEmpty()) {
                    int v3 = nodesStackTransitivityV3.getTop();
                    nodesStackTransitivityV3.remove();
                    tempStorageV3.add(v3);

                    if (graph.edgeExists(v1, v2) && graph.edgeExists(v2, v3)) {
                        if (!graph.edgeExists(v1, v3)) {
                            return false;
                        }
                    }
                }
                while (!tempStorageV3.isEmpty()) {
                    nodesStackTransitivityV3.add(tempStorageV3.getTop());
                    tempStorageV3.remove();
                }
            }
            while (!tempStorageV2.isEmpty()) {
                nodesStackTransitivityV2.add(tempStorageV2.getTop());
                tempStorageV2.remove();
            }
        }
        while (!tempStorageV1.isEmpty()) {
            nodesStackTransitivityV1.add(tempStorageV1.getTop());
            tempStorageV1.remove();
        }

        return true;
    }
}