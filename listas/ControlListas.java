package listas;

import excepciones.CaracterIlegal;
import java.util.ArrayList;

/** Interfaz ControlListas:
 *  Separa la definición del controlador de listas de su implementación
 *  @version 1.0
 *  @author Pau
 */
public interface ControlListas {
    /** Crea una nueva lista con el nombre que se le pase como argument
     *  @param nombre Nombre de la lista
     *  @return Identificador de la lista creada
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public int nuevaLista(String nombre) throws CaracterIlegal;

    /** Cambia el nombre de la lista cuyo identificador se reciba como argumento
     *  @param identificador Identificador de la lista
     *  @param nombre Nombre de la lista */
    public void editarLista(int identificador, String nombre) throws CaracterIlegal;

    /** Elimina una lista dada
     *  @param identificador Identificador de la lista */
    public void eliminarLista(int identificador);

    /** Crea un nuevo elemento en la lista dada
     *  @param identificador Identificador de la lista
     *  @param nombre Nombre del elemento
     *  @throws excepciones.CaracterIlegal Si el nombre contiene caracteres no permitidos */
    public int nuevoElemento(int identificador, String nombre) throws CaracterIlegal;

    /** Modifica el nombre de un elemento
     *  @param identificadorLista Identificador de la lista con el elemento
     *  @param identificadorElemento Identificador del elemento
     *  @param nombre Nuevo nombre del elemento
     *  @throws excepciones.CaracterIlegal Si el nuevo nombre contiene caracteres no permitidos
     */
    public void editarElemento(int identificadorLista, int identificadorElemento, String nombre) throws CaracterIlegal;

    /** Elimina el elemento dado de la lista
     *  @param identificadorLista Identificador de la lista con el elemento
     *  @param identificadorElemento Identificador del elemento */
    public void eliminarElemento(int identificadorLista, int identificadorElemento);

    /** Guarda las listas */
    public void guardarListas();

    /** Carga las listas */
    public void cargarListas();

    /** Devuelve las listas
     *  @return Arraylist de vectores de String con las listas */
    public ArrayList<String[]> obtenerListas();

    /** Devuelve el nombre de una lista
     *  @param identificador Identificador de la lista
     *  @return Nombre de la lista */
    public String obtenerLista(int identificador);

    /** Devuelve los elementos de la lista
     *  @param identificador Identificador de la lista
     *  @return Arraylist de vectores de String con los elementos de la lista  */
    public ArrayList<String[]> obtenerElementos(int identificador);

    /** Devuelve el nombre de un elemento
     *  @param identificadorLista Identificador de la lista con el elemento
     *  @param identificadorElemento Identificador del elemento
     *  @return Nombre del elemento */
    public String obtenerElemento(int identificadorLista, int identificadorElemento);
    
    /** Elimina todas las listas */
    public void eliminarTodo();

    /** Devuelve el método de validación.
     *  @return Valor de verificación */
    public String obtenerValidacion();
}
