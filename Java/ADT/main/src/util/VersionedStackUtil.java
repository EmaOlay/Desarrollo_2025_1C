package util;

import model.VersionedStack;

public class VersionedStackUtil {

    private VersionedStackUtil() {
        // Constructor privado para evitar la instanciación
    }

    /**
     * Crea una nueva versión de la VersionedStack a partir de una versión existente.
     * La nueva versión se convierte en la versión actual de la pila.
     *
     * Precondiciones:
     * - `stack` no debe ser null.
     * - `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (0-based).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila que es una copia de la versión especificada.
     * - La `currentVersionIndex` del `stack` se actualiza para apuntar a la nueva versión creada.
     * - Si el índice de la versión es inválido o se alcanza el límite de versiones, se lanza una excepción.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la pila es null. Si lo es, se lanza una excepción.
     * 2. Se verifica si el `versionIndex` está dentro del rango válido de versiones. Si no lo está, se lanza una excepción `IndexOutOfBoundsException`.
     * 3. Se verifica si se ha alcanzado el límite máximo de versiones. Si es así, se lanza una `RuntimeException`.
     * 4. Se obtiene el estado (los elementos y el tope) de la versión especificada.
     * 5. Se copia este estado a la siguiente posición disponible en la matriz de versiones.
     * 6. Se actualiza el `tops` array para la nueva versión.
     * 7. Se actualiza el `currentVersionIndex` para que apunte a la nueva versión.
     * 8. Se incrementa el `nextVersionIndex`.
     */
    public static void createVersionFrom(VersionedStack stack, int versionIndex) {
        if (stack == null) {
            throw new IllegalArgumentException("La pila no puede ser null.");
        }
        if (versionIndex < 0 || versionIndex >= stack.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }
        if (stack.nextVersionIndex == VersionedStack.MAX_VERSIONS) {
            throw new RuntimeException("Se ha alcanzado el límite máximo de versiones.");
        }

        int topOfSourceVersion = stack.tops[versionIndex];
        for (int i = 0; i <= topOfSourceVersion; i++) {
            stack.matriz[i][stack.nextVersionIndex] = stack.matriz[i][versionIndex];
        }
        stack.tops[stack.nextVersionIndex] = topOfSourceVersion;
        stack.currentVersionIndex = stack.nextVersionIndex;
        stack.nextVersionIndex++;
    }

    /**
     * Permite moverse a una versión específica de la VersionedStack.
     *
     * Precondiciones:
     * - `stack` no debe ser null.
     * - `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (0-based).
     *
     * Postcondiciones:
     * - La `currentVersionIndex` del `stack` se actualiza para apuntar a la versión especificada.
     * - Si el índice de la versión es inválido, se lanza una excepción `IndexOutOfBoundsException`.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la pila es null. Si lo es, se lanza una excepción.
     * 2. Se verifica si el `versionIndex` está dentro del rango válido de versiones. Si no lo está, se lanza una excepción `IndexOutOfBoundsException`.
     * 3. Se actualiza el `currentVersionIndex` de la pila al `versionIndex` proporcionado.
     */
    public static void goToVersion(VersionedStack stack, int versionIndex) {
        if (stack == null) {
            throw new IllegalArgumentException("La pila no puede ser null.");
        }
        if (versionIndex < 0 || versionIndex >= stack.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }
        stack.currentVersionIndex = versionIndex;
    }

    /**
     * Permite eliminar una versión específica de la VersionedStack.
     *
     * Precondiciones:
     * - `stack` no debe ser null.
     * - `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (0-based).
     *
     * Postcondiciones:
     * - La versión en el `versionIndex` especificado se marca como "eliminada" (en esta implementación, se podría invalidar su tope).
     * - Si la `currentVersionIndex` apuntaba a la versión eliminada, se podría decidir moverla a la versión anterior o siguiente existente (esta implementación la deja apuntando a un índice inválido, lo que requeriría manejo en el uso).
     * - Si el índice de la versión es inválido, se lanza una excepción `IndexOutOfBoundsException`.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la pila es null. Si lo es, se lanza una excepción.
     * 2. Se verifica si el `versionIndex` está dentro del rango válido de versiones. Si no lo está, se lanza una excepción `IndexOutOfBoundsException`.
     * 3. Se invalida el tope de la versión a eliminar estableciéndolo en un valor especial (por ejemplo, -2) para indicar que ya no es válida.
     * 4. **Consideración:** Se podría agregar lógica para ajustar `currentVersionIndex` si apunta a la versión eliminada. En esta implementación simple, no se hace.
     */
    public static void deleteVersion(VersionedStack stack, int versionIndex) {
        if (stack == null) {
            throw new IllegalArgumentException("La pila no puede ser null.");
        }
        if (versionIndex < 0 || versionIndex >= stack.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }
        stack.tops[versionIndex] = -2; // Marca la versión como eliminada
        // Consideración: Ajustar currentVersionIndex si apunta a la versión eliminada.
    }

    /**
     * Devuelve el número total de versiones existentes en la VersionedStack.
     *
     * Precondiciones:
     * - `stack` no debe ser null.
     *
     * Postcondiciones:
     * - Se devuelve el valor actual de `nextVersionIndex`.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la pila es null. Si lo es, se lanza una excepción.
     * 2. Se devuelve el valor de `stack.nextVersionIndex`.
     */
    public static int getTotalVersions(VersionedStack stack) {
        if (stack == null) {
            throw new IllegalArgumentException("La pila no puede ser null.");
        }
        return stack.nextVersionIndex;
    }

    /**
     * Devuelve el índice de la versión actual de la VersionedStack.
     *
     * Precondiciones:
     * - `stack` no debe ser null.
     *
     * Postcondiciones:
     * - Se devuelve el valor actual de `currentVersionIndex`.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la pila es null. Si lo es, se lanza una excepción.
     * 2. Se devuelve el valor de `stack.currentVersionIndex`.
     */
    public static int getCurrentVersionIndex(VersionedStack stack) {
        if (stack == null) {
            throw new IllegalArgumentException("La pila no puede ser null.");
        }
        return stack.currentVersionIndex;
    }

    /**
     * Imprime el estado de una versión específica de la VersionedStack.
     *
     * Precondiciones:
     * - `stack` no debe ser null.
     * - `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (0-based).
     *
     * Postcondiciones:
     * - Se imprime en la consola el contenido de la versión especificada.
     *
     * Estrategia de Implementación:
     * 1. Se verifica si la pila es null. Si lo es