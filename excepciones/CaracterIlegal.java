package excepciones;

/** CaracterIlegal:
 *  Excepción que se lanza cuando se intenta insertar o editar una tarea o una
 *  lista cuyo nombre contiene un caracter incorrecto (generalmente los usados
 *  como delimitadores de los archivos)
 *  @author Pau
 */
public class CaracterIlegal extends Exception {
    private String mensaje;

    /** Constructor por defecto */
    public CaracterIlegal() {
        super();
        mensaje = null;
    }

    /** Constructor que prepara un mensaje con la descripción del error */
    public CaracterIlegal(String mensaje) {
        super();
        this.mensaje = mensaje;
    }

    /** Devuelve el mensaje
     *  @return Mensaje del error */
    public String getMensaje() {
        return mensaje;
    }

}
