package Practica1;

public class Persona {
    String Nombre;
    int Edad;

    Persona(String Nombre, int Edad) {
        this.Nombre = Nombre;
        this.Edad = Edad;
    }
    void Presentarse(){
        System.out.println("Hola mi nombre es, "+this.Nombre+", tengo "+this.Edad+" anios.");
    }
}
