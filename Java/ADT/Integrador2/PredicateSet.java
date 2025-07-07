package Integrador2;
 
public class PredicateSet implements Set {
 
    private final Predicate predicate;
 
    public PredicateSet(Predicate predicate) {
        this.predicate = predicate;
    }
 
    @Override
    public void add(int x) {
        throw new UnsupportedOperationException("El conjunto basado en Predicate no permite agregar elementos directamente.");
    }
 
    @Override
    public void remove(int x) {
        throw new UnsupportedOperationException("El conjunto basado en Predicate no permite eliminar elementos directamente.");
    }
 
    @Override
    public boolean isEmpty() {
        // Estrategia: Intentar elegir un elemento. Si no existe, está vacío.  (estrategia del rango [0, 1000) [cite: 33, 34])
        return choose() == -1;
    }

 
    @Override
    public int choose() {
        // Estrategia:  Iterar hasta encontrar un elemento (estrategia del rango [0, 1000) [cite: 33])
        for (int i = 0; i < 1000; i++) {
            if (predicate.eval(i)) {
                return i;
            }
        }
        return -1; //  Indicador de conjunto vacío o universal (depende del contexto)
    }
}

// Justificación:
// - Estatica:  El "conjunto" no permite agregar o remover elementos(Predicates).
// - Es no destructiva.
// No se elimina información ni se modifica el estado interno.
// Los métodos como choose o isEmpty no alteran el conjunto.
