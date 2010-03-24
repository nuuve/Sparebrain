package interfaz;

import java.awt.Font;
import javax.swing.JComboBox;

/** Clase SJComboBox:
 *  JComboBox extendido
 *  @version 1.0
 *  @author Pau
 */
class SJComboBox extends JComboBox {
    /** Constructor de la clase */
    public SJComboBox() {
        super();
        setFont(new Font("Lucida grande", Font.PLAIN, 11));
    }

    /** Constructor de la clase
     *  @param datos Array de Strings para inicializar el Combo box */
    public SJComboBox(String[] datos) {
        super(datos);
        setFont(new Font("Lucida grande", Font.PLAIN, 11));
    }
}
