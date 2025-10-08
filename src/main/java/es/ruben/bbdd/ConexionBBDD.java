package es.ruben.bbdd;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * Clase encargada de gestionar la conexión con la base de datos de forma asíncrona.
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
 * ConexionBBDD.getConexionAsync().thenAccept(con -> {
 *     // usar la conexión aquí
 * }).exceptionally(ex -> {
 *     ex.printStackTrace();
 *     return null;
 * });
 * </pre>
 * </p>
 *
 * <p>Esta versión usa {@link CompletableFuture} para realizar la conexión
 * sin bloquear el hilo de JavaFX.</p>
 *
 * @author Rubén
 * @version 2.0
 */
public class ConexionBBDD {

    /** Conexión única compartida en toda la aplicación. */
    private static volatile Connection conexion;

    /**
     * Devuelve un {@link CompletableFuture} que obtiene una conexión a la base de datos de forma asíncrona.
     *
     * <p>Si la conexión no ha sido creada previamente, el metodo carga la configuración
     * desde el archivo <code>configuration.properties</code> y establece una nueva
     * conexión utilizando {@link java.sql.DriverManager}.</p>
     *
     * @return un CompletableFuture que completará con la conexión activa o lanzará una excepción en caso de error.
     *
     * @throws RuntimeException si el archivo de configuración no se encuentra o si
     *         ocurre un error al leerlo o conectar a la base de datos.
     */
    public static CompletableFuture<Connection> getConexionAsync() {
        return CompletableFuture.supplyAsync(() -> {
            if (conexion == null) {
                synchronized (ConexionBBDD.class) {
                    if (conexion == null) {
                        try (InputStream input = ConexionBBDD.class.getClassLoader()
                                .getResourceAsStream("configuration.properties")) {

                            if (input == null) {
                                throw new RuntimeException("No se encontró el archivo configuration.properties en los recursos.");
                            }

                            Properties props = new Properties();
                            props.load(input);

                            String url = props.getProperty("db.url");
                            String user = props.getProperty("db.user");
                            String password = props.getProperty("db.password");

                            conexion = DriverManager.getConnection(url, user, password);

                        } catch (Exception e) {
                            throw new RuntimeException("Error al establecer la conexión con la base de datos: " + e.getMessage(), e);
                        }
                    }
                }
            }
            return conexion;
        });
    }

    /**
     * Cierra la conexión si está activa.
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("✅ Conexión cerrada correctamente.");
            } catch (Exception e) {
                System.err.println("⚠️ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
