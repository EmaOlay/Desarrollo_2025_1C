package Integrador2;



public class PredicateOptimizer {
    public static Predicate optimize(Predicate p) {
        if (p instanceof BiPredicate bp) {
            Predicate left = optimize(bp.getLeft());
            Predicate right = optimize(bp.getRight());
 
            BiPredicateEnum op = bp.getOperation();
 
            // --- Propiedad 1: A ∪ ∅ = A ---
            if (op == BiPredicateEnum.OR) {
                if (isEmpty(left)) return right;
                if (isEmpty(right)) return left;
            }
 
            // --- Propiedad 2: A ∩ ∅ = ∅ ---
            if (op == BiPredicateEnum.AND) {
                if (isEmpty(left) || isEmpty(right)) {
                    return emptyPredicate();
                }
            }
 
            // --- Propiedad 3: A ∩ U = A ---
            if (op == BiPredicateEnum.AND) {
                if (isUniversal(left)) return right;
                if (isUniversal(right)) return left;
            }
 
            return new BiPredicate(op, left, right);
        }
 
        if (p instanceof UnitaryPredicate up) {
            return new UnitaryPredicate(up.getOperation(), optimize(up.getValue()));
        }
 
        return p; // atomic o ya reducido
    }

    private static boolean isEmpty(Predicate p) {
        Set s = new PredicateSet(p);
        return s.isEmpty();
    }
 
    private static boolean isUniversal(Predicate p) {
        for (int i = 0; i < 1000; i++) {
            if (!p.eval(i)) return false;
        }
        return true;
    }
    // ∅ = P(x) = x == -1 ∧ ¬(x == -1)
    private static Predicate emptyPredicate() {
        Predicate base = new AtomicPredicate(-1);
        return new BiPredicate(
            BiPredicateEnum.AND,
            base,
            new UnitaryPredicate(UnitaryPredicateEnum.NOT, base)
        );
    }
}