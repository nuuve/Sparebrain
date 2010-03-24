package tareas;

import excepciones.CaracterIlegal;
import java.io.*;
import java.util.*;
import util.Fichero;

/** ControlTareasFicheros:
 *  Clase de control de tareas implementada haciendo uso de ficheros
 *  @version 1.0
 *  @author Pau
 */
public class ControlTareasFicheros implements ControlTareas{
    private Fichero fichero;
    private ArrayList<Tarea> tareas;
    public final String cabecera = "<HEADER>Sparebrain::tasks::1.0</HEADER>";
    
    /** Constructor por defecto. Por defecto toma un nombre de archivo para
     *  almacenar las tareas. */
    private ControlTareasFicheros(){
        this(new File("tareas.txt"), new File("tareas.txt"));
    }

    /** Constructor. Toma un único nombre de archivo que se le pasa como argumento
     *  y que es a la vez de entrada y de salida
     *  @param unico Archivo de origen y destino */
    public ControlTareasFicheros(File unico){
        this(unico, unico);
    }

    /** Constructor. Toma los nombres de archivo que se le pasen como argumento
     *  @param origen Archivo de origen
     *  @param destino Archivo de destino */
    private ControlTareasFicheros(File origen, File destino){
        tareas = new ArrayList<Tarea>();
        fichero = new Fichero("</F>", Fichero.SEPARADOR, origen, destino);
    }

    /** Devuelve un array de String con los datos de una tarea
     *  @param identificador El identificador de la tarea
     *  @return Un array de Strings de dimensión 6 con los datos de la tarea */
    public String[] obtenerTarea(int identificador) {
        String datos [] = new String [6];

        Tarea tarea = tareas.get(identificador);
        datos[0] = tarea.getNombre();
        datos[1] = tarea.getDescripcion();
        datos[2] = tarea.getUrgencia() + "";
        datos[3] = tarea.getFecha() + "";
        datos[4] = tarea.getHoraInicio() + "";
        datos[5] = tarea.getHoraFin() + "";
        return datos;
    }

    /** Devuelve un ArrayList de arrays de String con los datos de las tareas
     *  @return ArrayList con los datos */
    public ArrayList<String []> obtenerTareas(){
        ArrayList <String []> listaTareas = new ArrayList<String []>();
        for(Tarea t : tareas){
            String [] tarea = new String [7];
            tarea[0] = tareas.indexOf(t) + "";
            tarea[1] = t.getNombre();
            tarea[2] = t.getDescripcion();
            tarea[3] = t.getUrgencia() + "";
            tarea[4] = t.getFecha() + "";
            tarea[5] = t.getHoraInicio() + "";
            tarea[6] = t.getHoraFin() + "";
            listaTareas.add(tarea);
        }
        return listaTareas;
    }

    /** Devuelve las tareas cuya fecha coincida con la que se reciba como
     *  argumento.
     *  @param fecha Para la que desea obtener las tareas
     *  @return Lista de tareas para la fecha indicada */
    public ArrayList<String []> obtenerTareasPorFecha(long fecha){
        ArrayList <String []> listaTareas = new ArrayList<String []>();
        for(Tarea t : tareas){
            long fechaTarea = t.getFecha();
            if(fechaTarea == fecha){
                String [] tarea = new String [7];
                tarea[0] = tareas.indexOf(t) + "";
                tarea[1] = t.getNombre();
                tarea[2] = t.getDescripcion();
                tarea[3] = t.getUrgencia() + "";
                tarea[4] = t.getFecha() + "";
                tarea[5] = t.getHoraInicio() + "";
                tarea[6] = t.getHoraFin() + "";
                listaTareas.add(tarea);
            }
        }
        return listaTareas;
    }

    /** Devuelve las tareas cuyo estado es pendiente (su fecha de inicio es 0)
     *  @return Lista de tareas pendientes */
    public ArrayList<String[]> obtenerTareasPendientes() {
        ArrayList <String []> listaTareas = new ArrayList<String []>();
        for(Tarea t : tareas){
            long horaInicio = t.getHoraInicio();
            if(horaInicio == 0){
                String [] tarea = new String [7];
                tarea[0] = tareas.indexOf(t) + "";
                tarea[1] = t.getNombre();
                tarea[2] = t.getDescripcion();
                tarea[3] = t.getUrgencia() + "";
                tarea[4] = t.getFecha() + "";
                tarea[5] = t.getHoraInicio() + "";
                tarea[6] = t.getHoraFin() + "";
                listaTareas.add(tarea);
            }
        }
        return listaTareas;
    }

    /** Devuelve las tareas cuyo estado es comenzado (su fecha de inicio > 0 y
     *  su fecha de finalización es 0)
     *  @return Lista de tareas */
    public ArrayList<String[]> obtenerTareasComenzadas() {
        ArrayList <String []> listaTareas = new ArrayList<String []>();
        for(Tarea t : tareas){
            long horaInicio = t.getHoraInicio();
            long horaFin = t.getHoraFin();
            if((horaInicio > 0) && (horaFin == 0)){
                String [] tarea = new String [7];
                tarea[0] = tareas.indexOf(t) + "";
                tarea[1] = t.getNombre();
                tarea[2] = t.getDescripcion();
                tarea[3] = t.getUrgencia() + "";
                tarea[4] = t.getFecha() + "";
                tarea[5] = t.getHoraInicio() + "";
                tarea[6] = t.getHoraFin() + "";
                listaTareas.add(tarea);
            }
        }
        return listaTareas;
    }

