package Integrador2;

public class Dupla {
    public int first;
    public int second;
 
    public Dupla(int first, int second) {
        this.first = first;
        this.second = second;
    }
    public boolean equals(Dupla other) {
        return this.first == other.first && this.second == other.second;
    }
     public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }
}
