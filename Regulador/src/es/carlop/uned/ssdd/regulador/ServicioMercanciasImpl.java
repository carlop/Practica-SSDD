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
import java.util.Iterator;
import java.util.List;
import es.carlop.uned.ssdd.comun.Demanda;
import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioMercanciasInterface;

public class ServicioMercanciasImpl implements ServicioMercanciasInterface {

    // Ofertas disponibles
//    private Map<Integer, Oferta> ofertas = new HashMap<Integer, Oferta>();
    private List<Oferta> ofertas = new ArrayList<Oferta>();

    // Demandas disponibles
//    private Map<Integer, TipoMercancia> demandas = new HashMap<Integer, TipoMercancia>();
    private List<Demanda> demandas = new ArrayList<Demanda>();

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
    }

    @Override
    public List<Oferta> recibirOfertas(int id) throws RemoteException {
	return null;
    }

    @Override
    public List<Oferta> listarOfertas() throws RemoteException {
//        List<Oferta> listaOfertas = new ArrayList<Oferta>();
//        
//        Iterator<Oferta> it = ofertas.iterator();
//        while (it.hasNext()) {
//            Oferta oferta= it.next();
//            listaOfertas.add(oferta);
//        }
//
//        return listaOfertas;
        return ofertas;
    }

    @Override
    public List<Demanda> listarDemandas() throws RemoteException {
//        List<Demanda> listaDemandas = new ArrayList<Demanda>();
//        
//        Iterator<Demanda> it = demandas.iterator();
//        while (it.hasNext()) {
//            Demanda demanda = it.next();
//            listaDemandas.add(demanda);
//        }
//        // TODO Auto-generated method stub
//        return null;
        return demandas;
    }

}