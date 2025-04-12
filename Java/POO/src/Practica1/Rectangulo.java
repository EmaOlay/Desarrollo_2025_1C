package Practica1;

public class Rectangulo {
    float base, altura;

    Rectangulo(float basesita, float alturita) {
        this.base = basesita;
        this.altura = alturita;
    }

    float CalculoArea() {
        return this.base * this.altura;
    }

    float CalculoPerimetro() {
        return 2 * this.base + 2 * this.altura;
    }

    void ImprimoResultados() {
        System.out.println("Mi area es: " + this.CalculoArea());
        System.out.println("Mi perimetro es: " + this.CalculoPerimetro());
    }
}
