package es.ruben.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase auxiliar para la carga de archivos de propiedades desde el classpath.
 * <p>
 * Permite leer archivos con extensión <code>.properties</code> ubicados en
 * el directorio de recursos del proyecto, devolviendo un objeto
 * {@link java.util.Properties} con las claves y valores cargados.
 * </p>
 *
 * <h2>Ejemplo de uso:</h2>
 * <pre>
 * Properties config = Propiedades.cargar("configuration.properties");
 * String url = config.getProperty("db.url");
 * </pre>
 *
 * <p>
 * Si el archivo especificado no se encuentra o ocurre un error de lectura,
 * se imprimirá la traza de la excepción en la consola y se devolverá un
 * objeto {@code Properties} vacío.
 * </p>
 *
 * @author Rubén
 * @version 1.0
 */
public class Propiedades {

    /**
     * Carga un archivo de propiedades desde el classpath.
     *
     * @param nombreArchivo Nombre del archivo de propiedades (por ejemplo, "config.properties").
     * @return Un objeto {@link Properties} con las propiedades cargadas.
     *         Si ocurre un error o el archivo no existe, se devuelve un objeto vacío.
     */
    public static Properties cargar(String nombreArchivo) {
        Properties props = new Properties();
        try (InputStream input = Propiedades.class.getClassLoader().getResourceAsStream(nombreArchivo)) {
            if (input == null) {
                throw new IOException("Archivo de propiedades no encontrado: " + nombreArchivo);
            }
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
