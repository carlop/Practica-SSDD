/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: yo@carlop.es
 *
 */
package es.carlop.uned.ssdd.comun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

public class Db {
    
    public static void guardarDatos(String ruta, Collection<?> datos) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(ruta);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(datos);
            oos.close();
            fos.close();
        } catch(IOException e){
            e.printStackTrace();
        } 
    }
    
    public static Collection<?> leerDatos(String ruta) throws IOException {
        Collection<?> datos = null;
        try {
            FileInputStream fis = new FileInputStream(ruta);
            ObjectInputStream ois = new ObjectInputStream(fis);
            datos = (Collection<?>) ois.readObject();
            ois.close();
            fis.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
        } catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
        }
        return datos;
    }

}