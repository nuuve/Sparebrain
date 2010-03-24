package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/** SJTextField:
 *  Extensión de JTextField
 *  @version 1.0
 *  @author Pau
 */
class SJTextField extends JTextField{
    /** Constructor. Recibe dos bordes, uno para cuando el control se encuentra
     *  activo y otro para cuando se encuentra inactivo.
     *  @param activo Borde para cuando el control se encuentra activo
     *  @param inactivo Borde para cuando el control se encuentra inactivo */
    public SJTextField(Border activo, Border inactivo){
        super();
        setBorder(inactivo);
        addFocusListener(new DetectorEdicionCampoTeclado(this, activo, inactivo));
        setFont(new Font("Lucida grande", Font.PLAIN, 11));
    }

    /** Constructor. Recibe la longitud del control y dos bordes, uno para
     *  cuando el control se encuentra activo y otro para cuando se encuentra
     *  inactivo.
     *  @param longitud Longitud del control
     *  @param activo Borde para cuando el control se encuentra activo
     *  @param inactivo Borde para cuando el control se encuentra inactivo */
    public SJTextField(int longitud, Border activo, Border inactivo){
        super(longitud);
        setBorder(inactivo);
        addFocusListener(new DetectorEdicionCampoTeclado(this, activo, inactivo));
        setFont(new Font("Lucida grande", Font.PLAIN, 11));
    }

    /** Constructor. Recibe la longitud del control; dos bordes, uno para cuando
     *  el control se encuentra activo y otro para cuando se encuentra inactivo;
     *  y la dimensión del control.
     *  @param longitud Longitud del control
     *  @param activo Borde para cuando el control se encuentra activo
     *  @param inactivo Borde para cuando el control se encuentra inactivo
     *  @param dimension Dimensión del control */
    public SJTextField(int longitud, Border activo, Border inactivo, Dimension dimension){
        this(longitud, activo, inactivo);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
    }

    /** Constructor. Recibe dos bordes, uno para cuando el control se encuentra
     *  activo y otro para cuando se encuentra inactivo y la dimensión del
     *  control.
     *  @param activo Borde para cuando el control se encuentra activo
     *  @param inactivo Borde para cuando el control se encuentra inactivo
     *  @param dimension Dimensión del control */
    public SJTextField(Border activo, Border inactivo, Dimension dimension){
        this(activo, inactivo, dimension, new Font("Lucida grande", Font.PLAIN, 11));
    }

    /** Constructor. Recibe dos bordes, uno para cuando el control se encuentra
     *  activo y otro para cuando se encuentra inactivo, la dimensión del
     *  control y el tipo de fuente del control
     *  @param activo Borde para cuando el control se encuentra activo
     *  @param inactivo Borde para cuando el control se encuentra inactivo
     *  @param dimension Dimensión del control
     *  @param fuente Fuente para el control */
    public SJTextField(Border activo, Border inactivo, Dimension dimension, Font fuente){
        this(activo, inactivo);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setFont(fuente);
    }
}

/** Clase para la detección de la entrada en el campo */
class DetectorEdicionCampoTeclado extends FocusAdapter {
    private JComponent objetivo;
    private Border inactivo;
    private Border activo;

    /** Constructor de la clase
     *  @param objetivo Componente objetivo
     *  @param activo Borde para cuando el control está inactivo
     *  @param inactivo  Borde para cuando el control está activo */
    public DetectorEdicionCampoTeclado(JComponent objetivo, Border activo, Border inactivo) {
        this.objetivo = objetivo;
        this.inactivo = inactivo;
        this.activo = activo;
    }

    /** Evento de ganancia del foco
     *  @param f Evento que desencadena la obtención del foco */
    public void focusGained(FocusEvent f) {
        objetivo.setBorder(activo);
    }

    /** Evento de pérdida del foco
     *  @param f Evento que desencadena la pérdida del foco */
    public void focusLost(FocusEvent f) {
        objetivo.setBorder(inactivo);
    }
}