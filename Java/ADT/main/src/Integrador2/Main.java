package Integrador2;

public class Main {
    // Main method to test the Predicate classes
    public static void main(String[] args) {
        System.out.println("--------------Testing Ejercicio 1------------");
        // Create atomic predicates
        Predicate p1 = new AtomicPredicate(5);
        Predicate p2 = new AtomicPredicate(10);

        // Create unitary predicate
        Predicate up = new UnitaryPredicate(UnitaryPredicateEnum.NOT, p1);
        Predicate up2 = new UnitaryPredicate(UnitaryPredicateEnum.IDENTITY, p1);

        // Create bi-predicate
        Predicate bp = new BiPredicate(BiPredicateEnum.AND, p1, p2);
        Predicate bp2 = new BiPredicate(BiPredicateEnum.AND, p2, p2);
        Predicate bp3 = new BiPredicate(BiPredicateEnum.OR, p1, p2);
        // Test predicates
        System.out.println("Atomic Predicate (5): " + p1.eval(5)); // true
        System.out.println("Atomic Predicate (10): " + p2.eval(10)); // true
        System.out.println("Unitary Predicate (NOT 5): " + up.eval(5)); // false
        System.out.println("Unitary Predicate (IDENTITY 10): " + up2.eval(10)); // false
        System.out.println("Unitary Predicate (IDENTITY 5): " + up2.eval(5)); // true
        System.out.println("Bi-Predicate (5 AND 10): " + bp.eval(5)); // false
        System.out.println("Bi-Predicate (10 AND 10): " + bp2.eval(10)); // true
        System.out.println("Bi-Predicate (5 OR 10): " + bp3.eval(10)); // true
        System.out.println("Bi-Predicate (5 OR 10): " + bp3.eval(15)); // false

        System.out.println("------------ FIN Testing Ejercicio 1 ------------");
        System.out.println("--------------Testing Ejercicio 2------------");

        Predicate a = new AtomicPredicate(3); // {3}
        Predicate b = new AtomicPredicate(5); // {5}
        Predicate conjuntoLogico = new BiPredicate(BiPredicateEnum.OR, a, b); // {3,5}
 
        Set set = new PredicateSet(conjuntoLogico);
 
        // Debatir try catch
        // set.remove(1); // throw an exception
        // set.add(1); // throw an exception
        System.out.println(set.choose());    // 3
        System.out.println(set.isEmpty());   // false
        System.out.println("--------------FIN Testing Ejercicio 2------------");
        System.out.println("--------------Testing Ejercicio 3------------");
        Predicate a1 = new AtomicPredicate(3); // {3}
        Predicate b1 = new BiPredicate(
            BiPredicateEnum.AND,
            a,
            new UnitaryPredicate(UnitaryPredicateEnum.NOT, a)
        ); // ∅
        
        Predicate expr = new BiPredicate(BiPredicateEnum.OR, a1, b1); // A ∪ ∅
        Predicate opt = PredicateOptimizer.optimize(expr);
        
        System.out.println(opt.eval(3)); // true
        System.out.println(opt.eval(4)); // false
        System.out.println("--------------FIN Testing Ejercicio 3------------");
        System.out.println("--------------Testing Ejercicio 4------------");
        SpecialPriorityQueue spq = new SpecialPriorityQueue();
        spq.add(1, 10);
        spq.add(2, 20);
        spq.add(4, 30);
        spq.add(3, 40);

        System.out.println("First element: " + spq.getFirst()); // 10
        System.out.println("First priority: " + spq.getPriority()); // 1
        System.out.println("Has peak: " + spq.hasPeak()); // true
        spq.remove();
        System.out.println("First element after remove: " + spq.getFirst()); // 20
        System.out.println("First priority after remove: " + spq.getPriority()); // 2
        System.out.println("Has peak after remove: " + spq.hasPeak()); // true
        spq.remove();
        spq.remove();
        spq.remove();
        // spq.remove(); // Throws exception
        System.out.println("Is empty: " + spq.isEmpty()); // true
        System.out.println("Has peak after all removes: " + spq.hasPeak()); // false
        System.out.println("--------------Fin Testing Ejercicio 4------------");
    }
    
}

