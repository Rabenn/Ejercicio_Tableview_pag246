package es.ruben.bbdd;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 *
 * <p>Esta clase implementa un patrón de tipo <b>singleton</b> para asegurar que solo
 * exista una conexión activa a la base de datos durante la ejecución de la aplicación.
 * La configuración de la conexión se obtiene desde el archivo
 * <code>configuration.properties</code>, el cual debe encontrarse en el
 * classpath de recursos.</p>
 *
 * <p>Ejemplo de archivo <b>configuration.properties</b>:
 * <pre>
 * db.url=jdbc:mysql://localhost:3306/mi_base
 * db.user=usuario
 * db.password=contraseña
 * </pre>
 * </p>
 *
 * <p>Uso típico:
 * <pre>
 * Connection conexion = ConexionBBDD.getConexion();
 * </pre>
 * </p>
 *
 * @author Rubén
 * @version 1.0
 */
public class ConexionBBDD {

    /** Conexión única compartida en toda la aplicación. */
    private static Connection conexion;

    /**
     * Devuelve una instancia de {@link java.sql.Connection} a la base de datos.
     *
     * <p>Si la conexión no ha sido creada previamente, el metodo carga la configuración
     * desde el archivo <code>configuration.properties</code> y establece una nueva
     * conexión utilizando {@link java.sql.DriverManager}.</p>
     *
     * @return un objeto {@link Connection} activo hacia la base de datos, o {@code null}
     *         si ocurre algún error durante el proceso de conexión.
     *
     * @throws RuntimeException si el archivo de configuración no se encuentra o si
     *         ocurre un error al leerlo o conectar a la base de datos.
     */
    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Properties props = new Properties();
                InputStream input = ConexionBBDD.class.getClassLoader()
                        .getResourceAsStream("configuration.properties");

                if (input == null) {
                    throw new RuntimeException("No se encontró el archivo configuration.properties en los recursos.");
                }

                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                conexion = DriverManager.getConnection(url, user, password);

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al establecer la conexión con la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }
}
