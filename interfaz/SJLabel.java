package interfaz;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;

/** Clase SJLabel:
 *  JLabel extendido
 *  @version 1.0
 *  @author Pau
 */
class SJLabel extends JLabel {
    /** Constructor de la clase
     *  @param texto Texto del JLabel
     *  @param alineacionHorizontal Alineación */
    public SJLabel(String texto, int alineacionHorizontal) {
        super(texto, alineacionHorizontal);
        setFont(new Font("Lucida grande", Font.BOLD, 11));
    }
    
    /** Constructor de la clase
     *  @param texto Texto del JLabel
     *  @param alineacionHorizontal Alineación
     *  @param fuente Fuente de la etiqueta */
    public SJLabel(String texto, int alineacionHorizontal, Font fuente) {
        this(texto, alineacionHorizontal);
        setFont(fuente);
    }

    /** Constructor de la clase
     *  @param texto Texto del JLabel
     *  @param alineacionHorizontal Alineación
     *  @param para Componente para el que es auxiliar la etiqueta */
    public SJLabel(String texto, int alineacionHorizontal, Component para) {
        this(texto, alineacionHorizontal);
        setLabelFor(para);
    }
}
