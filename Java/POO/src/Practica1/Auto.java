package Practica1;

public class Auto {
    int anio;
    String marca, modelo;

    Auto() {
        this.anio = 1900;
        this.marca = "No Marca";
        this.modelo = "No Modelo";
    }

    public void mostratFicha() {
        System.out.println("Ficha tecnica: " + this.anio + ";" + this.marca + ";" + this.modelo);
    }
}
