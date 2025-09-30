package es.ruben.dao;


import es.ruben.bbdd.ConexionBBDD;
import es.ruben.modelos.Persona;
import es.ruben.util.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * Dao para la tabla DNI
 */
public class DaoDni {


    private static final Logger log = LoggerFactory.getLogger(DaoDni.class);

    /**
     * Metodo que carga los datos de la tabla DNI y los devuelve para usarlos en un listado de personas
     *
     * @return listado de paises para cargar en un tableview
     */
    public static ObservableList<Persona> cargarListadoDNI() {
        ConexionBBDD conexion;
        ObservableList<Persona> listadoDePersonas= FXCollections.observableArrayList();
        System.out.println("Cargando listado de DNI");

        try{
            conexion = new ConexionBBDD();
            String consulta = "SELECT dni, nombre, apellidos FROM DNI";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                LocalDate fechaNacimiento= LocalDate.ofEpochDay(rs.getInt("dni"));

                Persona p = new Persona(nombre,apellidos, fechaNacimiento);
                //System.out.println("Nuevo dni leido= "+ dni+" "+nombre+" "+apellidos);
                listadoDePersonas.add(p);

            }
            rs.close();
            conexion.CloseConexion();
        }catch (
                SQLException e) {
            // O lo trato aquí arriesgandome a que fxml no este
                /*
                Alertas alertaError = new Alertas();
                alertaError.mostrarError("No he podido cargar el listado de paises");
                alertaError.mostrarError(e.getMessage());
                */
            // o lo trato aquí mostrandolo por consola
            //System.out.println(e.getMessage());
            log.error("No he podido cargar la lista sincrona {}",e.getMessage());
        }
        return listadoDePersonas;
    }

    /**
     * Metodo que modifica los datos de un dni  en la BD
     *
     * @param id		Instancia de la persona con datos nuevos
     * @param nuevoDni Nuevo dni de la persona a modificar
     * @return			true/false
     */

    public static  boolean modificarDni(Persona id, String nuevoDni) {
        ConexionBBDD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBBDD();

            String consulta = "UPDATE DNI SET dni = ? WHERE dni = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, nuevoDni);
            pstmt.setString(2, dni.getId());

            int filasAfectadas = pstmt.executeUpdate();

            log.info("Actualizada dnis");
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises");
            alertaError.mostrarError(e.getMessage());
            log.error("No he podido modificar el dni {}",e.getMessage());
            return false;

        }

    }

    /**
     * Metodo que CREA un nuevo un dni en la BD
     *
     * @param persona		Instancia del modelo persona con datos nuevos
     * @return			true/false
     */
    public  static boolean nuevoDNI(Persona persona) {
        ConexionBBDD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBBDD();
            // INSERT INTO `DNI`.`dni` (`dni`) VALUES ('el nuevo');

            String consulta = "INSERT INTO DNI (dni) VALUES (?) ";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, persona.getDni());

            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();
            System.out.println("Nueva entrada en  dni");
            return filasAfectadas > 0;
        } catch (SQLException e) {
               /* Alertas alertaError = new Alertas();
                alertaError.mostrarError("No he podido cargar el listado de dnis");
                alertaError.mostrarError(e.getMessage());*/
            System.out.println(e.getMessage());
            return false;

        }

    }

    /**
     * Elimina una persona en función del modelo Persona que le hayamos pasado
     *
     * @param personaAEliminar Persona a eliminar
     * @return a boolean
     */
    public  static boolean eliminarPersona (Persona personaAEliminar){

        ConexionBBDD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBBDD();
            //DELETE FROM `DNI`.`dni` WHERE (`dni` = 'asdasd');
            String consulta = "DELETE FROM DNI WHERE (dni = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, personaAEliminar.getDni());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();
            System.out.println("Eliminado con éxito");
            return filasAfectadas > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


    /**
     * Carga de manera asinconrana el listado de personas
     * @return la lista observable de personas DNI
     */
    public static CompletableFuture<ObservableList<Persona>> cargarListadoDNIAsync() {
        return CompletableFuture.supplyAsync(() -> {
            ConexionBBDD conexion;
            ObservableList<Persona> listadoDePersonas = FXCollections.observableArrayList();

            try {
                conexion = new ConexionBBDD();
                String consulta = "SELECT dni FROM DNI";
                PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String dni = rs.getString("dni");
                    Persona mp = new Persona(dni);
                    listadoDePersonas.add(mp);
                }
                rs.close();
                conexion.CloseConexionAsync().join();
            } catch (SQLException e) {
                log.error("No he podido cargar la tabla {}", e.getMessage());
            }
            return listadoDePersonas;
        });
    }

    /**
     * Si le pasas un dni lo modifica, hay que solo modifica el número del dni
     * @param dni a modificar
     * @param nuevoDni a escribir, no verifica si hay colisión
     * @return
     */
    public static CompletableFuture<Boolean> modificarDniAsync(Persona dni, String nuevoDni) {
        return CompletableFuture.supplyAsync(() -> {
            ConexionBBDD conexion;
            PreparedStatement pstmt;

            try {
                conexion = new ConexionBBDD();
                String consulta = "UPDATE DNI SET dni = ? WHERE dni = ?";
                pstmt = conexion.getConexion().prepareStatement(consulta);
                pstmt.setString(1, nuevoDni);
                pstmt.setString(2, dni.getDni());

                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();
                conexion.CloseConexionAsync().join();
                return filasAfectadas > 0;
            } catch (SQLException e) {
                log.error("No he podido modificarlo {}", e.getMessage());
                return false;
            }
        });
    }

    /**
     * Crea un nuevo DNI pero sin tener en cuenta su nombre y su apellido
     * @param persona con los datos mínimos de su número de dni
     * @return
     */
    public static CompletableFuture<Boolean> nuevoDNIAsync(Persona persona) {
        return CompletableFuture.supplyAsync(() -> {
            ConexionBBDD conexion;
            PreparedStatement pstmt;

            try {
                conexion = new ConexionBBDD();
                String consulta = "INSERT INTO DNI (dni) VALUES (?)";
                pstmt = conexion.getConexion().prepareStatement(consulta);
                pstmt.setString(1, persona.getDni());

                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();
                conexion.CloseConexionAsync().join();
                return filasAfectadas > 0;
            } catch (SQLException e) {
                log.error("No he podido crearlo {}",e.getMessage());
                return false;
            }
        });
    }

    /**
     * Elimina el registro de la bbdd que coincida con el dni que le pasamos
     * @param personaAEliminar la persona a eliminar
     * @return
     */
    public static CompletableFuture<Boolean> eliminarPersonaAsync(Persona personaAEliminar) {
        return CompletableFuture.supplyAsync(() -> {
            ConexionBBDD conexion;
            PreparedStatement pstmt;

            try {
                conexion = new ConexionBBDD();
                String consulta = "DELETE FROM DNI WHERE (dni = ?)";
                pstmt = conexion.getConexion().prepareStatement(consulta);
                pstmt.setString(1, personaAEliminar.getDni());
                int filasAfectadas = pstmt.executeUpdate();
                pstmt.close();
                conexion.CloseConexionAsync().join();
                return filasAfectadas > 0;
            } catch (SQLException e) {
                log.error("No he podido eliminarlo {}",e.getMessage());
                return false;
            }
        });
    }

//
//    public static void main(String[] args) {
//        System.out.println(
//            DaoDni.cargarListadoDNI()
//                    );
//    }


}


