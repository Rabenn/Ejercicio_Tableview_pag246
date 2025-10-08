package es.ruben.dao;

import es.ruben.modelos.Persona;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DAO asíncrono para operaciones CRUD sobre la tabla persona.
 * Usa CompletableFuture para ejecutar las consultas en un hilo separado,
 * evitando bloqueos en la UI de JavaFX.
 */
public class DaoPersona {

    private String URL;
    private String USER;
    private String PASS;

    // Pool de hilos para tareas en segundo plano
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public DaoPersona() {
        loadConfig();
    }

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

    // ====================== MÉTODOS ASÍNCRONOS ======================

    /**
     * Obtiene todas las personas de forma asíncrona.
     * @return CompletableFuture con la lista de personas.
     */
    public CompletableFuture<ObservableList<Persona>> getTodasPersonas() {
        return CompletableFuture.supplyAsync(() -> {
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

                    lista.add(new Persona(id, nombre, apellido, fecha));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return lista;
        }, executor);
    }

    /**
     * Obtiene una persona por ID de forma asíncrona.
     */
    public CompletableFuture<Persona> obtenerPersonaAsync(int id) {
        return CompletableFuture.supplyAsync(() -> {
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
        }, executor);
    }

    /**
     * Inserta una persona de forma asíncrona.
     */
    public CompletableFuture<Boolean> insertarPersona(Persona p) {
        return CompletableFuture.supplyAsync(() -> {
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
        }, executor);
    }

    /**
     * Borra una persona por ID de forma asíncrona.
     */
    public CompletableFuture<Boolean> borrarPersona(int id) {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "DELETE FROM persona WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, id);
                return ps.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }, executor);
    }

    /**
     * Borra todas las personas de forma asíncrona.
     */
    public CompletableFuture<Boolean> borrarTodasPersonas() {
        return CompletableFuture.supplyAsync(() -> {
            String sql = "DELETE FROM persona";
            try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                 Statement stmt = conn.createStatement()) {

                stmt.executeUpdate(sql);
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }, executor);
    }

    // ====================== UTILIDAD PARA UI ======================

    /**
     * Metodo auxiliar para ejecutar una acción en el hilo de la interfaz.
     */
    public static void runOnUI(Runnable action) {
        Platform.runLater(action);
    }
}
