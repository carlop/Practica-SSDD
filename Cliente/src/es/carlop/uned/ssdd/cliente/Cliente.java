/**
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.cliente;

import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;

public class Cliente {

    public void main(String[] args) {
	String[] opcionesMenu = {"Introducir demanda.", "Recibir ofertas.", "Comprar mercancía.", "Darse de baja en el sistema"};
	InterfazGraficaUsuario.mostrarMenu(opcionesMenu);
    }
}