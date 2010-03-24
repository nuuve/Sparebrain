package interfaz;

import util.Traduccion;
import util.Fichero;
import excepciones.CaracterIlegal;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import listas.ControlListas;
import listas.ControlListasFicheros;

/** Clase VisorListas:
 *  Representa un visor de Listas, extendiendo JPanel
 *  @version 1.0
 *  @author Pau
 */
public class VisorListas extends JPanel {
    private JList lListaElementos;
    private JList lListaListas;
    private ControlListas controladorListas;
    private ArrayList <Integer> indices;
    private ArrayList <Integer> indicesElementos;
    private EditorListas editorListas;
    private JButton bListasEliminar;
    private JButton bCopiar;
    private JButton bElementosNuevo;
    private JButton bElementosEliminar;
    private JMenu menu;
    private final int COPIAR = 0, ELIMINAR_LISTA = 3, NUEVO_ELEMENTO = 4, ELIMINAR_ELEMENTO = 5;
    private final int NINGUNO = -1;
    private Traduccion traduccion;
    
    /** Constructor de la clase
     *  @param traduccion Objeto Traduccion con la traducción */
    public VisorListas(File listas, Traduccion traduccion, JMenu menu){
        this.menu = menu;
        this.traduccion = traduccion;
        
        // Crear el controlador y cargar las listas
        controladorListas = new ControlListasFicheros(listas);
        controladorListas.cargarListas();
        indices = new ArrayList <Integer> ();
        indicesElementos = new ArrayList <Integer> ();

        // Cargar iconos y colores
        ImageIcon nuevaLista = new ImageIcon(ClassLoader.getSystemResource("recursos/nuevaLista.png"));
        ImageIcon eliminarLista = new ImageIcon(ClassLoader.getSystemResource("recursos/eliminarLista.png"));
        ImageIcon nuevoElemento = new ImageIcon(ClassLoader.getSystemResource("recursos/nuevoElemento.png"));
        ImageIcon eliminarElemento = new ImageIcon(ClassLoader.getSystemResource("recursos/eliminarElemento.png"));
        ImageIcon copiar = new ImageIcon(ClassLoader.getSystemResource("recursos/copiar.png"));
        Color gris = Color.decode("#cccccc");

        // Panel de la izquierda
        lListaListas = new JList();
        lListaListas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lListaListas.addMouseListener(new DetectorListaListas(this));
        lListaListas.setBorder(BorderFactory.createEmptyBorder());
        JPanel pIzquierda = new JPanel(new BorderLayout());
        JScrollPane jspI = new JScrollPane(lListaListas);
        jspI.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, gris));
        pIzquierda.add(jspI, BorderLayout.CENTER);
        pIzquierda.setPreferredSize(new Dimension(150, 0));

        // Panel de la derecha
        lListaElementos = new JList();
        lListaElementos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lListaElementos.addMouseListener(new DetectorListaElementos(this));       
        JPanel pDerecha = new JPanel(new BorderLayout());
        JScrollPane jspD = new JScrollPane(lListaElementos);
        jspD.setBorder(BorderFactory.createEmptyBorder());
        pDerecha.add(jspD, BorderLayout.CENTER);

        DetectorBotonesListas detectorBotones = new DetectorBotonesListas(this);

        // Barra de herramientas izquierda
        JToolBar pBarraIzquierda = new JToolBar();
        pBarraIzquierda.setFloatable(false);

        JButton bListasNueva = new JButton(nuevaLista);
        bListasNueva.setActionCommand("nuevalista");
        bListasNueva.addActionListener(detectorBotones);
        bListasNueva.setToolTipText(traduccion.ayudaNuevaLista);
        pBarraIzquierda.add(bListasNueva);
        
        bListasEliminar = new JButton(eliminarLista);
        bListasEliminar.setActionCommand("eliminarlista");
        bListasEliminar.addActionListener(detectorBotones);
        bListasEliminar.setToolTipText(traduccion.ayudaEliminarLista);
        bListasEliminar.setEnabled(false);

        pBarraIzquierda.add(bListasEliminar);
        bCopiar = new JButton(copiar);
        bCopiar.setActionCommand("copiar");
        bCopiar.addActionListener(detectorBotones);
        bCopiar.setToolTipText(traduccion.tCopiar);
        pBarraIzquierda.add(bCopiar);

        // Barra de herramientas derecha
        JToolBar pBarraDerecha = new JToolBar();
        pBarraDerecha.setFloatable(false);

        bElementosNuevo = new JButton(nuevoElemento);
        bElementosNuevo.setActionCommand("nuevoelemento");
        bElementosNuevo.addActionListener(detectorBotones);
        bElementosNuevo.setToolTipText(traduccion.ayudaNuevoElemento);
        pBarraDerecha.add(bElementosNuevo);
        
        bElementosEliminar = new JButton(eliminarElemento);
        bElementosEliminar.setActionCommand("eliminarelemento");
        bElementosEliminar.addActionListener(detectorBotones);
        bElementosEliminar.setToolTipText(traduccion.ayudaEliminarElemento);
        pBarraDerecha.add(bElementosEliminar);

        // Añadir la barra al panel de listas
        pIzquierda.add(pBarraIzquierda, BorderLayout.NORTH);
        pDerecha.add(pBarraDerecha, BorderLayout.NORTH);

        // Actualizar la lista y añadir al panel los dos subpaneles
        setLayout(new BorderLayout());
        actualizarListaListas();
        add(pIzquierda, BorderLayout.WEST);
        add(pDerecha, BorderLayout.CENTER);

        // Controlar la eliminación por teclado
        KeyAdapter detectorEliminarElementos = new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if((e.getKeyChar() == KeyEvent.VK_DELETE) || (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
                    eliminarElementoSeleccionado();
                }
        }};
        KeyAdapter detectorEliminarListas = new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if((e.getKeyChar() == KeyEvent.VK_DELETE) || (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
                    eliminarListaSeleccionada();
                }
        }};
        lListaElementos.addKeyListener(detectorEliminarElementos);
        lListaListas.addKeyListener(detectorEliminarListas);
        
        // Crear el editor de listas
        editorListas = new EditorListas(this, traduccion);
        editorListas.setPreferredSize(new Dimension(100, 75));
        add(editorListas, BorderLayout.SOUTH);

        desactivarListas();
    }

    // ----------------------------------------------------- Métodos para listas
    
    /** Muestra un cuadro para añadir una nueva lista */
    public void mostrarNuevaLista(){
        editorListas.mostrarNuevaLista();
        revalidate();
    }

    /** Muestra un cuadro para editar la lista actual */
    public void mostrarEditarLista(){
        int identificadorLista = obtenerListaSeleccionada();
        if(identificadorLista != -1){
            String datos = controladorListas.obtenerLista(identificadorLista);
            editorListas.mostrarEditarLista(identificadorLista, datos);
            actualizarListaElementos(identificadorLista);
            revalidate();
        }
    }

    /** Crea una nueva lista con el nombre que recibe como argumento
     *  @param nombre Nombre de la lista
     *  @return identificador de la lista creada */
    public int nuevaLista(String nombre) {
        int nuevo = -1;
        try {
            nuevo = controladorListas.nuevaLista(nombre);
        } catch (CaracterIlegal ex) {
            mensajeError(traduccion.tCaracterIlegal);
        }
        controladorListas.guardarListas();
        actualizarListaListas();
        establecerListaSeleccionada(nuevo);
        return nuevo;
    }

    /** Modifica el nombre de una lista
     *  @param identificadorLista Identificador de la lista
     *  @param nombre Nuevo nombre de la lista */
    public void editarLista(int identificadorLista, String nombre) {
        try {
            controladorListas.editarLista(identificadorLista, nombre);
            controladorListas.guardarListas();
        } catch (CaracterIlegal ex) {
            mensajeError(traduccion.tCaracterIlegal);
        }
        actualizarListaListas();
    }

    /** Elimina la lista seleccionada */
    public void eliminarListaSeleccionada(){
        int identificadorLista = obtenerListaSeleccionada();
        if(identificadorLista != -1){
            int respuesta = JOptionPane.showConfirmDialog(
                    this,
                    traduccion.tConfirmarBorrarLista,
                    traduccion.tTituloConfirmarBorrarLista,
                    JOptionPane.YES_NO_OPTION
                    );
            if(JOptionPane.YES_OPTION == respuesta){
                controladorListas.eliminarLista(identificadorLista);
                controladorListas.guardarListas();
                actualizarListaListas();
                lListaElementos.setModel(new DefaultListModel());  // Borra la lista
                lListaElementos.revalidate();
                editorListas.setVisible(false);
                desactivarListas();
                revalidate();
            }
        }
    }

    // -------------------------------------------------- Métodos para elementos

    /** Muestra un cuadro para añadir un nuevo elemento */
    public void mostrarNuevoElemento() {
        if(hayUnaListaSeleccionada()){
            editorListas.mostrarNuevoElemento();
            revalidate();
        }
    }

    /** Muestra un cuadro para editar el elemento actual */
    public void mostrarEditarElemento(){
        int identificadorElemento = obtenerElementoSeleccionado();
        if(identificadorElemento != -1){
            int identificadorLista = obtenerListaSeleccionada();
            String datos = controladorListas.obtenerElemento(identificadorLista,
                    identificadorElemento);
            editorListas.mostrarEditarElemento(identificadorElemento, datos);
            revalidate();
            activarElementos();
        }
    }

    /** Crea un nuevo elemento con el nombre que recibe como argumento y lo
     *  añade a la lista seleccionada
     *  @param nombre Nombre del elemento
     *  @return identificador del elemento creado */
    public int nuevoElemento(String nombre) {
        int identificadorLista = obtenerListaSeleccionada();
        int nuevo = -1;
        try {
            nuevo = controladorListas.nuevoElemento(identificadorLista, nombre);
        } catch (CaracterIlegal ex) {
            mensajeError(traduccion.tCaracterIlegal);
        }
        controladorListas.guardarListas();
        actualizarListaElementos(identificadorLista);
        return nuevo;
    }

    /** Modifica el nombre de un elemento de la lista seleccionada
     *  @param identificadorElemento Identificador del elemento
     *  @param nombre Nuevo nombre del elemento */
    public void editarElemento(int identificadorElemento, String nombre) {
        int identificadorLista = obtenerListaSeleccionada();
        try {
            controladorListas.editarElemento(identificadorLista, identificadorElemento, nombre);
        } catch (CaracterIlegal ex) {
            mensajeError(traduccion.tCaracterIlegal);
        }
        controladorListas.guardarListas();
        actualizarListaElementos(identificadorLista);        
    }

    /** Elimina el elemento selecconado de la lista seleccionada */
    void eliminarElementoSeleccionado() {
        int identificadorLista = obtenerListaSeleccionada();
        int identificadorElemento = obtenerElementoSeleccionado();
        if((identificadorLista != -1) && (identificadorElemento != -1)){
            controladorListas.eliminarElemento(identificadorLista, identificadorElemento);
            controladorListas.guardarListas();
            actualizarListaElementos(identificadorLista);
            editorListas.setVisible(false);
            desactivarElementos();
        }
    }

    // ---------------------------- Actualización de la interfaz gráfica y otros

    /** Recarga las listas y vuelve a mostrarlas en la lista de listas */
    public void recargarListas(){
        controladorListas.cargarListas();
        actualizarListaListas();
    }

    /** Actualiza la lista de listas */
    public void actualizarListaListas() {
        ArrayList <String []> listas = null;
        listas = controladorListas.obtenerListas();

        lListaListas.removeAll();
        indices.clear();
        ArrayList<String> nombresTareas = new ArrayList<String>();
        for(String [] t : listas){
             indices.add(Integer.parseInt(t[0]));
             nombresTareas.add(t[1]);
        }
        lListaListas.setListData(nombresTareas.toArray());
    }

    /** Actualiza la lista de elementos para una lista seleccionada
     *  @param identificadorLista identificador de la lista seleccionada */
    public void actualizarListaElementos(int identificadorLista) {
        ArrayList <String []> elementos = null;
        elementos = controladorListas.obtenerElementos(identificadorLista);

        lListaElementos.removeAll();
        indicesElementos.clear();
        ArrayList <String>nombresElementos = new ArrayList<String>();
        for(String [] t : elementos){
             nombresElementos.add(t[0]);
             indicesElementos.add(Integer.parseInt(t[1]));
        }
        lListaElementos.setListData(nombresElementos.toArray());
    }

    /** Devuelve la lista que está seleccionada actualmente o un -1 si no hay
     *  ninguna lista seleccionada
     *  @return La lista seleccionada o -1 si no hay ninguna */
    public int obtenerListaSeleccionada(){
        if(lListaListas.getSelectedIndex() != NINGUNO)
            return indices.get(lListaListas.getSelectedIndex());
        else
            return NINGUNO;
    }

    /** Devuelve el elemento que está seleccionado actualmente o un -1 si no hay
     *  ningún elemento seleccionado
     *  @return La lista seleccionada o -1 si no hay ninguna */
    public int obtenerElementoSeleccionado(){
        if(lListaElementos.getSelectedIndex() != NINGUNO)
            return indicesElementos.get(lListaElementos.getSelectedIndex());
        else
            return NINGUNO;
    }

    /** Devuelve si hay una lista seleccionada
     *  @return Verdadero si hay una lista seleccionada */
    public boolean hayUnaListaSeleccionada(){
        if(lListaListas.getSelectedIndex() == NINGUNO) return false;
        else return true;
    }

    /** Devuelve si hay un elemento seleccionado
     *  @return Verdadero si hay un elemento seleccionado */
    public boolean hayUnElementoSeleccionado(){
        if(lListaElementos.getSelectedIndex() == NINGUNO) return false;
        else return true;
    }

    /** Establece la categoría que está seleccionada actualmente. Si el índice
     *  es -1 o es mayor que el número de elementos de la lista, no se selecciona
     *  ningún elemento
     *  @param indice Categoría que debe seleccionarse */
    public void establecerListaSeleccionada(int indice){
        if((indice != -1) && (indice < lListaListas.getModel().getSize())){
            lListaListas.setSelectedIndex(indices.get(indice));
            actualizarListaElementos(indices.get(indice));
            activarListas();
            lListaListas.ensureIndexIsVisible(indices.get(indice));
        }
    }

    /** Devuelve un volcado de las listas existentes en formato textual, para
     *  impresión o exportación.
     *  @return String con el resultado del volcado  */
    public String obtenerVolcado(){
        String sp = Fichero.SEPARADOR;
        ArrayList <String []> listas = controladorListas.obtenerListas();
        StringBuffer volcado = new StringBuffer(sp+sp);
        for(String[] l : listas){
            volcado.append(l[1]).append(":"+sp);
            ArrayList <String []> elementos = controladorListas.obtenerElementos(Integer.parseInt(l[0]));
            for(String [] e : elementos){
                volcado.append("     ").append(e[0]).append(sp);
            }
            volcado.append(sp);
        }
        return volcado.toString();
    }

    /** Devuelve un volcado de la lista cuyo identificador se reciba como
     *  argumento en formato textual
     *  @param identificador Identificador de la lista
     *  @return String con el resultado del volcado  */
    public String obtenerVolcado(int identificador){
        String sp = Fichero.SEPARADOR;
        String nombre = controladorListas.obtenerLista(identificador);
        StringBuffer volcado = new StringBuffer(nombre);
        volcado.append(":"+sp);
        ArrayList <String []> elementos = controladorListas.obtenerElementos(identificador);
        for(String [] e : elementos){
            volcado.append("     ").append(e[0]).append(sp);
        }
        return volcado.toString();
    }

    /** Copia la lista seleccionada al portapapeles */
    public void copiar() {
        int lista = obtenerListaSeleccionada();
        if(lista != -1){
            String volcado = obtenerVolcado(lista);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(volcado), null);
        }
    }

    /** Activar los botones y opciones de menú relativas a las listas */
    public void activarListas(){
        if(hayUnaListaSeleccionada()){
            bElementosNuevo.setEnabled(true);
            menu.getItem(NUEVO_ELEMENTO).setEnabled(true);
            bListasEliminar.setEnabled(true);
            menu.getItem(ELIMINAR_LISTA).setEnabled(true);
            bCopiar.setEnabled(true);
            menu.getItem(COPIAR).setEnabled(true);
            if(hayUnElementoSeleccionado()){
                bElementosEliminar.setEnabled(true);
                menu.getItem(ELIMINAR_ELEMENTO).setEnabled(true);
            }
        }
    }

    /** Desactivar los botones y opciones de menú relativas a las listas */
    public void desactivarListas(){
        bElementosNuevo.setEnabled(false);
        menu.getItem(NUEVO_ELEMENTO).setEnabled(false);
        bElementosEliminar.setEnabled(false);
        menu.getItem(ELIMINAR_ELEMENTO).setEnabled(false);
        bCopiar.setEnabled(false);
        menu.getItem(COPIAR).setEnabled(false);
        bListasEliminar.setEnabled(false);
        menu.getItem(ELIMINAR_LISTA).setEnabled(false);
    }

    /** Activar los botones y opciones de menú relativas a los elementos */
    public void activarElementos(){
        if(hayUnElementoSeleccionado()){
            bElementosEliminar.setEnabled(true);
            menu.getItem(ELIMINAR_ELEMENTO).setEnabled(true);
        }
    }

    /** Desactivar los botones y opciones de menú relativas a los elementos */
    public void desactivarElementos(){
        bElementosEliminar.setEnabled(false);
        menu.getItem(ELIMINAR_ELEMENTO).setEnabled(false);
    }

    /** Elimina todas las listas */
    void eliminarTodo() {
        int respuesta = JOptionPane.showConfirmDialog(this, traduccion.tConfirmarBorrarTodoListas,
                traduccion.tTituloConfirmarBorrarLista, JOptionPane.YES_NO_OPTION);
        if(JOptionPane.YES_OPTION == respuesta){
            controladorListas.eliminarTodo();
            controladorListas.guardarListas();
            actualizarListaListas();
            lListaElementos.setModel(new DefaultListModel());  // Borra la lista
            editorListas.setVisible(false);
            desactivarListas();
            desactivarElementos();
        }
    }

    /** Muestra un mensaje de error en pantalla dependiente de esta ventana
     *  @param texto Texto del error */
    public void mensajeError(String texto){
        JOptionPane.showMessageDialog(this, texto, "Sparebrain", JOptionPane.WARNING_MESSAGE);
    }

    /** Comprueba si el archivo dado es un archivo de listas válido
     *  @param origen Archivo que se comprobará
     *  @return Verdadero si el archivo es válido, falso en otro caso */
    public boolean comprobarArchivo(File origen) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(origen));
            if(b.readLine().equals(controladorListas.obtenerValidacion())) return true;
            else return false;
        } catch (Exception ex) {
            return false;
        }
    }
}

