package interfaz;

import util.FactoriaArchivos;
import util.Traduccion;
import util.Propiedades;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import util.Fichero;

/** Clase VentanaPrincipal:
 *  Representa una ventana principal de la aplicación, con un visor de tareas y 
 *  un visor de listas. Contiene los menús y los diferentes elementos de la
 *  interfaz
 *  @version 1.0
 *  @author Pau
 */
public class VentanaPrincipal extends JFrame {
    private Propiedades propiedades;    // Archivo de propiedades
    protected Traduccion traduccion;    // Objeto con la traducción
    private FactoriaArchivos factoria;  // Factoría para los archivos
    private VisorListas visorListas;    // Visor para las listas
    private VisorTareas visorTareas;    // Visor para las tareas
    private JTabbedPane tpMenu;         // Panel tabulado
    private final int altoMinimo = 360, anchoMinimo = 420;  // Ctes. tamaño mínimo

    protected final int LISTAS = 1, TAREAS = 0; // Ctes. principales
    protected JMenu [] menus;                   // Menús
    protected JMenuBar barraMenus;              // Barra de menús
    protected int mascaraTeclado;               // Máscara para el teclado

    /** Constructor de la clase
     *  @param factoria Factoría de archivos */
    public VentanaPrincipal(FactoriaArchivos factoria){
        this(factoria, Event.CTRL_MASK);
    }

    /** Constructor de la clase
     *  @param factoria Factoría de archivos
     *  @param mascaraTeclado Máscara de teclado para los menús */
    public VentanaPrincipal(FactoriaArchivos factoria, int mascaraTeclado) {
        super("Sparebrain");
        this.factoria = factoria;
        this.mascaraTeclado = mascaraTeclado;

        propiedades = factoria.obtenerPropiedades();
        // Definir valores por defecto para las preferencias
        int x = 40, y = 40;
        int h = altoMinimo, w = anchoMinimo;
        int tab = 0;
        int seleccionarTarea = 0, seleccionarLista = -1;
        String locale = "auto";
        String tema = "auto";

        // Cargar las preferencias
        try{
            x = Integer.parseInt(propiedades.obtenerPropiedad("posicionX"));
            y = Integer.parseInt(propiedades.obtenerPropiedad("posicionY"));
            h = Integer.parseInt(propiedades.obtenerPropiedad("alto"));
            w = Integer.parseInt(propiedades.obtenerPropiedad("ancho"));
            tab = Integer.parseInt(propiedades.obtenerPropiedad("tab"));
            seleccionarLista = Integer.parseInt(propiedades.obtenerPropiedad("listasSel"));
            seleccionarTarea = Integer.parseInt(propiedades.obtenerPropiedad("tareasCat"));
            locale = propiedades.obtenerPropiedad("idioma");
            tema = propiedades.obtenerPropiedad("tema");
        } catch(Exception e){ }
        
        // Seleccionar el tema visual
        try{
            if((tema == null) || (tema.equals("auto")))
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            else
                UIManager.setLookAndFeel(tema);
        }
        catch(Exception e){ }

        // --------------------------------------------------- Ventana principal

        // Garantizar el tamaño mínimo de la ventana al cargar los valores
        if(w < anchoMinimo) w = anchoMinimo;
        if(h < altoMinimo) h = altoMinimo;

        // Configurar la ventana
        setSize(w, h);
        setLocation(x, y);
        setLayout(new GridLayout(0, 1));
        DetectorBotonCerrar dbc = new DetectorBotonCerrar(this);
        addWindowListener(dbc);

        // Evitar que la ventana sea redimensionada por debajo de los valores mínimos
        addComponentListener(new ComponentAdapter() {
        public void componentResized(ComponentEvent e) {
            int width = getWidth();
            int height = getHeight();

            boolean resize = false;
            if (width < anchoMinimo) {
                resize = true;
                width = anchoMinimo;
            }
            if (height < altoMinimo) {
                resize = true;
                height = altoMinimo;
            }
            if (resize)
                setSize(new Dimension(width, height));
            }
        });

        // -------------------------------------------------------- Traducciones

        Propiedades idioma = null;
        
        // Si no hay ningún idioma definido en las preferencias, obtener el del sistema
        if((locale == null) || (locale.equals("auto")))
            locale = System.getProperty("user.language");

        // Intentar cargar el fichero de traducciones elegido obteniéndolo del paquete
        try {
            idioma = new Propiedades(ClassLoader.getSystemResourceAsStream("recursos/locale_" + locale + ".xml"));
        }
        catch (Exception ex) {
            locale = "en";  // Si no existe esa traducción, cargar el inglés
            try {
                idioma = new Propiedades(ClassLoader.getSystemResourceAsStream("recursos/locale_" + locale + ".xml"));
            }
            catch (Exception ec) {
                mensajeError("Error: Default translation file not found.");
                System.exit(1);
            }
        }
        traduccion = new Traduccion(idioma);

        // ---------------------------------------------------------------- Menú
        
        crearMenu(tab);

        // ------------------------------------------ Paneles de listas y tareas

        // Crear los dos visores y seleccionar la tarea o lista correspondiente
        factoria = new FactoriaArchivos();
        visorTareas = new VisorTareas(factoria.obtenerArchivoTareas(), traduccion, menus[TAREAS]);
        visorTareas.establecerCategoriaSeleccionada(seleccionarTarea);
        visorListas = new VisorListas(factoria.obtenerArchivoListas(), traduccion, menus[LISTAS]);
        visorListas.establecerListaSeleccionada(seleccionarLista);

        // Crear panel tabulado
        tpMenu = new JTabbedPane();
        tpMenu.setBorder(BorderFactory.createEmptyBorder());
        tpMenu.addTab(traduccion.tTareas, null, visorTareas, traduccion.ayudaVerTareas);
        tpMenu.addTab(traduccion.tListas, null, visorListas, traduccion.ayudaVerListas);
        tpMenu.addChangeListener(new DetectorPaneles(this));
        tpMenu.setSelectedIndex(tab);
        add(tpMenu);

        // Finalmente, mostrar la ventana
        setVisible(true);
    }

