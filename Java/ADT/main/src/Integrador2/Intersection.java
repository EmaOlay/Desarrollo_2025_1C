package Integrador2;

import Integrador2.*;
 
public class Intersection {
 public static List<Dupla> interseccion(Stack<Dupla> pila, Stack<Dupla> colaDePrioridad) { // Cambiado a Stack<Dupla> para colaDePrioridad
        List<Dupla> comunes = crearLista(); // Usamos nuestra List
        List<Dupla> tempPila = crearLista(); // Usamos nuestra List para simular la pila temporal

        // Pasar la pila a la lista temporal (simulando el comportamiento de la pila)
        while (!pila.isEmpty()) {
            tempPila.add(pila.getTop()); // Añade al final de la lista temporal
            pila.remove();
        }

        // Iterar sobre la cola de prioridad (que ahora es una pila)
        while (!colaDePrioridad.isEmpty()) {
            Dupla duplaCola = colaDePrioridad.getTop();
            colaDePrioridad.remove();

            // Iterar sobre la pila temporal
            for (int i = 0; i < tempPila.size(); i++) {
                Dupla duplaPila = tempPila.get(i);
                if (duplaPila.first == duplaCola.first && duplaPila.second == duplaCola.second) {
                    comunes.add(duplaPila);
                    break;
                }
            }
        }

        return comunes;
    }

    // Método auxiliar para crear una instancia de List (asumiendo que tienes una clase que implementa List)
    private static List<Dupla> crearLista() {
        // Reemplaza esto con la forma correcta de instanciar tu List
        return new ListaDuplas(); // Suponiendo que tienes una clase ListaDuplas que implementa List<Dupla>
    }

    // Clase de ejemplo para implementar List<Dupla> (debes implementarla tú)
    private static class ListaDuplas implements List<Dupla> {
        private Dupla[] array = new Dupla[100]; // Tamaño inicial arbitrario
        private int size = 0;

        @Override
        public void add(Dupla a) {
            if (size == array.length) {
                 Dupla[] newArray = new Dupla[array.length * 2];
                for (int i = 0; i < array.length; i++) {
                    newArray[i] = array[i];
                }
                array = newArray;
            }
            array[size++] = a;
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public void remove() {
            if (size > 0) {
                array[--size] = null; // Para ayudar al garbage collector
            }
        }

        @Override
        public Dupla get(int index) {
            if (index >= 0 && index < size) {
                return array[index];
            }
            return null; // O lanzar una excepción, dependiendo de tus requerimientos
        }
    }
}