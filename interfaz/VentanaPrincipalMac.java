package interfaz;

import java.awt.*;
import util.FactoriaArchivos;

/** Clase VentanaPrincipalMac:
 *  Representa una ventana principal de la aplicación, con un visor de tareas y
 *  un visor de listas. Contiene los menús y los diferentes elementos de la
 *  interfaz. Está adaptada para funcionar sobre Mac OS X.
 *  @version 1.0
 *  @author Pau
 */
public class VentanaPrincipalMac extends VentanaPrincipal {
    /** Constructor de la clase
      *  @param factoria Factoría de archivos */
    public VentanaPrincipalMac(FactoriaArchivos factoria) {
        super(factoria, Event.META_MASK);

        com.apple.eawt.Application app = com.apple.eawt.Application.getApplication();
        app.setEnabledPreferencesMenu(true);
        app.setEnabledAboutMenu(false);

        class ControladorMacOSX extends com.apple.eawt.ApplicationAdapter{
            VentanaPrincipalMac padre = null;
            public ControladorMacOSX(VentanaPrincipalMac padre){
                this.padre = padre;
            }
            public void finalize() throws Throwable {
                padre.terminarAplicacion();
            }
            public void handlePreferences(com.apple.eawt.ApplicationEvent arg0) throws IllegalStateException{
                padre.mostrarVentanaPreferencias();
            }
            public void handleQuit(com.apple.eawt.ApplicationEvent arg0) throws IllegalStateException {
                padre.terminarAplicacion();
            }
        }

        app.addApplicationListener(new ControladorMacOSX(this));
    }

    /** Sobrescribe el método de creación del menú adaptándolo a Mac OS X
     *  @param tab Panel activo  */
    protected void crearMenu(int tab){
        String [] archivoNombres     = {"", traduccion.tExportarTareas,
            traduccion.tExportarListas, "-", traduccion.tGuardarCopiaTareas,
            traduccion.tCargarCopiaTareas, traduccion.tGuardarCopiaListas,
            traduccion.tCargarCopiaListas};
        String [] archivoTeclas     = {"", "T", "L", "", "", "", ""};
        generaMenu(tab, archivoNombres, archivoTeclas);
    }
}

   