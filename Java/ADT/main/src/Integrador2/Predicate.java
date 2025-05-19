package Integrador2;
// Interfaz Predicate [cite: 26, 27]
public interface Predicate {
    boolean eval(int a);
}

// Enumerados [cite: 23, 24, 25]
enum UnitaryPredicateEnum {
    IDENTITY, NOT
}

enum BiPredicateEnum {
    AND, OR
}

