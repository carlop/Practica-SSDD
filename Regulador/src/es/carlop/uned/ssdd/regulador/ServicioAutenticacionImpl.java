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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterface {

    // Clientes registrados en el sistema
    private Map<String, String> clientes = new HashMap<String, String>();

    // Distribuidores registrados en el sistema
    private Map<String, String> distribuidores = new HashMap<String, String>();
    
    // Lista de clientes conectados
    private List<Integer> clientesConectados = new ArrayList<Integer>();
    
    // Lista de distribuidores conectados
    private List<Integer> distribuidoresConectados = new ArrayList<Integer>();
    
    @Override
    public int registrar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException {
        if (tipoUsuario == TipoUsuario.CLIENTE) {
            clientes.put(usuario, password);
        } else if (tipoUsuario == TipoUsuario.DISTRIBUIDOR) {
            distribuidores.put(usuario, password);
        }
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
        List<String> listaClientes = new ArrayList<String>();
        if (clientesConectados.size() > 0) {
            for (Integer cliente : clientesConectados) {
                listaClientes.add(Integer.toString(cliente));
            }
        } else {
            listaClientes.add("No hay clientes conectados");
        }
        return listaClientes;
    }

    @Override
    public List<String> listarDistribuidores() throws RemoteException {
        List<String> listaDistribuidores = new ArrayList<String>();
        if (distribuidoresConectados.size() > 0) {
            for (Integer cliente : clientesConectados) {
                listaDistribuidores.add(Integer.toString(cliente));
            }
        } else {
            listaDistribuidores.add("No hay distribuidores conectados");
        }
        return listaDistribuidores;
    }
}