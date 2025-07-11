package model;

/**
 * Representa un apartamento diseñado para compatriotas de bajos recursos.
 * Su precio mensual es menor, posiblemente subsidiado o con condiciones especiales.
 */
public class ApartamentoProletario extends Apartamento {

    // Podrías agregar atributos específicos para este tipo de apartamento si fueran necesarios.
    // Por ejemplo, un subsidio específico o un recargo especial.

    public ApartamentoProletario(int numero, int capacidadMaxima, double precioBase, String tipo) {
        super(numero, capacidadMaxima, precioBase, tipo);
    }

    @Override
    public double calcularPrecioMensual() {
        // Ejemplo: Un apartamento proletario podría tener un descuento fijo
        // o un precio base menor por diseño. Aquí asumo que el precioBase
        // ya refleja su naturaleza "proletaria" o le aplicamos un descuento adicional.
        // Vamos a aplicar un pequeño descuento fijo sobre el precio base para demostrar.
        return getPrecioBase() * 0.9; // 10% de descuento sobre el precio base
    }
}