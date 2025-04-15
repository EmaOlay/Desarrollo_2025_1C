package TestManual;

import model.VersionedStack;
import model.StaticStack;
import util.StackUtil;
import util.VersionedStackUtil;

public class Main4 {

    public static void main(String[] args) {
        System.out.println("=== EJERCICIO 4: Prueba de VersionedStack ===");
        VersionedStack stack = new VersionedStack();
        stack.add(10); // v1
        stack.add(20); // v2
        stack.add(30); // v3
        StaticStack stackStatico = VersionedStackUtil.mapToStaticStack(stack,2);

        StackUtil.print(stackStatico);
    }
} 