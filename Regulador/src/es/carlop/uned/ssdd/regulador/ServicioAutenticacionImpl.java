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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.carlop.uned.ssdd.comun.ServicioAutenticacionInterface;
import es.carlop.uned.ssdd.comun.TipoUsuario;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterface {

    // Clientes registrados en el sistema
    private Map<String, String> clientes = new HashMap<String, String>();

    // Distribuidores registrados en el sistema
    private Map<String, String> distribuidores = new HashMap<String, String>();
    
    // Usuarios conectados
    private Map<Integer, String> usuariosConectados = new HashMap<Integer, String>();
    
    // Último identificador asignado
    private int ultimoId = 0; 
    
    @Override
    public boolean registrar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException {
        // Base de datos temporal
        Map<String, String> db = null;
        // 
        boolean registrado = false;
        if (tipoUsuario == TipoUsuario.CLIENTE) {
            db = clientes;
        } else if (tipoUsuario == TipoUsuario.DISTRIBUIDOR) {
            db = distribuidores;
        }
//        if (db.containsKey(usuario)) {
        if (clientes.containsKey(usuario) || distribuidores.containsKey(usuario)) {
            System.out.println("Intento fallido de registro de " + tipoUsuario + ": nombre " + usuario + " en uso.");
        } else {
            db.put(usuario, password);
            registrado = true;
            System.out.println(tipoUsuario + " registrado con nombre " + usuario);
        }
        return registrado;
    }

    @Override
    public int autenticar(String usuario, String password, TipoUsuario tipoUsuario) throws RemoteException {
        // Base de datos temporal
        Map<String, String> db = null;
        // Identificador temporal
        int idTemp = 0;
        // Establecemos que base de datos vamos a usar
        if (tipoUsuario == TipoUsuario.CLIENTE) {
            db = clientes;
        } else {
            db = distribuidores;
        }
        // Obtenemos el password del usuario de la base de datos
        String pass = db.get(usuario);
        if (pass != null && !pass.isEmpty()) {
            if (usuarioConectado(usuario, TipoUsuario.CLIENTE)) {
                idTemp = -3;
                System.out.println("Intento fallido de autenticación de " + tipoUsuario + " con nombre " + usuario + ": usuario ya autenticado.");
            } else if (pass.equals(password)) {
                actualizarId();
                usuariosConectados.put(ultimoId, usuario);
                idTemp = ultimoId;
                System.out.println(tipoUsuario + " " + usuario + " se ha autenticado con identificador " + idTemp + ".");
            } else {
                idTemp = -1;
                System.out.println(pass);
                System.out.println(password);
                System.out.println("Intento fallido de autenticación de " + tipoUsuario + " con nombre " + usuario + ": contraseña errónea.");
            }
        } else {
            idTemp = -2;
            System.out.println("Intento fallido de autenticación de " + tipoUsuario + " con nombre " + usuario + ": nombre erróneo.");
        }
        return idTemp;
    }

    @Override
    public void salir(int id) throws RemoteException {
        usuariosConectados.remove(id);
    }

    @Override
    public boolean baja(int id) throws RemoteException {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public List<String> listarUsuarios(TipoUsuario tipoUsuario) throws RemoteException {
        List<String> listaUsuarios = new ArrayList<String>();
        Map<String, String> db = null;
        
        if (tipoUsuario == TipoUsuario.CLIENTE) {
            db = clientes;
        } else {
            db = distribuidores;
        }
        if (db.size() > 0) {
            Iterator<String> it = db.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                String usuario = (String) key;
                listaUsuarios.add(usuario + (usuarioConectado(usuario, tipoUsuario) ? " [conectado]" : ""));
            }
        } else {
            listaUsuarios.add("No hay ningún " + tipoUsuario + " registrado.");
        }
        return listaUsuarios;
    }

    private boolean usuarioConectado(String usuario, TipoUsuario tipoUsuario) throws RemoteException {
        return usuariosConectados.containsValue(usuario);
    }
    
    /**
     * Aumenta el último identificador
     */
    private void actualizarId() {
        ultimoId = ultimoId + 1;
    }
}