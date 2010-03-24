package tareas;

import excepciones.CaracterIlegal;
import java.util.ArrayList;

/** Interfaz ControlTareas:
 *  Separa la definición del controlador de tareas de su implementación
 *  @author Pau
 *  @version 1.0
 */
public interface ControlTareas {
    /** Crea una nueva tarea con los datos que se reciban como argumento
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @return Identificador de la tarea creada
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public int nuevaTarea(String nombre, String descripcion, int urgencia, long fecha) throws CaracterIlegal;

    /** Crea una nueva tarea con los datos que se reciban como argumento
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @param horaFin Hora de finalización de la tarea
     *  @return Identificador de la tarea creada
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public int nuevaTarea(String nombre, String descripcion, int urgencia, long fecha, long horaInicio, long horaFin) throws CaracterIlegal;

    /** Crea una nueva tarea con los datos que se reciban como argumento, siendo
     *  todos ellos cadenas. Permite, por tanto, crear una tarea directamente con
     *  los datos que escriba el usuario si se requiere.
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @param horaFin Hora de finalización de la tarea
     *  @return Identificador de la tarea creada
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public int nuevaTarea(String nombre, String descripcion, String urgencia, String fecha, String horaInicio, String horaFin) throws CaracterIlegal;
    
    /** Edita la tarea cuyo identificador se reciba como argumento con los datos
     *  que se reciban como argumento
     *  @param identificador Identificador de la tarea que se edita
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha) throws CaracterIlegal;
    
    /** Edita la tarea cuyo identificador se reciba como argumento con los datos
     *  que se reciban como argumento.
     *  @param identificador Identificador de la tarea que se edita
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha, long horaInicio) throws CaracterIlegal;

    /** Edita la tarea cuyo identificador se reciba como argumento con los datos
     *  que se reciban como argumento.
     *  @param identificador Identificador de la tarea que se edita
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @param horaFin Hora de finalización de la tarea
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha, long horaInicio, long horaFin) throws CaracterIlegal;
    
    /** Elimina la tarea cuyo identificador se reciba como argumento
     *  @param identificador Identificador de la tarea */
    public void eliminarTarea(int identificador);

     /** Marca la tarea cuyo identificador se reciba como comenzada con la hora
     *  de inicio que se reciba como argumento. Si la tarea ya había comenzado
     *  no actualiza la fecha.
     *  @param identificador Identificador de la tarea
     *  @param horaInicio Hora de inicio de la tarea */
    public void comenzarTarea(int identificador, long horaInicio);

    /** Marca la tarea cuyo identificador se reciba como comenzada con la hora
     *  de finalización que se reciba como argumento
     *  @param identificador Identificador de la tarea
     *  @param horaFin Hora de finalización de la tarea */
    public void concluirTarea(int identificador, long horaFin);

    /** Guarda las tareas */
    public void guardarTareas();

    /** Carga las tareas */
    public void cargarTareas();

    /** Devuelve un array de String con los datos de una tarea
     *  @param identificador El identificador de la tarea
     *  @return Un array de Strings con los datos de la tarea */
    public String [] obtenerTarea(int identificador);

    /** Devuelve un ArrayList de arrays de String con los datos de las tareas
     *  @return ArrayList con los datos */
    public ArrayList<String []> obtenerTareas();

    /** Devuelve las tareas cuya fecha coincida con la que se reciba como
     *  argumento.
     *  @param fecha Para la que desea obtener las tareas
     *  @return Lista de tareas para la fecha indicada */
    public ArrayList<String []> obtenerTareasPorFecha(long fecha);

    /** Devuelve las tareas cuyo estado es pendiente 
     *  @return Lista de tareas pendientes */
    public ArrayList<String[]> obtenerTareasPendientes();

    /** Devuelve las tareas cuyo estado es comenzado
     *  @return Lista de tareas */
    public ArrayList<String[]> obtenerTareasComenzadas();

    /** Elimina todas las tareas */
    public void eliminarTodo();

    /** Devuelve el método de validación.
     *  @return Valor de verificación */
    public String obtenerValidacion();
}
