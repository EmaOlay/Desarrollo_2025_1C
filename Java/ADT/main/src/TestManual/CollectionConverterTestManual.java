// testmanual/CollectionConverterTestManual.java
package TestManual;

// import model.List;
// import model.Stack;
import model.StaticList;
import model.StaticStack;
// import model.StaticStack;
import util.CollectionConverter; // Asegúrate de que CollectionConverter esté en el mismo paquete o impórtalo correctamente

public class CollectionConverterTestManual {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas manuales para CollectionConverter:");

        testMapListToStaticStack();
    }

    public static void testMapListToStaticStack() {
        System.out.println("\nEjecutando prueba: testMapListToStack");

        // Caso 1: Lista vacía
        StaticList emptyList = new StaticList();
        StaticStack stack1 = CollectionConverter.mapListToStack(emptyList);
        reportResult("testMapListToStack - Caso 1 (lista vacía)", stack1.isEmpty());

        // Caso 2: Lista con valores
        StaticList list = new StaticList();
        list.add(1);
        list.add(2);
        list.add(3);
        StaticStack stack2 = CollectionConverter.mapListToStack(list);
        reportResult("testMapListToStack - Caso 2 (Lista con valores)", stack2.poll()==3 && stack2.poll()==2 && stack2.poll()==1 && stack2.isEmpty());
    }
    // Método utilitario para reportar los resultados de cada caso de prueba
    public static void reportResult(String testName, boolean passed) {
        if (passed) {
            System.out.println("[PASSED] " + testName);
        } else {
            System.out.println("[FAILED] " + testName);
        }
    }
}