package Integrador2;


public class Intersection {
 
    public static Dupla[] intersection(Stack stack, PriorityQueue queue) {
        Dupla[] pilaTemp = new Dupla[100];
        int pilaSize = 0;
 
        // Copiar pila sin modificarla
        while (!stack.isEmpty()) {
            Dupla d = stack.getTop();
            pilaTemp[pilaSize++] = d;
            stack.remove();
        }
 
        // Restaurar pila original
        for (int i = pilaSize - 1; i >= 0; i--) {
            stack.add(pilaTemp[i]);
        }
 
        Dupla[] colaTemp = new Dupla[100];
        int colaSize = 0;
 
        // Copiar cola sin modificarla
        while (!queue.isEmpty()) {
            int p = queue.getPriority();
            int v = queue.getFirst();
            colaTemp[colaSize++] = new Dupla(p, v);
            queue.remove();
        }
 
        // Comparar elementos
        Dupla[] comunes = new Dupla[100];
        int comunesSize = 0;
 
        for (int i = 0; i < pilaSize; i++) {
            for (int j = 0; j < colaSize; j++) {
                if (pilaTemp[i].equals(colaTemp[j])) {
                    comunes[comunesSize++] = pilaTemp[i];
                    break;
                }
            }
        }
 
        // Retornar solo los elementos comunes encontrados
        Dupla[] resultado = new Dupla[comunesSize];
        for (int i = 0; i < comunesSize; i++) {
            resultado[i] = comunes[i];
        }
 
        return resultado;
    }
}