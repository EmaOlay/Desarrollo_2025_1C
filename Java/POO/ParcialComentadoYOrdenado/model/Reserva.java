package model;

import java.util.List;

/**
 * Representa una reserva realizada por un inquilino.
 * Contiene la información del apartamento asignado, el inquilino,
 * el mes de ingreso, los servicios extra seleccionados y la lógica para calcular el total.
 */
public class Reserva {
    private Apartamento apartamento;
    private Inquilino inquilino;
    private String mes;
    private List<ServiciosExtras> serviciosExtra;
    private static final double IMPUESTO = 0.10; // 10% de impuesto sobre el total

    /**
     * Constructor de la clase Reserva.
     * @param apartamento Apartamento reservado.
     * @param inquilino Inquilino que realiza la reserva.
     * @param mes Mes de ingreso al apartamento.
     * @param serviciosExtra Lista de servicios extra seleccionados por el inquilino.
     */
    public Reserva(Apartamento apartamento, Inquilino inquilino, String mes, List<ServiciosExtras> serviciosExtra) {
        this.apartamento = apartamento;
        this.inquilino = inquilino;
        this.mes = mes;
        this.serviciosExtra = serviciosExtra;
        
        if (apartamento.isDisponible()) {
            apartamento.setDisponible(false);
        } else {
            // Esto solo se ejecutaría si se llama a este constructor sin la validación previa de la UI,
            // pero es una salvaguarda.
            System.out.println("Advertencia: Se intentó crear una reserva para un apartamento ya no disponible.");
        }
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }
    public String getMes() {
        return mes;
    }

    public List<ServiciosExtras> getServiciosExtra() {
        return serviciosExtra;
    }

    /**
     * Calcula el total mensual de la reserva, incluyendo el precio del apartamento,
     * los servicios extra y un impuesto del 10%.
     * Se eliminó el switch para usar el precio de cada objeto ServiciosExtras.
     * @return El total mensual a pagar.
     */
    public double calcularTotalMensual() {
        double total = apartamento.calcularPrecioMensual();
        for (ServiciosExtras servicio : serviciosExtra) {
            total += servicio.getPrecio(); // Ahora sumamos directamente el precio del objeto ServicioExtra
        }
        return total * (1 + IMPUESTO); // Aplicar impuesto del 10%
    }

    /**
     * Retorna un string con los detalles completos de la reserva para mostrar en la UI.
     * @return String con los detalles de la reserva.
     */
    public String obtenerDetallesReserva() {
        StringBuilder detalles = new StringBuilder();
        detalles.append("--- Detalle de la Reserva ---\n");
        detalles.append("Inquilino: ").append(inquilino.getNombre()).append("\n");
        detalles.append("Libreta N°: ").append(inquilino.getNumeroLibreta()).append("\n");
        detalles.append("Partido Político: ").append(inquilino.getPartido()).append("\n");
        detalles.append("Apartamento: ").append(apartamento.getTipo()).append(" ").append(apartamento.getNumero());
        detalles.append(" (Precio Base: $").append(String.format("%.2f", apartamento.getPrecioBase())).append(")\n");
        detalles.append("Mes de Ingreso: ").append(mes).append("\n");

        if (!serviciosExtra.isEmpty()) {
            detalles.append("Servicios Extra:\n");
            for (ServiciosExtras servicio : serviciosExtra) {
                detalles.append("- ").append(servicio.getNombre()).append(" ($").append(String.format("%.2f", servicio.getPrecio())).append(")\n");
            }
        } else {
            detalles.append("No se seleccionaron servicios extra.\n");
        }
        
        detalles.append("\nCosto Total Mensual (incl. Impuesto del 10%): $").append(String.format("%.2f", calcularTotalMensual()));
        
        return detalles.toString();
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "apartamento=" + apartamento +
                ", inquilino=" + inquilino +
                ", mes='" + mes + '\'' +
                ", serviciosExtra=" + serviciosExtra +
                ", totalMensual=" + calcularTotalMensual() +
                '}';
    }
}