    /** Guarda las tareas en el archivo */
    public void guardarTareas(){
        try {
            StringBuilder cadena = new StringBuilder(cabecera + Fichero.SEPARADOR);
            for(Tarea t : tareas){
                cadena.append(t.obtenerVolcado(fichero.getSeparadorCampos())).append(fichero.getSeparadorRegistros());
            }
            fichero.escribirCadena(cadena.toString());
        } catch (Exception ex) {
        }
    }

    /** Carga las tareas desde el archivo origen, creando los objetos correspondientes */
    public void cargarTareas() {
        String [] cadenas;
        String [] lineas;

        tareas.clear();
        try {
            String archivo = fichero.leerEnCadena();
            if(archivo != null){
                lineas = archivo.split(fichero.getSeparadorRegistros());
                for(String l : lineas){
                    if(!l.equals(cabecera)){
                        cadenas = l.split(fichero.getSeparadorCampos());
                        nuevaTarea(cadenas[0], cadenas[1], cadenas[2], cadenas[3], cadenas[4], cadenas[5]);
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    /** Crea una nueva tarea con los datos que se reciban como argumento
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @return Identificador de la tarea creada */
    public int nuevaTarea(String nombre, String descripcion, int urgencia, long fecha) throws CaracterIlegal {
        return nuevaTarea(nombre, descripcion, urgencia, fecha, 0, 0);
    }

    /** Crea una nueva tarea con los datos que se reciban como argumento
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @param horaFin Hora de finalización de la tarea
     *  @return Identificador de la tarea creada */
    public int nuevaTarea(String nombre, String descripcion, int urgencia, long fecha, long horaInicio, long horaFin) throws CaracterIlegal {
        if(!esCorrecto(nombre) || !esCorrecto(descripcion)) throw new CaracterIlegal();
        Tarea nueva = new Tarea(nombre, descripcion, urgencia, fecha, horaInicio, horaFin);
        tareas.add(nueva);
        return tareas.indexOf(nueva);
    }

    /** Crea una nueva tarea con los datos que se reciban como argumento, siendo
     *  todos ellos cadenas. Permite, por tanto, crear una tarea directamente con
     *  los datos que escriba el usuario si se requiere.
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @param horaFin Hora de finalización de la tarea
     *  @return Identificador de la tarea creada */
    public int nuevaTarea(String nombre, String descripcion, String urgencia, String fecha, String horaInicio, String horaFin) throws CaracterIlegal {
        return nuevaTarea(nombre, descripcion, Integer.parseInt(urgencia.trim()),
                Long.parseLong(fecha.trim()),
                Long.parseLong(horaInicio.trim()), Long.parseLong(horaFin.trim()));
    }

    /** Edita la tarea cuyo identificador se reciba como argumento con los datos
     *  que se reciban como argumento.
     *  @param identificador Identificador de la tarea que se edita
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @param horaFin Hora de finalización de la tarea */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha, long horaInicio, long horaFin) throws CaracterIlegal {
        if(!esCorrecto(nombre) || !esCorrecto(descripcion)) throw new CaracterIlegal();
        tareas.get(identificador).editar(nombre, descripcion, urgencia, fecha, horaInicio, horaFin);
    }

    /** Edita la tarea cuyo identificador se reciba como argumento con los datos
     *  que se reciban como argumento.
     *  @param identificador Identificador de la tarea que se edita
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha, long horaInicio) throws CaracterIlegal {
        if(!esCorrecto(nombre) || !esCorrecto(descripcion)) throw new CaracterIlegal();
        tareas.get(identificador).editar(nombre, descripcion, urgencia, fecha, horaInicio);
    }

    /** Edita la tarea cuyo identificador se reciba como argumento con los datos
     *  que se reciban como argumento
     *  @param identificador Identificador de la tarea que se edita
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha) throws CaracterIlegal {
        if(!esCorrecto(nombre) || !esCorrecto(descripcion)) throw new CaracterIlegal();
        tareas.get(identificador).editar(nombre, descripcion, urgencia, fecha);
    }

    /** Elimina la tarea cuyo identificador se reciba como argumento
     *  @param identificador Identificador de la tarea */
    public void eliminarTarea(int identificador) {
        tareas.remove(identificador);
    }

    /** Marca la tarea cuyo identificador se reciba como comenzada con la hora
     *  de inicio que se reciba como argumento. Si la tarea ya había comenzado
     *  no actualiza la fecha.
     *  @param identificador Identificador de la tarea
     *  @param horaInicio Hora de inicio de la tarea */
    public void comenzarTarea(int identificador, long horaInicio) {
        tareas.get(identificador).setHoraFin(0);
        if(tareas.get(identificador).getHoraInicio() == 0)
            tareas.get(identificador).setHoraInicio(horaInicio);
    }

    /** Marca la tarea cuyo identificador se reciba como comenzada con la hora
     *  de finalización que se reciba como argumento
     *  @param identificador Identificador de la tarea
     *  @param horaFin Hora de finalización de la tarea */
    public void concluirTarea(int identificador, long horaFin) {
        tareas.get(identificador).setHoraFin(horaFin);
    }

    /** Elimina todas las tareas */
    public void eliminarTodo() {
        tareas.clear();
    }

    /** Evalúa si una cadena que se use como nombre o descripción de una tarea
     *  contiene caracteres ilegales que pueden producir errores en el fichero
     *  delimitado.
     *  @param evaluar Cadena que se quiere evaluar
     *  @return Verdadero si la cadena es correcta y falso en caso contrario */
    private boolean esCorrecto(String evaluar) {
        if(evaluar.contains(fichero.getSeparadorCampos()) || evaluar.contains(fichero.getSeparadorRegistros()))
            return false;
        else
            return true;
    }

    /** Devuelve el método de validación. En la implementación con ficheros es
     *  la cabecera del archivo.
     *  @return Valor de verificación */
    public String obtenerValidacion(){
        return cabecera;
    }
}
