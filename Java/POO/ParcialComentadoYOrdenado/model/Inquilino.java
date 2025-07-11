package model;

// import model.Apartamento; // Asegúrate de importar Apartamento

/**
 * Representa a un camarada (inquilino) que solicita un apartamento.
 * Contiene información personal básica, como nombre y número de libreta.
 * Implementa los atributos, constructor, getters y setters.
 */
public class Inquilino {
    private String nombre;
    private String numeroLibreta;
    private Apartamento apartamento; // Apartamento asociado a la solicitud (puede ser el reservado)
    private String partido;

    /**
     * Constructor de la clase Inquilino.
     * @param nombre Nombre del inquilino.
     * @param numeroLibreta Número de libreta del inquilino.
     * @param apartamento Apartamento solicitado por el inquilino.
     * @param partido Partido político al que pertenece el inquilino.
     */
    public Inquilino(String nombre, String numeroLibreta, Apartamento apartamento, String partido) {
        this.nombre = nombre;
        this.numeroLibreta = numeroLibreta;
        this.apartamento = apartamento;
        this.partido = partido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroLibreta() {
        return numeroLibreta;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    // Ya existe: Este es el método importante para obtener el partido.
    public String getPartido() {
        return partido;
    }

    @Override
    public String toString() {
        return "Inquilino{" +
                "nombre='" + nombre + '\'' +
                ", numeroLibreta='" + numeroLibreta + '\'' +
                ", apartamento=" + (apartamento != null ? apartamento.getNumero() + " (" + apartamento.getTipo() + ")" : "N/A") +
                ", partido='" + partido + '\'' +
                '}';
    }
}