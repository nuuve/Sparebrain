package interfaz;

import javax.swing.JPanel;

/** Clase abstracta Editor:
 *  Fuerza a que las clases editor implementen la función guardar para poder
 *  tener un único ActionListener para los botones. Aglutina constantes y
 *  métodos comunes.
 *  @version 1.0
 *  @author Pau
 */
public abstract class Editor extends JPanel{
    protected final int NINGUNO = -1;

    /** Método que debe ser sobrescrito por los editores */
    public abstract boolean guardar();
}
