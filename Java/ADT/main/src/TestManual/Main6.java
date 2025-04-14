package TestManual;

import coord.Coord;
import coord.CoordUtils;

/**
 * Main class for Exercise 6
 */
public class Main6 {
    public static void main(String[] args) {
        System.out.println("Running Exercise 6 - Coord Utils");
        
        // Test the utility functions
        Coord coord1 = new Coord(1.0, 2.0, 3.0);
        Coord coord2 = new Coord(4.0, 5.0, 6.0);
        Coord coord3 = new Coord(1.0, 2.0, 3.0);
        Coord coord4 = new Coord(-1.0, 2.0, 3.0);
        
        // Test distance calculation
        System.out.println("Distance between " + coord1 + " and " + coord2 + ": " + 
                           CoordUtils.distance(coord1, coord2));
        
        // Test equality check
        System.out.println("Are " + coord1 + " and " + coord2 + " equal? " + 
                           CoordUtils.areEqual(coord1, coord2));
        System.out.println("Are " + coord1 + " and " + coord3 + " equal? " + 
                           CoordUtils.areEqual(coord1, coord3));
        
        // Test first octant check
        System.out.println("Is " + coord1 + " in the first octant? " + 
                           CoordUtils.isInFirstOctant(coord1));
        System.out.println("Is " + coord4 + " in the first octant? " + 
                           CoordUtils.isInFirstOctant(coord4));
    }
} 