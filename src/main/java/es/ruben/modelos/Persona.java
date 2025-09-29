package es.ruben.modelos;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Representa una persona con identificador, nombre, apellido y fecha de nacimiento.
 * Utiliza propiedades de JavaFX para permitir la vinculación con controles de la interfaz.
 */
public class Persona {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthDate = new SimpleObjectProperty<>();

    /**
     * Crea una nueva persona con los datos proporcionados.
     * @param id Identificador único de la persona
     * @param firstName Nombre de la persona
     * @param lastName Apellido de la persona
     * @param birthDate Fecha de nacimiento
     */
    public Persona(int id, String firstName, String lastName, LocalDate birthDate) {
        this.id.set(id);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.birthDate.set(birthDate);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public String getFirstName() { return firstName.get(); }
    public void setFirstName(String firstName) { this.firstName.set(firstName); }
    public StringProperty firstNameProperty() { return firstName; }

    public String getLastName() { return lastName.get(); }
    public void setLastName(String lastName) { this.lastName.set(lastName); }
    public StringProperty lastNameProperty() { return lastName; }

    public LocalDate getBirthDate() { return birthDate.get(); }
    public void setBirthDate(LocalDate birthDate) { this.birthDate.set(birthDate); }
    public ObjectProperty<LocalDate> birthDateProperty() { return birthDate; }
}