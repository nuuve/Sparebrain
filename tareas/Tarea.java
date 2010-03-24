package tareas;

/** Clase Tarea:
 *  Clase que representa una tarea. Sólo es visible desde el paquete tareas
 *  @version 1.0
 *  @author Pau
 */
class Tarea {
    private String nombre;
    private String descripcion;
    private int urgencia;
    private long fecha;
    private long horaInicio;
    private long horaFin;

    /** Constructor de la clase
     *  @param nombre Nombre para la tarea
     *  @param descripcion Descripción para la tarea
     *  @param urgencia Urgencia para la tarea
     *  @param fecha Fecha para la tarea
     *  @param horaInicio Hora de inicio para la tarea
     *  @param horaFin Hora de finalización para la tarea */
    public Tarea(String nombre, String descripcion, int urgencia, long fecha, long horaInicio, long horaFin){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urgencia = urgencia;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /** Edita la tarea actual
     *  @param nombre Nuevo nombre para la tarea
     *  @param descripcion Nueva descripción para la tarea
     *  @param urgencia Nueva urgencia para la tarea
     *  @param fecha Nueva fecha para la tarea
     *  @param horaInicio Nueva hora de inicio para la tarea
     *  @param horaFin Nueva hora de finalización para la tarea */
    public void editar (String nombre, String descripcion, int urgencia, long fecha, long horaInicio, long horaFin){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urgencia = urgencia;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /** Edita la tarea actual
     *  @param nombre Nuevo nombre para la tarea
     *  @param descripcion Nueva descripción para la tarea
     *  @param urgencia Nueva urgencia para la tarea
     *  @param fecha Nueva fecha para la tarea
     *  @param horaInicio Nueva hora de inicio para la tarea */
    public void editar (String nombre, String descripcion, int urgencia, long fecha, long horaInicio){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urgencia = urgencia;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
    }

        /** Edita la tarea actual
     *  @param nombre Nuevo nombre para la tarea
     *  @param descripcion Nueva descripción para la tarea
     *  @param urgencia Nueva urgencia para la tarea
     *  @param fecha Nueva fecha para la tarea */
    public void editar (String nombre, String descripcion, int urgencia, long fecha){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urgencia = urgencia;
        this.fecha = fecha;
    }

    /** Devuelve un volcado de la tarea separando los campos mediante el separador
     *  que recibe como argumento.
     *  @param separadorCampos El separador para los campos de la tarea
     *  @return La cadena con el volcado de la tarea */
    public String obtenerVolcado(String separadorCampos){
        StringBuilder cadena = new StringBuilder();
        cadena.append(nombre).append(separadorCampos);
        cadena.append(descripcion).append(separadorCampos);
        cadena.append(urgencia).append(separadorCampos);
        cadena.append(fecha).append(separadorCampos);
        cadena.append(horaInicio).append(separadorCampos);
        cadena.append(horaFin);
        return cadena.toString();
    }

    /** Devuelve el objeto convertido a una cadena lineal
     *  @return La tarea convertida a String */
    public String toString(){
        StringBuilder cadena = new StringBuilder();
        cadena.append(nombre).append(": ");
        cadena.append(horaInicio).append(", ");
        cadena.append(horaFin).append(", ");
        cadena.append(descripcion).append(", ");
        cadena.append(urgencia).append(", ");
        cadena.append(fecha).append(".");
        return cadena.toString();
    }

    /** Devuelve el nombre de la tarea
     *  @return El nombre de la tarea */
    public String getNombre() {
        return nombre;
    }

    /** Establece el nombre de la tarea
     *  @param nombre Nombre de la tarea */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Devuelve la hora de inicio de la tarea
     *  @return La hora de inicio de la tarea */
    public long getHoraInicio() {
        return horaInicio;
    }

    /** Establece la hora de inicio de la tarea
     *  @param horaInicio La hora de inicio de la tarea */
    public void setHoraInicio(long horaInicio) {
        this.horaInicio = horaInicio;
    }

    /** Devuelve la hora de finalización de la tarea
     *  @return La hora de finalización de la tarea */
    public long getHoraFin() {
        return horaFin;
    }

    /** Establece la hora de finalización de la tarea
     *  @param horaFin La hora de finalización de la tarea */
    public void setHoraFin(long horaFin) {
        this.horaFin = horaFin;
    }

    /** Devuelve la descripción de la tarea
     *  @return La descripcion de la tarea */
    public String getDescripcion() {
        return descripcion;
    }

    /** Establece la descripción de la tarea
     *  @param descripcion La descripción de la tarea */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /** Devuelve la urgencia de la tarea
     *  @return La urgencia */
    public int getUrgencia() {
        return urgencia;
    }

    /** Establece la urgencia de la tarea
     *  @param urgencia La clase de urgencia de la tarea */
    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    /** Devuelve la fecha de la tarea
     *  @return La fecha de la tarea */
    public long getFecha() {
        return fecha;
    }

    /** Establece la fecha de la tarea
     *  @param fecha La fecha de la tarea */
    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
