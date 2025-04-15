package TestManual;

import coord.Coord;
import coord.CoordUtils;

public class Main6 {
    public static void main(String[] args) {

        

        Coord coord1 = new Coord(1.0, 2.0, 3.0);
        Coord coord2 = new Coord(4.0, 5.0, 6.0);
        Coord coord3 = new Coord(1.0, 2.0, 3.0);
        Coord coord4 = new Coord(-1.0, 2.0, 3.0);

        System.out.println("Distnacia " + coord1 + " y " + coord2 + ": " + 
                           CoordUtils.distance(coord1, coord2));
        

        System.out.println("Son " + coord1 + " y " + coord2 + " igual? " + 
                           CoordUtils.areEqual(coord1, coord2));
        System.out.println("Son " + coord1 + " y " + coord3 + " igual? " + 
                           CoordUtils.areEqual(coord1, coord3));
        

        System.out.println("Esta " + coord1 + " en el primer octante? " + 
                           CoordUtils.isInFirstOctant(coord1));
        System.out.println("Esta " + coord4 + " en el primer octante? " + 
                           CoordUtils.isInFirstOctant(coord4));
    }
} 