/**
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: flopez1046@alumno.uned.es
 *
 */
package es.carlop.uned.ssdd.cliente;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import es.carlop.uned.ssdd.comun.Demanda;
import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.ServicioMercanciasInterface;
import es.carlop.uned.ssdd.comun.ServicioVentaInterface;
import es.carlop.uned.ssdd.comun.TipoMercancia;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class Cliente {

    // Servicio de autenticación
    private static ServicioAutenticacionInterface servicioAutenticacion;

    // Servicio de mercancias
    private static ServicioMercanciasInterface servicioMercancias;
    
    // Identificador
    private static String id = "";
    
    // Identificador de sesión
    private static int idSesion = 0;
    
    // Opción del menú
    private static int opcion = 0;

    public static void main(String[] args) throws IOException {

        try {
            // Iniciamos el registro RMI
            Registry registroRMI = LocateRegistry.getRegistry(8888);

            // Conectamos al servicio de autenticacion
            servicioAutenticacion = (ServicioAutenticacionInterface) registroRMI.lookup("rmi://localhost/servicioautenticacion");
            System.out.println("Cliente conectado al servicio de autenticación...");

            // Conectamos al servicio de mercancias
            servicioMercancias = (ServicioMercanciasInterface) registroRMI.lookup("rmi://localhost/serviciomercancias");
            System.out.println("Distribuidor conectado al servicio de mercancías...");

            int seleccion = 0;
            do {
                if (getIdSesion() > 0) {
                    String[] opcionesMenu = {"Introducir demanda", "Recibir ofertas", "Comprar mercancía", "Darse de baja en el sistema"};
                    do {
                        opcion = InterfazGraficaUsuario.mostrarMenu("Cliente: " + getId(), opcionesMenu);
                        switch (opcion) {
                        case 1:
                            InterfazGraficaUsuario.limpiarPantalla();
                            introducirDemanda();
                            break;
                        case 2:
                            InterfazGraficaUsuario.limpiarPantalla();
                            recibirOfertas();
                            break;
                        case 3:
                            InterfazGraficaUsuario.limpiarPantalla();
                            comprarMercancia();
                            break;
                        case 4:
                            InterfazGraficaUsuario.limpiarPantalla();
                            darDeBaja();
                            opcion = 5;
                            seleccion = -1;
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
            System.err.println("No se ha podido conectar con el servicio de autenticación, probablemente tengas que ejecutar regulador primero");
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
        };
        demanda = new Demanda(mercancia, getId());
        try {
            int op = servicioMercancias.introducirDemanda(demanda);
            if (op == 0) {
                System.out.println("Nueva demanda de " + demanda.getMercancia() + " introducida");
            } else {
                System.out.println("Ya tiene una demanda de " + demanda.getMercancia() + " previa");
            }
        } catch (RemoteException e) {
            System.err.println("Ha habido un error al introducir la demanda, vuelva a intentarlo");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Recibe las ofertas disponibles para las demandas del cliente
     */
    private static void recibirOfertas() {
        List<Oferta> ofertas = null;
        
        InterfazGraficaUsuario.mostrarTitulo("Ofertas seleccionadas:");
        try {
            ofertas = servicioMercancias.recibirOfertas(getId());
            if (ofertas.size() > 0) {
                for (int i = 0; i < ofertas.size(); i++) {
                    Oferta oferta = ofertas.get(i);
                    System.out.println("[" + i + "] " + oferta.getId() + ": " + oferta.getMercancia() + ", " +
                                        oferta.getPrecio() + "€, " + oferta.getPeso() + "Kg");
                }
            } else {
                System.out.println("No hay ninguna oferta disponible para usted");
            }
        } catch (RemoteException e) {
            System.err.println("Ha habido un error recibiendo las ofertas disponibles");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Compra la mercancía seleccionada al distribuidor de la misma
     */
    private static void comprarMercancia() {
        List<Oferta> ofertas = null;
        
        InterfazGraficaUsuario.mostrarTitulo("Seleccione la oferta que desea comprar:");
        try {
            ofertas = servicioMercancias.recibirOfertas(getId());
            if (ofertas.size() > 0) {
                for (int i = 0; i < ofertas.size(); i++) {
                    Oferta oferta = ofertas.get(i);
                    System.out.println("[" + i + "] " + oferta.getId() + ": " + oferta.getMercancia() + ", " +
                                        oferta.getPrecio() + "€, " + oferta.getPeso() + "Kg");
                }
                int cm = Integer.parseInt(InterfazGraficaUsuario.pedirDato("Seleciona la oferta"));
                Oferta ofertaComprar = ofertas.get(cm);
                if (servicioAutenticacion.usuarioConectado(ofertaComprar.getId(), TipoUsuario.DISTRIBUIDOR)) {
                    int puerto = 8890 + servicioAutenticacion.getIdSesion(ofertaComprar.getId());
                    try {
                        // Conectamos con el servicio de ventas del distribuidor de la oferta seleccionada
                        Registry registroRMI = LocateRegistry.getRegistry(8888);
                        ServicioVentaInterface servicioVentas = (ServicioVentaInterface) registroRMI.lookup("rmi://localhost:" + puerto + "/servicioventa" + ofertaComprar.getId());
                        System.out.println("Cliente conectado al servicio de ventas de " + ofertaComprar.getId());
                        
                        // Compramos la oferta seleccionada
                        String opcionComprar = InterfazGraficaUsuario.pedirDato("¿Desea comprar " +
                                ofertaComprar.getMercancia() + ", " + ofertaComprar.getPeso() + "Kg, " +
                                ofertaComprar.getPrecio() + "€ a " + ofertaComprar.getId() + "? (s/n)");
                        if (opcionComprar.equals("s")) {
                            servicioVentas.comprarMercancia(ofertaComprar, getId());
                            System.out.println("Compra realizada correctamente...");
                            // Pedimos la eliminación de la demanda
                            String opcionEliminar = InterfazGraficaUsuario.pedirDato("¿Desea eliminar la demanda de " +
                                    ofertaComprar.getMercancia() + "? (s/n)");
                            if (opcionEliminar.equals("s")) {
                                Demanda demandaEliminar = new Demanda(ofertaComprar.getMercancia(), getId());
                                servicioMercancias.eliminarDemanda(demandaEliminar);
                                System.out.println("Demanda de " + ofertaComprar.getMercancia() + " eliminada");
                            }
                        }
                    } catch (RemoteException e) {
                        System.err.println("Ha habido un problema al realizar la compra, vuelva a intentarlo");
                        System.err.println(e.getMessage());
                        e.printStackTrace();
                    } catch (NotBoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Este distribuidor no está autenticado actualmente, " + 
                                       "por lo que no se puede realizar la compra, seleccione una oferta distinta");
                }
            } else {
                System.out.println("No hay ninguna oferta disponible para usted");
            }
        } catch (RemoteException e) {
            System.err.println("Ha habido un error recibiendo las ofertas disponibles");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Da de baja al cliente del sistema
     */
    private static void darDeBaja() {
        try {
            boolean exito = servicioAutenticacion.baja(getIdSesion(), TipoUsuario.CLIENTE);
            if (exito) {
                servicioMercancias.eliminarDemandas(getId());
                System.out.println("Se eliminó su cuenta de usuario");
                salir();
            } else {
                System.out.println("No puedo eliminarse su cuenta de usuario");
            }
        } catch (RemoteException e) {
            System.err.println("Ha habido un error eliminando su cuenta");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    
    /**
     * Permite cerrar sesión al cliente
     */
    private static void salir() {
        try {
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
                System.out.println("Ya está registrado, inicie sesión para continuar");
                autenticarCliente();
            } else {
                System.out.println("Ya existe un usuario con este nombre, seleccione uno diferente");
            }
        } catch (RemoteException e) {
            System.err.println("No se ha podido registrar, inténtelo de nuevo");
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
                setId(usuario);
                setIdSesion(idTemp);
                opcion = 3;
                System.out.println("Autenticación correcta...");
            } else if (idTemp == -1) {
                System.out.println("Contraseña errónea");
            } else if (idTemp == -2) {
                System.out.println("No existe ningún " + TipoUsuario.CLIENTE + " registrado con nombre " + usuario);
            } else if (idTemp == -3) {
                System.out.println("¡ups! " + usuario + " ya está autenticado en el sistema");
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
    public static String getId() {
        return id;
    }

    /**
     * Establece el identificador del cliente
     * @param id identificador del cliente
     */
    public static void setId(String id) {
        Cliente.id = id;
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
        Cliente.idSesion = idSesion;
    }
}