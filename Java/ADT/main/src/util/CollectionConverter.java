package util;

// import model.List;
// import model.Stack;
import model.StaticList;
import model.StaticStack;


public class CollectionConverter {

    /**
     * Convierte una lista en una pila (Stack).
     * Los elementos de la lista se apilan en el mismo orden en que aparecen en la lista
     * (el primer elemento de la lista será el fondo de la pila).
     *
     * @param list La lista a convertir.
     * @param <T>  El tipo de elemento en la lista y la pila.
     * @return Una nueva pila (Stack) que contiene los elementos de la lista.
     * @throws NullPointerException si la lista de entrada es null.
     */
    public static StaticStack mapListToStack(StaticList list) {
        if (list == null) {
            throw new NullPointerException("La lista de entrada no puede ser null.");
        }
        StaticStack stack = new StaticStack(list.size());
        for (int i = 0; i < list.size(); i++) {
            stack.add(list.get(i));
        }
        return stack;
    }

}