package interfaz;

import util.Traduccion;
import excepciones.CaracterIlegal;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import tareas.ControlTareas;
import tareas.ControlTareasFicheros;
import util.Fechas;
import util.Fichero;

/** Clase VisorTareas:
 *  Representa un visor de tareas, extendiendo JPanel
 *  @version 1.0
 *  @author Pau
 */
public class VisorTareas extends JPanel {
    private JList lListaTareas;
    private JList lListaDias;
    private ControlTareas controladorTareas;
    private ArrayList <Integer> indices;
    private EditorTareas editorTareas;
    private JButton bTareasEliminar;
    private JButton bCopiar;
    private JMenu menu;
    private final int COPIAR = 0, ELIMINAR = 3;
    private final int NINGUNO = -1;
    private Traduccion traduccion;
    
    /** Constructor de la clase
     *  @param traduccion Objeto Traduccion con la traducción */
    public VisorTareas(File tareas, Traduccion traduccion, JMenu menu){
        this.traduccion = traduccion;
        this.menu = menu;
        
        // Crear el controlador y cargar las tareas
        controladorTareas = new ControlTareasFicheros(tareas);
        controladorTareas.cargarTareas();
        indices = new ArrayList <Integer> ();

        // Cargar iconos y colores
        ImageIcon nuevaTarea    = new ImageIcon(ClassLoader.getSystemResource("recursos/nuevaTarea.png"));
        ImageIcon eliminarTarea = new ImageIcon(ClassLoader.getSystemResource("recursos/eliminarTarea.png"));
        ImageIcon copiar        = new ImageIcon(ClassLoader.getSystemResource("recursos/copiar.png"));
        Color gris = Color.decode("#cccccc");

        // Panel de la izquierda
        String [] listaDias = { traduccion.tTodas, traduccion.tHoy, traduccion.tManana, traduccion.tPendientes, traduccion.tComenzadas};
        lListaDias = new JList(listaDias);
        lListaDias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lListaDias.addMouseListener(new DetectorListaDias(this));
        lListaDias.setSelectedIndex(0);
        lListaDias.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, gris));
        JScrollPane jspI = new JScrollPane(lListaDias);
        jspI.setBorder(BorderFactory.createEmptyBorder());
        JPanel pIzquierda = new JPanel(new BorderLayout());
        pIzquierda.add(jspI, BorderLayout.CENTER);
        pIzquierda.setPreferredSize(new Dimension(150, 0));
        
        // Panel de la derecha
        lListaTareas = new JList();
        lListaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lListaTareas.addMouseListener(new DetectorListaTareas(this));
        JScrollPane jspD = new JScrollPane(lListaTareas);
        jspD.setBorder(BorderFactory.createEmptyBorder());
        JPanel pDerecha = new JPanel(new BorderLayout());
        pDerecha.add(jspD, BorderLayout.CENTER);

        DetectorBotonesTareas detectorBotones = new DetectorBotonesTareas(this);

        // Barra de herramientas
        JToolBar pBarra = new JToolBar();
        pBarra.setFloatable(false);

        JButton bTareasNueva = new JButton(nuevaTarea);
        bTareasNueva.setActionCommand("nueva");
        bTareasNueva.addActionListener(detectorBotones);
        bTareasNueva.setToolTipText(traduccion.ayudaNuevaTarea);
        pBarra.add(bTareasNueva);

        bTareasEliminar = new JButton(eliminarTarea);
        bTareasEliminar.setActionCommand("eliminar");
        bTareasEliminar.addActionListener(detectorBotones);
        bTareasEliminar.setToolTipText(traduccion.ayudaEliminarTarea);
        pBarra.add(bTareasEliminar);
        
        bCopiar = new JButton(copiar);
        bCopiar.setActionCommand("copiar");
        bCopiar.addActionListener(detectorBotones);
        bCopiar.setToolTipText(traduccion.tCopiar);
        pBarra.add(bCopiar);

        //  Actualizar la lista y añadir los dos paneles y la barra al panel
        actualizarListaTareas();
        setLayout(new BorderLayout());
        add(pBarra, BorderLayout.NORTH);
        add(pIzquierda, BorderLayout.WEST);
        add(pDerecha);

        desactivar();

        // Controlar la eliminación por teclado
        KeyAdapter detectorEliminarTareas = new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if((e.getKeyChar() == KeyEvent.VK_DELETE) || (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)){
                    eliminarTareaSeleccionada();
                }
        }};
        lListaTareas.addKeyListener(detectorEliminarTareas);
        
        // Crear el editor de tareas
        editorTareas = new EditorTareas(this, traduccion);
        editorTareas.setPreferredSize(new Dimension(100, 175));
        add(editorTareas, BorderLayout.SOUTH);
    }

    /** Activar los botones de la barra de herramientas */
    public void activar(){
        if(hayUnaTareaSeleccionada()){
            bCopiar.setEnabled(true);
            bTareasEliminar.setEnabled(true);
            menu.getItem(COPIAR).setEnabled(true);
            menu.getItem(ELIMINAR).setEnabled(true);
        }
    }

    /** Desactivar los botones de la barra de herramientas */
    public void desactivar(){
        bCopiar.setEnabled(false);
        bTareasEliminar.setEnabled(false);
        menu.getItem(COPIAR).setEnabled(false);
        menu.getItem(ELIMINAR).setEnabled(false);
    }

    /** Muestra un cuadro para añadir una nueva tarea */
    public void mostrarNuevaTarea(){
        editorTareas.mostrarNuevaTarea();
        revalidate();
    }

    /** Muestra un cuadro para editar la tarea actual */
    public void mostrarEditarTarea(){
        int identificador = obtenerTareaSeleccionada();
        if(identificador != NINGUNO){
            String [] datos = controladorTareas.obtenerTarea(identificador);
            editorTareas.mostrarEditarTarea(identificador, datos);
            revalidate();
        }
    }

    /** Ordena al controlador de las tareas la creación de una nueva tarea
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @return Identificador de la nueva tarea creada */
    public int nuevaTarea(String nombre, String descripcion, int urgencia, long fecha) {
        int nuevo = -1;
        try{
            nuevo = controladorTareas.nuevaTarea(nombre, descripcion, urgencia, fecha);
            controladorTareas.guardarTareas();
            actualizarListaTareas(); 
        } catch(CaracterIlegal c){
            mensajeError(traduccion.tCaracterIlegal);
        }
        return nuevo;
    }

    /** Ordena al controlador de las tareas editar la tarea cuyo identificador
     *  se reciba como argumento.
     *  @param identificador Identificador de la tarea
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha){
        try{
            controladorTareas.editarTarea(identificador, nombre, descripcion, urgencia, fecha);
            controladorTareas.guardarTareas();
            actualizarListaTareas();
        } catch(CaracterIlegal c){
            mensajeError(traduccion.tCaracterIlegal);
        }
    }

    /** Ordena al controlador de las tareas editar la tarea cuyo identificador
     *  se reciba como argumento.
     *  @param identificador Identificador de la tarea
     *  @param nombre Nombre de la tarea
     *  @param descripcion Descripción de la tarea
     *  @param urgencia Urgencia de la tarea
     *  @param fecha Fecha de la tarea
     *  @param horaInicio Hora de inicio de la tarea
     *  @param horaFin Hora de finalización de la tarea */
    public void editarTarea(int identificador, String nombre, String descripcion, int urgencia, long fecha, long horaInicio, long horaFin){
        try {
            controladorTareas.editarTarea(identificador, nombre, descripcion, urgencia, fecha, horaInicio, horaFin);
            controladorTareas.guardarTareas();
            actualizarListaTareas();
        } catch (CaracterIlegal c) {
            mensajeError(traduccion.tCaracterIlegal);
        }
    }

    /** Marca la tarea cuyo identificador se reciba como comenzada con la hora
     *  de inicio que se le pase como argumento.
     *  @param identificador Identificador de la tarea
     *  @param horaInicio Hora de inicio de la tarea */
    public void comenzarTarea(int identificador, long horaInicio){
        controladorTareas.comenzarTarea(identificador, horaInicio);
        controladorTareas.guardarTareas();
        actualizarListaTareas();
    }

    /** Marca la tarea cuyo identificador se reciba como finalizada con la hora
     *  de finalización que se le pase como argumento.
     *  @param identificador Identificador de la tarea
     *  @param horaFin Hora de finalización de la tarea */
    public void concluirTarea(int identificador, long horaFin){
        controladorTareas.concluirTarea(identificador, horaFin);
        controladorTareas.guardarTareas();
        actualizarListaTareas();
    }

    /** Elimina la tarea seleccionada */
    public void eliminarTareaSeleccionada(){
        int objetivo = obtenerTareaSeleccionada();
        if(objetivo != NINGUNO){
            int respuesta = JOptionPane.showConfirmDialog(this, traduccion.tConfirmarBorrarTarea,
                    traduccion.tTituloConfirmarBorrarTarea, JOptionPane.YES_NO_OPTION);
            if(JOptionPane.YES_OPTION == respuesta){
                controladorTareas.eliminarTarea(objetivo);
                controladorTareas.guardarTareas();
                actualizarListaTareas();
                editorTareas.setVisible(false);
                desactivar();
            }
        }
    }

    /** Recarga las tareas y vuelve a mostrarlas en la lista de tareas */
    public void recargarTareas(){
        controladorTareas.cargarTareas();
        actualizarListaTareas();
    }

    /** Actualiza la lista de tareas */
    public void actualizarListaTareas(){
        ArrayList <String []> tareas = null;

        switch(lListaDias.getSelectedIndex()){
            case 1: // Para hoy
                GregorianCalendar cHoy = new GregorianCalendar();
                GregorianCalendar fechaHoy = new GregorianCalendar(cHoy.get(Calendar.YEAR), cHoy.get(Calendar.MONTH), cHoy.get(Calendar.DAY_OF_MONTH));
                tareas = controladorTareas.obtenerTareasPorFecha(fechaHoy.getTime().getTime());
                break;
            case 2: // Para mañana
                GregorianCalendar cManana = new GregorianCalendar();
                GregorianCalendar fechaManana = new GregorianCalendar(cManana.get(Calendar.YEAR), cManana.get(Calendar.MONTH), cManana.get(Calendar.DAY_OF_MONTH)+1);
                tareas = controladorTareas.obtenerTareasPorFecha(fechaManana.getTime().getTime());
                break;
            case 3: // Pendientes
                tareas = controladorTareas.obtenerTareasPendientes();
                break;
            case 4: // Comenzadas
                tareas = controladorTareas.obtenerTareasComenzadas();
                break;
            default:
                tareas = controladorTareas.obtenerTareas();
        }
        lListaTareas.removeAll();
        indices.clear();     
        ArrayList<String> nombresTareas = new ArrayList<String>();
        for(String [] t : tareas){
             indices.add(Integer.parseInt(t[0]));
             nombresTareas.add(t[1]);
        }
        lListaTareas.setListData(nombresTareas.toArray());
    }

    /** Devuelve la tarea que está seleccionada actualmente o un -1 si no hay
     *  ninguna tarea seleccionada
     *  @return La tarea seleccionada o -1 si no hay ninguna */
    public int obtenerTareaSeleccionada(){
        if(lListaTareas.getSelectedIndex() != NINGUNO)
            return indices.get(lListaTareas.getSelectedIndex());
        else
            return NINGUNO;
    }

    /** Devuelve la categoría que está seleccionada actualmente o un -1 si no hay
     *  ninguna seleccionada
     *  @return La categoría seleccionada o -1 si no hay ninguna */
    public int obtenerCategoriaSeleccionada(){
        return lListaDias.getSelectedIndex();
    }

    /** Establece la categoría que está seleccionada actualmente 
     *  @param indice Categoría que debe seleccionarse */
    public void establecerCategoriaSeleccionada(int indice){
        if(indice == -1) indice = 0;
        lListaDias.setSelectedIndex(indice);
        actualizarListaTareas();
        lListaDias.ensureIndexIsVisible(indice);
    }

    /** Devuelve un volcado de las tareas existentes en formato textual, para
     *  impresión o exportación.
     *  @return String con el resultado del volcado  */
    public String obtenerVolcado(){
        String sp = Fichero.SEPARADOR;

        String importancia [] = {traduccion.tSelNinguna, traduccion.tSelPoco,
            traduccion.tSelImportante, traduccion.tSelMuy};
        Fechas fechas = new Fechas(traduccion);

        ArrayList <String []> tareas = controladorTareas.obtenerTareas();
        StringBuffer volcado = new StringBuffer(sp+sp);
        for(String [] t : tareas){
            volcado.append(t[1]);
            GregorianCalendar fecha = new GregorianCalendar();
            fecha.setTime(new Date(Long.parseLong(t[4])));

            volcado.append(" (").append(fechas.parsearFecha(fecha)).append(")"+sp);
            if(!t[2].equals(""))
                volcado.append(t[2]).append(sp);
            volcado.append(importancia[Integer.parseInt(t[3])]).append(sp);
            Date hInicio = new Date(Long.parseLong(t[5]));
            Date hFin = new Date(Long.parseLong(t[6]));

            if(hInicio.getTime() == 0){
                // La tarea no ha comenzado
                volcado.append(traduccion.tPendiente);
            }
            else{
                GregorianCalendar horaInicio = new GregorianCalendar();
                horaInicio.setTime(hInicio);
                if(hFin.getTime() == 0){
                    // La tarea ha comenzado
                    volcado.append(traduccion.tComenzada + ". " + traduccion.tDesdeEl + " " +
                            fechas.parsearFechaHora(horaInicio));
                }
                else{
                    // La tarea está concluida:
                    GregorianCalendar horaFin = new GregorianCalendar();
                    horaFin.setTime(hFin);
                    volcado.append(traduccion.tConcluida + ". " + traduccion.tDesdeEl + " " +
                            fechas.parsearFechaHora(horaInicio) + " " +
                            traduccion.tHastaEl + " " + fechas.parsearFechaHora(horaFin));
                }
            }
            volcado.append(sp+sp);
        }
        return volcado.toString();
    }

    /** Devuelve un volcado de la cuyo identificador se recibe como parámetro
     *  en formato textual, para impresión o exportación.
     *  @param identificadorTarea Identificador de la tarea
     *  @return String con el resultado del volcado  */
    public String obtenerVolcado(int identificadorTarea){
        String sp = Fichero.SEPARADOR;

        String importancia [] = {traduccion.tSelNinguna, traduccion.tSelPoco,
            traduccion.tSelImportante, traduccion.tSelMuy};
        Fechas fechas = new Fechas(traduccion);

        StringBuffer volcado = new StringBuffer("");
        String t[] = controladorTareas.obtenerTarea(identificadorTarea);

        volcado.append(t[0]);
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTime(new Date(Long.parseLong(t[3])));

        volcado.append(" (").append(fechas.parsearFecha(fecha)).append(")"+sp);
        if(!t[1].equals(""))
            volcado.append(t[1]).append(sp);
        volcado.append(importancia[Integer.parseInt(t[2])]).append(sp);
        Date hInicio = new Date(Long.parseLong(t[4]));
        Date hFin = new Date(Long.parseLong(t[5]));

        if(hInicio.getTime() == 0){
            volcado.append(traduccion.tPendiente);
        }
        else{
            GregorianCalendar horaInicio = new GregorianCalendar();
            horaInicio.setTime(hInicio);
            if(hFin.getTime() == 0){
                volcado.append(traduccion.tComenzada + ". " + traduccion.tDesdeEl + " " +
                        fechas.parsearFechaHora(horaInicio));
            }
            else{
                GregorianCalendar horaFin = new GregorianCalendar();
                horaFin.setTime(hFin);
                volcado.append(traduccion.tConcluida + ". " + traduccion.tDesdeEl + " " +
                        fechas.parsearFechaHora(horaInicio) + " " +
                        traduccion.tHastaEl + " " + fechas.parsearFechaHora(horaFin));
            }
        }
        return volcado.toString();
    }

    /** Copiar al portapapeles la tarea seleccionada */
    public void copiar(){
        int tarea = obtenerTareaSeleccionada();
        if(tarea != NINGUNO){
            String volcado = obtenerVolcado(tarea);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(volcado), null);
        }
    }

    /** Devuelve si hay una tarea seleccionada
     *  @return Verdadero si hay una tarea seleccionada */
    public boolean hayUnaTareaSeleccionada(){
        if(lListaTareas.getSelectedIndex() == -1) return false;
        else return true;
    }
 
    /** Elimina todas las tareas */
    void eliminarTodo() {
        int respuesta = JOptionPane.showConfirmDialog(this, 
                traduccion.tConfirmarBorrarTodoTareas,
                traduccion.tTituloConfirmarBorrarTarea, JOptionPane.YES_NO_OPTION);
        if(JOptionPane.YES_OPTION == respuesta){
            controladorTareas.eliminarTodo();
            controladorTareas.guardarTareas();
            actualizarListaTareas();
            editorTareas.setVisible(false);
            desactivar();
        }
    }

    /** Muestra un mensaje de error en pantalla dependiente de esta ventana
     *  @param texto Texto del error */
    public void mensajeError(String texto){
        JOptionPane.showMessageDialog(this, texto, "Sparebrain", JOptionPane.WARNING_MESSAGE);
    }

    /** Comprueba si el archivo dado es un archivo de tareas válido
     *  @param origen Archivo que se comprobará
     *  @return Verdadero si el archivo es válido, falso en otro caso */
    boolean comprobarArchivo(File origen) {
        try {
            BufferedReader b = new BufferedReader(new FileReader(origen));
            if(b.readLine().equals(controladorTareas.obtenerValidacion())) return true;
            else return false;
        } catch (Exception ex) {
            return false;
        }
    }
}

