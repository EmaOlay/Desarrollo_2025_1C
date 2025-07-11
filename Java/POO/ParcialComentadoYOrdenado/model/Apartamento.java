package model;

/**
 * Clase base para representar un apartamento del edificio.
 * Se espera que sea extendida por clases como ApartamentoSimple, ApartamentoDoble, etc.
 * Cada apartamento tiene un número único, una capacidad máxima y un precio base por mes.
 * Cumple con el Principio de Abierto/Cerrado (OCP) de SOLID.
 */
public abstract class Apartamento {
    private int numero;
    private int capacidadMaxima;
    private double precioBase;
    private String tipo;
    private boolean disponible = true; // Por defecto, un apartamento está disponible

    /**
     * Constructor de la clase Apartamento.
     * @param numero Número único del apartamento.
     * @param capacidadMaxima Capacidad máxima de personas que puede alojar el apartamento.
     * @param precioBase Precio base mensual del apartamento.
     * @param tipo Tipo de apartamento (e.g., "Simple", "Doble", "Proletario").
     */
    public Apartamento(int numero, int capacidadMaxima, double precioBase, String tipo) {
        this.numero = numero;
        this.capacidadMaxima = capacidadMaxima;
        this.precioBase = precioBase;
        this.tipo = tipo;
    }

    /**
     * Método abstracto para calcular el precio mensual del apartamento.
     * Este método debe ser implementado por las subclases.
     * @return Precio mensual del apartamento.
     */
    public abstract double calcularPrecioMensual();

    public int getNumero() {
        return numero;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Apartamento{" +
                "numero=" + numero +
                ", capacidadMaxima=" + capacidadMaxima +
                ", precioBase=" + precioBase +
                ", tipo='" + tipo + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}