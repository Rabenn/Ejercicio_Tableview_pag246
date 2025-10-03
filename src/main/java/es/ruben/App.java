package es.ruben;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase principal que carga la interfaz gráfica a partir del FXML.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image(
                App.class.getResource("imagenes/icono_1.png").toExternalForm()
        );
        stage.getIcons().add(icon);

        Locale local = Locale.forLanguageTag("es");
        ResourceBundle bundle = ResourceBundle.getBundle("texto", local);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/table_app_info.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("Ejemplo Personas - BBDD");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
