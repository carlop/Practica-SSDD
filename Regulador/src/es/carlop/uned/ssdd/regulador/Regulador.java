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
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.carlop.uned.ssdd.comun.Codebase;
import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class Regulador {

    private static ServicioAutenticacionInterface remoteServicioAutenticacion;

    public static void main(String[] args) throws IOException {
        
        try {
            Codebase.setCodeBase(ServicioAutenticacionInterface.class);

            Registry registry = LocateRegistry.createRegistry(8888);

            ServicioAutenticacionInterface servicioAutenticacion = new ServicioAutenticacionImpl();
            remoteServicioAutenticacion = (ServicioAutenticacionInterface) UnicastRemoteObject.exportObject(servicioAutenticacion, 8888);
            registry.rebind("servicioautenticacion", remoteServicioAutenticacion);
            System.out.println("Servicio de autenticacion preparado...");
            int opcion = 0;
            String[] opcionesMenu = {"Listar ofertas actuales.", "Listar demandas actuales.", "Listar clientes.",
                    "Listar distribuidores."};
            do {
                opcion = InterfazGraficaUsuario.mostrarMenu("Regulador", opcionesMenu);
                switch (opcion) {
                case 1:
                    InterfazGraficaUsuario.limpiarPantalla();
                    break;
                case 2:
                    InterfazGraficaUsuario.limpiarPantalla();
                    break;
                case 3:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarUsuarios(TipoUsuario.CLIENTE);
                    break;
                case 4:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarUsuarios(TipoUsuario.DISTRIBUIDOR);
                    break;
                default:
            InterfazGraficaUsuario.limpiarPantalla();
                    break;
                }
            } while (opcion != 5);
            System.out.println("Cerrando el regulador...");
            System.exit(0);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("Servicio de autenticacion no iniciado :(");
            System.err.println(e.getMessage());
            System.exit(0);
        }
        
    }

    private static void listarUsuarios(TipoUsuario tipoUsuario) {
        if (tipoUsuario == TipoUsuario.CLIENTE) {
            InterfazGraficaUsuario.mostrarTitulo("Clientes registrados");
        } else {
            InterfazGraficaUsuario.mostrarTitulo("Distribuidores registrados");
        }
        try {
            List<String> listaUsuarios = remoteServicioAutenticacion.listarUsuarios(tipoUsuario);
            for (String usuario : listaUsuarios) {
                System.out.println(usuario);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}