/** Oyente para los botones de las listas */
class DetectorBotonesListas implements ActionListener{
    private VisorListas padre;

    /** Constructor de la clase
     *  @param padre Visor principal */
    public DetectorBotonesListas(VisorListas padre){
        this.padre = padre;
    }

    /** Disparador del evento
     *  @param ae Evento */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equalsIgnoreCase("nuevalista"))
            padre.mostrarNuevaLista();
        else if(ae.getActionCommand().equalsIgnoreCase("eliminarlista"))
            padre.eliminarListaSeleccionada();
        else if(ae.getActionCommand().equalsIgnoreCase("nuevoelemento"))
            padre.mostrarNuevoElemento();
        else if(ae.getActionCommand().equalsIgnoreCase("eliminarelemento"))
            padre.eliminarElementoSeleccionado();
        else if(ae.getActionCommand().equalsIgnoreCase("copiar"))
            padre.copiar();
    }
}

/** Adaptador para detectar selección de elementos */
class DetectorListaElementos extends MouseAdapter{
    private VisorListas padre;

    /** Constructor de la clase
     *  @param padre Visor principal */
    public DetectorListaElementos(VisorListas padre){
        this.padre = padre;
    }

    /** Disparador del evento
     *  @param m Disparador */
    public void mousePressed(MouseEvent m) {
        padre.mostrarEditarElemento();
    }
}

/** Adaptador para detectar selección de listas */
class DetectorListaListas extends MouseAdapter{
    private VisorListas padre;

    /** Constructor de la clase
     *  @param padre Visor principal */
    public DetectorListaListas(VisorListas padre){
        this.padre = padre;
    }

    /** Disparador del evento
     *  @param m Disparador */
    public void mousePressed(MouseEvent m) {
        padre.mostrarEditarLista();
        padre.activarListas();
        padre.desactivarElementos();
    }
}
