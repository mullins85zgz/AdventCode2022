package day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.TreeSet;

public class Day1_2 {
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        int max = 0;
        int aux = 0;

        int totalSuma = 3;

        ArrayList<Integer> listado = new ArrayList<>();

        TreeSet<Integer> miSetOrdenado = new TreeSet<Integer>();

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File ("D:\\input.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    int suma = Integer.valueOf(linea);
                    aux+=suma;
                }else{
                    Integer col = Integer.valueOf(aux);
//                    listado.add(col);
                    miSetOrdenado.add(col);

                    if(aux > max)
                        max = aux;
                    aux = 0;
                }
            }

            int cont = 0;
            aux = 0;
            for(Integer i: miSetOrdenado.descendingSet()){
                if(cont< totalSuma){
                    aux += i;
                    cont++;
                }
            }

            System.out.println(aux);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta
            // una excepcion.
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
}
