package day9;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day9_1 {


    static HashMap<String,String> marcados = new HashMap<String,String>();
//    static int TAIL_X = TOTAL_GRID/2;
    static int TAIL_X = 0;
//    static int TAIL_Y = TOTAL_GRID/2;
    static int TAIL_Y = 0;
//    static int HEAD_X = TOTAL_GRID/2;
    static int HEAD_X = 0;
//    static int HEAD_Y = TOTAL_GRID/2;
    static int HEAD_Y = 0;

    final static String LEFT = "L";
    final static String RIGHT = "R";
    final static String UP = "U";
    final static String DOWN = "D";

    static int cont = 0;

    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;


        try {
            archivo = new File (Constantes.inputRoot + "input9.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);



            // Lectura del fichero
            String linea;

            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    String[] p = linea.split(" ");
                    cont++;
                    procesaLinea(linea);
                }
            }


            System.out.println(suma());


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

    /**
     * Solucion hardcodeada (funciona pero no vale si aumentas la cuerda, ya que los nudos se mueven respecto al anterior)
     * La parte 2 funciona tanto para este caso (longitud de cuerda 2) como el que sea, como longitud de cuerda 10
     * @param linea
     */
    public static void procesaLinea(String linea){
        String[] valores = linea.split(" ");
        String movimiento = valores[0];
        int cantidad = Integer.valueOf(valores[1]);



        for(int i=0;i<cantidad;i++){
            if(RIGHT.equals(movimiento)){
                HEAD_X++;
                boolean hayQueMoverTail = hayQueMover();
                if(hayQueMoverTail){
                    TAIL_X++;
                    TAIL_Y=HEAD_Y;
                }
            }
            if(LEFT.equals(movimiento)){
                HEAD_X--;
                boolean hayQueMoverTail = hayQueMover();
                if(hayQueMoverTail){
                    TAIL_X--;
                    TAIL_Y=HEAD_Y;
                }
            }
            if(DOWN.equals(movimiento)){
                HEAD_Y--;
                boolean hayQueMoverTail = hayQueMover();
                if(hayQueMoverTail){
                    TAIL_Y--;
                    TAIL_X=HEAD_X;
                }
            }
            if(UP.equals(movimiento)){
                HEAD_Y++;
                boolean hayQueMoverTail = hayQueMover();
                if(hayQueMoverTail){
                    TAIL_Y++;
                    TAIL_X=HEAD_X;
                }
            }
//            grid[TAIL_Y][TAIL_X] = 1;
            marcados.put("Y"+TAIL_Y+"X"+TAIL_X,"#");
        }

    }


    public static boolean hayQueMover(){
        int dRow = HEAD_X - TAIL_X;
        int dCol = HEAD_Y - TAIL_Y;
        boolean mismaFilaOColumna = TAIL_X == HEAD_X || HEAD_Y  == TAIL_Y;
        int distanciaAdyacente = Math.abs(dRow) + Math.abs(dCol);   //Ver Manhattan Distance
        if ((mismaFilaOColumna && distanciaAdyacente > 1) || distanciaAdyacente > 2) {
            return true;    //Si es la primera "jugada" ambas estan en la misma, y distanciaAdyacente es 0, por lo que no hay movimiento
        }else{
            return false;
        }
    }

    public static int suma(){
        int suma = 0;

//        for(int i=grid.length-1;i>=0;i--){
//            int[] filas = grid[i];
//            for(int valor:filas){
//                suma+=valor;
//            }
//        }

        for(String itera:marcados.values())
            suma++;

        return suma;
    }




}
