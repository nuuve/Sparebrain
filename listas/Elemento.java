package listas;

/** Clase Elemento:
 *  Representa un elemento de una lista. Sólo es visible desde el paquete listas
 *  @version 1.0
 *  @author Pau
 */
class Elemento {
    private String nombre;

    /** Constructor de la clase
     *  @param nombre Nombre del elemento */
    public Elemento(String nombre){
        this.nombre = nombre;
    }

    /** Devuelve un volcado en forma de cadena del elemento
     *  @return Cadena con el volcado */
    public String obtenerVolcado(){
        return nombre;
    }
    
    /** Devuelve el nombre del elemento
     *  @return El nombre del elemento */
    public String getNombre() {
        return nombre;
    }

    /** Establece el nombre del elemento 
     *  @param nombre El nombre del elemento */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
