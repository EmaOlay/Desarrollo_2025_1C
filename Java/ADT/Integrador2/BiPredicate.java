package Integrador2;

public class BiPredicate implements Predicate {
    BiPredicateEnum op;
    Predicate p1;
    Predicate p2;

    public BiPredicate(BiPredicateEnum op, Predicate p1, Predicate p2) {
        this.op = op;
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean eval(int a) {
        if (op == BiPredicateEnum.AND) {
            return p1.eval(a) && p2.eval(a);
        } else { // OR
            return p1.eval(a) || p2.eval(a);
        }
    }
    //Ej 3
    public Predicate getLeft() {
        return p1;
    }
    public Predicate getRight() {
        return p2;
    }
    public BiPredicateEnum getOperation() {
        return op;
    }
}
