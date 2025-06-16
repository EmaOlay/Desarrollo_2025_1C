package Practica1;

public class circulo {
    float radio;
    float diametro;
    double area;
    String Color;

    void CalculoDiametro() {
        this.diametro = 2 * this.radio;
    }

    void CalculoArea() {
        this.area = Math.PI * Math.pow(this.radio, 2);
    }

    void ImprimoArea() {
        System.out.println("Mi area es: " + this.area + ".");
    }

    void ImprimoColor() {
        System.out.println("Mi Color es: " + this.Color + ".");
    }

    void ImprimoColorArea() {
        this.ImprimoArea();
        this.ImprimoColor();
    }

    circulo() {
        this.radio = 1;
        this.CalculoDiametro();
        this.CalculoArea();
        this.Color = "Black";
    }

    circulo(float numerito) {
        this.radio = numerito;
        this.CalculoDiametro();
        this.CalculoArea();
        this.Color = "Black";
    }

    circulo(float numerito, String colorcito) {
        this.radio = numerito;
        this.CalculoDiametro();
        this.CalculoArea();
        this.Color = colorcito;
    }
}