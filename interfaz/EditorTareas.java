package interfaz;

import util.Traduccion;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.border.Border;
import util.Fechas;

/** Clase EditorTareas:
 *  Editor para las tareas
 *  @version 1.0
 *  @author Pau
 */
public class EditorTareas extends Editor {
    private Traduccion traduccion;      // Propiedades con la traducción
    private VisorTareas padre;          // Visor de tareas padre del editor
    private JTextField textoNombre;
    private JTextField textoDescripcion;
    private JComboBox selecUrgencia;
    private JTextField textoDia;
    private JTextField textoMes;
    private JTextField textoAno;
    private JCheckBox checkEstado;
    private JLabel etiqInfoEstado;
    private int editando;               // Indica el elemento que se está editando
    private int estado;                 // Estado de la tarea
    private final int PENDIENTE = 0, COMENZADA = 1, CONCLUIDA = 2;
    private final int DIA = 0, MES = 1, ANO = 2;
    
    /** Constructor de la clase
     *  @param padre Visor de tareas padre del editor
     *  @param traduccion Objeto Traduccion con la traduccion */
    public EditorTareas(VisorTareas padre, Traduccion traduccion){
        this.padre = padre;
        this.traduccion = traduccion;

        Color gris  = Color.decode("#cccccc");
        Color fondo = Color.decode("#ffffff");
        
        setVisible(false);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, gris));
        setBackground(fondo);

        Border vacio  = BorderFactory.createLineBorder(fondo);
        Border normal = BorderFactory.createLineBorder(Color.lightGray);

        SpringLayout disp = new SpringLayout();
        setLayout(disp);

        Dimension dim = new Dimension(200, 20);

        int col1 = 20;
        int col2 = 120;
        int base = 10;
        int fil = 10;
        int margen = 10;
        
        // Definición del campo Nombre
        textoNombre = new SJTextField(normal, vacio, dim, new Font("Lucida grande", Font.BOLD, 14));

        // Posición del campo Nombre
        SpringLayout.Constraints restTextoNombre = disp.getConstraints(textoNombre);
        restTextoNombre.setX(Spring.constant(col2));
        restTextoNombre.setY(Spring.constant(fil));

        fil = base + 20;

        // Definición del campo Descripción
        textoDescripcion = new SJTextField(normal, vacio, dim);
        JLabel etiqDescripcion = new SJLabel(traduccion.tEtiqDescripcion + ":", JLabel.TRAILING, textoDescripcion);

        // Posición del campo Descripción
        SpringLayout.Constraints restEtiqDescripcion = disp.getConstraints(etiqDescripcion);
        restEtiqDescripcion.setX(Spring.constant(col1));
        restEtiqDescripcion.setY(Spring.constant(fil + fil/10));
        restEtiqDescripcion.setWidth(Spring.constant(col2-col1-margen));

        SpringLayout.Constraints restTextoDescripcion = disp.getConstraints(textoDescripcion);
        restTextoDescripcion.setX(Spring.constant(col2));
        restTextoDescripcion.setY(Spring.constant(fil));

        fil = base + 40;
        
        // Definición del campo Prioridad
        selecUrgencia = new SJComboBox();
        selecUrgencia.setBackground(fondo);
        selecUrgencia.addItem(traduccion.tSelNinguna);
        selecUrgencia.addItem(traduccion.tSelPoco);
        selecUrgencia.addItem(traduccion.tSelImportante);
        selecUrgencia.addItem(traduccion.tSelMuy);
        JLabel etiqUrgencia = new SJLabel(traduccion.tEtiqUrgencia + ":", JLabel.TRAILING, selecUrgencia);

        // Posición del campo Prioridad
        SpringLayout.Constraints restEtiqUrgencia = disp.getConstraints(etiqUrgencia);
        restEtiqUrgencia.setX(Spring.constant(col1));
        restEtiqUrgencia.setY(Spring.constant(fil + fil/10));
        restEtiqUrgencia.setWidth(Spring.constant(col2-col1-margen));
        SpringLayout.Constraints restSelecUrgencia = disp.getConstraints(selecUrgencia);
        restSelecUrgencia.setX(Spring.constant(col2));
        restSelecUrgencia.setY(Spring.constant(fil + 1));

        fil = base + 60;

        // Definición del campo Fecha
        textoDia = new SJTextField(2, normal, vacio);
        textoMes = new SJTextField(2, normal, vacio);
        textoAno = new SJTextField(4, normal, vacio);
        textoDia.setInputVerifier(new Verificador(DIA));
        textoMes.setInputVerifier(new Verificador(MES));
        textoAno.setInputVerifier(new Verificador(ANO));
        JPanel fecha = new JPanel(new FlowLayout());
        fecha.setBackground(fondo);
        JLabel etiqFecha = new SJLabel(traduccion.tEtiqFecha + ":", SwingConstants.RIGHT, fecha);

        // Si la localización es inglesa, modificar la disposición de la fecha
        if(traduccion.obtenerIdioma().equals("en")){
            fecha.add(textoMes);
            fecha.add(new JLabel("/"));
            fecha.add(textoDia);
            fecha.add(new JLabel("/"));
            fecha.add(textoAno);
        }
        else{
            fecha.add(textoDia);
            fecha.add(new JLabel("/"));
            fecha.add(textoMes);
            fecha.add(new JLabel("/"));
            fecha.add(textoAno);
        }        

        // Posición del campo Fecha
        SpringLayout.Constraints restEtiqFecha = disp.getConstraints(etiqFecha);
        restEtiqFecha.setX(Spring.constant(col1));
        restEtiqFecha.setY(Spring.constant(fil + fil/10));
        restEtiqFecha.setWidth(Spring.constant(col2-col1-margen));
        SpringLayout.Constraints restTextoFecha = disp.getConstraints(fecha);
        restTextoFecha.setX(Spring.constant(col2 - 4));
        restTextoFecha.setY(Spring.constant(fil + 1));

        fil = base + 80;

        // Definición del campo Estado
        checkEstado = new JCheckBox();
        checkEstado.addActionListener(new DetectorEstado(this));
        checkEstado.setFont(new Font("Lucida grande", Font.PLAIN, 11));
        checkEstado.setBackground(fondo);
        etiqInfoEstado = new SJLabel(traduccion.tPendiente, SwingConstants.RIGHT, new Font("Lucida grande", Font.PLAIN, 11));
        JLabel etiqEstado = new SJLabel(traduccion.tEtiqEstado + ":", SwingConstants.RIGHT, checkEstado);
        
        // Posición del campo Estado
        SpringLayout.Constraints restEtiqEstado = disp.getConstraints(etiqEstado);
        restEtiqEstado.setX(Spring.constant(col1));
        restEtiqEstado.setY(Spring.constant(fil + fil/10));
        restEtiqEstado.setWidth(Spring.constant(col2 - col1 - margen));
        SpringLayout.Constraints restEtiqInfoEstado = disp.getConstraints(etiqInfoEstado);
        restEtiqInfoEstado.setX(Spring.constant(col2 + 3));
        restEtiqInfoEstado.setY(Spring.constant(fil + fil/10));

        fil = base + 100;

        SpringLayout.Constraints restCheckEstado = disp.getConstraints(checkEstado);
        restCheckEstado.setX(Spring.constant(col2));
        restCheckEstado.setY(Spring.constant(fil + 4));

        fil = base + 120;

        // Botón Aceptar
        JButton botonAceptar = new JButton(traduccion.tBotonGuardar);
        botonAceptar.setActionCommand("guardar");
        botonAceptar.setBackground(fondo);

        // Botón Cancelar
        JButton botonCancelar = new JButton(traduccion.tBotonCancelar);
        botonCancelar.setActionCommand("cancelar");
        botonCancelar.setBackground(fondo);

        // Botonera
        JPanel botonera = new JPanel(new FlowLayout());
        botonera.setBackground(fondo);
        botonera.add(botonAceptar);
        botonera.add(botonCancelar);
        SpringLayout.Constraints restBotonera = disp.getConstraints(botonera);
        restBotonera.setX(Spring.constant(col2 - 4));
        restBotonera.setY(Spring.constant(fil + 2));

        // Añadir manejador a los botones
        DetectorBotones dbn = new DetectorBotones(this);
        botonAceptar.addActionListener(dbn);
        botonCancelar.addActionListener(dbn);       

        // Controlar pulsación del enter
        KeyAdapter detectorEnter = new KeyAdapter(){
            public void keyPressed(KeyEvent e){
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    if(guardar())
                        setVisible(false);
                }
        }};    
        botonAceptar.addKeyListener(detectorEnter);
        textoNombre.addKeyListener(detectorEnter);
        textoDescripcion.addKeyListener(detectorEnter);
        selecUrgencia.addKeyListener(detectorEnter);
        textoDia.addKeyListener(detectorEnter);
        textoMes.addKeyListener(detectorEnter);
        textoAno.addKeyListener(detectorEnter);

        // Añadir todos los controles al editor
        add(textoNombre);
        add(etiqDescripcion);
        add(textoDescripcion);
        add(etiqUrgencia);
        add(selecUrgencia);
        add(etiqFecha);
        add(fecha);
        add(etiqEstado);
        add(etiqInfoEstado);
        add(checkEstado);
        add(botonera);
    }

    /** Guarda la tarea que se esté editando
     *  @return Verdadero si la tarea se guarda con éxito */
    public boolean guardar(){
        Verificador d = new Verificador(DIA);
        Verificador m = new Verificador(MES);
        Verificador a = new Verificador(ANO);

        if(!(d.verify(textoDia) && m.verify(textoMes) && a.verify(textoAno))){
            return false;
        }
        else{
            String nombre = textoNombre.getText();
            if(nombre.equals("")) nombre = traduccion.tSinNombre;
            String descripcion = textoDescripcion.getText();
            int urgencia = selecUrgencia.getSelectedIndex();
            GregorianCalendar gc = new GregorianCalendar(
                    Integer.parseInt(textoAno.getText()),
                    Integer.parseInt(textoMes.getText()) - 1,
                    Integer.parseInt(textoDia.getText())
                    );
            long fecha = gc.getTime().getTime();
            Date momento = new Date();
            if(editando == NINGUNO){
                // Insertando una nueva tarea
                int nueva = padre.nuevaTarea(nombre, descripcion, urgencia, fecha);
                // Ver si está iniciada
                if(checkEstado.isSelected())
                    padre.comenzarTarea(nueva, momento.getTime());
            }
            else{
                // Editando una tarea
                padre.editarTarea(editando, nombre, descripcion, urgencia, fecha);
                switch(estado){
                    case COMENZADA: // Tarea comenzada
                        // Distinguir si es una que se acaba de empezar o una que se recomienza
                        padre.comenzarTarea(editando, momento.getTime());
                        break;
                    case CONCLUIDA: // Tarea concluida
                        padre.concluirTarea(editando, momento.getTime());
                        break;
                }
            }
            return true;
        }
    }

    // -------------------------------------------------------- Editor de tareas

    /** Prepara el editor para insertar una nueva tarea */
    public void mostrarNuevaTarea(){
        setVisible(true);
        estado = PENDIENTE;
        editando = NINGUNO;
        textoNombre.requestFocus();
        textoNombre.setText(traduccion.tNuevaTarea);
        textoNombre.selectAll();
        textoDescripcion.setText("");
        checkEstado.setText(traduccion.tComenzada);
        checkEstado.setSelected(false);
        selecUrgencia.setSelectedIndex(0);
        etiqInfoEstado.setText(traduccion.tPendiente);
        GregorianCalendar fecha = new GregorianCalendar();
        String ano = Fechas.procesar(fecha.get(Calendar.YEAR));
        String mes = Fechas.procesar(fecha.get(Calendar.MONTH) + 1);
        String dia = Fechas.procesar(fecha.get(Calendar.DAY_OF_MONTH));
        textoAno.setText(ano + "");
        textoMes.setText(mes + "");
        textoDia.setText(dia + "");
    }

    /** Prepara el editor para editar una tarea
     *  @param identificador Identificador de la tarea que se edita
     *  @param datos Datos de la tarea que se edita */
    public void mostrarEditarTarea(int identificador, String [] datos){
        editando = identificador;
        textoNombre.setText(datos[0]);
        textoDescripcion.setText(datos[1]);
        selecUrgencia.setSelectedIndex(Integer.parseInt(datos[2]));
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTime(new Date(Long.parseLong(datos[3])));

        String ano = Fechas.procesar(fecha.get(Calendar.YEAR));
        String mes = Fechas.procesar(fecha.get(Calendar.MONTH) + 1);
        String dia = Fechas.procesar(fecha.get(Calendar.DAY_OF_MONTH));
        textoAno.setText(ano);
        textoMes.setText(mes);
        textoDia.setText(dia);

        Date hInicio = new Date(Long.parseLong(datos[4]));
        Date hFin = new Date(Long.parseLong(datos[5]));
        if(hInicio.getTime() == 0){
            // La tarea no ha comenzado
            checkEstado.setSelected(false);
            checkEstado.setText(traduccion.tComenzada);
            checkEstado.setActionCommand("comenzar");
            etiqInfoEstado.setText(traduccion.tPendiente);
            estado = PENDIENTE;
        }
        else{
            Fechas fechas = new Fechas(traduccion);
            // La tarea ha comenzado
            GregorianCalendar horaInicio = new GregorianCalendar();
            horaInicio.setTime(hInicio);
            if(hFin.getTime() == 0){
                // La tarea ha comenzado pero no está concluida. Mostrar la opción
                // para concluirla
                etiqInfoEstado.setText(traduccion.tDesdeEl + " " +  fechas.parsearFechaHora(horaInicio));
                checkEstado.setText(traduccion.tConcluida);
                checkEstado.setSelected(false);
                checkEstado.setActionCommand("concluir");
                estado = COMENZADA;
            }
            else{
                // La tarea está concluida: seleccionar el botón. Si se desmarca, la
                // tarea debería continuar
                GregorianCalendar horaFin = new GregorianCalendar();
                horaFin.setTime(hFin);
                etiqInfoEstado.setText(traduccion.tDesdeEl + " " + fechas.parsearFechaHora(horaInicio) +
                        " " + traduccion.tHastaEl + " " + fechas.parsearFechaHora(horaFin));
                checkEstado.setText(traduccion.tConcluida);
                checkEstado.setSelected(true);
                checkEstado.setActionCommand("proseguir");
                estado = CONCLUIDA;
            }
        }
        setVisible(true);
    }

    /** Establece un estado
     *  @param estado Nuevo estado */
    public void setEstado(int estado){
        this.estado = estado;
    }
}

