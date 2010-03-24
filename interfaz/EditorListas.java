package interfaz;

import util.Traduccion;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.Border;

/** Clase EditorListas:
 *  Editor para las listas
 *  @version 1.0
 *  @author Pau
 */
public class EditorListas extends Editor {
    private VisorListas padre;      // Visor de listas padre del editor
    private JTextField textoNombre; // Campo de texto para el nombre
    private int editando;           // Indica el elemento que se está editando
    private int tipo;               // Indica el tipo del elemento que se edita
    private final int LISTA = 0, ELEMENTO = 1;
    private Traduccion traduccion;
    
    /** Constructor de la clase
     *  @param padre Visor de listas padre del editor
     *  @param traduccion Objeto Traduccion con la traducción */
    public EditorListas(VisorListas padre, Traduccion traduccion) {
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
        
        int col2 = 120;
        int fil = 10;

        // Definición del campo Nombre
        textoNombre = new SJTextField(normal, vacio, dim, new Font("Lucida grande", Font.BOLD, 14));

        // Posición del campo Nombre
        SpringLayout.Constraints restTextoNombre = disp.getConstraints(textoNombre);
        restTextoNombre.setX(Spring.constant(col2 + 2));
        restTextoNombre.setY(Spring.constant(fil));

        // Botón Aceptar
        JButton botonAceptar = new JButton(traduccion.tBotonGuardar);
        botonAceptar.setActionCommand("guardar");
        botonAceptar.setBackground(fondo);

        // Botón Cancelar
        JButton botonCancelar = new JButton(traduccion.tBotonCancelar);
        botonCancelar.setActionCommand("cancelar");
        botonCancelar.setBackground(fondo);

        fil = 30;
        
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
        textoNombre.addKeyListener(detectorEnter);
        botonAceptar.addKeyListener(detectorEnter);

        // Añadir todos los controles al editor
        add(textoNombre);
        add(botonera);
    }

    /** Guarda la lista o el elemento que se esté editando 
     *  @return Verdadero si la lista se guarda con éxito */
    public boolean guardar(){
        String nombre = textoNombre.getText();
        if(nombre.equals(""))
            nombre = traduccion.tSinNombre;

        if(tipo == 0){
            if(editando == NINGUNO)
                padre.nuevaLista(nombre);
            else
                padre.editarLista(editando, nombre);
        }
        else{
            if(editando == NINGUNO)
                padre.nuevoElemento(nombre);
            else
                padre.editarElemento(editando, nombre);
        }
        return true;
    }

    // -------------------------------------------------------- Editor de listas

    /** Prepara el editor para crear una nueva lista */
    public void mostrarNuevaLista(){
        tipo = LISTA;
        editando = NINGUNO;
        textoNombre.setText(traduccion.tNuevaLista);
        setVisible(true);
        textoNombre.requestFocus();
        textoNombre.selectAll();
    }

    /** Prepara el editor para editar una lista
     *  @param identificador Identificador de la lista
     *  @param datos Nombre de la lista que se va a editar */
    public void mostrarEditarLista(int identificador, String datos){
        tipo = LISTA;
        editando = identificador;
        textoNombre.setText(datos);
        setVisible(true);
    }

    // ----------------------------------------------------- Editor de elementos

    /** Prepara el editor para añadir un nuevo elemento a la lista */
    public void mostrarNuevoElemento(){
        tipo = ELEMENTO;
        editando = NINGUNO;
        textoNombre.setText(traduccion.tNuevoElemento);
        setVisible(true);
        textoNombre.requestFocus();
        textoNombre.selectAll();
    }

    /** Prepara el editor para editar un elemento
     *  @param identificador Identificador de elemento
     *  @param datos Nombre del elemento que se va a editar */
    public void mostrarEditarElemento(int identificador, String datos){
        tipo = ELEMENTO;
        editando = identificador;
        textoNombre.setText(datos);
        setVisible(true);
    }
}