    /** Crea el menú
     *  @param tab Panel activo  */
    protected void crearMenu(int tab){
        String [] archivoNombres     = { traduccion.tMenuPreferencias, "-",
            traduccion.tExportarTareas, traduccion.tExportarListas, "-",
            traduccion.tGuardarCopiaTareas, traduccion.tCargarCopiaTareas,
            traduccion.tGuardarCopiaListas, traduccion.tCargarCopiaListas, "-",
            traduccion.tSalir};
        String [] archivoTeclas     = {"R", "T", "L", "", "", "", "", "S"};
        generaMenu(tab, archivoNombres, archivoTeclas);
    }

    /** Genera el menú tomando el menú archivo y los atajos de teclado que
     *  recibe como argumento
     *  @param tab Panel activo
     *  @param archivoNombres Nombres del archivo
     *  @param archivoTeclas Atajos de teclado de archivo */
    protected void generaMenu(int tab, String [] archivoNombres, String [] archivoTeclas){
         // Cargar iconos para el menú
        ImageIcon nuevaTarea = new ImageIcon(ClassLoader.getSystemResource("recursos/nuevaTarea.png"));
        ImageIcon eliminarTarea = new ImageIcon(ClassLoader.getSystemResource("recursos/eliminarTarea.png"));
        ImageIcon copiar = new ImageIcon(ClassLoader.getSystemResource("recursos/copiar.png"));
        ImageIcon nuevaLista = new ImageIcon(ClassLoader.getSystemResource("recursos/nuevaLista.png"));
        ImageIcon eliminarLista = new ImageIcon(ClassLoader.getSystemResource("recursos/eliminarLista.png"));
        ImageIcon nuevoElemento = new ImageIcon(ClassLoader.getSystemResource("recursos/nuevoElemento.png"));
        ImageIcon eliminarElemento = new ImageIcon(ClassLoader.getSystemResource("recursos/eliminarElemento.png"));

        // Generar el menú
        ImageIcon [] edicionTareasIconos     = {copiar, nuevaTarea, eliminarTarea};
        ImageIcon [] edicionListasIconos     = {copiar, nuevaLista, eliminarLista, nuevoElemento, eliminarElemento};

        DetectorMenu dm = new DetectorMenu(this);
        
        String [] edicionTareasNombres    = {traduccion.tCopiar, "-",
            traduccion.tNuevaTarea, traduccion.tEliminarTarea, "-",
            traduccion.tEliminarTodo};
        String [] edicionTareasTeclas     = {"C", "N", "D"};
        String [] edicionListasNombres    = {traduccion.tCopiar, "-",
            traduccion.tNuevaLista, traduccion.tEliminarLista,
            traduccion.tNuevoElemento, traduccion.tEliminarElemento, "-",
            traduccion.tEliminarTodo};
        String [] edicionListasTeclas     = {"C", "N", "D"};
        String [] verNombres        = {traduccion.tTareas, traduccion.tListas};
        String [] verTeclas         = {"1", "2"};

        barraMenus = new JMenuBar();
        barraMenus.add(factoriaMenus(traduccion.tMenuArchivo, "a", dm, archivoNombres, archivoTeclas));
        menus = new JMenu[2];
        menus[TAREAS] = factoriaMenus(traduccion.tTareas, "t", dm, edicionTareasNombres, edicionTareasTeclas, edicionTareasIconos);
        menus[LISTAS] = factoriaMenus(traduccion.tListas, "l", dm, edicionListasNombres, edicionListasTeclas, edicionListasIconos);
        barraMenus.add(menus[tab]);
        barraMenus.add(factoriaMenus(traduccion.tMenuVer, "v", dm, verNombres, verTeclas));

        setJMenuBar(barraMenus);
    }

