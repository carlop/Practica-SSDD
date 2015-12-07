package es.carlop.uned.ssdd.distribuidor;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.carlop.uned.ssdd.comun.InterfazGraficaUsuario;
import es.carlop.uned.ssdd.comun.Oferta;
import es.carlop.uned.ssdd.comun.ServicioVentaInterface;

public class ServicioVentaImpl implements ServicioVentaInterface {

    // Ventas realizadas por el distribuidor
    private Map<String, List<Oferta>> ventas = new HashMap<String, List<Oferta>>();

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
                System.out.println("--" + oferta.getMercancia() + ", " + oferta.getPeso() + ", " + oferta.getPrecio());
                precioTotal = precioTotal + oferta.getPrecio();
            }
        }
        System.out.println("Total: " + precioTotal);

    }

    @Override
    public void comprarMercancia() throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void guardarVentas() throws RemoteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void cargarVentas() throws RemoteException {
        // TODO Auto-generated method stub
        
    }

}
