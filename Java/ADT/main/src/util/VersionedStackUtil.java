package util;

import model.StaticStack;
import model.VersionedStack;

public class VersionedStackUtil {

    public static StaticStack mapToStaticStack(VersionedStack versionedStack, int versionIndex) {
        if (versionIndex < 0 || versionIndex > versionedStack.getCurrentVersion()) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }

        int topSize = versionedStack.getTopIndex();
        StaticStack staticStack = new StaticStack(topSize); // Creamos un StaticStack con la capacidad topSize
        VersionedStack aux = new VersionedStack();
        // Me guardo en un auxiliar
        // Copiamos los elementos de la versión especificada al StaticStack
        while (!versionedStack.isEmpty()) {
            aux.add(versionedStack.getTop());
            versionedStack.remove(); // asumimos que nos alcanzan las vueltas a nivel de versiones para hacer remove
        }

        // Copiamos los elementos de la versión especificada al StaticStack
        // Y recreamos el versionedStack Entendemos que no es igual al inicial porque pasa por muchas versiones
        while (!aux.isEmpty()) {
            int elemento_agregar = aux.getTop();
            staticStack.add(elemento_agregar);
            versionedStack.add(elemento_agregar); // asumimos que nos alcanzan las vueltas para hacerle add
            aux.remove();
        }

        return staticStack;
    }

    /**
     * Mapea la versión actual de un VersionedStack a un nuevo StaticStack.
     * El StaticStack resultante contendrá los mismos elementos que la versión
     * actual del VersionedStack, con el mismo orden.
     *
     * @param versionedStack La pila versionada de la cual se tomará la versión actual.
     * @return Un nuevo StaticStack que contiene los elementos de la versión actual
     * del VersionedStack.
     */
    public static StaticStack mapCurrentVersionToStaticStack(VersionedStack versionedStack) {
        return mapToStaticStack(versionedStack, versionedStack.getCurrentVersion());
    }
}