package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Clase DetectorBotones:
 *  Detecta la pulsación de los botones de "Guardar" y "Cancelar"
 *  @version 1.0
 *  @author Pau
 */
class DetectorBotones implements ActionListener{
    Editor padre;

    /** Constructor de la clase
     *  @param padre Editor padre de los botones */
    public DetectorBotones(Editor padre){
        this.padre = padre;
    }

    /** Disparador del evento
     *  @param ae Evento */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equalsIgnoreCase("guardar")){
            if(padre.guardar())
                padre.setVisible(false);
        }
        else{
            padre.setVisible(false);
        }
    }
}
