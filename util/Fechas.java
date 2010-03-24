package util;

import java.util.Calendar;

/** Clase Fechas:
 *  Representa una clase auxiliar para el manejo de fechas
 *  @version 1.0
 *  @author Pau
 */
public class Fechas {
    private String spFechaHora;
    private int localizacion;
    private final int EN = 1, OTRA = 0;
    private final String spFechas = "/";

    /** Construye la clase auxiliar para las fechas teniendo en cuenta la
     *  localización
     *  @param traduccion Objeto Traduccion con las traducciones */
    public Fechas(Traduccion traduccion){
        String loc = traduccion.obtenerIdioma();
        spFechaHora = traduccion.tALas;
        if(loc.equals("en")) localizacion = EN;
        else localizacion = OTRA;
    }

    /** Devuelve la cadena formada por la fecha del calendario que se reciba
     *  como argumento, teniendo en cuenta la localización.
     *  @param fecha Calendario con la fecha
     *  @return Cadena con la fecha formateada */
    public String parsearFecha(Calendar fecha){
        String dia = procesar(fecha.get(Calendar.DAY_OF_MONTH));
        String mes = procesar(fecha.get(Calendar.MONTH) + 1);
        String ano = procesar(fecha.get(Calendar.YEAR));

        switch(localizacion){
            case EN:
                return  mes + spFechas +
                        dia + spFechas +
                        ano;
            default:
                return  dia + spFechas +
                        mes + spFechas +
                        ano;
        }
    }

    /** Devuelve la cadena formada por la fecha y la hora del calendario que se
     *  reciba como argumento, teniendo en cuenta la localización.
     *  @param fecha Calendario con la fecha y la hora
     *  @return Cadena con la fecha y hora formateadas */
    public String parsearFechaHora(Calendar fecha){
        String dia    = procesar(fecha.get(Calendar.DAY_OF_MONTH));
        String mes    = procesar(fecha.get(Calendar.MONTH) + 1);
        String ano    = procesar(fecha.get(Calendar.YEAR));
        String hora   = procesar(fecha.get(Calendar.HOUR_OF_DAY));
        String minuto = procesar(fecha.get(Calendar.MINUTE));

        switch(localizacion){
            case EN:
                return  mes + spFechas +
                        dia + spFechas +
                        ano + " " + spFechaHora + " " +
                        hora + ":" +
                        minuto;
            default:
                return  dia + spFechas +
                        mes + spFechas +
                        ano + " " + spFechaHora + " " +
                        hora + ":" +
                        minuto;
         }
    }

    /** Procesa el elemento de la fecha y devuelve la cadena adecuada
     *  @param fecha Elemento de fecha (mes, dia, año...)
     *  @return Fecha procesada */
    public static String procesar(int fecha){
        if(fecha < 10) return "0" + fecha;
        else return String.valueOf(fecha);
    }
}
