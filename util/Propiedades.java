package util;

import java.io.*;
import java.util.*;

/** Clase Propiedades:
 *  Clase para almacenar las preferencias de la aplicación
 *  @version 1.0
 *  @author Pau
 */
public class Propiedades {
    private Properties propiedades;
    private File archivo;

    /** Constructor de la clase
     *  @param archivo File con la ruta del archivo de propiedades */
    public Propiedades(File archivo){
        propiedades = new Properties();
        FileInputStream fis;
        this.archivo = archivo;
        try {
            fis = new FileInputStream(archivo);
            propiedades.loadFromXML(fis);
        } catch (Exception ex) {}
    }

    /** Constructor de la clase para su uso con archivos que se encuentran dentro
     *  del paquete de la aplicación. Lanza una excepción para poder controlar
     *  la inexistencia de los archivos de traducción
     *  @param archivo InputStream con el archivo
     *  @throws java.lang.Exception */
    public Propiedades(InputStream archivo) throws Exception{
        propiedades = new Properties();
        this.archivo = null;
        propiedades.loadFromXML(archivo);
    }

    /** Devuelve un tipo String con el valor de la propiedad cuyo valor se pasa
     *  por parámetro
     *  @param propiedad Propiedad cuyo valir se desea obtener
     *  @return Valor de la propiedad indicada
     *  @throws java.io.IOException */
    public String obtenerPropiedad(String propiedad) {
        try {
            return propiedades.getProperty(propiedad);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /** Establece una propiedad segun el valor recibido
     *  @param propiedad Propiedad cuyo valor se establece
     *  @param valor Valor que debe establecerse para la propiedad
     *  @throws java.io.IOException */
    public void establecerPropiedad(String propiedad, String valor) throws IOException {
        if(archivo != null){
            FileOutputStream fos = new FileOutputStream(archivo);
            propiedades.setProperty(propiedad, valor);
            propiedades.storeToXML(fos, "Propiedades");
        }
    }
}
