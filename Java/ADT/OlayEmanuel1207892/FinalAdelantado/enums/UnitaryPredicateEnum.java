package enums;

public enum UnitaryPredicateEnum {
    // Represents P(0), P(1), P(2)
    P0(0),
    P1(1),
    P2(2);

    private final int value;

    UnitaryPredicateEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // Static method to determine the predicate based on LU
    public static UnitaryPredicateEnum getPredicate(int lu) {
        int result = lu % 3;
        switch (result) {
            case 0:
                return P0;
            case 1:
                return P1;
            case 2:
                return P2;
            default:
                // This case should ideally not be reached with modulo 3
                throw new IllegalArgumentException("Invalid LU modulo result.");
        }
    }
}