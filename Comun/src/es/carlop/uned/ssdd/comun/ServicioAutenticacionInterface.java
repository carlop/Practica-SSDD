/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.comun;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterface extends Remote {
    public int registrar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException;
    public int autenticar(String usuario, String password) throws RemoteException;
    public boolean salir(int id) throws RemoteException;
    public boolean baja(int id) throws RemoteException;
}