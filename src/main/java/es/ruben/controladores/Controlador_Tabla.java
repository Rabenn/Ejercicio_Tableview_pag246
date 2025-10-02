package es.ruben.controladores;

import es.ruben.dao.DaoPersona;
import es.ruben.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class Controlador_Tabla {

    @FXML private TextField TextoFirstName;
    @FXML private TextField TextoLastName;
    @FXML private DatePicker Dates;
    @FXML private Button BotonAdd;
    @FXML private Button botonRestore;
    @FXML private Button botonDelete;
    @FXML private TableView<Persona> tablaPersonas;
    @FXML private TableColumn<Persona, Integer> colId;
    @FXML private TableColumn<Persona, String> colFirstName;
    @FXML private TableColumn<Persona, String> colLastName;
    @FXML private TableColumn<Persona, LocalDate> colBirthDate;

    private DaoPersona dao = new DaoPersona();
    private ObservableList<Persona> listaOriginal;

    @FXML
    public void initialize() {
        // Configurar columnas de la tabla
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        // Cargar datos iniciales
        listaOriginal = dao.getTodasPersonas();
        tablaPersonas.setItems(listaOriginal);

        // Permitir seleccionar múltiples filas para borrar
        tablaPersonas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void onaddClicked() {
        String nombre = TextoFirstName.getText();
        String apellido = TextoLastName.getText();
        LocalDate fecha = Dates.getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || fecha == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Rellena todos los campos");
            alert.showAndWait();
            return;
        }

        Persona p = new Persona(0, nombre, apellido, fecha);
        if (dao.insertarPersona(p)) {
            tablaPersonas.getItems().add(p);
            TextoFirstName.clear();
            TextoLastName.clear();
            Dates.setValue(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo añadir la persona");
            alert.showAndWait();
        }
    }

    @FXML
    private void onRestoreClicked() {
        ObservableList<Persona> todas = dao.getTodasPersonas();
        tablaPersonas.setItems(todas);
    }

    @FXML
    private void onDeleteClicked() {
        ObservableList<Persona> selected = tablaPersonas.getSelectionModel().getSelectedItems();

        if (selected == null || selected.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona al menos una fila");
            alert.showAndWait();
            return;
        }

        // Hacemos copia para evitar errores de modificación mientras iteramos
        ObservableList<Persona> toRemove = FXCollections.observableArrayList(selected);

        for (Persona p : toRemove) {
            if (dao.borrarPersona(p.getId())) {
                tablaPersonas.getItems().remove(p);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo borrar a " + p.getNombre());
                alert.showAndWait();
            }
        }
    }
}
