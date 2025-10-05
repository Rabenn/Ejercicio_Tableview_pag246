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
 * Controlador JavaFX encargado de gestionar la interfaz de la tabla de personas.
 *
 * <p>Esta clase actúa como puente entre la vista FXML y la lógica de negocio (DAO).
 * Permite añadir, eliminar y restaurar registros de personas en una tabla visual
 * de tipo {@link TableView}. Además, realiza validaciones básicas y muestra alertas
 * en caso de errores o campos incompletos.</p>
 *
 * <p>Los datos mostrados en la tabla se obtienen y manipulan a través de
 * {@link DaoPersona}, que gestiona las operaciones con la base de datos.</p>
 *
 * <p>Controles FXML esperados en la vista:
 * <ul>
 *     <li>Campos de texto para nombre y apellido</li>
 *     <li>Selector de fecha de nacimiento</li>
 *     <li>Botones para añadir, eliminar y restaurar</li>
 *     <li>Tabla con columnas: ID, nombre, apellido, fecha de nacimiento</li>
 * </ul>
 * </p>
 *
 * <p>Uso:
 * <pre>
 * &lt;TableView fx:id="tablaPersonas" ... /&gt;
 * &lt;Button fx:id="BotonAdd" onAction="#onAddClicked" /&gt;
 * </pre>
 * </p>
 *
 * @author Rubén
 * @version 1.0
 */
public class Controlador_Tabla {

    /** Campo de texto para el nombre de la persona. */
    @FXML private TextField TextoFirstName;

    /** Campo de texto para el apellido de la persona. */
    @FXML private TextField TextoLastName;

    /** Selector de fecha de nacimiento. */
    @FXML private DatePicker Dates;

    /** Botón para añadir una nueva persona a la tabla y a la base de datos. */
    @FXML private Button BotonAdd;

    /** Botón para restaurar la tabla a su estado original desde la base de datos. */
    @FXML private Button botonRestore;

    /** Botón para eliminar las personas seleccionadas. */
    @FXML private Button botonDelete;

    /** Tabla que muestra la lista de personas. */
    @FXML private TableView<Persona> tablaPersonas;

    /** Columna que muestra el ID de la persona. */
    @FXML private TableColumn<Persona, Integer> colId;

    /** Columna que muestra el nombre de la persona. */
    @FXML private TableColumn<Persona, String> colFirstName;

    /** Columna que muestra el apellido de la persona. */
    @FXML private TableColumn<Persona, String> colLastName;

    /** Columna que muestra la fecha de nacimiento. */
    @FXML private TableColumn<Persona, LocalDate> colBirthDate;

    /** Objeto DAO encargado de la comunicación con la base de datos. */
    private DaoPersona dao = new DaoPersona();

    /** Lista original de personas obtenida al iniciar la interfaz. */
    private ObservableList<Persona> listaOriginal;

    /**
     * Inicializa el controlador tras la carga del archivo FXML.
     *
     * <p>Configura las columnas de la tabla para asociarlas con las propiedades
     * del modelo {@link Persona}, carga los datos iniciales desde el DAO y
     * permite la selección múltiple en la tabla.</p>
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        listaOriginal = dao.getTodasPersonas();
        tablaPersonas.setItems(listaOriginal);
        tablaPersonas.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Manejador del evento del botón "Añadir".
     *
     * <p>Valida los campos del formulario y, si son correctos, crea una nueva
     * instancia de {@link Persona}, la inserta en la base de datos mediante el DAO,
     * y actualiza la tabla visual. Si ocurre un error, muestra una alerta adecuada.</p>
     */
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

    /**
     * Manejador del evento del botón "Restaurar".
     *
     * <p>Recarga los datos originales de la base de datos para deshacer cualquier
     * modificación temporal o filtro realizado en la tabla.</p>
     */
    @FXML
    private void onRestoreClicked() {
        ObservableList<Persona> todas = dao.getTodasPersonas();
        tablaPersonas.setItems(todas);
    }

    /**
     * Manejador del evento del botón "Eliminar".
     *
     * <p>Permite eliminar una o varias personas seleccionadas de la tabla y de la
     * base de datos. Si no hay selección o ocurre un error en el borrado, se muestra
     * una alerta informativa o de error.</p>
     */
    @FXML
    private void onDeleteClicked() {
        ObservableList<Persona> selected = tablaPersonas.getSelectionModel().getSelectedItems();

        if (selected == null || selected.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona al menos una fila");
            alert.showAndWait();
            return;
        }

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
