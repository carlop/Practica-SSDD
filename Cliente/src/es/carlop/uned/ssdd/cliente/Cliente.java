/**
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.cliente;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import es.carlop.uned.ssdd.comun.Demanda;
import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.ServicioMercanciasInterface;
import es.carlop.uned.ssdd.comun.TipoMercancia;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class Cliente {

    // Servicio de autenticación
    private static ServicioAutenticacionInterface servicioAutenticacion;

    // Servicio de mercancias
    private static ServicioMercanciasInterface servicioMercancias;
    
    // Identificador
    private static int id = 0;
    
    // Opción del menú
    private static int opcion = 0;

    public static void main(String[] args) throws IOException {

        try {
            // Conectamos al servicio de autenticacion
            Registry registryAutenticacion = LocateRegistry.getRegistry(8888);
            servicioAutenticacion = (ServicioAutenticacionInterface) registryAutenticacion.lookup("servicioautenticacion");
            System.out.println("Cliente conectado al servicio de autenticación...");

            // Conectamos al servicio de mercancias
            Registry registroMercancias = LocateRegistry.getRegistry(8889);
            servicioMercancias = (ServicioMercanciasInterface) registroMercancias.lookup("serviciomercancias");
            System.out.println("Distribuidor conectado al servicio de mercancías...");

            int seleccion = 0;
            do {
                if (getId() > 0) {
                    String[] opcionesMenu = {"Introducir demanda.", "Recibir ofertas.", "Comprar mercancía.", "Darse de baja en el sistema"};
                    do {
                        opcion = InterfazGraficaUsuario.mostrarMenu("Cliente", opcionesMenu);
                        switch (opcion) {
                        case 1:
                            InterfazGraficaUsuario.limpiarPantalla();
                            introducirDemanda();
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
                        opcion = InterfazGraficaUsuario.mostrarMenu("Cliente", opcionesMenu);
                        switch (opcion) {
                        case 1:
                            InterfazGraficaUsuario.limpiarPantalla();
                            registrarCliente();
                            break;
                        case 2:
                            InterfazGraficaUsuario.limpiarPantalla();
                            autenticarCliente();
                            break;
                        case 3:
                            seleccion = -1;
                            break;
                        }
                    } while (opcion != 3);
                }
            } while (seleccion != -1);
            System.out.println("Cerrando el cliente...");
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
     * Introduce una demanda en el servicio de mercancias
     */
    private static void introducirDemanda() {
        // Demanda que vamos a realizar
        Demanda demanda = null;
        // Tipo de mercancía de la demanda
        TipoMercancia mercancia = null;
        
        // Mostramos el título
        InterfazGraficaUsuario.mostrarTitulo("Introduce una demanda");
        // Pedimos el tipo de mercancia
        int tm = Integer.parseInt(InterfazGraficaUsuario.pedirDato("Tipo de mercancia:\n[1] Arroz\n[2] Lentejas\n[2] Garbanzos\n[3] Judias\n[4] Soja"));
        switch (tm) {
            case 1:
                mercancia = TipoMercancia.ARROZ;
                break;
            case 2:
                mercancia = TipoMercancia.LENTEJAS;
                break;
            case 3:
                mercancia = TipoMercancia.GARBANZOS;
                break;
            case 4:
                mercancia = TipoMercancia.JUDIAS;
                break;
            case 5:
                mercancia = TipoMercancia.SOJA;
                break;
        };
        demanda = new Demanda(mercancia, getId());
        try {
            servicioMercancias.introducirDemanda(demanda);
        } catch (RemoteException e) {
            System.err.println("Ha habido un error al introducir la demanda. Vuelva a intentarlo.");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Registra un usuario en el servicio de autenticación
     */
    private static void registrarCliente() {
        // Nombre de usuario 
        String usuario = "";
        // Contraseña de usuario
        String password = "";

        InterfazGraficaUsuario.mostrarTitulo("Registro de " + TipoUsuario.CLIENTE);
        // Pedimos el usuario
        usuario = InterfazGraficaUsuario.pedirDato("Usuario");
        // Pedimos la contraseña
        password = InterfazGraficaUsuario.pedirDato("Password");
       
        try {
            // registramos el cliente en el servicio de auntenticación
            boolean registrado = servicioAutenticacion.registrar(usuario, password, TipoUsuario.CLIENTE);
            if (registrado) {
                System.out.println("Ya está registrado. Ahora inicie sesión para continuar.");
                autenticarCliente();
            } else {
                System.out.println("Ya existe un usuario con este nombre. Por favor, seleccione uno diferente.");
            }
        } catch (RemoteException e) {
            System.err.println("No se ha podido registrar. Inténtelo de nuevo.");
            e.printStackTrace();
        }
    }

    /**
     * Registra un usuario en el servicio de autenticación
     */
    private static void autenticarCliente() {
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
            idTemp = servicioAutenticacion.autenticar(usuario, password, TipoUsuario.CLIENTE);
            if (idTemp > 0) {
                setId(idTemp);
                opcion = 3;
                System.out.println("Autenticación correcta. Su identificador es: " + getId());
            } else if (idTemp == -1) {
                System.out.println("Contraseña errónea.");
            } else if (idTemp == -2) {
                System.out.println("No existe ningún " + TipoUsuario.CLIENTE + " registrado con nombre " + usuario);
            } else if (idTemp == -3) {
                System.out.println("¡ups! " + usuario + " ya está autenticado en el sistema.");
            }
        } catch (RemoteException e) {
            System.err.println("No se ha podido autenticar");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el identificador del cliente
     * @return el id
     */
    public static int getId() {
        return id;
    }

    /**
     * Establece el identificador del cliente
     * @param id identificador del cliente
     */
    public static void setId(int id) {
        Cliente.id = id;
    }
}