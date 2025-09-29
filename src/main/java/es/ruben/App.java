package es.ruben;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase principal que carga la interfaz de usuario desde un archivo FXML.
 * La estructura y los estilos están definidos en table_app_info.fxml y estilos.css.
 */

public class App extends Application {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
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

    public static void main(String[] args) {
        launch();
    }
}