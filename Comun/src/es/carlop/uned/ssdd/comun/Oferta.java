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

public class Oferta implements Serializable {

    private static final long serialVersionUID = 4481373006800701140L;

    // Tipo de mercancía
    private TipoMercancia mercancia;
    
    // Precio de la oferta
    private float precio;
    
    // Peso de la oferta
    private float peso;
    
    // Identificador del distribuidor que hace la oferta
    private String id;
    
    // Constructor
    public Oferta(TipoMercancia mercancia, float precio, float peso, String id) {
        this.setMercancia(mercancia);
        this.setPrecio(precio);
        this.setPeso(peso);
        this.setId(id);
    }

    // Devuelve el tipo de mercancía de la oferta
    public TipoMercancia getMercancia() {
        return mercancia;
    }

    // Establece el tipo de mercancía de la oferta
    public void setMercancia(TipoMercancia mercancia) {
        this.mercancia = mercancia;
    }

    // Devuelve el precio de la oferta
    public float getPrecio() {
        return precio;
    }

    // Establece el precio de la oferta
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    // Deveuelve los kilos de la oferta
    public float getPeso() {
        return peso;
    }

    // Establece los kilos de la oferta
    public void setPeso(float peso) {
        this.peso = peso;
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
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Compara si dos ofertas son iguales
     * @param otraOferta la otra oferta a comparar
     * @return true si son iguales las dos ofertas
     */
    public boolean equals(Oferta otraOferta) {
        if (!(otraOferta instanceof Oferta)) {
            return false;
        }
        
        Oferta ofertaTemp = (Oferta) otraOferta;
        
        return (this.getId() == ofertaTemp.getId()) && (this.getMercancia() == ofertaTemp.getMercancia()) &&
               (this.getPeso() == ofertaTemp.getPeso()) && (this.getPrecio() == ofertaTemp.getPrecio());
    }
}