package day6;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Day6_2 {


    final static int MARKER_SIZE = 14;

    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;



        try {
            archivo = new File (Constantes.inputRoot + "input6.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    int marker=procesaLinea(linea);
                    System.out.println(marker);
                }
            }


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

    private static int procesaLinea(String linea) {
        int marker = -1;
        for(int i=0; i<linea.length()-MARKER_SIZE+1;i++){
            if(isMarker(linea.substring(i,i+MARKER_SIZE))){
                return i+MARKER_SIZE;
            }
        }
        return marker;
    }

    private static boolean isMarker(String marker) {
        boolean res = true;

        for(int i = 0;i<marker.length();i++){
            int total = marker.length() - marker.replaceAll(String.valueOf(marker.charAt(i)),"").length();
            if(total>1) return false;
        }

        return res;
    }


}
