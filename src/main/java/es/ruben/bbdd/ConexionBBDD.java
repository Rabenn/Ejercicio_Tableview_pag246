package es.ruben.bbdd;

import es.ruben.util.Propiedades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanAttributeInfo;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * Clase de conexión a la bbdd
 *
 * @author israel
 * @version $Id: $Id
 */
public class ConexionBBDD {

    /**
     * Objeto de conexion
     */
    private final Connection conexion;

    /**
     * Objeto para el logueo de las activiades de esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ConexionBBDD.class);






    /**
     * Es el constructor que se llama al crear un objeto de esta clase, lanzado la conexión
     *
     * @throws java.sql.SQLException Hay que controlar errores de SQL
     */
    public ConexionBBDD() throws SQLException {
        Properties connConfig = new Properties();
        // los parámetros de la conexion leidos desde fuera
        // nada de dejar en el repo las credenciales
        String user = Propiedades.getValor("user");
        String password = Propiedades.getValor("password");
        // las propiedades de la conexión
        connConfig = new Properties();
        connConfig.setProperty("user", user);
        connConfig.setProperty("password", password);
        //la conexion en sí
        //usamos el driver de mariadb o el que toque
        // sería interesante sacar fuera ese nombre del esquema
        conexion = DriverManager.getConnection("jdbc:mysql://localhost/DNI", connConfig);
        conexion.setAutoCommit(true);
        DatabaseMetaData databaseMetaData = conexion.getMetaData();

        logger.info("Conectado a la base de datos");
        conexion.setAutoCommit(true);
    }

    /**
     * Esta clase devuelve la conexión creada
     *
     * @return una conexión a la BBDD
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Metodo de cerrar la conexion con la base de datos
     *
     * @return La conexión cerrada.
     * @throws java.sql.SQLException Se lanza en caso de errores de SQL al cerrar la conexión.
     */
    public Connection CloseConexion() throws SQLException{
        conexion.close();
        return conexion;
    }


    /**
     * Conexiones asíncronas, se establece mediante este procedimiento
     * @return
     */
    public CompletableFuture<Connection> getConexionAsync() {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Properties connConfig = new Properties();
                // los parámetros de la conexion leidos desde fuera
                // nada de dejar en el repo las credenciales
                String user = Propiedades.getValor("user");
                String password = Propiedades.getValor("password");
                // las propiedades de la conexión
                // las propiedades de la conexión
                connConfig = new Properties();
                connConfig.setProperty("user", user);
                connConfig.setProperty("password", password);
                return DriverManager.getConnection("jdbc:mysql://localhost/DNI", connConfig);
            } catch (SQLException e) {
                logger.error("Fallo en la conexión a bbdd asíncronamente {}", e.getMessage());
                throw new RuntimeException(e);
            }
        });
    }

    public CompletableFuture<Void> CloseConexionAsync() {
        return CompletableFuture.runAsync(() -> {
            try {
                conexion.close();
            } catch (SQLException e) {
                logger.info("Cerrando la  base de datos asíncronamente {}", e.getMessage());
                throw new RuntimeException(e);

            }
        });
    }
// PRUEBA PARA LLAMADA SÍNCRONA
//    public static void main(String[] args) {
//        try {
//            ConexionBBDD conexiontest = new ConexionBBDD();
//        } catch (SQLException e) {
//            System.out.println("error de conexion");
//            throw new RuntimeException(e);
//        }
//
//    }



}

