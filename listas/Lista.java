package listas;

import java.util.ArrayList;

/** Clase Lista:
 *  Clase que representa una lista. Sólo es visible desde el paquete listas
 *  @author Pau
 *  @version 1.0
 */
class Lista {
    private String nombre;
    private ArrayList<Elemento> elementos;

    /** Constructor de la clase
     *  @param nombre El nombre de la lista */
    public Lista(String nombre){
        elementos = new ArrayList<Elemento>();
        this.nombre = nombre;
    }

    /** Edita las propiedades de la lista con el nombre que se reciba como argumento
     *  @param nombre El nombre de la lista */
    public void editar(String nombre){
        this.nombre = nombre;
    }

    /** Crea un nuevo elemento en la lista con el nombre que se reciba como
     *  argumento.
     *  @param nombre El nombre del elemento de la lista
     *  @return El identificador del nuevo elemento creado */
    public int nuevoElemento(String nombre){
        Elemento nuevo = new Elemento(nombre);
        elementos.add(nuevo);
        return elementos.indexOf(nuevo);
    }

    /** Edita el nombre del elemento cuyo identificador se reciba como parámetro
     *  por el nombre que se reciba como parámetro.
     *  @param identificador El identificador del elemento
     *  @param nombre El nombre del evento */
    public void editarElemento(int identificador, String nombre){
        elementos.get(identificador).setNombre(nombre);
    }

    /** Elimina de la lista el elemento cuyo identificador se reciba como argumento
     *  @param identificador Identificador del elemento que se quiere eliminar  */
    public void eliminarElemento(int identificador){
        elementos.remove(identificador);
    }

    /** Devuelve un volcado en forma de cadena de la lista
     *  @param separadorCampos separador entre campos de un registro
     *  @param separadorElementos separador entre los elementos de la lista
     *  @return Cadena con el volcado */
    public String obtenerVolcado(String separadorCampos, String separadorElementos){
        StringBuilder cadena = new StringBuilder();
        cadena.append(nombre).append(separadorCampos);
        for(Elemento e : elementos){
            cadena.append(e.obtenerVolcado()).append(separadorElementos);
        }
        return cadena.toString();
    }

    /** Devuelve una lista con los elementos de la lista
     *  @return ArrayList con los elementos de la lista actual */
    public ArrayList<String []> obtenerElementos() {
        ArrayList<String []> resultado = new ArrayList<String []>();
        for(Elemento e : elementos){
            String [] elemento = new String [2];
            elemento[0] = e.getNombre();
            elemento[1] = elementos.indexOf(e) + "";
            resultado.add(elemento);
        }
        return resultado;
    }

    /** Devuelve el nombre de un elemento cuyo identificador se reciba como
     *  parámetro
     *  @param identificador Identificador del elemento
     *  @return Nombre del elemento */
    public String obtenerElemento(int identificador){
        return elementos.get(identificador).getNombre();
    }

    /** Devuelve el nombre de la lista
     *  @return El nombre de la lista */
    public String getNombre() {
        return nombre;
    }

    /** Establece el nombre de la lista
     *  @param nombre El nombre de la lista */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
