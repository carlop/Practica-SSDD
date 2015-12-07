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

public interface ServicioVentaInterface extends Remote {
    // Muestra las ventas realizadas por el distribuidor
    public void mostarVentas() throws RemoteException;
    // Compra una de las ofertas del distribuidor
    public void comprarMercancia() throws RemoteException;
    // Guarda los datos del servicio de ventas
    public void guardarDatos() throws RemoteException;
    // Carga los datos del servicio de ventas
    public void cargarDatos() throws RemoteException;
    // Establece el identificador del distribuidor
    public void setId(String id) throws RemoteException;
}