/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.comun;

public class InterfazGraficaUsuario {
    public static void mostrarMenu(String[] opciones) {
	for (int i = 0; i < opciones.length; i++) {
	   System.out.println((i+1) + ". " + opciones[i]); 
	}
	System.out.println(opciones.length + 1 + ". Salir");
    }
}