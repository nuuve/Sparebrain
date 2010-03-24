package listas;

import excepciones.CaracterIlegal;
import java.io.*;
import java.util.*;
import util.Fichero;

/** ControlListasFicheros:
 *  Clase de control de listas implementada haciendo uso de ficheros
 *  @version 1.0
 *  @author Pau
 */
public class ControlListasFicheros implements ControlListas {
    private Fichero fichero;
    private ArrayList<Lista> listas;
    private final String cabecera = "<HEADER>Sparebrain::lists::1.0</HEADER>";

    /** Constructor por defecto. Por defecto toma un nombre de archivo para
     *  almacenar las listas. */
    public ControlListasFicheros(){
        this(new File("listas.txt"), new File("listas.txt"));
    }

    /** Constructor. Toma un único nombre de archivo que se le pasa como argumento
     *  y que es a la vez de entrada y de salida
     *  @param unico Archivo de origen y destino */
    public ControlListasFicheros(File unico){
        this(unico, unico);
    }

    /** Constructor. Toma como nombres de archivo que se le pasen como argumento
     *  @param origen Archivo de origen
     *  @param destino Archivo de destino */
    public ControlListasFicheros(File origen, File destino){
        listas = new ArrayList<Lista>();
        fichero = new Fichero("</L>", Fichero.SEPARADOR, "</I>", origen, destino);
    }

    /** Guarda las listas en el archivo destino */
    public void guardarListas() {
         try {
            StringBuilder cadena = new StringBuilder(cabecera + Fichero.SEPARADOR);
            for(Lista l : listas){
                cadena.append(l.obtenerVolcado(fichero.getSeparadorCampos(),
                        fichero.getSeparadorElementos())).append(fichero.getSeparadorRegistros());
            }
            fichero.escribirCadena(cadena.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Carga las listas desde el archivo origen, creando los objetos correspondientes */
    public void cargarListas() {
        String [] cadenaRegistros;
        String [] cadenaCampos;
        String [] cadenaElementos;
        
        listas.clear();
        try {
            String archivo = fichero.leerEnCadena();
            if(archivo != null){
                cadenaRegistros = archivo.split(fichero.getSeparadorRegistros());
                for(String registro : cadenaRegistros){
                    if(!registro.equals(cabecera)){
                        cadenaCampos = registro.split(fichero.getSeparadorCampos());
                        int nueva = nuevaLista(cadenaCampos[0]);
                        if(cadenaCampos.length > 1){
                            cadenaElementos = cadenaCampos[1].split(fichero.getSeparadorElementos());

                            for(String elemento : cadenaElementos){
                                listas.get(nueva).nuevoElemento(elemento);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Crea una nueva lista con el nombre que se le pase como argumento
     *  @param nombre Nombre de la lista
     *  @return Identificador de la lista creada */
    public int nuevaLista(String nombre) throws CaracterIlegal {
        if(!esCorrecto(nombre)) throw new CaracterIlegal();
        Lista nueva = new Lista(nombre);
        listas.add(nueva);
        return listas.indexOf(nueva);
    }

    /** Cambia el nombre de la lista cuyo identificador se reciba como argumento
     *  @param identificador Identificador de la lista
     *  @param nombre Nombre de la lista */
    public void editarLista(int identificador, String nombre) throws CaracterIlegal {
        if(!esCorrecto(nombre)) throw new CaracterIlegal();
        listas.get(identificador).editar(nombre);
    }

    /** Añade a la lista cuyo identificador se reciba como argumento, un elemento
     *  con el nombre indicado
     *  @param identificadorLista Identificador de la lista a la que añadir el elemento
     *  @param nombre Nombre del elemento que se añadirá
     *  @return Identificador del elemento creado */
    public int nuevoElemento(int identificadorLista, String nombre) throws CaracterIlegal {
        if(!esCorrecto(nombre)) throw new CaracterIlegal();
        return listas.get(identificadorLista).nuevoElemento(nombre);
    }

    /** Para un elemento con identificador identificadorElemento perteneciente a
     *  la lista con identificador identificadorLista, cambia su nombre por el que
     *  se reciba como parámetro
     *  @param identificadorLista Lista que contiene el elemento
     *  @param identificadorElemento Elemento que se edita
     *  @param nombre Nuevo nombre del elemento */
    public void editarElemento(int identificadorLista, int identificadorElemento, String nombre) throws CaracterIlegal {
        if(!esCorrecto(nombre)) throw new CaracterIlegal();
        listas.get(identificadorLista).editarElemento(identificadorElemento, nombre);
    }

    /** Elimina el elemento con identificador identificadorElemento perteneciente a
     *  la lista con identificador identificadorLista
     *  @param identificadorLista Lista que contiene el elemento
     *  @param identificadorElemento Elemento que se edita */
    public void eliminarElemento(int identificadorLista, int identificadorElemento) {
        listas.get(identificadorLista).eliminarElemento(identificadorElemento);
    }

    /** Elimina la lista cuyo identificador se recibe como parámetro
     *  @param identificador Identificador de la lista que se quiere eliminar */
    public void eliminarLista(int identificador) {
        listas.remove(identificador);
    }

    /** Devuelve el nombre de la lista cuyo identificador se reciba como argumento
     *  @param identificador Identificador de la lista
     *  @return Nombre de la lista */
    public String obtenerLista(int identificador){
        return listas.get(identificador).getNombre();
    }

    /** Devuelve una lista con las listas y sus atributos
     *  @return Array con las listas */
    public ArrayList<String[]> obtenerListas() {
        ArrayList <String []> listaListas = new ArrayList<String []>();
        for(Lista t : listas){
            String [] tarea = new String [2];
            tarea[0] = listas.indexOf(t) + "";
            tarea[1] = t.getNombre();
            listaListas.add(tarea);
        }
        return listaListas;
    }

    /** Devuelve los elementos para una lista determinada
     *  @param identificador Identificador de la lista
     *  @return Lista de elementos de la lista */
    public ArrayList<String[]> obtenerElementos(int identificador) {
        return listas.get(identificador).obtenerElementos();
    }

    /** Devuelve el nombre de un elemento determinado de una lista determinada
     *  @param identificadorLista Identificador de la lista
     *  @param identificadorElemento Identificador del elemento
     *  @return Nombre del elemento de la lista */
    public String obtenerElemento(int identificadorLista, int identificadorElemento) {
        return listas.get(identificadorLista).obtenerElemento(identificadorElemento);
    }

    /** Elimina todas las listas */
    public void eliminarTodo(){
        listas.clear();
    }

    /** Devuelve el método de validación. En la implementación con ficheros es
     *  la cabecera del archivo.
     *  @return Valor de verificación */
    public String obtenerValidacion(){
        return cabecera;
    }

    /** Evalúa si una cadena que se use como nombre o descripción de una tarea
     *  contiene caracteres ilegales que pueden producir errores en el fichero
     *  delimitado.
     *  @param evaluar Cadena que se quiere evaluar
     *  @return Verdadero si la cadena es correcta y falso en caso contrario */
    private boolean esCorrecto(String evaluar) {
        if(evaluar.contains(fichero.getSeparadorCampos()) 
                || evaluar.contains(fichero.getSeparadorRegistros())
                || evaluar.contains(fichero.getSeparadorElementos()))
            return false;
        else
            return true;
    }
}