    /** Crea un menú desplegable partiendo de los datos que recibe
     *  @param titulo Título del menú superior
     *  @param id Identificador del submenú (se utiliza en el ActionCommand)
     *  @param dm ActionListener para añadir a los botones
     *  @param nombres Archivo con los nombres de los submenús
     *  @param teclas Archivo con los atajos de teclado para los submenús
     *  @return Un JMenu con el menú formado */
    private JMenu factoriaMenus(String titulo, String id, ActionListener dm, String [] nombres, String [] teclas, ImageIcon [] iconos){
        JMenu menu = new JMenu(titulo);
        JMenuItem [] jmi = new JMenuItem[nombres.length];
        int ac = 0;

        for(int i = 0; i < jmi.length; i++){
            if(nombres[i].equals("-")){
                menu.add(new JSeparator());
            }
            else if(nombres[i].equals("")){
                ac++;
            }
            else{
                if((ac < iconos.length) && (iconos[ac] != null)){
                    jmi[i] = new JMenuItem(nombres[i], iconos[ac]);
                }
                else{
                    jmi[i] = new JMenuItem(nombres[i]);
                }
                jmi[i].setActionCommand(id + ac);
                if((ac < teclas.length) && (!teclas[ac].equals(""))){
                    jmi[i].setAccelerator(KeyStroke.getKeyStroke(teclas[ac].charAt(0), mascaraTeclado));
                }
                jmi[i].addActionListener(dm);
                menu.add(jmi[i]);
                ac++;
            }
        }
        return menu;
    }

    /** Crea un menú desplegable partiendo de los datos que recibe
     *  @param titulo Título del menú superior
     *  @param id Identificador del submenú (se utiliza en el ActionCommand)
     *  @param dm ActionListener para añadir a los botones
     *  @param nombres Archivo con los nombres de los submenús
     *  @param teclas Archivo con los atajos de teclado para los submenús
     *  @return Un JMenu con el menú formado */
    private JMenu factoriaMenus(String titulo, String id, ActionListener dm, String [] nombres, String [] teclas){
        JMenu menu = new JMenu(titulo);
        JMenuItem [] jmi = new JMenuItem[nombres.length];
        int ac = 0;

        for(int i = 0; i < jmi.length; i++){
            if(nombres[i].equals("-")){
                menu.add(new JSeparator());
            }
            else if(nombres[i].equals("")){
                ac++;
            }
            else{
                jmi[i] = new JMenuItem(nombres[i]);
                jmi[i].setActionCommand(id + ac);
                if((ac < teclas.length) && (!teclas[ac].equals(""))){
                    jmi[i].setAccelerator(KeyStroke.getKeyStroke(teclas[ac].charAt(0), mascaraTeclado));
                }
                jmi[i].addActionListener(dm);
                menu.add(jmi[i]);
                ac++;
            }
        }
        return menu;
    }

    /** Guardar las preferencias de la interfaz de usuario en el archivo de propiedades */
    private void guardarPreferencias(){
        String x   = String.valueOf(getLocationOnScreen().x);
        String y   = String.valueOf(getLocationOnScreen().y);
        String th  = String.valueOf(getSize().height);
        String tw  = String.valueOf(getSize().width);
        String tab = String.valueOf(tpMenu.getSelectedIndex());
        String cat = String.valueOf(visorTareas.obtenerCategoriaSeleccionada());
        String lis = String.valueOf(visorListas.obtenerListaSeleccionada());

        try {
            propiedades.establecerPropiedad("posicionX", x);
            propiedades.establecerPropiedad("posicionY", y);
            propiedades.establecerPropiedad("alto", th);
            propiedades.establecerPropiedad("ancho", tw);
            propiedades.establecerPropiedad("tab", tab);
            propiedades.establecerPropiedad("tareasCat", cat);
            propiedades.establecerPropiedad("listasSel", lis);
        }
        catch (Exception ex) { }
    }

