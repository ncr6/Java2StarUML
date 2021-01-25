/*
Copyleft 2021. Nícolas Castillo.

Este programa es software libre.
Redistribución y modificación permitida.
*/


import javax.swing.JOptionPane;
import javax.swing.JTextArea;


public class Java2StarUML {
    
    public static void main(String[] args) {
        
        char signo = ' ';
        String a, c, d = "", tipo = "", nombre = "";
        String[] params, paramTypes, paramNames;
        boolean isAbstract = false;
        boolean isFinal = false;
        boolean isStatic = false;
        
        
        //Entrada de datos
        JOptionPane.showMessageDialog(null,
                "Este programa convierte un atributo \n" +
                "o método de Java a la sintaxis de StarUML \npara que " +
                "sea fácilmente copiado y pegado", "Bienvenido", 1);
        
        a = JOptionPane.showInputDialog(null,
                "Ingresa el atributo o declaración de método \n " +
                "que deseas convertir a StarUML:",
                "Ingreso de datos", 3);
       
        
        //Copia de seguridad de 'a' (entrada del usuario)
        c = a;
        
        
        //Eliminando espacios, puntos y comas y símbolos sobrantes
        c = c.trim();
        c = c.replace(";",""); c = c.replace("{",""); c = c.replace("}","");
        
        /* Quitamos los modificadores de acceso (public, private, etc)
        y almacenamos sus valores como un signo de StarUML (+ - #) */
        if (c.startsWith("public")){
            c = c.replace("public ", ""); signo = '+';
        } else if (c.startsWith("private")){
            c = c.replace("private ", ""); signo = '-';
        } else if (c.startsWith("protected")){
            c = c.replace("protected ", ""); signo = '#';
        }
        
        
        /* Eliminamos (si es que existe) la palabra "abstract"
           de la string pero emitimos una advertencia */
        if (c.contains("abstract")){
            c = c.replace("abstract", ""); c = c.trim();
            isAbstract = true;
        }
        
        
        /* Eliminamos (si es que existe) la palabra "final"
           de la string pero emitimos una advertencia */
        if (c.contains("final")){
            c = c.replace("final", ""); c = c.trim();
            isFinal = true;
        }
        
        
        /* Eliminamos (si es que existe) la palabra "static"
           de la string pero emitimos una advertencia */
        if (c.contains("static")){
            c = c.replace("static", ""); c = c.trim();
            isStatic = true;
        }
        
        
        //Adios espacios sobrantes con regex ;)
        c = c.replaceAll("\\s{2,}", " ").trim();
        
        
        //Si es método    
        if (c.contains("(") && !c.contains("=")){
            
            //Si es método de clase (no constructor)
            if (c.split("\\(")[0].contains(" ")){   
                /* Consideramos el tipo del método como
                lo primero antes de un espacio */
                tipo = c.split("\\s")[0];
                c = c.replace(tipo, ""); c = c.trim();
            }
            
            /* Consideramos el nombre del método como lo primero
            antes de abrir paréntesis */
            nombre = c.split("\\(")[0]; nombre = nombre.trim();
            
            //Guardamos en c todo lo que le siga a los paréntesis
            c = c.split("\\(")[1]; c = c.trim();
            
            //Eliminamos el paréntesis de cierre y espacios sobrantes
            c = c.replace(")", ""); c = c.trim();
 
            //Uniformizamos las comas separadoras
            c = c.replace(", ",",");
            
            //Separamos los parametros por comas y los guardamos en un array
            params = c.split(",");
            
            //Inicializamos arrays donde guardaremos nombres y tipos
            paramTypes = new String[params.length];
            paramNames = new String[params.length];            
            
            //Almacenamos nombres y tipos en su respectivo array
            for (int i=0; i<params.length; i++){
                paramTypes[i] = params[i].split("\\s")[0];
                paramNames[i] = params[i].split("\\s")[1];
            }
             
            //Procedemos al armado de la string resultado
            d += signo + nombre + "(";
            
            for (int i=0; i<params.length; i++){
                d += paramNames[i] + ": ";
                d += paramTypes[i] + ", ";
            }
            
            //Eliminamos la coma y espacio sobrantes y cerramos paréntesis
            d = d.substring(0, d.length()-2); d += ")";
            
            //Añadimos el tipo de dato de retorno
            d += ": " + tipo;
            
            d += "\n\nHaz doble click sobre la línea del resultado "
                   + "para seleccionar todo\nen caso sea más larga que "
                   + "la ventana y luego presiona Ctrl C.";
            
            //Advertencias de palabras clave suprimidas
            if(isAbstract){
                d += "\n\nNOTA: Este método es abstracto.";
                d += "\nNo olvides marcar la casilla.";
            }
            if(isStatic){
                d += "\n\nNOTA: Este método es estático (static).";
                d += "\nNo olvides marcar la casilla para que salga subrayado.";
            }
            if (isFinal) d += "OJO: Este método es final.";
              
        } else {
            //Si es atributo

            /* Consideramos el tipo del método como
            lo primero antes de un espacio */
            tipo = c.split("\\s")[0];
            c = c.replaceFirst(tipo, ""); c = c.trim();
            
            /* Consideramos el nombre del método como lo primero
            antes de un igual */
            nombre = c.split("=")[0]; nombre = nombre.trim();         
            
            //Dejamos a c como el valor del atributo
            c = c.replace(nombre, ""); c = c.trim();
            
            //Procedemos al armado de la string resultado
            d += signo + nombre + ": " + tipo + " " + c;
        }
        

        JTextArea ta = new JTextArea(10,15);
        
        ta.setText(d);
        
        JOptionPane.showMessageDialog(null, ta, "Resultado", 1);
    }
}

