package day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Day1_1 {
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        int max = 0;
        int aux = 0;

        try {
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
                    if(aux > max)
                        max = aux;
                    aux = 0;
                }
            }

            System.out.println(max);
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
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
