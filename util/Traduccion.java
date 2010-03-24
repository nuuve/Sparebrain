package util;

/** Clase Traduccion:
 *  Almacena una serie de atributos para permitir el acceso a las traducciones.
 *  Se prescinde del encapsulamiento de los campos (¡que no cunda el pánico!)
 *  para mejorar la eficiencia y porque no va a modificarse la forma en que se
 *  accede a los atributos. Además, los atributos son de sólo lectura.
 *  Acceder a los atributos constantes de una clase es más eficiente que acceder
 *  cada vez a Properties o a una colección con los datos, por lo que se ha op-
 *  tado por esta solución. 
 *  @version 1.0
 *  @author Pau
 */
public class Traduccion {
    private String locale;  // Identificador del idioma utilizado
    public final String ayudaVerTareas;
    public final String ayudaVerListas;
    public final String tMenuArchivo;
    public final String tMenuVer;
    public final String tMenuPreferencias;
    public final String tExportarTareas;
    public final String tExportarListas;
    public final String tGuardarCopiaListas;
    public final String tCargarCopiaListas;
    public final String tGuardarCopiaTareas;
    public final String tImprimir;
    public final String tCargarCopiaTareas;
    public final String tSalir;
    public final String tCopiar;
    public final String tNuevaTarea;
    public final String tEliminarTarea;
    public final String tEliminarLista;
    public final String tNuevaLista;
    public final String tNuevoElemento;
    public final String tEliminarElemento;
    public final String tEliminarTodo;
    public final String tListas;
    public final String tTareas;
    public final String tErrorExportacion;
    public final String tErrorImportacion;
    public final String tEtiqIdioma;
    public final String tEtiqInterfaz;
    public final String tDetectar;
    public final String tBotonGuardar;
    public final String tBotonCancelar;
    public final String tSinNombre;
    public final String ayudaNuevaLista;
    public final String ayudaEliminarLista;
    public final String ayudaNuevoElemento;
    public final String ayudaEliminarElemento;
    public final String tConfirmarBorrarTodoListas;
    public final String tCaracterIlegal;
    public final String tConfirmarBorrarLista;
    public final String tTituloConfirmarBorrarLista;
    public final String tLista;
    public final String ayudaNuevaTarea;
    public final String ayudaEliminarTarea;
    public final String tTodas;
    public final String tHoy;
    public final String tManana;
    public final String tPendientes;
    public final String tComenzadas;
    public final String tConfirmarBorrarTodoTareas;
    public final String tConfirmarBorrarTarea;
    public final String tTituloConfirmarBorrarTarea;
    public final String tConcluida;
    public final String tPendiente;
    public final String tComenzada;
    public final String tALas;
    public final String tDesdeEl;
    public final String tHastaEl;
    public final String tSelNinguna;
    public final String tSelPoco;
    public final String tSelImportante;
    public final String tSelMuy;
    public final String tEtiqDescripcion;
    public final String tEtiqUrgencia;
    public final String tEtiqFecha;
    public final String tEtiqEstado;
    public final String tAvisoIdioma;
    public final String tErrorFormatoIncorrecto;

