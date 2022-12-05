package day2;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Day2_2 {

    //Indexes
    final static int ROCK = 0;
    final static int PAPER = 1;
    final static int SCISSORS = 2;

    final static String[] JUGADAS_RIVALES = {"A","B","C"};
    final static String[] JUGADAS_PROPIAS = {"X","Y","Z"};
    final static int[] PUNTUACION = {1,2,3};

    final static int[] PUNTUACION_JUGADA = {0,3,6};

    static int [][] tablaResultados =  null;



    final static int WIN_SCORE = 6;
    final static int DRAW_SCORE = 3;
    final static int LOSE_SCORE = 0;
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        // Definicion de las jugadas, donde el primer elemento del array es la jugada rival y el segundo la jugada nuestra (al reves que el dia 2-1)
        tablaResultados = new int[][]{
                {DRAW_SCORE, WIN_SCORE,  LOSE_SCORE },
                {LOSE_SCORE, DRAW_SCORE, WIN_SCORE},
                {WIN_SCORE, LOSE_SCORE, DRAW_SCORE}
        };

        int aux = 0;

        try {
            archivo = new File (Constantes.inputRoot +"input2.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    String jugadaRival = linea.substring(0,1);
                    String jugadaPropia = linea.substring(2,3);
//                    System.out.println(jugadaRival + "-"  + jugadaPropia);
                    aux+=calculatePlayScore(jugadaRival,jugadaPropia);
                }
            }

            System.out.println(aux);
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

    public static int calculatePlayScore (String jugadaRival, String resultadoForzado){
        int score = 0;

        int posicionResultadoForzado = findIndex(JUGADAS_PROPIAS,resultadoForzado);
        int posicionJugadaRival = findIndex(JUGADAS_RIVALES,jugadaRival);

        // Segun resultado esperado sumamos
        int playScore  = PUNTUACION_JUGADA[posicionResultadoForzado];
        score += playScore;

        //Ahora, debemos saber que eleccion hacemos, y segun la misma sumamos
        int eleccion = devuelveJugada(tablaResultados[posicionJugadaRival],PUNTUACION_JUGADA[posicionResultadoForzado]);
        int chooseScore  = PUNTUACION[eleccion];
        score += chooseScore;

        return score;
    }

    public static int findIndex(String arr[], String t)
    {
        // Creating ArrayList
        ArrayList<String> clist = new ArrayList<>();

        // adding elements of array
        // to ArrayList
        for (String i : arr)
            clist.add(i);

        // returning index of the element
        return clist.indexOf(t);
    }

    public static int devuelveJugada(int[] rival, int resultadoForzado){
        int posicionJugada = -1;

        for(int i=0; i < rival.length;i++){
            if(resultadoForzado==rival[i])  return i;   //Devolvemos el indice del resultado esperado vs el del rival para saber cuanto vale la jugada
        }

        return posicionJugada;
    }
}
