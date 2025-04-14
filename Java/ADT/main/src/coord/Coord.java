package coord;

/**
 * TDA Coord - Represents a 3D coordinate
 */
public class Coord {
    private final double x;
    private final double y;
    private final double z;
    
    /**
     * Constructor for Coord
     * Precondition: All parameters must be valid double values
     * Postcondition: A new Coord object is created with the specified values
     */
    public Coord(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Gets the x component
     * @return x value
     */
    public double getX() {
        return x;
    }
    
    /**
     * Gets the y component
     * @return y value
     */
    public double getY() {
        return y;
    }
    
    /**
     * Gets the z component
     * @return z value
     */
    public double getZ() {
        return z;
    }
    
    /**
     * Utility class to project the i-th component of a coordinate
     */
    public static class Projector {
        /**
         * Projects the i-th component of a coordinate
         * Precondition: i must be 0, 1, or 2
         * Postcondition: Returns the i-th component value
         * @param coord The coordinate to project
         * @param i The component index (0 for x, 1 for y, 2 for z)
         * @return The value of the i-th component
         * @throws IllegalArgumentException if i is not 0, 1, or 2
         */
        public static double project(Coord coord, int i) {
            // Invariant: i must be 0, 1, or 2
            if (i < 0 || i > 2) {
                throw new IllegalArgumentException("Component index must be 0, 1, or 2");
            }
            
            switch (i) {
                case 0: return coord.getX();
                case 1: return coord.getY();
                case 2: return coord.getZ();
                default: throw new IllegalArgumentException("Component index must be 0, 1, or 2");
            }
        }
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
} 