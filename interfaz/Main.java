package interfaz;

import util.FactoriaArchivos;

/** Copyright (C) 2009 - 2010 Nuuve
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/. */

/** Thanks to Reven for all his support (and the logo :-P) */

/** Clase principal
 *  @version 1.0
 *  @author Pau
 */
public class Main {
    /** Procedimiento principal de la aplicación
     *  @param args Argumentos de la línea de comandos */
    public static void main(String[] args) {
        if(System.getProperty("os.name").toLowerCase().indexOf("mac") != -1) {
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Sparebrain");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }

        // Cargar la ventana principal creando un hilo
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                FactoriaArchivos factoria = new FactoriaArchivos();
                if(System.getProperty("os.name").toLowerCase().indexOf("mac") != -1)
                    new VentanaPrincipalMac(factoria);
                else
                    new VentanaPrincipal(factoria);
            }
        });
    } 
}
