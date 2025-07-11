package service;

import model.Apartamento;
import model.Inquilino; 
import model.Reserva;
import model.ServiciosExtras;
import java.util.List;

/**
 * Clase encargada de gestionar la lógica de las reservas:
 * - Creación de reservas
 * - Liberación de apartamentos
 */
public class GestorReserva {

    /**
     * Crea una nueva reserva para un apartamento y un inquilino dados.
     * Marca el apartamento como no disponible.
     * @param apartamento El apartamento a reservar.
     * @param inquilino El inquilino que realiza la reserva.
     * @param mes El mes de ingreso al apartamento.
     * @param serviciosExtra Lista de servicios extra seleccionados como objetos ServiciosExtras.
     * @return La nueva instancia de Reserva si es exitosa, null si el apartamento no está disponible.
     * @throws IllegalArgumentException Si el apartamento no está disponible.
     */
    public Reserva crearReserva(Apartamento apartamento, Inquilino inquilino, String mes, List<ServiciosExtras> serviciosExtra) {
        if (!apartamento.isDisponible()) {
            throw new IllegalArgumentException("El apartamento " + apartamento.getNumero() + " no está disponible para reserva.");
        }
        
        return new Reserva(apartamento, inquilino, mes, serviciosExtra);
    }

    /**
     * Libera un apartamento, marcándolo como disponible.
     * @param apartamento El apartamento a liberar.
     * @return true si el apartamento fue liberado exitosamente, false si ya estaba disponible o es nulo.
     */
    public boolean liberarApartamento(Apartamento apartamento) {
        if (apartamento != null && !apartamento.isDisponible()) {
            apartamento.setDisponible(true); // Marca el apartamento como disponible
            System.out.println("Apartamento " + apartamento.getNumero() + " liberado.");
            return true;
        } else if (apartamento != null && apartamento.isDisponible()) {
            System.out.println("Apartamento " + apartamento.getNumero() + " ya estaba disponible.");
            return false;
        }
        return false; // Apartamento nulo
    }
}