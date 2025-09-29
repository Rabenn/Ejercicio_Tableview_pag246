package es.ruben.controladores;

import es.ruben.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.util.Stack;
import javafx.scene.control.Tooltip;

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

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        colLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        colBirthDate.setCellValueFactory(cellData -> cellData.getValue().birthDateProperty());

        // Vinculación de la lista
        tablaPersonas.setItems(personas);

        // Precargar con 3 personas
        personas.addAll(
                new Persona(1, "Mikel", "Boada", LocalDate.of(2005, 3, 17)),
                new Persona(2, "Unai", "Zugaza", LocalDate.of(2006, 3, 20)),
                new Persona(3, "Igor", "Esparza", LocalDate.of(2003, 10, 8))
        );
    }


    @FXML
    private void onaddClicked() {
        String firstName = TextoFirstName.getText().trim();
        String lastName = TextoLastName.getText().trim();
        LocalDate birthDate = Dates.getValue();

        if(firstName.isEmpty() || lastName.isEmpty() || birthDate == null) {
            //Intento de alertWindow para excepciones si falta algun campo
            Alert alert = new Alert(Alert.AlertType.WARNING, "Rellena todos los campos.");
            alert.showAndWait();
            return;
        }

        // ID usando algo como un contador
        int newId = personas.size() + 1;
        Persona p = new Persona(newId, firstName, lastName, birthDate);
        personas.add(p);

        //Para vaciar los campos si hago un add
        TextoFirstName.clear();
        TextoLastName.clear();
        Dates.setValue(null);
    }

    @FXML
    private void onDeleteClicked() {
        Persona selected = tablaPersonas.getSelectionModel().getSelectedItem();
        if(selected != null) {
            personas.remove(selected);
            deletedStack.push(selected); // Guardar la fila eliminada para restore
        } else {
            //Intento de alertWindow para excepciones de eliminar
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona una fila para eliminar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onRestoreClicked() {
        if(!deletedStack.isEmpty()) {
            Persona restored = deletedStack.pop();
            personas.add(restored);
            // Tipico i++ de los contadores
            for(int i = 0; i < personas.size(); i++) {
                personas.get(i).idProperty().set(i + 1);
            }
        } else {
            //Intento de alertWindow para excepciones de restaurar
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No hay filas para restaurar.");
            alert.showAndWait();
        }
    }
}