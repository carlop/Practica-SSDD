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
import java.util.List;

public interface ServicioMercanciasInterface extends Remote {
    // Introduce una demanda de una mercancía por el cliente
    public int introducirDemanda(Demanda demanda) throws RemoteException;
    // Introduce una oferta de una mercancía por el distribuidor
    public void introducirOferta(Oferta oferta) throws RemoteException;
    // Elimina una oferta de una mercancía por el distribuidor
    public void eliminarOferta(Oferta oferta) throws RemoteException;
    // Devuelte una lista con las ofertas que recibe un cliente
    public List<Oferta> recibirOfertas(int id) throws RemoteException;
    // Devuelve una lista con todas las ofertas actuales
    public List<Oferta> listarOfertas() throws RemoteException;
    // Devuelve una lista con todas las demandas actuales
    public List<Demanda> listarDemandas() throws RemoteException;
    // Devuelve una lista con las ofertas del distribuidor indicado
    public List<Oferta> listarOfertasDistribuidor(String id) throws RemoteException;
    // Guarda los datos de ofertas y demandas en un archivo
    public void guardarDatos() throws RemoteException;
    // Carga los datos de ofertas y demandas
    public void cargarDatos() throws RemoteException;
}