/** Clase que detecta cambios de estado y los aplica en el momento de guardar */
class DetectorEstado implements ActionListener{
    private EditorTareas padre;
    private final int COMENZADA = 1, CONCLUIDA = 2;

    /** Constructor de la clase
     *  @param padre Editor de tareas padre */
    public DetectorEstado(EditorTareas padre){
        this.padre = padre;
    }

    /** Disparador del evento
     *  @param ae Evento */
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equalsIgnoreCase("comenzar")){
            padre.setEstado(COMENZADA);
        }
        else if(ae.getActionCommand().equalsIgnoreCase("concluir")){
            padre.setEstado(CONCLUIDA);
        }
        else if(ae.getActionCommand().equalsIgnoreCase("proseguir")){
            padre.setEstado(COMENZADA);
        }
    }
}

/** Clase para la verificación */
class Verificador extends InputVerifier{
    private int tipo;
    private final int DIA = 0, MES = 1, ANO = 2, TEXTO = 3;

    /** Construye el verificador teniendo en cuenta el tipo */
    public Verificador(int tipo){
        this.tipo = tipo;
    }

    /** Rellamada de la verificación
     *  @param jc Componente verificado
     *  @return Verdadero si el texto del componente es válido */
    public boolean verify(JComponent jc) {
        String cadena = ((JTextField) jc).getText();
        try{
            switch(tipo){
                case DIA:
                    int d = Integer.parseInt(cadena);
                    if((d > 0) && (d <= 31)) return true;
                    break;
                case MES:
                    int m = Integer.parseInt(cadena);
                    if((m > 0) && (m <= 12)) return true;
                    break;
                case ANO:
                    int a = Integer.parseInt(cadena);
                    if(a > 0) return true;
                    break;
                case TEXTO:
                    if(!cadena.contains(";"))
                    return true;
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
}