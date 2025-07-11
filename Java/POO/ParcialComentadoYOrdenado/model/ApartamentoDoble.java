// ApartamentoDoble.java
package model;

public class ApartamentoDoble extends Apartamento {
    public ApartamentoDoble(int numero, int capacidadMaxima, double precioBase, String tipo) {
        super(numero, capacidadMaxima, precioBase, tipo);
    }

    @Override
    public double calcularPrecioMensual() {
        return getPrecioBase() * 1.2; // Ejemplo: 20% más caro por ser doble
    }
}