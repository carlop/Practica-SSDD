/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.distribuidor;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class Distribuidor {

    // Servicio de autenticación
    private static ServicioAutenticacionInterface servicioAutenticacion;
    
    // Identificador
    private static int id = 0;
    
    // Opción del menú
    private static int opcion = 0;

    public static void main(String[] args) throws IOException {

        try {
            Registry registry = LocateRegistry.getRegistry(8888);
            servicioAutenticacion = (ServicioAutenticacionInterface) registry.lookup("servicioautenticacion");
            System.out.println("Distribuidor conectado al servicio de autenticación...");

            int seleccion = 0;
            do {
                if (getId() > 0) {
                    String[] opcionesMenu = {"Introducir oferta.", "Quitar oferta.", "Mostrar ventas.", "Darse de baja en el sistema"};
                    do {
                        opcion = InterfazGraficaUsuario.mostrarMenu("Distribuidor", opcionesMenu);
                        switch (opcion) {
                        case 1:
                            InterfazGraficaUsuario.limpiarPantalla();
                            break;
                        case 2:
                            InterfazGraficaUsuario.limpiarPantalla();
                            break;
                        case 3:
                            InterfazGraficaUsuario.limpiarPantalla();
                            break;
                        case 4:
                            InterfazGraficaUsuario.limpiarPantalla();
                            break;
                        case 5:
                            seleccion = -1;
                            break;
                        }
                    } while (opcion != 5);
                } else {
                    String[] opcionesMenu = {"Registrar un nuevo usuario.", "Autenticarse en el sistema (hacer login)."};
                    do {
                        opcion = InterfazGraficaUsuario.mostrarMenu("Distribuidor", opcionesMenu);
                        switch (opcion) {
                        case 1:
                            InterfazGraficaUsuario.limpiarPantalla();
                            registrarDistribuidor();
                            break;
                        case 2:
                            InterfazGraficaUsuario.limpiarPantalla();
                            autenticarDistribuidor();
                            break;
                        case 3:
                            seleccion = -1;
                            break;
                        }
                    } while (opcion != 3);
                }
            } while (seleccion != -1);
            System.out.println("Cerrando el distribuidor...");
            System.exit(0);
        } catch (RemoteException e) {
            System.err.println("No se ha podido conectar con el servicio de autenticación. Probablemente tengas que ejecutar regulador primero.");
            System.err.println(e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException");
            System.err.println(e.getMessage());
        }

    }

    /**
     * Registra un usuario en el servicio de autenticación
     */
    private static void registrarDistribuidor() {
        // Nombre de usuario 
        String usuario = "";
        // Contraseña de usuario
        String password = "";

        InterfazGraficaUsuario.mostrarTitulo("Registro de " + TipoUsuario.DISTRIBUIDOR);
        // Pedimos el usuario
        usuario = InterfazGraficaUsuario.pedirDato("Usuario");
        // Pedimos la contraseña
        password = InterfazGraficaUsuario.pedirDato("Password");
       
        try {
            // registramos el cliente en el servicio de auntenticación
            boolean registrado = servicioAutenticacion.registrar(usuario, password, TipoUsuario.DISTRIBUIDOR);
            if (registrado) {
                System.out.println("Ya está registrado. Ahora inicie sesión para continuar.");
                autenticarDistribuidor();
            } else {
                System.out.println("Ya existe un usuario " + TipoUsuario.DISTRIBUIDOR + " con este nombre. Por favor, seleccione uno diferente.");
            }
        } catch (RemoteException e) {
            System.err.println("No se ha podido registrar. Inténtelo de nuevo.");
            e.printStackTrace();
        }
    }

    /**
     * Registra un usuario en el servicio de autenticación
     */
    private static void autenticarDistribuidor() {
        // Nombre de usuario 
        String usuario = "";
        // Contraseña de usuario
        String password = "";
        // Identificador temporal
        int idTemp;

        InterfazGraficaUsuario.mostrarTitulo("Introduce tus datos de acceso");
        // Pedimos el usuario
        usuario = InterfazGraficaUsuario.pedirDato("Usuario");
        // Pedimos la contraseña
        password = InterfazGraficaUsuario.pedirDato("Password");
        
        try {
            // Autenticamos el cliente en el servicio de autenticación
            idTemp = servicioAutenticacion.autenticar(usuario, password, TipoUsuario.DISTRIBUIDOR);
            if (idTemp > 0) {
                setId(idTemp);
                opcion = 3;
                System.out.println("Autenticación correcta. Su identificador es: " + getId());
            } else if (idTemp == -1) {
                System.out.println("Contraseña errónea.");
            } else if (idTemp == -2) {
                System.out.println("No existe ningún " + TipoUsuario.DISTRIBUIDOR + " registrado con nombre " + usuario);
            } else if (idTemp == -3) {
                System.out.println("¡ups! " + usuario + " ya está autenticado en el sistema.");
            }
        } catch (RemoteException e) {
            System.err.println("No se ha podido autenticar");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el identificador del distribuidor
     * @return el id
     */
    public static int getId() {
        return id;
    }

    /**
     * Establece el identificador del distribuidor
     * @param id identificador del distribuidor
     */
    public static void setId(int id) {
        Distribuidor.id = id;
    }

}