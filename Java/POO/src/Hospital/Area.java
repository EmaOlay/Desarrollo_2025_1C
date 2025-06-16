package Hospital;

import java.util.ArrayList;
import java.util.List;

public class Area {
    private String nombre;
    private int piso;
    private List<Habitacion> habitaciones = new ArrayList<>();


    public String getNombre() {
        return nombre;
    }
    public Habitacion getHabitacionPorNumero(int numero) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumero() == numero) {
                return habitacion;
            }
        }
        return null;
    }
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }
}