    /** Constructor. Carga los atributos con sus correspondientes valores 
     *  @param idioma Archivo de propiedades con la traducción */
    public Traduccion (Propiedades idioma){
        tEtiqDescripcion = idioma.obtenerPropiedad("tEtiqDescripcion");
        tEtiqUrgencia = idioma.obtenerPropiedad("tEtiqUrgencia");
        tEtiqFecha = idioma.obtenerPropiedad("tEtiqFecha");
        tEtiqEstado = idioma.obtenerPropiedad("tEtiqEstado");
        locale = idioma.obtenerPropiedad("locale");
        tConcluida = idioma.obtenerPropiedad("tConcluida");
        tPendiente = idioma.obtenerPropiedad("tPendiente");
        tComenzada = idioma.obtenerPropiedad("tComenzada");
        tALas = idioma.obtenerPropiedad("tAlas");
        tDesdeEl = idioma.obtenerPropiedad("tDesdeEl");
        tHastaEl = idioma.obtenerPropiedad("tHastaEl");
        tSelNinguna = idioma.obtenerPropiedad("tSelNinguna");
        tSelPoco = idioma.obtenerPropiedad("tSelPoco");
        tSelImportante = idioma.obtenerPropiedad("tSelImportante");
        tSelMuy = idioma.obtenerPropiedad("tSelMuy");
        ayudaNuevaTarea = idioma.obtenerPropiedad("ayudaNuevaTarea");
        ayudaEliminarTarea = idioma.obtenerPropiedad("ayudaEliminarTarea");
        tTodas = idioma.obtenerPropiedad("tTodas");
        tHoy = idioma.obtenerPropiedad("tHoy");
        tManana = idioma.obtenerPropiedad("tManana");
        tPendientes = idioma.obtenerPropiedad("tPendientes");
        tComenzadas = idioma.obtenerPropiedad("tComenzadas");
        tConfirmarBorrarTodoTareas = idioma.obtenerPropiedad("tConfirmarBorrarTodoTareas");
        tConfirmarBorrarTarea = idioma.obtenerPropiedad("tConfirmarBorrarTarea");
        tTituloConfirmarBorrarTarea = idioma.obtenerPropiedad("tTituloConfirmarBorrarTarea");
        tListas = idioma.obtenerPropiedad("tListas");
        tTareas = idioma.obtenerPropiedad("tTareas");
        ayudaVerTareas = idioma.obtenerPropiedad("ayudaVerTareas");
        ayudaVerListas = idioma.obtenerPropiedad("ayudaVerListas");
        tMenuArchivo = idioma.obtenerPropiedad("tMenuArchivo");
        tMenuVer = idioma.obtenerPropiedad("tMenuVer");
        tMenuPreferencias = idioma.obtenerPropiedad("tMenuPreferencias");
        tExportarTareas = idioma.obtenerPropiedad("tExportarTareas");
        tExportarListas = idioma.obtenerPropiedad("tExportarListas");
        tGuardarCopiaListas = idioma.obtenerPropiedad("tGuardarCopiaListas");
        tCargarCopiaListas = idioma.obtenerPropiedad("tCargarCopiaListas");
        tGuardarCopiaTareas = idioma.obtenerPropiedad("tGuardarCopiaTareas");
        tCargarCopiaTareas = idioma.obtenerPropiedad("tCargarCopiaTareas");
        tImprimir = idioma.obtenerPropiedad("tImprimir");
        tSalir = idioma.obtenerPropiedad("tSalir");
        tCopiar = idioma.obtenerPropiedad("tCopiar");
        tNuevaTarea = idioma.obtenerPropiedad("tNuevaTarea");
        tEliminarTarea = idioma.obtenerPropiedad("tEliminarTarea");
        tNuevaLista = idioma.obtenerPropiedad("tNuevaLista");
        tEliminarLista = idioma.obtenerPropiedad("tEliminarLista");
        tNuevoElemento = idioma.obtenerPropiedad("tNuevoElemento");
        tEliminarElemento = idioma.obtenerPropiedad("tEliminarElemento");
        tEliminarTodo = idioma.obtenerPropiedad("tEliminarTodo");
        tErrorExportacion = idioma.obtenerPropiedad("tErrorExportacion");
        tErrorImportacion = idioma.obtenerPropiedad("tErrorImportacion");
        tEtiqIdioma = idioma.obtenerPropiedad("tEtiqIdioma");
        tEtiqInterfaz = idioma.obtenerPropiedad("tEtiqInterfaz");
        tDetectar = idioma.obtenerPropiedad("tDetectar");
        tBotonGuardar = idioma.obtenerPropiedad("tBotonGuardar");
        tBotonCancelar = idioma.obtenerPropiedad("tBotonCancelar");
        tSinNombre = idioma.obtenerPropiedad("tSinNombre");
        ayudaNuevaLista = idioma.obtenerPropiedad("ayudaNuevaLista");
        ayudaEliminarLista = idioma.obtenerPropiedad("ayudaEliminarLista");
        ayudaNuevoElemento = idioma.obtenerPropiedad("ayudaNuevoElemento");
        ayudaEliminarElemento = idioma.obtenerPropiedad("ayudaEliminarElemento");
        tConfirmarBorrarTodoListas = idioma.obtenerPropiedad("tConfirmarBorrarTodoListas");
        tCaracterIlegal = idioma.obtenerPropiedad("tCaracterIlegal");
        tConfirmarBorrarLista = idioma.obtenerPropiedad("tConfirmarBorrarLista");
        tTituloConfirmarBorrarLista = idioma.obtenerPropiedad("tTituloConfirmarBorrarLista");
        tLista = idioma.obtenerPropiedad("tLista");
        tAvisoIdioma = idioma.obtenerPropiedad("tAvisoIdioma");
        tErrorFormatoIncorrecto = idioma.obtenerPropiedad("tErrorFormatoIncorrecto");
    }

    /** Devuelve el idioma que está en uso actualmente
     *  @return Identificador del idioma que se está utilizando */
    public final String obtenerIdioma() {
        return locale;
    }
}
