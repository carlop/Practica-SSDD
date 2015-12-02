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

import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterface {

    @Override
    public int registrar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public int autenticar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public boolean salir(int id) throws RemoteException {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean baja(int id) throws RemoteException {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public List<String> listarClientes() throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<String> listarDistribuidores() throws RemoteException {
	// TODO Auto-generated method stub
	return null;
    }

}
