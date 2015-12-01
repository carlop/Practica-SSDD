/**
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.cliente;

import java.io.IOException;

import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;

public class Cliente {
    
    private static int opcion = 0;

    public static void main(String[] args) throws IOException {
        String[] opcionesMenuInicio = {"Introducir demanda.", "Recibir ofertas.", "Comprar mercancía.", "Darse de baja en el sistema"};
        String[] opcionesMenu = {"Introducir demanda.", "Recibir ofertas.", "Comprar mercancía.", "Darse de baja en el sistema"};
        InterfazGraficaUsuario.limpiarPantalla();
        do {
            opcion = InterfazGraficaUsuario.mostrarMenu(opcionesMenu);
            switch (opcion) {
            case 1:
        	InterfazGraficaUsuario.limpiarPantalla();
                System.out.println(opcion);
                break;
            case 2:
        	InterfazGraficaUsuario.limpiarPantalla();
                System.out.println(opcion);
                break;
            case 3:
        	InterfazGraficaUsuario.limpiarPantalla();
                System.out.println(opcion);
                break;
            case 4:
        	InterfazGraficaUsuario.limpiarPantalla();
                System.out.println(opcion);
                break;
            default:
        	InterfazGraficaUsuario.limpiarPantalla();
                break;
            }
        } while (opcion != 5);

    }
}