/** Oyente para los botones de las tareas */
class DetectorBotonesTareas implements ActionListener{
    private VisorTareas padre;

    /** Constructor de la clase
     *  @param padre Visor principal */
    public DetectorBotonesTareas(VisorTareas padre){
        this.padre = padre;
    }
    
    /** Disparador del evento
     *  @param ae Evento */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equalsIgnoreCase("nueva"))
            padre.mostrarNuevaTarea();
        else if(ae.getActionCommand().equalsIgnoreCase("eliminar"))
            padre.eliminarTareaSeleccionada();
        else if(ae.getActionCommand().equalsIgnoreCase("copiar"))
            padre.copiar();
    }   
}

/** Adaptador para detectar selección de las tareas */
class DetectorListaTareas extends MouseAdapter{
    private VisorTareas padre;
    
    /** Constructor de la clase
     *  @param padre Visor principal */
    public DetectorListaTareas(VisorTareas padre){
        this.padre = padre;
    }

    /** Disparador del evento
     *  @param m Disparador */
    public void mousePressed(MouseEvent m) {
        padre.activar();
        padre.mostrarEditarTarea();
    }
}

/** Adaptador para detectar selección de las cateogorías */
class DetectorListaDias extends MouseAdapter{
    private VisorTareas padre;

    /** Constructor de la clase
     *  @param padre Visor principal */
    public DetectorListaDias(VisorTareas padre){
        this.padre = padre;
    }

    /** Disparador del evento
     *  @param m Disparador */
    public void mousePressed(MouseEvent m) {
        padre.desactivar();
        padre.actualizarListaTareas();
    }
}
