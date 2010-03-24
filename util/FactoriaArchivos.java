package util;

import java.io.File;

/** FactoriaArchivos:
 *  Construye y mantiene apuntadores a los archivos del sistema para que el resto
 *  de las clases puedan solicitarlos.
 *  @version 1.0
 *  @author Pau
 */
public class FactoriaArchivos {
    private final String rutaPreferencias;
    private final String rutaSoporte;
    private final String archivo;

    /** Constructor de la clase */
    public FactoriaArchivos(){
        if(System.getProperty("os.name").toLowerCase().indexOf("mac") != -1) {
            // Para Mac OS X
            rutaPreferencias = System.getProperty("user.home") + "/Library/Preferences/";
            rutaSoporte = System.getProperty("user.home") + "/Library/Application Support/Sparebrain/";
            archivo = "com.nosolopau.sparebrain.plist/";
        }
        else if(System.getProperty("os.name").toLowerCase().indexOf("win") != -1){
            // Para Windows
            rutaPreferencias = System.getenv("APPDATA") + "/Sparebrain/";
            rutaSoporte = System.getenv("APPDATA") + "/Sparebrain/";
            archivo = "sparebrain.xml/";
        }
        else if(System.getProperty("os.name").toLowerCase().indexOf("linux") != -1){
            // Para Linux
            rutaPreferencias = System.getProperty("user.home") + "/.sparebrain/";
            rutaSoporte = System.getProperty("user.home") + "/.sparebrain/";
            archivo = "sparebrain.xml/";
        }
        else{
            // Otros sistemas
            rutaPreferencias = System.getProperty("user.home") + "/Sparebrain/";
            rutaSoporte = System.getProperty("user.home") + "/Sparebrain/";
            archivo = "sparebrain.xml";
        }
        (new File(rutaPreferencias)).mkdirs();
        (new File(rutaSoporte)).mkdirs();
    }

    /** Devuelve el archivo de propiedades del sistema
     *  @return Objeto propiedades con las preferencias del sistema */
    public Propiedades obtenerPropiedades(){
        return new Propiedades(new File(rutaPreferencias + archivo));
    }

    /** Devuelve el archivo de tareas del sistema
     *  @return Archivo para las tareas */
    public File obtenerArchivoTareas(){
        return new File(rutaSoporte + "tasks");
    }

    /** Devuelve el archivo de listas del sistema
     *  @return Archivo para las listas */
    public File obtenerArchivoListas(){
        return new File(rutaSoporte + "lists");
    }
}
