package day4;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Day4_1 {
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        int totalScore=0;

        try {
            archivo = new File (Constantes.inputRoot + "input4.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    String[] pares = linea.split(",");
                    totalScore+=getUnusedElves(pares,0);
                }
            }

            System.out.println(totalScore);

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

    public static int getUnusedElves(String[] pares, int posicion){
        int unusedElves=0;

        String parLectura = pares[posicion];
        String[] partesPar = parLectura.split("-");
        int parteBaja = Integer.valueOf(partesPar[0]);
        int parteAlta = Integer.valueOf(partesPar[1]);

        for(int i= 0; i < pares.length;i++){
            //No leemos la misma linea
            if(i!=posicion){
                String parCompara = pares[i];
                String[] partesParCompara = parCompara.split("-");
                int parteBajaCompara = Integer.valueOf(partesParCompara[0]);
                int parteAltaCompara = Integer.valueOf(partesParCompara[1]);

                if(parteBajaCompara>=parteBaja && parteAltaCompara<=parteAlta)
                    unusedElves++;
            }
        }

        if(posicion<pares.length-1){
            unusedElves+= getUnusedElves(pares,++posicion);
        }

        return unusedElves>1?unusedElves-1:unusedElves;     //Parche por si hay resultados iguales, al menos que deje un elfo "util"
    }


}
