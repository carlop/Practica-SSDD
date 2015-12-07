/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.regulador;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.carlop.uned.ssdd.comun.Db;
import es.carlop.uned.ssdd.comun.Demanda;
import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioMercanciasInterface;

public class ServicioMercanciasImpl implements ServicioMercanciasInterface {

    // Ofertas disponibles
    private List<Oferta> ofertas = null;

    // Demandas disponibles
    private List<Demanda> demandas = null;

    @Override
    public void introducirDemanda(Demanda demanda) throws RemoteException {
        demandas.add(demanda);
        System.out.println("Nueva demanda introducida: " + demanda.getMercancia());
    }

    @Override
    public void introducirOferta(Oferta oferta) throws RemoteException {
        ofertas.add(oferta);
        System.out.println("Nueva oferta introducida: " + oferta.getMercancia() + ", " + oferta.getPeso() + " kilos, " +
                           oferta.getPrecio() + "€");
    }

    @Override
    public void eliminarOferta(Oferta oferta) throws RemoteException {
        for (int i = 0; i < ofertas.size(); i++) {
            Oferta oe = ofertas.get(i);
            System.out.println(oe.getId() + "" + oe.getMercancia() + "" + oe.getPeso() + "" + oe.getPrecio());
            System.out.println(oferta.getId() + "" + oferta.getMercancia() + "" + oferta.getPeso() + "" + oferta.getPrecio());
            if (oe.equals(oferta)) {
                ofertas.remove(i);
                System.out.println("Oferta eliminada: " + oferta.getMercancia() + ", " + oferta.getPeso() + " kilos, " +
                           oferta.getPrecio() + "€");
            }
        }
    }

    @Override
    public List<Oferta> recibirOfertas(int id) throws RemoteException {
	return null;
    }

    @Override
    public List<Oferta> listarOfertas() throws RemoteException {
        return ofertas;
    }

    @Override
    public List<Oferta> listarOfertasDistribuidor(String id) throws RemoteException {
        List<Oferta> listaOfertas = new ArrayList<Oferta>();
        
        Iterator<Oferta> it = ofertas.iterator();
        while (it.hasNext()) {
            Oferta oferta= it.next();
            if (oferta.getId().equals(id)) {
                listaOfertas.add(oferta);
            }
        }

        return listaOfertas;
    }

    @Override
    public List<Demanda> listarDemandas() throws RemoteException {
        return demandas;
    }
    
    @Override
    public void guardarDatos() throws RemoteException {
        try {
            Db.guardarDatos("ofertas.db", ofertas);
            Db.guardarDatos("demandas.db", demandas);
        } catch (IOException e) {
            System.err.println("No se han podido guardar los datos de ofertas y demandas");
            e.printStackTrace();
        }
    }
    
    @Override
    public void cargarDatos() throws RemoteException {
        try {
            ofertas = (List<Oferta>) Db.leerDatos("ofertas.db", 2);
            if (ofertas == null) {
                ofertas = new ArrayList<Oferta>();
            }
            demandas = (List<Demanda>) Db.leerDatos("demandas.db", 2);
            if (demandas == null) {
                demandas = new ArrayList<Demanda>();
            }
        } catch (IOException e) {
            System.err.println("no se han podido cargar los datos de ofertas y demandas");
            e.printStackTrace();
        }
    }

}