    /** Guarda una copia de seguridad de las tareas en el archivo que elija el
     *  usuario */
    public void guardarCopiaTareas(){
        JFileChooser jfc = new JFileChooser();
        jfc.setSelectedFile(new File(traduccion.tTareas.toLowerCase() + ".txt"));
        jfc.setDialogTitle(traduccion.tGuardarCopiaTareas);
        if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File destino = jfc.getSelectedFile();
            if(destino != null){
                try{
                    Fichero.copiar(factoria.obtenerArchivoTareas(), destino);
                }
                catch (IOException ex) {
                    mensajeError(traduccion.tErrorExportacion);
                }
            }
        }
    }

    /** Carga una copia de seguridad de las tareas desde el archivo que elija el
     *  usuario */
    public void cargarCopiaTareas() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle(traduccion.tCargarCopiaTareas);
        if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File origen = jfc.getSelectedFile();
            if(origen.exists()){
                if(!visorTareas.comprobarArchivo(origen)){
                    mensajeError(traduccion.tErrorFormatoIncorrecto);
                }
                else{
                    try{
                        Fichero.copiar(origen, factoria.obtenerArchivoTareas());
                        visorTareas.recargarTareas();
                    }
                    catch (IOException ex) {
                        mensajeError(traduccion.tErrorImportacion);
                    }
                }
            }
        }
    }

    /** Guarda una copia de seguridad de las listas en el archivo que elija el
     *  usuario */
    public void guardarCopiaListas(){
        JFileChooser jfc = new JFileChooser();
        jfc.setSelectedFile(new File(traduccion.tListas.toLowerCase() + ".txt"));
        jfc.setDialogTitle(traduccion.tGuardarCopiaListas);
        if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File destino = jfc.getSelectedFile();
            if(destino != null){
                try{
                    Fichero.copiar(factoria.obtenerArchivoListas(), destino);
                }
                catch (IOException ex) {
                    mensajeError(traduccion.tErrorExportacion);
                }
            }
        }
    }

    /** Carga una copia de seguridad de las listas desde el archivo que elija el
     *  usuario */
    public void cargarCopiaListas() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle(traduccion.tCargarCopiaListas);
        if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            File origen = jfc.getSelectedFile();
            if(origen.exists()){
                if(!visorListas.comprobarArchivo(origen)){
                    mensajeError(traduccion.tErrorFormatoIncorrecto);
                }
                else{
                    try{
                        Fichero.copiar(origen, factoria.obtenerArchivoListas());
                        visorListas.recargarListas();
                    }
                    catch (IOException ex) {
                        mensajeError(traduccion.tErrorImportacion);
                    }
                }
            }
        }
    }

    /** Exporta las tareas al archivo que el usuario elija */
    public void exportarTareas(){
        JFileChooser jfc = new JFileChooser();
        jfc.setSelectedFile(new File(traduccion.tTareas.toLowerCase() + ".txt"));
        jfc.setDialogTitle(traduccion.tExportarTareas);
        if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File destino = jfc.getSelectedFile();

            String volcado = traduccion.tTareas + ": " + visorTareas.obtenerVolcado();

            try {
                BufferedWriter archivo = new BufferedWriter(new FileWriter(destino));
                archivo.write(volcado.toString());
                archivo.close();
            }
            catch (IOException ex) {
                mensajeError(traduccion.tErrorExportacion);
            }
        }
    }

    /** Exporta las listas al archivo que el usuario elija */
    public void exportarListas(){
        JFileChooser jfc = new JFileChooser();
        jfc.setSelectedFile(new File(traduccion.tListas.toLowerCase() + ".txt"));
        jfc.setDialogTitle(traduccion.tExportarListas);
        if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            File destino = jfc.getSelectedFile();
            String volcado = traduccion.tListas + ": " + visorListas.obtenerVolcado();

            try {
                BufferedWriter archivo = new BufferedWriter(new FileWriter(destino));
                archivo.write(volcado.toString());
                archivo.close();
            }
            catch (IOException ex) {
                mensajeError(traduccion.tErrorImportacion);
            }
        }
    }

    /** Copia al portapapeles la tarea o lista seleccionada actualmente */
    public void copiar(){
        if(tpMenu.getSelectedIndex() == TAREAS)
            visorTareas.copiar();
        else
            visorListas.copiar();
    }

    /** Nueva tarea o lista */
    public void nueva(){
        if(tpMenu.getSelectedIndex() == TAREAS)
            visorTareas.mostrarNuevaTarea();
        else
            visorListas.mostrarNuevaLista();
    }

    /** Eliminar una tarea o lista */
    public void eliminar(){
        if(tpMenu.getSelectedIndex() == TAREAS)
            visorTareas.eliminarTareaSeleccionada();
        else
            visorListas.eliminarListaSeleccionada();
    }

    /** Ver las listas */
    public void verListas() {
        tpMenu.setSelectedIndex(LISTAS);
    }

    /** Ver las tareas */
    public void verTareas() {
        tpMenu.setSelectedIndex(TAREAS);
    }

    /** Nuevo elemento */
    public void nuevoElemento() {
        visorListas.mostrarNuevoElemento();
    }

    /** Eliminar el elemento */
    public void eliminarElemento() {
        visorListas.eliminarElementoSeleccionado();
    }

    /** Elimina todas las tareas o las listas */
    public void eliminarTodo() {
        if(tpMenu.getSelectedIndex() == TAREAS)
            visorTareas.eliminarTodo();
        else
            visorListas.eliminarTodo();
    }

    /** Guarda las preferencias del usuario y termina la aplicación con éxito */
    public void terminarAplicacion(){
        guardarPreferencias();
        System.exit(0);
    }

    /** Muestra la ventanas de preferencias */
    public void mostrarVentanaPreferencias(){
        new VentanaPreferencias(propiedades, traduccion, this);
    }

    /** Actualiza el menú del programa */
    public void actualizaMenu() {
        if(tpMenu.getSelectedIndex() == LISTAS){
            barraMenus.remove(1);
            barraMenus.add(menus[LISTAS], 1);
        }
        else{
            barraMenus.remove(1);
            barraMenus.add(menus[TAREAS], 1);
        }
        barraMenus.repaint();    // Fuerza el repintado del menú
    }

    /** Muestra un mensaje de error en pantalla dependiente de esta ventana
     *  @param texto Texto del error */
    private void mensajeError(String texto){
        JOptionPane.showMessageDialog(this, texto, "Sparebrain", JOptionPane.ERROR_MESSAGE);
    }
}


