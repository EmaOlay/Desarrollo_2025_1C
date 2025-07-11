package model;

public class ApartamentoSimple extends Apartamento {
    public ApartamentoSimple(int numero, int capacidadMaxima, double precioBase, String tipo) {
        super(numero, capacidadMaxima, precioBase, tipo);
    }

    @Override
    public double calcularPrecioMensual() {
        return getPrecioBase();
    }
}