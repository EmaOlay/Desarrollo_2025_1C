package model;

import java.util.Arrays;

public class VersionedStack_mio implements Stack {

    private static final int MAX_SIZE = 1000;
    private int[][] matriz;
    private int currentVersionIndex = 0;
    private int nextVersionIndex = 1;
    private int[] tops;

    /**
     * Constructor para una VersionedStack_mio vacía.
     *
     * Precondiciones: Ninguna.
     * Postcondiciones: Se crea una nueva VersionedStack_mio con una versión inicial vacía (versión 0).
     * El índice del tope de la versión 0 se inicializa en -1.
     * Estrategia de Implementación: Se inicializa el array `tops` y se establece `tops[0]` en -1.
     */
    public VersionedStack_mio() {
        this.matriz = new int[MAX_SIZE][1]; // Inicializamos con capacidad para 1 versión
        this.tops = new int[1];
        this.tops[0] = -1; // La versión inicial está vacía
    }

    /**
     * Agrega un elemento a la cima de la pila, creando una nueva versión.
     *
     * Precondiciones:
     * - La versión actual de la pila no debe estar llena (`tops[currentVersionIndex] < MAX_SIZE - 1`).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila con el elemento agregado en la cima.
     * - `currentVersionIndex` se actualiza para apuntar a la nueva versión.
     * - `nextVersionIndex` se incrementa.
     * - El elemento `a` se encuentra en la cima de la nueva versión.
     *
     * Estrategia de Implementación:
     * 1. Verificar si la versión actual está llena. Si es así, lanzar una `RuntimeException`.
     * 2. Crear un nuevo array `tops` con una longitud incrementada en 1.
     * 3. Copiar los elementos del array `tops` anterior al nuevo array.
     * 4. Crear una nueva matriz `matriz` con el mismo número de filas (`MAX_SIZE`) y una columna adicional.
     * 5. Copiar el contenido de la matriz `matriz` anterior a la nueva matriz.
     * 6. Copiar los elementos de la versión actual a la nueva columna de la matriz.
     * 7. Incrementar el índice del tope para la nueva versión en el nuevo array `tops`.
     * 8. Almacenar el elemento `a` en la cima de la nueva versión en la nueva matriz.
     * 9. Actualizar `currentVersionIndex` y `nextVersionIndex`.
     * 10. Asignar la nueva matriz `matriz` y el nuevo array `tops` a los atributos de la clase.
     */
    @Override
    public void add(int a) {
        if (tops[currentVersionIndex] == MAX_SIZE - 1) {
            throw new RuntimeException("Current version is full");
        }

        // Crear nuevo array tops con una versión más
        int[] newTops = Arrays.copyOf(this.tops, this.tops.length + 1);

        // Crear nueva matriz con una columna más
        int[][] newMatriz = new int[MAX_SIZE][this.matriz[0].length + 1];
        for (int i = 0; i < MAX_SIZE; i++) {
            System.arraycopy(this.matriz[i], 0, newMatriz[i], 0, this.matriz[0].length);
        }

        // Copiar la versión actual a la nueva versión
        for (int i = 0; i <= tops[currentVersionIndex]; i++) {
            newMatriz[i][nextVersionIndex] = this.matriz[i][currentVersionIndex];
        }

        // Incrementar el top para la nueva versión y agregar el nuevo elemento
        newTops[nextVersionIndex] = tops[currentVersionIndex] + 1;
        newMatriz[newTops[nextVersionIndex]][nextVersionIndex] = a;

        this.tops = newTops;
        this.matriz = newMatriz;
        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Elimina el elemento de la cima de la pila, creando una nueva versión.
     *
     * Precondiciones:
     * - La pila no debe estar vacía (`!isEmpty()`).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila con el elemento de la cima eliminado.
     * - `currentVersionIndex` se actualiza para apuntar a la nueva versión.
     * - `nextVersionIndex` se incrementa.
     * - La nueva versión contiene los mismos elementos que la versión anterior, excepto el que estaba en la cima.
     *
     * Estrategia de Implementación:
     * 1. Verificar si la pila está vacía. Si es así, lanzar una `RuntimeException`.
     * 2. Crear un nuevo array `tops` con una longitud incrementada en 1.
     * 3. Copiar los elementos del array `tops` anterior al nuevo array.
     * 4. Crear una nueva matriz `matriz` con el mismo número de filas (`MAX_SIZE`) y una columna adicional.
     * 5. Copiar el contenido de la matriz `matriz` anterior a la nueva matriz.
     * 6. Copiar los elementos de la versión actual a la nueva columna de la matriz, excluyendo el elemento de la cima.
     * 7. Decrementar el índice del tope para la nueva versión en el nuevo array `tops`.
     * 8. Actualizar `currentVersionIndex` y `nextVersionIndex`.
     * 9. Asignar la nueva matriz `matriz` y el nuevo array `tops` a los atributos de la clase.
     */
    @Override
    public void remove() {
        if (this.isEmpty()) {
            throw new RuntimeException("Can't unstack an empty version.");
        }

        // Crear nuevo array tops con una versión más
        int[] newTops = Arrays.copyOf(this.tops, this.tops.length + 1);

        // Crear nueva matriz con una columna más
        int[][] newMatriz = new int[MAX_SIZE][this.matriz[0].length + 1];
        for (int i = 0; i < MAX_SIZE; i++) {
            System.arraycopy(this.matriz[i], 0, newMatriz[i], 0, this.matriz[0].length);
        }

        // Copiar la versión actual a la nueva versión (excluyendo el top element)
        for (int i = 0; i < tops[currentVersionIndex]; i++) {
            newMatriz[i][nextVersionIndex] = this.matriz[i][currentVersionIndex];
        }

        // Decrementar el top para la nueva versión
        newTops[nextVersionIndex] = tops[currentVersionIndex] - 1;

        this.tops = newTops;
        this.matriz = newMatriz;
        currentVersionIndex = nextVersionIndex;
        nextVersionIndex++;
    }

    /**
     * Obtiene el elemento de la cima de la versión actual de la pila.
     *
     * Precondiciones: La pila no debe estar vacía (`!isEmpty()`).
     *
     * Postcondiciones: Se devuelve el elemento que se encuentra en la cima de la versión actual. La pila no se modifica.
     *
     * Estrategia de Implementación:
     * 1. Verificar si la pila está vacía. Si es así, lanzar una `RuntimeException`.
     * 2. Devolver el elemento ubicado en `matriz[tops[currentVersionIndex]][currentVersionIndex]`.
     */
    @Override
    public int getTop() {
        if (this.isEmpty()) {
            throw new RuntimeException("Cannot get top of an empty stack.");
        }
        return this.matriz[tops[currentVersionIndex]][currentVersionIndex];
    }

    /**
     * Obtiene el elemento de la cima de una versión específica de la pila.
     *
     * Precondiciones:
     * - `version` debe ser un índice válido dentro del rango de versiones existentes (`0 <= version < nextVersionIndex`).
     * - La versión especificada no debe estar vacía (`tops[version] != -1`).
     *
     * Postcondiciones: Se devuelve el elemento que se encuentra en la cima de la versión especificada. La pila no se modifica.
     *
     * Estrategia de Implementación:
     * 1. Verificar si el índice de versión es válido. Si no lo es, lanzar una `RuntimeException`.
     * 2. Verificar si la versión especificada está vacía. Si lo está, lanzar una `RuntimeException`.
     * 3. Devolver el elemento ubicado en `matriz[tops[version]][version]`.
     */
    public int getTop(int version) {
        if (version < 0 || version >= this.nextVersionIndex || tops[version] == -1) {
            throw new RuntimeException("Cannot get top of the specified version.");
        }
        return this.matriz[tops[version]][version];
    }

    public int getTopIndex(){
        return this.tops[currentVersionIndex];
    }

    /**
     * Verifica si la versión actual de la pila está vacía.
     *
     * Precondiciones: Ninguna.
     *
     * Postcondiciones: Se devuelve `true` si la versión actual no contiene elementos, `false` en caso contrario. La pila no se modifica.
     *
     * Estrategia de Implementación: Comprobar si el índice del tope de la versión actual (`tops[currentVersionIndex]`) es igual a -1.
     */
    @Override
    public boolean isEmpty() {
        return tops[currentVersionIndex] == -1;
    }

    /**
     * Imprime el contenido de una versión específica de la pila.
     *
     * Precondiciones: `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (`0 <= versionIndex < nextVersionIndex`).
     *
     * Postcondiciones: Se imprime en la consola el contenido de la versión especificada, con los elementos separados por comas y encerrados entre corchetes.
     *
     * Estrategia de Implementación:
     * 1. Verificar si el índice de versión es válido. Si no lo es, lanzar una `IndexOutOfBoundsException`.
     * 2. Iterar desde el índice 0 hasta el tope de la versión especificada (`tops[versionIndex]`).
     * 3. Imprimir cada elemento de la versión.
     * 4. Formatear la salida con corchetes y comas.
     */
    public void printVersion(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido o versión eliminada: " + versionIndex);
        }
        System.out.print("Versión " + versionIndex + ": [");
        for (int i = 0; i <= this.tops[versionIndex]; i++) {
            System.out.print(this.matriz[i][versionIndex]);
            if (i < this.tops[versionIndex]) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    /**
     * Obtiene el índice de la versión actual de la pila.
     *
     * Precondiciones: Ninguna.
     *
     * Postcondiciones: Se devuelve el valor de `currentVersionIndex`. La pila no se modifica.
     *
     * Estrategia de Implementación: Simplemente devolver el valor de la variable `currentVersionIndex`.
     */
    public int getCurrentVersion() {
        return this.currentVersionIndex;
    }

    /**
     * Crea una nueva versión de la VersionedStack_mio a partir de una versión existente.
     * La nueva versión se convierte en la versión actual de la pila.
     *
     * Precondiciones:
     * - `versionIndex` debe ser un índice válido dentro del rango de versiones existentes (`0 <= versionIndex < nextVersionIndex`).
     *
     * Postcondiciones:
     * - Se crea una nueva versión de la pila que es una copia de la versión especificada.
     * - La `currentVersionIndex` se actualiza para apuntar a la nueva versión creada.
     * - `nextVersionIndex` se incrementa.
     * - Si el `versionIndex` es inválido, se lanza una excepción.
     *
     * Estrategia de Implementación:
     * 1. Verificar si el `versionIndex` está dentro del rango válido de versiones. Si no lo está, lanzar una `IndexOutOfBoundsException`.
     * 2. Crear un nuevo array `tops` con una longitud incrementada en 1.
     * 3. Copiar los elementos del array `tops` anterior al nuevo array.
     * 4. Crear una nueva matriz `matriz` con el mismo número de filas (`MAX_SIZE`) y una columna adicional.
     * 5. Copiar el contenido de la matriz `matriz` anterior a la nueva matriz.
     * 6. Obtener el índice del tope de la versión especificada (`tops[versionIndex]`).
     * 7. Copiar los elementos desde la versión especificada a la siguiente posición disponible en la nueva matriz.
     * 8. Actualizar el índice del tope (`tops`) para la nueva versión en el nuevo array.
     * 9. Actualizar el `currentVersionIndex` para que apunte a la nueva versión.
     * 10. Incrementar el `nextVersionIndex`.
     * 11. Asignar la nueva matriz `matriz` y el nuevo array `tops` a los atributos de la clase.
     */
    public void createVersionFrom(int versionIndex) {
        if (versionIndex < 0 || versionIndex >= this.nextVersionIndex) {
            throw new IndexOutOfBoundsException("Índice de versión inválido: " + versionIndex);
        }

        int topOfSourceVersion = this.tops[versionIndex];

        // Crear nuevo array tops con una versión más
        int[] newTops = Arrays.copyOf(this.tops, this.tops.length + 1);

        // Crear nueva matriz con una columna más
        int[][] newMatriz = new int[MAX_SIZE][this.matriz[0].length + 1];
        for (int i = 0; i < MAX_SIZE; i++) {
            System.arraycopy(this.matriz[i], 0, newMatriz[i], 0, this.matriz[0].length);
        }

        // Copiar los elementos de la versión fuente a la nueva versión
        for (int i = 0; i <= topOfSourceVersion; i++) {
            newMatriz[i][this.nextVersionIndex] = this.matriz[i][versionIndex];
        }

        newTops[this.nextVersionIndex] = topOfSourceVersion;
        this.tops = newTops;
        this.matriz = newMatriz;
        this.currentVersionIndex = this.nextVersionIndex;
        this.nextVersionIndex++;
    }
}