package es.ruben.controladores;

import es.ruben.dao.DaoPersona;
import es.ruben.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

/**
 * Controlador JavaFX para la gestión de personas en la tabla.
 * Adaptado para funcionar con un DAO asíncrono (DaoPersona).
 */
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
    private ObservableList<Persona> listaOriginal = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        tablaPersonas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Cargar datos iniciales de forma asíncrona
        dao.getTodasPersonas().thenAccept(lista -> {
            DaoPersona.runOnUI(() -> {
                listaOriginal = lista;
                tablaPersonas.setItems(listaOriginal);
            });
        });
    }

    @FXML
    private void onaddClicked() {
        String nombre = TextoFirstName.getText();
        String apellido = TextoLastName.getText();
        LocalDate fecha = Dates.getValue();

        if (nombre.isEmpty() || apellido.isEmpty() || fecha == null) {
            new Alert(Alert.AlertType.WARNING, "Rellena todos los campos").showAndWait();
            return;
        }

        Persona p = new Persona(0, nombre, apellido, fecha);

        dao.insertarPersona(p).thenAccept(exito -> {
            DaoPersona.runOnUI(() -> {
                if (exito) {
                    tablaPersonas.getItems().add(p);
                    TextoFirstName.clear();
                    TextoLastName.clear();
                    Dates.setValue(null);
                } else {
                    new Alert(Alert.AlertType.ERROR, "No se pudo añadir la persona").showAndWait();
                }
            });
        });
    }

    @FXML
    private void onRestoreClicked() {
        dao.getTodasPersonas().thenAccept(lista -> {
            DaoPersona.runOnUI(() -> {
                // Actualiza los elementos sin perder la referencia de ObservableList
                listaOriginal.setAll(lista);
            });
        }).exceptionally(ex -> {
            DaoPersona.runOnUI(() -> {
                new Alert(Alert.AlertType.ERROR, "No se pudo cargar la lista: " + ex.getMessage()).showAndWait();
            });
            return null;
        });
    }


    @FXML
    private void onDeleteClicked() {
        ObservableList<Persona> seleccionadas = tablaPersonas.getSelectionModel().getSelectedItems();

        if (seleccionadas == null || seleccionadas.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Selecciona al menos una fila").showAndWait();
            return;
        }

        ObservableList<Persona> copia = FXCollections.observableArrayList(seleccionadas);

        // Eliminar cada una de forma asíncrona
        for (Persona p : copia) {
            dao.borrarPersona(p.getId()).thenAccept(exito -> {
                DaoPersona.runOnUI(() -> {
                    if (exito) {
                        tablaPersonas.getItems().remove(p);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "No se pudo borrar a " + p.getNombre()).showAndWait();
                    }
                });
            });
        }
    }
}
