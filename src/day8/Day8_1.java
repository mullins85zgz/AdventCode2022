package day8;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Day8_1 {



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

            System.out.println(visibles(arboles));

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
        int visibles = 0;
        int colSize = arboles.get(0).size();
        int rowSize = arboles.size();

        for(int i=0; i<rowSize;i++){
            for(int j=0; j<colSize;j++){
                Integer arbol = arboles.get(i).get(j);
                boolean esVisible = esVisible(arbol,i,j,arboles,colSize,rowSize);
                if(esVisible) visibles++;
            }
        }

        return visibles;
    }

    public static boolean esVisible(Integer arbol,int linea,int columna,ArrayList<ArrayList<Integer>>arboles,int colSize,int rowSize){
        boolean visible = true;

        //En los bordes siempre es visible
        if(linea==0 || columna== 0 || linea==rowSize -1  || columna== colSize -1)
            return true;

        boolean visibleOeste=true;
        //Comprobamos visibilidad  Oeste
        for(int i=0; i<columna;i++){
            Integer compara = arboles.get(linea).get(i);
            if(compara>=arbol)
                visibleOeste =  false;
        }

        boolean visibleEste=true;
        //Comprobamos visibilidad  este
        for(int i=columna+1; i<colSize;i++){
            Integer compara = arboles.get(linea).get(i);
            if(compara>=arbol)
                visibleEste = false;
        }

        boolean visibleNorte=true;
        //Comprobamos visibilidad norte
        for(int i=0; i<linea;i++){
            Integer compara = arboles.get(i).get(columna);
            if(compara>=arbol)
                visibleNorte = false;
        }

        boolean visibleSur=true;
        //Comprobamos visibilidad norte
        for(int i=linea+1; i<rowSize;i++){
            Integer compara = arboles.get(i).get(columna);
            if(compara>=arbol)
                visibleSur = false;
        }

        return visibleSur || visibleNorte || visibleEste || visibleOeste;
    }

}
