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
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;

public class Regulador {
    
    private static ServicioAutenticacionInterface servicioAutenticacion;

    public static void main(String[] args) throws IOException {
        
        // Gestor de seguridad
        //System.setProperty("java.security.policy", "jaich");
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }

        try {
            servicioAutenticacion = new ServicioAutenticacionImpl();
            Registry registry = LocateRegistry.createRegistry(9000);
            registry.rebind("regulador", servicioAutenticacion);
            System.out.println("Servicio de autenticacion preparado...");
            int opcion = 0;
            String[] opcionesMenu = {"Listar ofertas actuales.", "Listar demandas actuales.", "Listar clientes.",
                    "Listar distribuidores."};
            InterfazGraficaUsuario.limpiarPantalla();
            do {
                opcion = InterfazGraficaUsuario.mostrarMenu(opcionesMenu);
                switch (opcion) {
                case 1:
            InterfazGraficaUsuario.limpiarPantalla();
                    break;
                case 2:
            InterfazGraficaUsuario.limpiarPantalla();
                    break;
                case 3:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarClientes();
                    break;
                case 4:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarDistribuidores();
                    break;
                default:
            InterfazGraficaUsuario.limpiarPantalla();
                    break;
                }
            } while (opcion != 5);
            System.out.println("Cerrando el regulador...");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Servicio de autenticacion no iniciado :(");
            System.out.println(e.getMessage());
            System.exit(0);
        }
        
    }

    private static void listarClientes() {
        List<String> listaClientes = new ArrayList<String>();
        try {
            listaClientes = servicioAutenticacion.listarClientes();
            for (String cliente : listaClientes) {
                System.out.println(cliente);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void listarDistribuidores() {
        List<String> listaDistribuidores = new ArrayList<String>();
        try {
            listaDistribuidores = servicioAutenticacion.listarDistribuidores();
            for (String distribuidor : listaDistribuidores) {
                System.out.println(distribuidor);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}