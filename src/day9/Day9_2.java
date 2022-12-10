package day9;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day9_2 {

    final static int TOTAL_GRID=800;
    //    static int[][] grid = new int[TOTAL_GRID][TOTAL_GRID];
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



            //grid[0][0] = 1; //Iniciamos la primera celda

            // Lectura del fichero
            String linea;

            Rope rope = new Rope();

            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    String[] p = linea.split(" ");
                    cont++;
                    procesaLinea(linea,rope);
                }
            }


            System.out.println(rope.suma());


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
    public static void procesaLinea(String linea,Rope rope){
        String[] valores = linea.split(" ");
        String movimiento = valores[0];
        int cantidad = Integer.valueOf(valores[1]);
        rope.actualiza(cantidad,movimiento);
    }

}

class Rope{
    static HashMap<String,String> marcados = new HashMap<String,String>();
    final static String LEFT = "L";
    final static String RIGHT = "R";
    final static String UP = "U";
    final static String DOWN = "D";
    final static int POS_X = 0;
    final static int POS_Y = 1;
    int ROPE_LENGTH=10;

    Rope(){
        //Inicializamos
        System.out.println("INIT ROPE...");
        for(int i=0;i<ROPE_LENGTH;i++){
            posiciones[i][POS_X] = 0;
            posiciones[i][POS_Y] = 0;
        }
    }

    int[][] posiciones = new int[ROPE_LENGTH][2];
    void actualiza(int cantidadMov,String movimiento) {
        int ultimoMov = 0;
        for (int i = 0; i < cantidadMov; i++) {
            for (int j = 0; j < ROPE_LENGTH ; j++) { //LA cola no trae nada detras de ahi el -1
                //Movemos la cabeza
                if(j==0) {
                    if (RIGHT.equals(movimiento)) {
                        posiciones[j][POS_X] = posiciones[j][POS_X] + 1;
                    }else
                    if (LEFT.equals(movimiento)) {
                        if (j == 0) posiciones[j][POS_X] = posiciones[j][POS_X] - 1;
                    }else
                    if (DOWN.equals(movimiento)) {
                        if (j == 0) posiciones[j][POS_Y] = posiciones[j][POS_Y] - 1;
                    }else
                    if (UP.equals(movimiento)) {
                        if (j == 0) posiciones[j][POS_Y] = posiciones[j][POS_Y] + 1;
                    }
                }else{
                    //Ahora debemos detectar el movimiento que tiene que hacer el siguiente
                    boolean hayQueMoverTail = hayQueMover(posiciones,j-1);
                    if (hayQueMoverTail) {
                        //Movimiento horizontal
                        int movX =  ( posiciones[j-1][POS_X] - posiciones[j][POS_X] );
                        if(movX>0) movX=1; if(movX<0) movX=-1; if(movX==0) movX=0;
                        posiciones[j][POS_X] = posiciones[j][POS_X] + movX;

                        //Movimiento vertical
                        int movY =  ( posiciones[j-1][POS_Y] - posiciones[j][POS_Y] );
                        if(movY>0) movY=1; if(movY<0) movY=-1; if(movY==0) movY=0;
                        posiciones[j][POS_Y] = posiciones[j][POS_Y] + movY;
                    }
                }


                //Solo para la "cola"
                if(j==ROPE_LENGTH-1) {
                    if(!marcados.containsKey("Y" + posiciones[j][POS_Y] + "X" + posiciones[j][POS_X])) {
                        System.out.println("CLAVE: [Y " + posiciones[j][POS_Y] + "X " + posiciones[j][POS_X] + "]");
                    }

                    marcados.put("Y" + posiciones[j][POS_Y] + "X" + posiciones[j][POS_X], "#");
                }
            }
        }
    }
    public static boolean hayQueMover(int[][] posiciones,int indice){
        int HEAD_X = posiciones[indice][POS_X];
        int TAIL_X = posiciones[indice + 1][POS_X];
        int HEAD_Y = posiciones[indice][POS_Y];
        int TAIL_Y = posiciones[indice + 1][POS_Y];
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

        for(String itera:marcados.values())
            suma++;

        return suma;
    }
}
