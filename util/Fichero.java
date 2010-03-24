package util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

/** Clase Fichero:
 *  Implementa los métodos y atributos específicos de la capa de persistencia
 *  mediante ficheros
 *  @version 1.0
 *  @author Pau
 */
public class Fichero {
    private String separadorCampos;       // Separador para los campos
    private String separadorRegistros;    // Separador para los registros
    private String separadorElementos;    // Separador para los elementos de cada campo
    private File origen;                  // Fichero de origen
    private File destino;                 // Fichero de destino

    public static final String SEPARADOR = System.getProperty("line.separator");
    
    /** Constructor de la clase
     * @param separadorCampos Separador para los campos
     * @param separadorRegistros Separador para los registros
     * @param separadorElementos Separador para los elementos de cada campo
     * @param origen Fichero de origen
     * @param destino Fichero de destino */
    public Fichero(String separadorCampos, String separadorRegistros, String separadorElementos, File origen, File destino){
        this.separadorCampos = separadorCampos;
        this.separadorRegistros = separadorRegistros;
        this.separadorElementos = separadorElementos;
        this.origen = origen;
        this.destino = destino;
    }

    /** Constructor de la clase sin parámetro para el separador de elementos
     * @param separadorCampos Separador para los campos
     * @param separadorRegistros Separador para los registros
     * @param origen Fichero de origen
     * @param destino Fichero de destino */
    public Fichero(String separadorCampos, String separadorRegistros, File origen, File destino){
        this(separadorCampos, separadorRegistros, "", origen, destino);
    }

    public static void copiar(File origen, File destino) throws FileNotFoundException, IOException{
        FileInputStream fis = new FileInputStream(origen);
        FileOutputStream fos = new FileOutputStream(destino);
        FileChannel canalFuente = fis.getChannel();
        FileChannel canalDestino = fos.getChannel();
        canalFuente.transferTo(0, canalFuente.size(), canalDestino);
        fis.close();
        fos.close();
    }

    /** Escribir la cadena que reciba como argumento en el archivo de salida
     *  @param cadena Cadena que se tiene que escribir en el archivo
     *  @throws java.io.IOException */
    public void escribirCadena(String cadena) throws IOException{
        BufferedWriter archivo = new BufferedWriter(new FileWriter(destino));
        archivo.write(cadena.toString());
        archivo.close();
    }

    /** Lee el contenido del archivo de origen en una cadena que se devuelve
     *  @return Contenido del archivo */
    public String leerEnCadena() {
        Scanner s = null;
        String resultado = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(origen))).useDelimiter("\\Z");
        }
        catch(Exception e) {
            return null;
        }
        try {
            resultado = s.next();
        }
        catch(Exception e) {
            resultado = null;
        }
        finally {
            s.close();
        }
        return resultado;
    }

    /** Devuelve el apuntador de tipo File al fichero de destino
     *  @return El fichero de destino */
    public File getDestino() {
        return destino;
    }

    /** Devuelve el apuntador de tipo File al fichero de origen
     *  @return El fichero de origen */
    public File getOrigen() {
        return origen;
    }

    /** Devuelve el separador entre elementos de un campo
     *  @return Separador entre elementos de un campo */
    public String getSeparadorElementos() {
        return separadorElementos;
    }

    /** Devuelve el separador entre registros
     *  @return Separador entre registros */
    public String getSeparadorRegistros() {
        return separadorRegistros;
    }

    /** Devuelve el separador entre campos
     *  @return Separador entre campos */
    public String getSeparadorCampos() {
        return separadorCampos;
    }

 
}