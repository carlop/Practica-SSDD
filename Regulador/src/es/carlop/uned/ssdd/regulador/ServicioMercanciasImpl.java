/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.regulador;

import java.rmi.RemoteException;
import java.util.List;

import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioMercanciasInterface;
import es.carlop.uned.ssdd.comun.TipoMercancia;

public class ServicioMercanciasImpl implements ServicioMercanciasInterface {

    @Override
    public void introducirDemanda(TipoMercancia tipoMercancia) throws RemoteException {
	// TODO Auto-generated method stub

    }

    @Override
    public void introducirOferta(Oferta oferta) throws RemoteException {
	// TODO Auto-generated method stub

    }

    @Override
    public void eliminarOferta(Oferta oferta) throws RemoteException {
	// TODO Auto-generated method stub

    }

    @Override
    public List<Oferta> recibirOfertas(int id) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Oferta> listarOfertas(TipoMercancia tipoMercancia) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Integer> listarDemandas(TipoMercancia tipoMercancia) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

}
