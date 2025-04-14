package coord;

/**
 * Utility class for coordinate operations
 */
public class CoordUtils {
    
    /**
     * Calculates the Euclidean distance between two coordinates
     * @param c1 First coordinate
     * @param c2 Second coordinate
     * @return The distance between c1 and c2
     */
    public static double distance(Coord c1, Coord c2) {
        double dx = c1.getX() - c2.getX();
        double dy = c1.getY() - c2.getY();
        double dz = c1.getZ() - c2.getZ();
        
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
    
    /**
     * Checks if two coordinates are equal
     * Two coordinates are considered equal if all their components are equal
     * @param c1 First coordinate
     * @param c2 Second coordinate
     * @return true if the coordinates are equal, false otherwise
     */
    public static boolean areEqual(Coord c1, Coord c2) {
        return c1.getX() == c2.getX() && 
               c1.getY() == c2.getY() && 
               c1.getZ() == c2.getZ();
    }
    
    /**
     * Checks if a coordinate belongs to the first octant
     * A coordinate is in the first octant if all its components are positive
     * @param c The coordinate to check
     * @return true if the coordinate is in the first octant, false otherwise
     */
    public static boolean isInFirstOctant(Coord c) {
        return c.getX() > 0 && c.getY() > 0 && c.getZ() > 0;
    }
} 