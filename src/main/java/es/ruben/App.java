package es.ruben;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase principal que inicia la aplicación JavaFX.
 * <p>
 * Esta clase se encarga de:
 * <ul>
 *     <li>Cargar el archivo FXML que define la interfaz gráfica.</li>
 *     <li>Establecer el idioma mediante un {@link ResourceBundle}.</li>
 *     <li>Asignar un icono y un título a la ventana principal.</li>
 * </ul>
 * </p>
 *
 * <h2>Funcionamiento:</h2>
 * <p>
 * El metodo {@link #start(Stage)} se ejecuta automáticamente al lanzar la aplicación.
 * Dentro de él se carga el archivo <code>table_app_info.fxml</code> desde el paquete
 * <code>es.ruben.fxml</code> y se crea una nueva escena asociada al {@link Stage} principal.
 * </p>
 *
 * <p>
 * El idioma por defecto se establece en español (<code>es</code>) y se cargan
 * los textos desde el archivo de propiedades <code>texto.properties</code>.
 * </p>
 *
 * <h2>Requisitos:</h2>
 * <ul>
 *     <li>El archivo FXML debe encontrarse en <code>resources/es/ruben/fxml/</code>.</li>
 *     <li>El archivo de icono debe encontrarse en <code>resources/es/ruben/imagenes/</code>.</li>
 *     <li>El archivo de idioma debe llamarse <code>texto.properties</code>.</li>
 * </ul>
 *
 * @author Rubén
 * @version 1.0
 */
public class App extends Application {

    /**
     * Inicia la interfaz principal de la aplicación.
     *
     * @param stage La ventana principal (Stage) donde se carga la escena.
     * @throws Exception Si ocurre un error al cargar el archivo FXML o los recursos.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image(
                App.class.getResource("imagenes/icono_1.png").toExternalForm()
        );
        stage.getIcons().add(icon);

        Locale local = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("texto", local);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/table_app_info.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Ejemplo Personas - BBDD");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo principal que lanza la aplicación JavaFX.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta aplicación).
     */
    public static void main(String[] args) {
        launch();
    }
}