/** Adaptador que controla el cambio de pestaña */
class DetectorPaneles implements ChangeListener{
    VentanaPrincipal padre = null;

    /** Constructor de la clase
     *  @param padre Ventana principal */
    DetectorPaneles(VentanaPrincipal padre) {
        this.padre = padre;
    }

    /** Detecta el cambio de panel
     *  @param ce Evento de cambio */
    public void stateChanged(ChangeEvent ce) {
        padre.actualizaMenu();
    }
}

/** Adaptador que controla el cierre de la ventana, evento que utiliza para
 *  guardar la posición y salir del programa */
class DetectorBotonCerrar extends WindowAdapter {
    VentanaPrincipal padre = null;

    /** Constructor de la clase
     *  @param padre Ventana principal */
    DetectorBotonCerrar(VentanaPrincipal padre){
        this.padre = padre;
    }

    /** Detecta el cierre de la ventana
     *  @param e Evento de cierre */
    public void windowClosing(WindowEvent e) {
        padre.terminarAplicacion();
    }
}

/** Oyente para las opciones del menú */
class DetectorMenu implements ActionListener{
    VentanaPrincipal padre = null;

    /** Constructor de la clase
     *  @param padre Ventana principal */
    public DetectorMenu(VentanaPrincipal padre){
        this.padre = padre;
    }

    /** Detecta las pulsaciones en el menú
     *  @param ae Evento de pulsación */
    public void actionPerformed(ActionEvent ae) {
        String accion = ae.getActionCommand();
        if(accion.equals("a0"))
            padre.mostrarVentanaPreferencias();
        else if(accion.equals("a1"))
            padre.exportarTareas();
        else if(accion.equals("a2"))
            padre.exportarListas();
        else if(accion.equals("a3"))
            padre.guardarCopiaTareas();
        else if(accion.equals("a4"))
            padre.cargarCopiaTareas();
        else if(accion.equals("a5"))
            padre.guardarCopiaListas();
        else if(accion.equals("a6"))
            padre.cargarCopiaListas();
        else if(accion.equals("a7"))
            padre.terminarAplicacion();
        else if(accion.equals("v0"))
            padre.verTareas();
        else if(accion.equals("v1"))
            padre.verListas();
        else if(accion.equals("t0") || accion.equals("l0"))
            padre.copiar();
        else if(accion.equals("t1") || accion.equals("l1"))
            padre.nueva();
        else if(accion.equals("t2") || accion.equals("l2"))
            padre.eliminar();
        else if(accion.equals("t3") || accion.equals("l5"))
            padre.eliminarTodo();
        else if(accion.equals("l3"))
            padre.nuevoElemento();
        else if(accion.equals("l4"))
            padre.eliminarElemento();
    }
}
