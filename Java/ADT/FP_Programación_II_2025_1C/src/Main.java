// Main.java

import implementations.AdjacencyMatrixGraph;
import implementations.ArrayQueue;
import implementations.ArrayStack;
import implementations.BinaryTreeImpl;
import interfaces.Graph;
import interfaces.Queue;
import interfaces.Stack;
import interfaces.BinaryTree;
import utils.BinaryTreeUtil;
import utils.GraphUtils;
import utils.SetUtil;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Ejercicio 1: Relación de equivalencia en Grafos ---");
        // Test Case 1: Grafo que representa una relación de equivalencia
        // (4,5) (5,4) y self-loops
        Graph graph1 = new AdjacencyMatrixGraph(6);
        graph1.addNode(1);
        graph1.addNode(2);
        graph1.addNode(3);
        graph1.addNode(4);
        graph1.addNode(5);
        graph1.addNode(6);

        // Reflexividad
        graph1.addEdge(1, 1, 1);
        graph1.addEdge(2, 2, 1);
        graph1.addEdge(3, 3, 1);
        graph1.addEdge(4, 4, 1);
        graph1.addEdge(5, 5, 1);
        graph1.addEdge(6, 6, 1);

        // Simetría y Transitividad para componentes
        // Componente {1,2,3}
        graph1.addEdge(1, 2, 1);
        graph1.addEdge(2, 1, 1);
        graph1.addEdge(1, 3, 1);
        graph1.addEdge(3, 1, 1);
        graph1.addEdge(2, 3, 1);
        graph1.addEdge(3, 2, 1);

        // Componente {4,5}
        graph1.addEdge(4, 5, 1);
        graph1.addEdge(5, 4, 1);

        System.out.println("Grafo 1 (Esperado: true - es de equivalencia): " + GraphUtils.isEquivalenceRelation(graph1));

        // Test Case 2: Grafo NO reflexivo
        Graph graph2 = new AdjacencyMatrixGraph(3);
        graph2.addNode(1);
        graph2.addNode(2);
        graph2.addNode(3);
        graph2.addEdge(1, 1, 1);
        graph2.addEdge(1, 2, 1);
        graph2.addEdge(2, 1, 1);
        System.out.println("Grafo 2 (Esperado: false - no reflexivo): " + GraphUtils.isEquivalenceRelation(graph2));

        // Test Case 3: Grafo NO simétrico
        Graph graph3 = new AdjacencyMatrixGraph(3);
        graph3.addNode(1);
        graph3.addNode(2);
        graph3.addNode(3);
        graph3.addEdge(1, 1, 1);
        graph3.addEdge(2, 2, 1);
        graph3.addEdge(3, 3, 1);
        graph3.addEdge(1, 2, 1);
        System.out.println("Grafo 3 (Esperado: false - no simétrico): " + GraphUtils.isEquivalenceRelation(graph3));

        // Test Case 4: Grafo NO transitivo
        Graph graph4 = new AdjacencyMatrixGraph(3);
        graph4.addNode(1);
        graph4.addNode(2);
        graph4.addNode(3);
        graph4.addEdge(1, 1, 1);
        graph4.addEdge(2, 2, 1);
        graph4.addEdge(3, 3, 1);
        graph4.addEdge(1, 2, 1);
        graph4.addEdge(2, 3, 1);
        // Falta 1 -> 3 para ser transitivo
        System.out.println("Grafo 4 (Esperado: false - no transitivo): " + GraphUtils.isEquivalenceRelation(graph4));

        // Ejercicio 1.c
        System.out.println("\n--- Ejercicio 1.c: Camino Hamiltoniano ---");
        System.out.println("Si un grafo es una relación de equivalencia, todos sus nodos están en una o más componentes conexas, donde dentro de cada componente, cada nodo está conectado a todos los demás (es decir, cada componente es un grafo completo). ");
        System.out.println("Un grafo tiene un camino hamiltoniano si existe un camino simple que visita cada vértice exactamente una vez. ");
        System.out.println("Si un grafo es una relación de equivalencia y tiene más de una componente de equivalencia, no puede tener un camino hamiltoniano, ya que un camino hamiltoniano debe visitar todos los nodos y, por definición, no puede haber aristas entre diferentes componentes de equivalencia. ");
        System.out.println("Si un grafo de n vértices es una relación de equivalencia y es una única componente conexa (es decir, es un grafo completo), entonces siempre tiene un camino hamiltoniano si n >= 1. ");
        System.out.println("Para saber si no existe un camino hamiltoniano usando la información del método isEquivalenceRelation, podríamos: ");
        System.out.println("1. Determinar si el grafo es una relación de equivalencia usando el método isEquivalenceRelation. ");
        System.out.println("2. Si lo es, entonces analizar el número de componentes conexas. Si hay más de una componente conexa, entonces no existe un camino hamiltoniano. ");


        System.out.println("\n--- Ejercicio 3: Rama más larga en BinaryTree ---");
        // Construcción del árbol de la Figura 2
        // El árbol es:
        //      1
        //     / \
        //    2   3
        //   / \
        //  4   5
        //       \
        //        6
        BinaryTree tree = new BinaryTreeImpl(1);
        tree.addLeft(2);
        tree.addRight(3);

        BinaryTree leftChild = tree.getLeft();
        leftChild.addLeft(4);
        leftChild.addRight(5);

        BinaryTree rightGrandchild = leftChild.getRight();
        rightGrandchild.addRight(6);

        // La rama más larga es 1 -> 2 -> 5 -> 6 (longitud 3)
        // Opcionalmente, 1 -> 2 -> 4 (longitud 2)
        // Opcionalmente, 1 -> 3 (longitud 1)

        System.out.println("Número 4 en rama más larga (Esperado: false): " + BinaryTreeUtil.isOnLongestBranch(tree, 4));
        System.out.println("Número 3 en rama más larga (Esperado: false): " + BinaryTreeUtil.isOnLongestBranch(tree, 3));
        System.out.println("Número 2 en rama más larga (Esperado: true): " + BinaryTreeUtil.isOnLongestBranch(tree, 2));
        System.out.println("Número 6 en rama más larga (Esperado: true): " + BinaryTreeUtil.isOnLongestBranch(tree, 6));
        System.out.println("Número 1 en rama más larga (Esperado: true): " + BinaryTreeUtil.isOnLongestBranch(tree, 1));
        System.out.println("Número 5 en rama más larga (Esperado: true): " + BinaryTreeUtil.isOnLongestBranch(tree, 5));
        System.out.println("Número 99 (no existe) en rama más larga (Esperado: false): " + BinaryTreeUtil.isOnLongestBranch(tree, 99));

        System.out.println("\n--- Ejercicio 4: Comparar Stack y Queue ---");
        Stack stack = new ArrayStack();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        stack.add(1); // Duplicado

        Queue queue = new ArrayQueue();
        queue.add(3);
        queue.add(2);
        queue.add(1);
        queue.add(3); // Duplicado

        System.out.println("Stack y Queue tienen los mismos elementos (Esperado: true): " + SetUtil.areStackAndQueueEqual(stack, queue));

        Stack stack2 = new ArrayStack();
        stack2.add(1);
        stack2.add(2);

        Queue queue2 = new ArrayQueue();
        queue2.add(1);
        queue2.add(3);

        System.out.println("Stack2 y Queue2 tienen los mismos elementos (Esperado: false): " + SetUtil.areStackAndQueueEqual(stack2, queue2));

        Stack stack3 = new ArrayStack();
        stack3.add(1);

        Queue queue3 = new ArrayQueue();
        System.out.println("Stack3 y Queue3 (vacía) tienen los mismos elementos (Esperado: false): " + SetUtil.areStackAndQueueEqual(stack3, queue3));

        Stack stack4 = new ArrayStack();
        Queue queue4 = new ArrayQueue();
        System.out.println("Stack4 (vacía) y Queue4 (vacía) tienen los mismos elementos (Esperado: true): " + SetUtil.areStackAndQueueEqual(stack4, queue4));
    }
}