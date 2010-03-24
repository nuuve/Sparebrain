package interfaz;

import java.awt.Font;
import util.Traduccion;
import util.Propiedades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/** Clase VentanaPreferencias:
 *  Ventana para elegir las preferencias de la aplicación
 *  @version 1.0
 *  @author Pau
 */
public class VentanaPreferencias extends JFrame {
    private JComboBox selecIdioma, selecInterfaz;
    private Propiedades propiedades;
    private final String listaCodigos [] = {"auto", "es", "en", "kl"};
    private final String listaIdiomas [] = {"", "Español", "English", "Klingon"};
    private LookAndFeelInfo[] aspectos;
    private VentanaPrincipal ventanaPrincipal;
    private Traduccion traduccion;
    private JLabel etiqAviso;
    private String idiomaActual;
    
    /** Constructor de la clase
     *  @param propiedades Objeto Propiedades con las preferencias
     *  @param traduccion Objeto Traduccion con la traducción
     *  @param ventanaPrincipal Referencia a la ventana principal de la aplicación */
    public VentanaPreferencias(Propiedades propiedades, Traduccion traduccion, VentanaPrincipal ventanaPrincipal){
        super("Sparebrain");
        this.propiedades = propiedades;
        this.ventanaPrincipal = ventanaPrincipal;
        this.traduccion = traduccion;
        
        idiomaActual = propiedades.obtenerPropiedad("idioma");
        if(idiomaActual == null){
            idiomaActual = "auto";
        }

        String temaActual = propiedades.obtenerPropiedad("tema");
        listaIdiomas[0] = traduccion.tDetectar;

        // Configurar la ventana
        setSize(400, 120);
        setLocationRelativeTo(null);
        setResizable(false);
        SpringLayout disp = new SpringLayout();
        setLayout(disp);

        int col1   = 30;
        int col2   = 130;
        int fil    = 10;
        int margen = 10;

        // Definición del campo Interfaz
        selecInterfaz = new SJComboBox();
        aspectos = UIManager.getInstalledLookAndFeels();

        selecInterfaz.addItem(traduccion.tDetectar);
        if(temaActual != null)
            if(temaActual.equals("auto"))
                selecInterfaz.setSelectedIndex(0);
        for(int i = 0; i < aspectos.length; i++){
            selecInterfaz.addItem((aspectos[i]).getName());
            if(temaActual != null)
            if(temaActual.equals((aspectos[i]).getClassName()))
                selecInterfaz.setSelectedIndex(i+1);
        }
        selecInterfaz.setActionCommand("interfaz");
        JLabel etiqInterfaz = new SJLabel(traduccion.tEtiqInterfaz + ":", JLabel.TRAILING, selecInterfaz);

        // Posición del campo Interfaz
        SpringLayout.Constraints restEtiqInterfaz = disp.getConstraints(etiqInterfaz);
        restEtiqInterfaz.setX(Spring.constant(col1));
        restEtiqInterfaz.setY(Spring.constant(fil + 4));
        restEtiqInterfaz.setWidth(Spring.constant(col2-col1-margen));
        SpringLayout.Constraints restSelecInterfaz = disp.getConstraints(selecInterfaz);
        restSelecInterfaz.setX(Spring.constant(col2));
        restSelecInterfaz.setY(Spring.constant(fil));

        fil = 40;

        // Definición del campo Idioma
        selecIdioma = new SJComboBox(listaIdiomas);
        selecIdioma.setActionCommand("idioma");
        JLabel etiqIdioma = new SJLabel(traduccion.tEtiqIdioma + ":", JLabel.TRAILING, selecIdioma);

        // Posición del campo Idioma
        SpringLayout.Constraints restEtiqIdioma = disp.getConstraints(etiqIdioma);
        restEtiqIdioma.setX(Spring.constant(col1));
        restEtiqIdioma.setY(Spring.constant(fil + 4));
        restEtiqIdioma.setWidth(Spring.constant(col2-col1-margen));
        SpringLayout.Constraints restSelecIdioma = disp.getConstraints(selecIdioma);
        restSelecIdioma.setX(Spring.constant(col2));
        restSelecIdioma.setY(Spring.constant(fil));

        for(int i = 0; i < listaCodigos.length; i++){
            if(listaCodigos[i].equals(idiomaActual))
                selecIdioma.setSelectedIndex(i);
        }

        fil = 70;

        etiqAviso = new SJLabel(traduccion.tAvisoIdioma, JLabel.TRAILING, new Font("Lucida grande", Font.PLAIN, 11));
        SpringLayout.Constraints restEtiqAviso = disp.getConstraints(etiqAviso);
        restEtiqAviso.setX(Spring.constant(10));
        restEtiqAviso.setY(Spring.constant(fil));
        etiqAviso.setVisible(false);
        add(etiqIdioma);
        add(selecIdioma);
        add(etiqInterfaz);
        add(selecInterfaz);

        add(etiqAviso);

        selecIdioma.addActionListener(new DetectorCambios(this));
        selecInterfaz.addActionListener(new DetectorCambios(this));

        setVisible(true);
    }

    /** Cambia el idioma de la aplicación */
    protected void cambiarIdioma(){
        String nuevoIdioma = listaCodigos[selecIdioma.getSelectedIndex()];
        if(!idiomaActual.equals(nuevoIdioma)){
            etiqAviso.setVisible(true);
        }
        else{
            etiqAviso.setVisible(false);
        }
        try {
            propiedades.establecerPropiedad("idioma", nuevoIdioma);
        }
        catch (IOException ex) {
        }
    }

    /** Cambia el tema (Look and feel) de la aplicación */
    protected void cambiarTema(){
        try {
            if(selecInterfaz.getSelectedIndex() == 0){
                propiedades.establecerPropiedad("tema", "auto");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            else{
                String tema = (aspectos[selecInterfaz.getSelectedIndex() - 1]).getClassName();

                propiedades.establecerPropiedad("tema", tema);
                UIManager.setLookAndFeel(tema);
            }
            SwingUtilities.updateComponentTreeUI(this);
            SwingUtilities.updateComponentTreeUI(ventanaPrincipal);
        }
        catch (Exception ex) { }
    }
}

class DetectorCambios implements ActionListener{
    VentanaPreferencias padre;

    DetectorCambios(VentanaPreferencias padre){
        this.padre = padre;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("idioma"))
            padre.cambiarIdioma();
        else if(e.getActionCommand().equals("interfaz"))
            padre.cambiarTema();
}};