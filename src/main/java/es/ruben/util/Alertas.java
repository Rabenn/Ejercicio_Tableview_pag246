package es.ruben.util;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Alertas class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class Alertas {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(Alertas.class);

    private String laDireccionDelIcono = "/es/ruben/imagenes/icono.png";

    /**
     * Metodo para que salga la ventana de tipo INFORMATION con el mensaje querido
     *
     * @param mensaje a {@link String} object
     */
    public void mostrarInformacion(String mensaje) {
        Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
        alertaInfo.setTitle("Información de la aplicación");
        alertaInfo.setHeaderText(null);
        alertaInfo.setContentText(mensaje);
        alertaInfo.initStyle(StageStyle.UTILITY);


        Image icono = new Image(String.valueOf(getClass().getResource(laDireccionDelIcono)));
        // Obtener el Stage del Alert y establecer el icono
        Stage stage = (Stage) alertaInfo.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icono);

        alertaInfo.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo ERROR con el mensaje querido
     *
     * @param mensaje a {@link String} object
     */
    public void mostrarError(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.ERROR);
        alertaError.setTitle("Error en la aplicación");
        alertaError.setHeaderText(null);
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);
        Image icono = new Image(String.valueOf(getClass().getResource(laDireccionDelIcono)));
        // Obtener el Stage del Alert y establecer el icono
        Stage stage = (Stage) alertaError.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icono);
        alertaError.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo WARNING con el mensaje querido
     *
     * @param mensaje a {@link String} object
     */
    public void mostrarAviso(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.WARNING);
        alertaError.setTitle("Información adicional");
        alertaError.setHeaderText(null);
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);
        Image icono = new Image(String.valueOf(getClass().getResource(laDireccionDelIcono)));
        // Obtener el Stage del Alert y establecer el icono
        Stage stage = (Stage) alertaError.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icono);
        alertaError.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo CONFIRMATION con el mensaje querido
     *
     * @param mensaje a {@link String} object
     */
    public void mostrarAlertaConfirmacion(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.CONFIRMATION);
        alertaError.setTitle("Confirma esta acción");
        alertaError.setHeaderText(null);
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);
        Image icono = new Image(String.valueOf(getClass().getResource(laDireccionDelIcono)));
        // Obtener el Stage del Alert y establecer el icono
        Stage stage = (Stage) alertaError.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icono);
        alertaError.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo INFORMATION con el mensaje querido
     *
     * @param mensaje a {@link String} object
     */
    public void mostrarAlertaCabecera(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.INFORMATION);
        alertaError.setTitle("ERROR");
        alertaError.setHeaderText("Información importante");
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);
        Image icono = new Image(String.valueOf(getClass().getResource(laDireccionDelIcono)));
        // Obtener el Stage del Alert y establecer el icono
        Stage stage = (Stage) alertaError.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icono);
        alertaError.showAndWait();
    }


}
