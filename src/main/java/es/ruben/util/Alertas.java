package es.ruben.util;

import javafx.scene.control.Alert;

/**
 * Clase de utilidad para mostrar diferentes tipos de ventanas de alerta
 * (informativas, de advertencia o de error) en una aplicación JavaFX.
 * <p>
 * Cada metodo crea y muestra un cuadro de diálogo modal con el mensaje
 * proporcionado, bloqueando la interacción con la interfaz hasta que
 * el usuario lo cierre.
 * </p>
 *
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * Alertas.mostrarInfo("Operación completada correctamente");
 * Alertas.mostrarWarning("Debes rellenar todos los campos");
 * Alertas.mostrarError("No se pudo conectar con la base de datos");
 * </pre>
 *
 * <p>
 * Esta clase está pensada para centralizar la gestión de mensajes al usuario,
 * evitando la repetición de código y facilitando el mantenimiento.
 * </p>
 *
 * @author Rubén
 * @version 1.0
 */
public class Alertas {

    /**
     * Muestra una ventana de información con el mensaje especificado.
     *
     * @param mensaje Texto que se mostrará en la alerta.
     */
    public static void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una ventana de error con el mensaje especificado.
     *
     * @param mensaje Texto que se mostrará en la alerta.
     */
    public static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una ventana de advertencia con el mensaje especificado.
     *
     * @param mensaje Texto que se mostrará en la alerta.
     */
    public static void mostrarWarning(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
