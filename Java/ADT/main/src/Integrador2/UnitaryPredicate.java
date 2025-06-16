package Integrador2;

public class UnitaryPredicate implements Predicate {
    UnitaryPredicateEnum op;
    Predicate p;

    public UnitaryPredicate(UnitaryPredicateEnum op, Predicate p) {
        this.op = op;
        this.p = p;
    }

    public boolean eval(int a) {
        if (op == UnitaryPredicateEnum.IDENTITY) {
            return p.eval(a);
        } else { // NOT
            return !p.eval(a);
        }
    }
    //Ej 3
    public Predicate getValue() {
        return p;
    }
    public UnitaryPredicateEnum getOperation() {
        return op;
    }

}

