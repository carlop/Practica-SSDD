/**
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.cliente;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;

public class Cliente {

    public static void main(String[] args) throws IOException {

        try {
            Registry registry = LocateRegistry.getRegistry(8888);
            ServicioAutenticacionInterface servicioAutenticacion = (ServicioAutenticacionInterface) registry.lookup("servicioautenticacion");
            System.out.println("Cliente conectado al servicio de autenticación");

            int opcion = 0;
//            String[] opcionesMenuInicio = {"Introducir demanda.", "Recibir ofertas.", "Comprar mercancía.", "Darse de baja en el sistema"};
            String[] opcionesMenu = {"Registrar un nuevo usuario.", "Autenticarse en el sistema (hacer login)."};
    //        InterfazGraficaUsuario.limpiarPantalla();
            do {
                opcion = InterfazGraficaUsuario.mostrarMenu(opcionesMenu);
                switch (opcion) {
                case 1:
                    InterfazGraficaUsuario.limpiarPantalla();
                    registrarUsuario();
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
            System.out.println("Cerrando el cliente...");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("No se ha podido conectar con el cliente. Probablemente tengas que ejecutar regulador primero.");
            System.err.println(e.getMessage());
        }

    }

    private static void registrarUsuario() {
        System.out.println("registre un usuario");
    }
}