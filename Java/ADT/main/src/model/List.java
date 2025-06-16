package model;

public interface List {

    /**
     * Precondición: La lista no está llena (en implementaciones estáticas).
     * Postcondición: El elemento se agrega al final de la lista.
     * Estrategia: Se busca la primera posición libre al final del arreglo interno
     * y se coloca el elemento en esa posición, actualizando el contador de elementos.
     * @param element El elemento a agregar a la lista.
     */
    void add(int element);

    /**
     * Precondición: La lista no está vacía.
     * Postcondición: Se elimina el elemento al final de la lista.
     * Estrategia: Se decrementa el contador de elementos, efectivamente
     * "olvidando" el último elemento. En implementaciones con borrado real,
     * se podría marcar la posición como libre o null.
     */
    void remove();

    /**
     * Precondición: La lista no está vacía.
     * Postcondición: No se modifica la lista.
     * Estrategia: Se accede al último elemento de la lista utilizando el
     * contador de elementos - 1 como índice en el arreglo interno.
     * @return El último elemento de la lista.
     */
    int getFirst();

    /**
     * Precondición: La lista no está vacía.
     * Postcondición: No se modifica la lista.
     * Estrategia: Se accede al primer elemento de la lista (índice 0)
     * del arreglo interno.
     * @return El primer elemento de la lista.
     */
    int getLast();

    /**
     * Precondición: La lista no está vacía y la posición es válida
     * (0 <= position < size()).
     * Postcondición: No se modifica la lista.
     * Estrategia: Se accede al elemento en la posición dada utilizando
     * el índice directamente en el arreglo interno.
     * @param position La posición del elemento a obtener (basada en 0).
     * @return El elemento en la posición especificada.
     */
    int get(int position);

    /**
     * Precondición: La lista no está llena (en implementaciones estáticas) y
     * la posición es válida (0 <= position <= size()).
     * Postcondición: El elemento se inserta en la posición especificada,
     * desplazando los elementos siguientes hacia la derecha.
     * Estrategia: Se desplazan los elementos desde la posición dada hasta el final
     * una posición a la derecha. Luego, se coloca el nuevo elemento en la posición dada
     * y se incrementa el contador de elementos.
     * @param element El elemento a insertar.
     * @param position La posición donde se insertará el elemento (basada en 0).
     */
    void insert(int element, int position);

    /**
     * Precondición: La lista no está vacía y la posición es válida
     * (0 <= position < size()).
     * Postcondición: El elemento en la posición especificada se elimina,
     * y los elementos siguientes se desplazan hacia la izquierda.
     * Estrategia: Se desplazan los elementos desde la posición dada + 1 hasta el final
     * una posición a la izquierda. Se decrementa el contador de elementos.
     * @param position La posición del elemento a eliminar (basada en 0).
     */
    void delete(int position);

    /**
     * Postcondición: Indica si la lista no contiene elementos.
     * Estrategia: Se verifica si el contador de elementos es igual a 0.
     * @return true si la lista está vacía, false en caso contrario.
     */
    boolean isEmpty();

    /**
     * Postcondición: Indica la cantidad de elementos en la lista.
     * Estrategia: Se devuelve el valor del contador de elementos.
     * @return El número de elementos en la lista.
     */
    int size();

    /**
     * Precondición: El elemento a buscar no es null.
     * Postcondición: No se modifica la lista.
     * Estrategia: Se itera sobre los elementos de la lista comparando cada
     * elemento con el valor buscado. Se devuelve la primera posición donde se encuentra.
     * @param element El elemento a buscar.
     * @return La primera posición (basada en 0) donde se encuentra el elemento,
     * o -1 si el elemento no está en la lista.
     */
    int find(int element);
}