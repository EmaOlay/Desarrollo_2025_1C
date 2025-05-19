package Integrador2;

public class AtomicPredicate implements Predicate {
    int value;

    public AtomicPredicate(int value) {
        this.value = value;
    }

    public boolean eval(int a) {
        return a == value;
    }
}
