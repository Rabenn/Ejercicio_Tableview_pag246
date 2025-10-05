package es.ruben.dao;

import es.ruben.modelos.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

/**
 * Clase DAO (Data Access Object) encargada de realizar las operaciones CRUD
 * sobre la tabla <b>persona</b> de la base de datos.
 *
 * <p>Esta clase encapsula la lógica de acceso a datos, gestionando las consultas,
 * inserciones y eliminaciones en la tabla. Utiliza JDBC para conectarse a la base
 * de datos, y las credenciales se cargan desde el archivo
 * <code>configuration.properties</code>.</p>
 *
 * <p>Ejemplo del archivo de configuración:
 * <pre>
 * db.url=jdbc:mysql://localhost:3306/mi_base
 * db.user=usuario
 * db.password=contraseña
 * </pre>
 * </p>
 *
 * <p>Uso típico:
 * <pre>
 * DaoPersona dao = new DaoPersona();
 * ObservableList&lt;Persona&gt; personas = dao.getTodasPersonas();
 * dao.insertarPersona(new Persona(0, "Juan", "Pérez", LocalDate.now()));
 * </pre>
 * </p>
 *
 * @author Rubén
 * @version 1.0
 */
public class DaoPersona {

    /** URL de conexión a la base de datos. */
    private String URL;

    /** Usuario para la conexión. */
    private String USER;

    /** Contraseña para la conexión. */
    private String PASS;

    /**
     * Constructor por defecto que carga la configuración de la base de datos
     * desde el archivo <code>configuration.properties</code>.
     */
    public DaoPersona() {
        loadConfig();
    }

    /**
     * Carga la configuración de conexión desde el archivo
     * <code>configuration.properties</code> ubicado en el classpath.
     *
     * <p>Si el archivo no se encuentra o ocurre un error al leerlo, se mostrará
     * un mensaje de error en la consola.</p>
     */
    private void loadConfig() {
        Properties props = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/configuration.properties")) {
            if (input == null) {
                System.err.println("No se encontró configuration.properties");
                return;
            }
            props.load(input);
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASS = props.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene todas las personas almacenadas en la base de datos.
     *
     * @return una lista observable de objetos {@link Persona} con todos los registros encontrados.
     */
    public ObservableList<Persona> getTodasPersonas() {
        ObservableList<Persona> lista = FXCollections.observableArrayList();
        String sql = "SELECT id, first_name, last_name, birth_date FROM persona";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("first_name");
                String apellido = rs.getString("last_name");
                Date fechaSQL = rs.getDate("birth_date");
                LocalDate fecha = fechaSQL != null ? fechaSQL.toLocalDate() : null;

                Persona persona = new Persona(id, nombre, apellido, fecha);
                lista.add(persona);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Obtiene una persona concreta a partir de su identificador.
     *
     * @param id identificador de la persona.
     * @return un objeto {@link Persona} con los datos encontrados, o {@code null} si no existe.
     */
    public Persona obtenerPersona(int id) {
        String sql = "SELECT id, first_name, last_name, birth_date FROM persona WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("first_name");
                String apellido = rs.getString("last_name");
                Date fechaSQL = rs.getDate("birth_date");
                LocalDate fecha = fechaSQL != null ? fechaSQL.toLocalDate() : null;

                return new Persona(id, nombre, apellido, fecha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Inserta una nueva persona en la base de datos.
     *
     * <p>Tras la inserción, se actualiza el ID del objeto {@link Persona}
     * con el valor generado automáticamente por la base de datos.</p>
     *
     * @param p objeto {@link Persona} que se desea insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertarPersona(Persona p) {
        String sql = "INSERT INTO persona(first_name, last_name, birth_date) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setDate(3, p.getFechaNacimiento() != null ? Date.valueOf(p.getFechaNacimiento()) : null);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    p.setId(keys.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Elimina una persona de la base de datos según su ID.
     *
     * @param id identificador único de la persona a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean borrarPersona(int id) {
        String sql = "DELETE FROM persona WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina todos los registros de la tabla <b>persona</b>.
     *
     * <p>Se debe usar con precaución, ya que esta operación no puede revertirse.</p>
     *
     * @return {@code true} si la operación fue exitosa, {@code false} si ocurrió un error.
     */
    public boolean borrarTodasPersonas() {
        String sql = "DELETE FROM persona";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
