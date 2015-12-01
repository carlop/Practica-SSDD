/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.regulador;

import java.io.IOException;

import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;

public class Regulador {

    public static void main(String[] args) throws IOException {
        int opcion = 0;
        String[] opcionesMenu = {"Listar ofertas actuales.", "Listar demandas actuales.", "Listar clientes.",
                "Listar distribuidores."};
        InterfazGraficaUsuario.limpiarPantalla();
        do {
            opcion = InterfazGraficaUsuario.mostrarMenu(opcionesMenu);
            switch (opcion) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
            }
        } while (opcion != 5);
    }

}