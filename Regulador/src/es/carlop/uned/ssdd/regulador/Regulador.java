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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import es.carlop.uned.ssdd.comun.Codebase;
import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.PoliticaSeguridad;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;

public class Regulador {
    

    public static void main(String[] args) throws IOException {
        
        try {
            Codebase.setCodeBase(ServicioAutenticacionInterface.class);

            Registry registry = LocateRegistry.createRegistry(8888);

            ServicioAutenticacionImpl servicioAutenticacion = new ServicioAutenticacionImpl();
            ServicioAutenticacionInterface remoteAutenticacion = (ServicioAutenticacionInterface) UnicastRemoteObject.exportObject(servicioAutenticacion, 8890);
//            registry.rebind("rmi://localhost/servicioautenticacion:8890", remoteAutenticacion);
            registry.rebind("servicioautenticacion", remoteAutenticacion);
            System.out.println("Servicio de autenticacion preparado..." + registry.toString());
            int opcion = 0;
            String[] opcionesMenu = {"Listar ofertas actuales.", "Listar demandas actuales.", "Listar clientes.",
                    "Listar distribuidores."};
//            InterfazGraficaUsuario.limpiarPantalla();
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
            System.err.println("Servicio de autenticacion no iniciado :(");
            System.err.println(e.getMessage());
            System.exit(0);
        }
        
    }

    private static void listarClientes() {
//        List<String> listaClientes = new ArrayList<String>();
//        try {
//            listaClientes = servicioAutenticacion.listarClientes();
//            for (String cliente : listaClientes) {
//                System.out.println(cliente);
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    private static void listarDistribuidores() {
//        List<String> listaDistribuidores = new ArrayList<String>();
//        try {
//            listaDistribuidores = servicioAutenticacion.listarDistribuidores();
//            for (String distribuidor : listaDistribuidores) {
//                System.out.println(distribuidor);
//            }
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

}