package es.ruben;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal que carga la interfaz de usuario desde un archivo FXML.
 * Configura la ventana principal y aplica los estilos definidos en CSS.
 */
public class App extends Application {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Inicia la aplicación JavaFX y muestra la ventana principal.
     * @param stage Escenario principal de la aplicación
     * @throws Exception si ocurre un error al cargar el FXML
     */
    @Override
    public void start(Stage stage) throws Exception {
        logger.info("App lanzada");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/table_app_info.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Ejercicio de tabla");

        scene.getStylesheets().add(getClass().getResource("css/estilos.css").toExternalForm());

        stage.setMinWidth(400);
        stage.setMinHeight(600);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada principal de la aplicación.
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}
