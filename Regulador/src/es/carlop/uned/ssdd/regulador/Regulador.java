/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: flopez1046@alumno.uned.es
 *
 */
package es.carlop.uned.ssdd.regulador;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import es.carlop.uned.ssdd.comun.Codebase;
import es.carlop.uned.ssdd.comun.Demanda;
import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.ServicioMercanciasInterface;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class Regulador {

    // Servicio de autenticación
    private static ServicioAutenticacionInterface remoteServicioAutenticacion;
    
    // Servicio de mercancias
    private static ServicioMercanciasInterface remoteServicioMercancias;
    
    private static Registry registroRMI;

    private static ServicioAutenticacionInterface servicioAutenticacion;

    private static ServicioMercanciasInterface servicioMercancias;

    public static void main(String[] args) throws IOException {
        
        try {
            // Ruta a las interfaces
            Codebase.setCodeBase(ServicioAutenticacionInterface.class);
            Codebase.setCodeBase(ServicioMercanciasInterface.class);

            // Iniciamos el registro RMI
            registroRMI = LocateRegistry.createRegistry(8888);

            // Levantamos el servicio de autenticación
            servicioAutenticacion = new ServicioAutenticacionImpl();
            remoteServicioAutenticacion = (ServicioAutenticacionInterface) UnicastRemoteObject.exportObject(servicioAutenticacion, 8888);
            registroRMI.rebind("rmi://localhost/servicioautenticacion", remoteServicioAutenticacion);
            System.out.println("Servicio de autenticacion preparado...");
            
            // Levantamos el servicio de mercancias
            servicioMercancias = new ServicioMercanciasImpl();
            remoteServicioMercancias = (ServicioMercanciasInterface) UnicastRemoteObject.exportObject(servicioMercancias, 8889);
            registroRMI.rebind("rmi://localhost/serviciomercancias", remoteServicioMercancias);
            System.out.println("Servicio de mercancías preparado...");
            
            // Cargamos los datos si existen
            cargarDatos();
            
            int opcion = 0;
            String[] opcionesMenu = {"Listar ofertas actuales.", "Listar demandas actuales.", "Listar clientes.",
                    "Listar distribuidores."};
            do {
                opcion = InterfazGraficaUsuario.mostrarMenu("Regulador", opcionesMenu);
                switch (opcion) {
                case 1:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarOfertasActuales();
                    break;
                case 2:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarDemandasActuales();
                    break;
                case 3:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarUsuarios(TipoUsuario.CLIENTE);
                    break;
                case 4:
                    InterfazGraficaUsuario.limpiarPantalla();
                    listarUsuarios(TipoUsuario.DISTRIBUIDOR);
                    break;
                case 5:
                    guardarDatos();
                    salir();
                default:
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

    private static void listarOfertasActuales() {
        InterfazGraficaUsuario.mostrarTitulo("Ofertas actuales");
        try {
            List<Oferta> listaOfertas = remoteServicioMercancias.listarOfertas();
            if (listaOfertas.size() > 0) {
                for (Oferta oferta : listaOfertas) {
                    System.out.println(oferta.getId() + ": " + oferta.getMercancia() + ", " + oferta.getPrecio() + "€, " + oferta.getPeso() + "kg, ");
                }
            } else {
                System.out.println("No hay ofertas.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void listarDemandasActuales() {
        InterfazGraficaUsuario.mostrarTitulo("Demandas actuales");
        try {
            List<Demanda> listaDemandas = remoteServicioMercancias.listarDemandas();
            if (listaDemandas.size() >0) {
                for (Demanda demanda : listaDemandas) {
                    System.out.println(demanda.getId() + ": " + demanda.getMercancia());
                }
            } else {
                System.out.println("No hay demandas.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
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
    
    private static void salir() {
        try {
            registroRMI.unbind("rmi://localhost/servicioautenticacion");
            registroRMI.unbind("rmi://localhost/serviciomercancias");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            e.printStackTrace();
        }
        try {
            UnicastRemoteObject.unexportObject(servicioAutenticacion, true);
            UnicastRemoteObject.unexportObject(servicioMercancias, true);
            UnicastRemoteObject.unexportObject(registroRMI, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }

    private static void guardarDatos() {
        try {
            remoteServicioAutenticacion.guardarDatos();
            remoteServicioMercancias.guardarDatos();
        } catch (RemoteException e) {
            System.err.println("Error al solicitar guardar los datos de clientes y distribuidores");
            e.printStackTrace();
        }
    }
    
    private static void cargarDatos() {
        try {
            remoteServicioAutenticacion.cargarDatos();
            remoteServicioMercancias.cargarDatos();
        } catch (RemoteException e) {
            System.err.println("Error al solicitar cargar los datos de clientes y distribuidores");
            e.printStackTrace();
        }
    }
}