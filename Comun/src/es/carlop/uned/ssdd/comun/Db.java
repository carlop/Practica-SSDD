/**
 * 
 * Práctica de Sistemas Distribuidos
 * Curso 2015-2016 
 * Alumno: Francisco Carlos López Porcel
 * Correo electrónico: flopez1046@alumno.uned.es
 *
 */
package es.carlop.uned.ssdd.comun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Db {
    
    public static void guardarDatos(String ruta, Object datos) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(ruta);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(datos);
            oos.close();
            fos.close();
            System.out.println("Archivo " + ruta + " guardado");
        } catch(IOException e){
            e.printStackTrace();
        } 
    }
    
    public static Object leerDatos(String ruta, int tipo) throws IOException {
        File archivo = new File(ruta); 
        Object datos = null;
        if (archivo.exists()) {
            try {
                FileInputStream fis = new FileInputStream(ruta);
                ObjectInputStream ois = new ObjectInputStream(fis);
                if (tipo == 1) {
                    datos = new HashMap<String, String>();
                    datos = (HashMap<?, ?>) ois.readObject();
                } else if (tipo ==  2) {
                    datos = new ArrayList<>();
                    datos = (ArrayList<?>) ois.readObject();
                } else if (tipo == 3) {
                    datos = new HashMap<String, List<Oferta>>();
                    datos = (HashMap<?, ?>) ois.readObject();
                }
                ois.close();
                fis.close();
            } catch(IOException ioe){
                ioe.printStackTrace();
            } catch(ClassNotFoundException c){
                System.out.println("Class not found");
                c.printStackTrace();
            }
        } else {
            System.out.println("No existe el archivo " + ruta);
        }
        return datos;
    }

}