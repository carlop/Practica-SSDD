/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.distribuidor;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.carlop.uned.ssdd.comun.Db;
import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioVentaInterface;

public class ServicioVentaImpl implements ServicioVentaInterface {

    // Ventas realizadas por el distribuidor
    private Map<String, List<Oferta>> ventas = new HashMap<String, List<Oferta>>();
    
    // Identificador del distribuidor
    private String id;

    @Override
    public void mostarVentas() throws RemoteException {
        InterfazGraficaUsuario.mostrarTitulo("Ventas realizadas");
        Iterator<String> it = ventas.keySet().iterator();
        float precioTotal = 0;
        while (it.hasNext()) {
            String key = it.next();
            List<Oferta> listaVentas = ventas.get(key);
            System.out.println("Cliente: " + key);
            for (Oferta oferta : listaVentas) {
                System.out.println("       * " + oferta.getMercancia() + ", " + oferta.getPeso() + "Kg, " + oferta.getPrecio() + "€");
                precioTotal = precioTotal + oferta.getPrecio();
            }
        }
        System.out.println("Total: " + precioTotal + "€");

    }
    
    @Override
    public void comprarMercancia(Oferta oferta, String id) throws RemoteException {
        // Sacamos las ventas realizadas a ese cliente
        List<Oferta> ventasCliente = ventas.get(id);
        if (ventasCliente == null) {
            ventasCliente = new ArrayList<Oferta>();
        }
        // Añadimos la venta a la lista
        ventasCliente.add(oferta);
        // Guardamos las ventas realizadas a ese cliente
        ventas.put(id, ventasCliente);
        
        System.out.println("Venta realizada a " + id + ": " + oferta.getMercancia() + ", " + oferta.getPeso() + "Kg, " + oferta.getPrecio() + "€");
    }

    @Override
    public void guardarDatos() throws RemoteException {
        try {
            Db.guardarDatos("distribuidor" + getId() + ".db", ventas);
        } catch (IOException e) {
            System.err.println("Ha habido un error al guardar los datos, inténtelo de nuevo");
            System.err.print(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void cargarDatos() throws RemoteException {
        try {
            ventas = (Map<String, List<Oferta>>) Db.leerDatos("distribuidor" + getId() + ".db", 3);
            if (ventas == null) {
                ventas = new HashMap<String, List<Oferta>>();
            }
        } catch (IOException e) {
            System.err.println("No se han podido cargar los datos del distribuidor");
            e.printStackTrace();
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(String id) throws RemoteException {
        this.id = id;
    }

}