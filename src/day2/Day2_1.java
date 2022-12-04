package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Day2_1 {

    //Indexes
    final static int ROCK = 0;
    final static int PAPER = 1;
    final static int SCISSORS = 2;

    final static String[] JUGADAS_RIVALES = {"A","B","C"};
    final static String[] JUGADAS_PROPIAS = {"X","Y","Z"};
    final static int[] PUNTUACION = {1,2,3};

    static int [][] tablaResultados =  null;



    final static int WIN_SCORE = 6;
    final static int DRAW_SCORE = 3;
    final static int LOSE_SCORE = 0;
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        // Definicion de las jugadas, donde el primer elemento del array es la jugada nuestra y el segundo la jugada rival
        tablaResultados = new int[][]{
                {DRAW_SCORE, LOSE_SCORE, WIN_SCORE  },
                {WIN_SCORE, DRAW_SCORE, LOSE_SCORE},
                {LOSE_SCORE, WIN_SCORE, DRAW_SCORE}
        };

        int aux = 0;

        try {
            archivo = new File ("D:\\input2.txt");
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

    public static int calculatePlayScore (String jugadaRival, String jugadaPropia){
        int score = 0;

        int posicionJugadaPropia = findIndex(JUGADAS_PROPIAS,jugadaPropia);
        int posicionJugadaRival = findIndex(JUGADAS_RIVALES,jugadaRival);

        // Player score based on election
        int chooseScore  = PUNTUACION[posicionJugadaPropia];
        score += chooseScore;

        //Result of the play
        int playScore = tablaResultados[posicionJugadaPropia][posicionJugadaRival];
        score += playScore;

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
}
