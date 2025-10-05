package es.ruben.modelos;

import java.time.LocalDate;

/**
 * Clase de modelo que representa una persona dentro de la aplicación.
 *
 * <p>Esta clase actúa como entidad principal del sistema, reflejando la estructura
 * de la tabla <b>persona</b> en la base de datos. Se utiliza tanto para mostrar
 * datos en la interfaz JavaFX como para realizar operaciones CRUD a través del DAO.</p>
 *
 * <p>Campos principales:
 * <ul>
 *     <li><b>id</b>: identificador único en la base de datos.</li>
 *     <li><b>nombre</b>: nombre de la persona.</li>
 *     <li><b>apellido</b>: apellido de la persona.</li>
 *     <li><b>fechaNacimiento</b>: fecha de nacimiento en formato {@link LocalDate}.</li>
 * </ul>
 * </p>
 *
 * <p>Ejemplo de uso:
 * <pre>
 * Persona p = new Persona(1, "María", "López", LocalDate.of(1990, 5, 12));
 * System.out.println(p.getNombre()); // "María"
 * </pre>
 * </p>
 *
 * @author Rubén
 * @version 1.0
 */
public class Persona {

    /** Identificador único de la persona (clave primaria en la base de datos). */
    private int id;

    /** Nombre de la persona. */
    private String nombre;

    /** Apellido de la persona. */
    private String apellido;

    /** Fecha de nacimiento de la persona. */
    private LocalDate fechaNacimiento;

    /**
     * Crea una nueva instancia de {@code Persona} con los datos especificados.
     *
     * @param id identificador único de la persona (usualmente asignado por la base de datos).
     * @param nombre nombre de la persona.
     * @param apellido apellido de la persona.
     * @param fechaNacimiento fecha de nacimiento de la persona en formato {@link LocalDate}.
     */
    public Persona(int id, String nombre, String apellido, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
    }

    /** @return el identificador único de la persona. */
    public int getId() {
        return id;
    }

    /** @return el nombre de la persona. */
    public String getNombre() {
        return nombre;
    }

    /** @return el apellido de la persona. */
    public String getApellido() {
        return apellido;
    }

    /** @return la fecha de nacimiento de la persona. */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /** @param id nuevo identificador único para la persona. */
    public void setId(int id) {
        this.id = id;
    }

    /** @param nombre nuevo nombre de la persona. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @param apellido nuevo apellido de la persona. */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /** @param fechaNacimiento nueva fecha de nacimiento de la persona. */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Devuelve una representación legible del objeto {@code Persona}.
     *
     * @return cadena de texto con el formato "nombre apellido (fechaNacimiento)".
     */
    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + fechaNacimiento + ")";
    }
}
