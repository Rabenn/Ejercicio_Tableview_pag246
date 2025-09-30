package es.ruben.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase para leer las propiedades del sistema, urls, idioma instalado, directorios...
 *
 * @author israel
 * @version $Id: $Id
 */
public class Propiedades {

    private static final Properties props = new Properties();
    static {

        try(
                InputStream input = Propiedades.class.getResourceAsStream
                        ("/es/ruben/configuration.properties")
        ){
            props.load(input);
        } catch (Exception e) {

            System.out.println(e.getMessage());
        }

    }



    /**
     * Obtiene el valor asociado a una clave desde el archivo de propiedades situado en la raíz del proyecto
     *
     * @param clave La clave cuyo valor se desea obtener.
     * @return El valor asociado a la clave.
     * @throws java.lang.RuntimeException Si el archivo de configuración no existe o la clave no tiene un valor asociado.
     */
    public static String getValor(String clave) {
        String valor = props.getProperty(clave);
        if (valor != null) {
            return valor;
        }
        throw new RuntimeException("No he logrado leer esa clave en concreto");
    }
}
