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
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import es.carlop.uned.ssdd.comun.Codebase;
import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.ServicioMercanciasInterface;
import es.carlop.uned.ssdd.comun.ServicioVentaInterface;
import es.carlop.uned.ssdd.comun.TipoMercancia;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class Distribuidor {

    // Servicio de autenticación
    private static ServicioAutenticacionInterface servicioAutenticacion;

    // Servicio de mercancias
    private static ServicioMercanciasInterface servicioMercancias;
    
    // Servicio de ventas
    private static ServicioVentaInterface remoteServicioVenta;
    
    // Identificador
    private static String id = "";
    
    // Identificador de sesión
    private static int idSesion = 0;

    // Opción del menú
    private static int opcion = 0;

    public static void main(String[] args) throws IOException {

        try {
            // Conectamos al servicio de autenticación
            Registry registroAutenticacion = LocateRegistry.getRegistry(8888);
            servicioAutenticacion = (ServicioAutenticacionInterface) registroAutenticacion.lookup("servicioautenticacion");
            System.out.println("Distribuidor conectado al servicio de autenticación...");
            
            // Conectamos al servicio de mercancias
            Registry registroMercancias = LocateRegistry.getRegistry(8889);
            servicioMercancias = (ServicioMercanciasInterface) registroMercancias.lookup("serviciomercancias");
            System.out.println("Distribuidor conectado al servicio de mercancías...");
            

            int seleccion = 0;
            do {
                if (getIdSesion() > 0) {
                    String[] opcionesMenu = {"Introducir oferta", "Quitar oferta", "Mostrar ventas", "Darse de baja en el sistema"};
                    do {
                        opcion = InterfazGraficaUsuario.mostrarMenu("Distribuidor: " + getId(), opcionesMenu);
                        switch (opcion) {
                        case 1:
                            InterfazGraficaUsuario.limpiarPantalla();
                            introducirOferta();
                            break;
                        case 2:
                            InterfazGraficaUsuario.limpiarPantalla();
                            quitarOferta();
                            break;
                        case 3:
                            InterfazGraficaUsuario.limpiarPantalla();
                            mostrarVentas();
                            break;
                        case 4:
                            InterfazGraficaUsuario.limpiarPantalla();
                            darseDeBaja();
                            break;
                        case 5:
                            salir();
                            seleccion = -1;
                            break;
                        }
                    } while (opcion != 5);
                } else {
                    String[] opcionesMenu = {"Registrar un nuevo usuario", "Autenticarse en el sistema (hacer login)"};
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
            System.err.println("¡ups! algo ha fallado, prueba a ejecutar regulador primero");
            System.err.println(e.getMessage());
        } catch (NotBoundException e) {
            System.err.println("NotBoundException");
            System.err.println(e.getMessage());
        }
 
    }
    
    /**
     * Introduce una oferta en el servicio de mercancias
     */
    private static void introducirOferta() {
        // Oferta que vamos a introducir
        Oferta oferta = null;
        // Tipo de mercancía de la oferta
        TipoMercancia mercancia = null;
        // Precio de la oferta
        float precio = 0f;
        // Peso de la oferta
        float peso = 0f;

        // Mostramos el título
        InterfazGraficaUsuario.mostrarTitulo("Introduce una oferta");
        // Pedimos el tipo de mercancia
        int tm = Integer.parseInt(InterfazGraficaUsuario.pedirDato("Tipo de mercancia:\n[1] Arroz\n[2] Lentejas\n[3] Garbanzos\n[4] Judias\n[5] Soja"));
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
            default:
                System.out.println("Selecciona el tipo de mercancía correcto");
        };
        // Pedimos el precio de la oferta
        precio = Float.parseFloat(InterfazGraficaUsuario.pedirDato("Precio"));
        // Pedimos el peso de la oferta
        peso = Float.parseFloat(InterfazGraficaUsuario.pedirDato("Peso"));
        // Iniciamos la oferta
        oferta = new Oferta(mercancia, peso, peso, id);
        try {
            servicioMercancias.introducirOferta(oferta);
            System.out.println("Oferta introducida correctamente");
        } catch (RemoteException e) {
            System.err.println("Ha habido un error al introducir la oferta, vuelva a intentarlo");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Quita una de las ofertas añadidas en el servicio de mercancías
     */
    private static void quitarOferta() {
        List<Oferta> ofertas = null;
        
        InterfazGraficaUsuario.mostrarTitulo("Seleccione la oferta que desea eliminar:");
        try {
            ofertas = servicioMercancias.listarOfertasDistribuidor(getId());
            
            if (ofertas.size() > 0) {
                for (int i = 0; i < ofertas.size(); i++) {
                    Oferta oferta = ofertas.get(i);
                    System.out.println("[" + i + "] " + oferta.getMercancia() + ", " + oferta.getPeso() + ", " + oferta.getPrecio());
                }
                int oe = Integer.parseInt(InterfazGraficaUsuario.pedirDato("Oferta a eliminar"));
                Oferta ofertaEliminar = ofertas.get(oe);
                servicioMercancias.eliminarOferta(ofertaEliminar);
                System.out.println("Oferta: " + ofertaEliminar.getMercancia() + ", " + ofertaEliminar.getPeso() + ", " + ofertaEliminar.getPrecio() + " quitada");
            } else {
                System.out.println("No ha introducido ninguna oferta");
            }
        } catch (RemoteException e) {
            System.err.println("Ha habido un error eliminando la oferta, vuelva a intentarlo");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /** 
     * Muestra las ventas realizadas
     */
    private static void mostrarVentas() {
        try {
            remoteServicioVenta.mostarVentas();
        } catch (RemoteException e) {
            System.err.println("Ha ocurrido un error y no se ha podido conseguir la información de las ventas");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Da de baja al distribuidor del sistema
     */
    private static void darseDeBaja() {
        // TODO Auto-generated method stub
        
    }

    /**
     * Permite cerrar sesión al distribuidor
     */
    private static void salir() {
        try {
            remoteServicioVenta.guardarDatos();
            servicioAutenticacion.salir(getIdSesion());
            System.out.println("Sesión cerrada correctamente");
        } catch (RemoteException e) {
            System.err.println("Ha ocurrido un error y no se ha salido del sistema correctamente");
            System.err.println(e.getMessage());
            e.printStackTrace();
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

        // Mostramos el título
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
                System.out.println("Ya existe un usuario " + TipoUsuario.DISTRIBUIDOR + " con este nombre, seleccione uno diferente");
            }
        } catch (RemoteException e) {
            System.err.println("No se ha podido registrar, inténtelo de nuevo");
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
                setId(usuario);
                setIdSesion(idTemp);
                opcion = 3;
                System.out.println("Autenticación correcta...");
            } else if (idTemp == -1) {
                System.out.println("Contraseña errónea");
            } else if (idTemp == -2) {
                System.out.println("No existe ningún " + TipoUsuario.DISTRIBUIDOR + " registrado con nombre " + usuario);
            } else if (idTemp == -3) {
                System.out.println("¡ups! " + usuario + " ya está autenticado en el sistema");
            }
        } catch (RemoteException e) {
            System.err.println("No se ha podido autenticar");
            e.printStackTrace();
        }
        
        // Iniciamos el servicio de ventas
        Codebase.setCodeBase(ServicioVentaInterface.class);
        Registry registroVenta;
        int puerto = 8890 + getIdSesion();
        try {
            registroVenta = LocateRegistry.createRegistry(puerto);
            ServicioVentaInterface servicioVenta = new ServicioVentaImpl();
            servicioVenta.setId(String.valueOf(getId()));
            servicioVenta.cargarDatos();
            remoteServicioVenta = (ServicioVentaInterface) UnicastRemoteObject.exportObject(servicioVenta, puerto);
            registroVenta.rebind("servicioventa" + getId(), remoteServicioVenta);
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Servicio de venta preparado...");
    }

    /**
     * Devuelve el identificador del distribuidor
     * @return el id
     */
    public static String getId() {
        return id;
    }

    /**
     * Establece el identificador del distribuidor
     * @param id identificador del distribuidor
     */
    public static void setId(String id) {
        Distribuidor.id = id;
    }

    /**
     * @return the idSesion
     */
    public static int getIdSesion() {
        return idSesion;
    }

    /**
     * @param idSesion the idSesion to set
     */
    public static void setIdSesion(int idSesion) {
        Distribuidor.idSesion = idSesion;
    }

}