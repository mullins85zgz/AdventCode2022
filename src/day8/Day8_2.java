package day8;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Day8_2 {

    static int MAX_SCENIC_SCORE = 0;

    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        ArrayList<ArrayList<Integer>> arboles = new ArrayList<ArrayList<Integer>>();

        try {
            archivo = new File (Constantes.inputRoot + "input8.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            int cont = 0;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    procesaLinea(arboles,cont,linea);
                    cont++;
                }
            }

//            for(ArrayList<Integer> lineaS : arboles){
//                for(Integer pinta: lineaS) {
//                    System.out.print(pinta);
//                }
//                System.out.println("");
//            }

            visibles(arboles);

            System.out.println(MAX_SCENIC_SCORE);

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

    private static void procesaLinea(ArrayList<ArrayList<Integer>> arboles,int cont,String linea) {
        if(arboles.size()<=cont) arboles.add(new ArrayList<Integer>());
        for(int i=0; i<linea.length();i++){
            arboles.get(cont).add(i,Integer.valueOf(String.valueOf(linea.charAt(i))));
        }
    }

    private static int visibles(ArrayList<ArrayList<Integer>> arboles) {
        int scenicScore = 0;
        int colSize = arboles.get(0).size();
        int rowSize = arboles.size();

        for(int i=0; i<rowSize;i++){
            for(int j=0; j<colSize;j++){
                Integer arbol = arboles.get(i).get(j);
                scenicScore = esVisible(arbol,i,j,arboles,colSize,rowSize);
                if(scenicScore>MAX_SCENIC_SCORE) MAX_SCENIC_SCORE=scenicScore;
            }
        }

        return scenicScore;
    }

    public static int esVisible(Integer arbol,int linea,int columna,ArrayList<ArrayList<Integer>>arboles,int colSize,int rowSize){
        int scenifScoreEast = 0;
        int scenifScoreWest = 0;
        int scenifScoreNorth = 0;
        int scenifScoreSouth = 0;


        //Comprobamos visibilidad  Oeste
        if(columna>0) {
            for (int i = columna - 1; i >= 0; i--) {
                Integer compara = arboles.get(linea).get(i);
                if (compara < arbol)
                    scenifScoreWest++;
                if (compara >= arbol) {
                    scenifScoreWest++;
                    break;
                }
            }
        }

        //Comprobamos visibilidad  este
        if(columna+1<colSize) {
            for (int i = columna + 1; i < colSize; i++) {
                Integer compara = arboles.get(linea).get(i);
                if (compara < arbol)
                    scenifScoreEast++;
                if (compara >= arbol) {
                    scenifScoreEast++;
                    break;
                }
            }
        }

        if(linea>0) {
            //Comprobamos visibilidad norte
            for (int i = linea - 1; i >= 0; i--) {
                Integer compara = arboles.get(i).get(columna);
                if (compara < arbol)
                    scenifScoreNorth++;
                if (compara >= arbol) {
                    scenifScoreNorth++;
                    break;
                }
            }
        }

        if(linea+1<rowSize) {
            //Comprobamos visibilidad norte
            for (int i = linea + 1; i < rowSize; i++) {
                Integer compara = arboles.get(i).get(columna);
                if (compara < arbol)
                    scenifScoreSouth++;
                if (compara >= arbol) {
                    scenifScoreSouth++;
                    break;
                }
            }
        }

        return scenifScoreEast * scenifScoreWest * scenifScoreNorth * scenifScoreSouth;
    }

}
