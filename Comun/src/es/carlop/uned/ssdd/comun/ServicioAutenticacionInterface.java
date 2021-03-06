/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: flopez1046@alumno.uned.es
 *
 */
package es.carlop.uned.ssdd.comun;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServicioAutenticacionInterface extends Remote {
    // Registra un usuario nuevo en el sistema
    public boolean registrar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException;
    // Autentica un usuario en el sistema
    public int autenticar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException;
    // Saca a un usuario del sistema
    public void salir(int id) throws RemoteException;
    // Da de baja a un usuario del sistema
    public boolean baja(int id, TipoUsuario tipoUsuario) throws RemoteException;
    // Devuelve una lista con los clientes
    public List<String> listarUsuarios(TipoUsuario tipoUsuario) throws RemoteException;
    // Comprueba si un usuario está autenticado en el sistema
    public boolean usuarioConectado(String usuario, TipoUsuario tipoUsuario) throws RemoteException;
    // Devuelve el identificador de sesión de un usuario
    public int getIdSesion(String id) throws RemoteException;
    // Guarda los datos de los usuarios a un archivo
    public void guardarDatos() throws RemoteException;
    // Carga los datos de los usuraios
    public void cargarDatos() throws RemoteException;
}