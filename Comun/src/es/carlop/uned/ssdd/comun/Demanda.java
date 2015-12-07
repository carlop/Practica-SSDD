/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.comun;

import java.io.Serializable;

public class Demanda implements Serializable {

    private static final long serialVersionUID = -1346846657951374089L;

    // Tipo de mercancía
    private TipoMercancia mercancia;

    // Identificador del cliente que hace la demanda
    private String id;
    
    // Constructor
    public Demanda(TipoMercancia mercancia, String id) {
        this.setMercancia(mercancia);
        this.setId(id);
    }

    /**
     * Devuelvel el tipo de mercancia de la demanda
     * @return la mercancia
     */
    public TipoMercancia getMercancia() {
        return mercancia;
    }

    /**
     * La mercancía que busca el cliente
     * @param mercancia la mercancía que busca el cliente
     */
    public void setMercancia(TipoMercancia mercancia) {
        this.mercancia = mercancia;
    }

    /**
     * Devuelve el identificador del cliente
     * @return el identificador
     */
    public String getId() {
        return id;
    }

    /**
     * El identificador del cliente 
     * @param id 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Compara si dos demandas son iguales
     * @param otraDemanda la otra demanda a comparar
     * @return true si son iguales las dos demandas
     */
    public boolean equals(Demanda otraDemanda) {
        if (!(otraDemanda instanceof Demanda)) {
            return false;
        }
        
        Demanda demandaTemp = (Demanda) otraDemanda;
        
        return (this.getId().equals(demandaTemp.getId())) && (this.getMercancia() == demandaTemp.getMercancia());
    }
}