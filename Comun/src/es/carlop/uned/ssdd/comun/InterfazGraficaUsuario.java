/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.comun;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterfazGraficaUsuario {

    private static Console console = System.console();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int mostrarMenu(String[] opciones) {
        int opcion = 0;
        System.out.println("Seleccione una opción:");
        for (int i = 0; i < opciones.length; i++) {
           System.out.println((i+1) + ". " + opciones[i]); 
        }
        System.out.println((opciones.length + 1) + ". Salir");
        opcion = leerConsola();
        return opcion;
    }
    
    private static int leerConsola() {
        if (console != null) {
            return Integer.parseInt(console.readLine());
        }
        try {
            return Integer.parseInt(reader.readLine());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void limpiarPantalla() throws IOException {
        try {
            if (System.getProperty("os.name").startsWith("Window")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException e) {
            for (int i = 0; i < 1000; i++) {
                System.out.println();
            }
        }
    }
}