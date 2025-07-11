import enums.UnitaryPredicateEnum;
import model.BinaryTreeImpl;
import model.DictionaryImpl;
import model.Node;
import util.BinaryTreeHelper;
import util.BinaryTreeOrderChecker;


public class Main {

    private static final int MY_LU = 1207892;

    public static void main(String[] args) {
        System.out.println("\n--- Pruebas Ejercicio 1: Diccionario ---");
        testDictionary();

        System.out.println("\n--- Pruebas Ejercicio 2: Árbol Binario y Matriz Identidad ---");
        testBinaryTreeIdentityMatrix();
    }

    private static void testDictionary() {
        int nValue = UnitaryPredicateEnum.getPredicate(MY_LU).getValue();
        System.out.println("LU: " + MY_LU + ", n = " + nValue + ". Maximos valores por clave: " + (nValue + 1));

        DictionaryImpl dictionary = new DictionaryImpl(MY_LU); // Use DictionaryImpl as it has helper methods

        System.out.println("Agrego clave 1 con valor 10.");
        dictionary.add(1, 10);
        System.out.println("Valor de clave 1: " + dictionary.getValue(1));

        System.out.println("Agrego mas valores para clave 1 hasta el limite de " + (nValue + 1));
        dictionary.add(1, 20);
        dictionary.add(1, 30);
        dictionary.add(1, 40);
        if (nValue + 1 > 3) {
            dictionary.add(1, 50);
        }

        System.out.println("Obtengo valores de clave 1 (aleatorios si hay varios):");
        for (int i = 0; i < 5; i++) {
            System.out.print(dictionary.getValue(1) + " ");
        }
        System.out.println();

        System.out.println("Agrego clave 2 con valor 50.");
        dictionary.add(2, 50);
        System.out.println("Valor para clave 2: " + dictionary.getValue(2));

        System.out.println("Contiene clave 1: " + dictionary.containsKey(1));
        System.out.println("Contiene clave 3: " + dictionary.containsKey(3));
        System.out.println("Tamaño del diccionario: " + dictionary.size());

        System.out.println("Remuevo clave 1 (el segundo parametro v es el valor, en esta implementacion remueve la clave si esta asociada):");
        dictionary.remove(1, 0);
        System.out.println("Contiene clave 1 despues de remover: " + dictionary.containsKey(1));
        System.out.println("Tamaño del diccionario despues de remover: " + dictionary.size());
    }

    private static void testBinaryTreeIdentityMatrix() {
        System.out.println("Pruebo Ejercicio 2: Árbol Binario y Matriz Identidad");

        // Ejemplo de la figura 1
        //       1
        //      / \
        //     2   3
        //    / \   \
        //   4   5   6
        //        \   \
        //         7   8
        //              \
        //               9

        Node root = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);

        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setRight(node6);
        node5.setRight(node7);
        node6.setRight(node8);
        node8.setRight(node9);

        BinaryTreeImpl tree = new BinaryTreeImpl();
        tree.setRoot(root);

        System.out.println("Arbol de la figura construido.");
        System.out.print("Recorrido In-order: ");
        BinaryTreeHelper.inorderTraversal(tree.getRoot());
        System.out.println();
        System.out.println("Altura del arbol: " + BinaryTreeHelper.getHeight(tree.getRoot()));
        System.out.println("Cantidad de nodos: " + BinaryTreeHelper.getTotalNodes(tree.getRoot()));

        System.out.println("Verificando si el arbol es una matriz identidad:");
        boolean isIdentity = BinaryTreeOrderChecker.isIdentityMatrixTree(tree.getRoot());
        System.out.println("Es matriz identidad? " + isIdentity);

        // Prueba con un árbol que debería ser matriz identidad (ej. 2x2)
        //      1
        //     / \
        //    0   0
        //   / \ / \
        //  0  1 0  0
        Node idRoot = new Node(1);
        idRoot.setLeft(new Node(0));
        idRoot.setRight(new Node(0));
        idRoot.getLeft().setLeft(new Node(0));
        idRoot.getLeft().setRight(new Node(1));
        idRoot.getRight().setLeft(new Node(0));
        idRoot.getRight().setRight(new Node(0));

        BinaryTreeImpl idTree = new BinaryTreeImpl();
        idTree.setRoot(idRoot);
        System.out.println("\nProbando arbol matriz identidad de 3x3:");
        System.out.println("Es matriz identidad? " + BinaryTreeOrderChecker.isIdentityMatrixTree(idTree.getRoot()));


        // Prueba con un arbol que no es una matriz identidad
        //      1
        //     / \
        //    2   3
        Node nonIdRoot = new Node(1);
        nonIdRoot.setLeft(new Node(2));
        nonIdRoot.setRight(new Node(3));
        BinaryTreeImpl nonIdTree = new BinaryTreeImpl();
        nonIdTree.setRoot(nonIdRoot);
        System.out.println("\nPrueba arbol no identidad:");
        System.out.println("Es matriz identidad? " + BinaryTreeOrderChecker.isIdentityMatrixTree(nonIdTree.getRoot()));
    }
}