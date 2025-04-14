package TestManual;

import coord.Coord;

/**
 * Main class for Exercise 5
 */
public class Main5 {
    public static void main(String[] args) {
        System.out.println("Running Exercise 5 - Coord TDA");
        
        // Test the Coord class
        Coord coord1 = new Coord(1.0, 2.0, 3.0);
        System.out.println("Created coordinate: " + coord1);
        
        // Test the projector utility
        System.out.println("X component (i=0): " + Coord.Projector.project(coord1, 0));
        System.out.println("Y component (i=1): " + Coord.Projector.project(coord1, 1));
        System.out.println("Z component (i=2): " + Coord.Projector.project(coord1, 2));
        
        try {
            Coord.Projector.project(coord1, 3); // This should throw an exception
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught as expected: " + e.getMessage());
        }
    }
} 