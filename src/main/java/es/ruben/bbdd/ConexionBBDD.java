package es.ruben.bbdd;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Clase para gestionar la conexión a la base de datos.
 */
public class ConexionBBDD {

    private static Connection conexion;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Properties props = new Properties();
                InputStream input = ConexionBBDD.class.getClassLoader().getResourceAsStream("configuration.properties");
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                conexion = DriverManager.getConnection(url, user, password);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }
}
