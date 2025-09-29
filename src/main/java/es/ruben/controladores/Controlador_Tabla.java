package es.ruben.controladores;

import es.ruben.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.Stack;
import javafx.scene.control.Tooltip;

/**
 * Controlador de la vista de la tabla de personas.
 * Gestiona la interacción entre la interfaz FXML y la lógica de la aplicación.
 * Permite añadir nuevas personas, eliminar seleccionadas y restaurar la última eliminada.
 * Inicializa la tabla con tres personas de ejemplo.
 */
public class Controlador_Tabla {

    @FXML private Button BotonAdd;
    @FXML private DatePicker Dates;
    @FXML private TextField TextoFirstName;
    @FXML private TextField TextoLastName;
    @FXML private Button botonDelete;
    @FXML private Button botonRestore;

    @FXML private TableView<Persona> tablaPersonas;
    @FXML private TableColumn<Persona, Number> colId;
    @FXML private TableColumn<Persona, String> colFirstName;
    @FXML private TableColumn<Persona, String> colLastName;
    @FXML private TableColumn<Persona, LocalDate> colBirthDate;

    private final ObservableList<Persona> personas = FXCollections.observableArrayList();
    private final Stack<Persona> deletedStack = new Stack<>();

    /**
     * Inicializa la tabla de personas y vincula las columnas con las propiedades
     * de la clase Persona. También precarga la tabla con tres registros de ejemplo.
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        colBirthDate.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());

        tablaPersonas.setItems(personas);

        personas.addAll(
                new Persona(1, "Mikel", "Boada", LocalDate.of(2005, 3, 17)),
                new Persona(2, "Unai", "Zugaza", LocalDate.of(2006, 3, 20)),
                new Persona(3, "Igor", "Esparza", LocalDate.of(2003, 10, 8))
        );
    }

    /**
     * Añade una nueva persona a la tabla tomando los valores de los campos de texto y del datepicker.
     * Si algún campo está vacío, muestra una alerta.
     */
    @FXML
    private void onaddClicked() {
        String firstName = TextoFirstName.getText().trim();
        String lastName = TextoLastName.getText().trim();
        LocalDate birthDate = Dates.getValue();

        if(firstName.isEmpty() || lastName.isEmpty() || birthDate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Rellena todos los campos.");
            alert.showAndWait();
            return;
        }

        int newId = personas.size() + 1;
        Persona p = new Persona(newId, firstName, lastName, birthDate);
        personas.add(p);

        TextoFirstName.clear();
        TextoLastName.clear();
        Dates.setValue(null);
    }

    /**
     * Elimina la fila seleccionada de la tabla y la guarda en la pila de eliminados
     * para poder restaurarla posteriormente. Muestra una alerta si no hay fila seleccionada.
     */
    @FXML
    private void onDeleteClicked() {
        Persona selected = tablaPersonas.getSelectionModel().getSelectedItem();
        if(selected != null) {
            personas.remove(selected);
            deletedStack.push(selected);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona una fila para eliminar.");
            alert.showAndWait();
        }
    }

    /**
     * Restaura la última persona eliminada agregándola nuevamente a la tabla.
     * Actualiza los ID de todas las filas. Muestra una alerta si no hay filas para restaurar.
     */
    @FXML
    private void onRestoreClicked() {
        if(!deletedStack.isEmpty()) {
            Persona restored = deletedStack.pop();
            personas.add(restored);
            for(int i = 0; i < personas.size(); i++) {
                personas.get(i).idProperty().set(i + 1);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No hay filas para restaurar.");
            alert.showAndWait();
        }
    }
}
