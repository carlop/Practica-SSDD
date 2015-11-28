/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.comun;

public class Oferta {
    // Tipo de mercancía
    private TipoMercancia mercancia;
    
    // Precio de la oferta
    private float precio;
    
    // Peso de la oferta
    private float kilos;
    
    // Constructor
    public Oferta(TipoMercancia mercancia, float precio, float kilos) {
            this.setMercancia(mercancia);
            this.setPrecio(precio);
            this.setKilos(kilos);
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
    public float getKilos() {
            return kilos;
    }

    // Establece los kilos de la oferta
    public void setKilos(float kilos) {
            this.kilos = kilos;
    }
}