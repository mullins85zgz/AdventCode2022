package day3;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Day3_1 {
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        int totalScore=0;

        try {
            archivo = new File (Constantes.inputRoot +"input3.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    int length = linea.length();
                    String part1 = linea.substring(0,length/2);
                    String part2 = linea.substring(length/2,length);
//                    System.out.println(linea + "-" + part1 + "-" + part2);
                    String letraComun = encuentraLetraComun(part1,part2);
//                    System.out.println(letraComun);
                    totalScore+=getPriority(letraComun);
//                    System.out.println(letraComun + "-" + getPriority(letraComun));
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

    public static String encuentraLetraComun(String s1,String s2){
        for(int i = 0; i < s1.length();i++){
            if(s2.contains(String.valueOf(s1.charAt(i)))){
                return String.valueOf(s1.charAt(i));
            }
        }

        return "";
    }

    public static int getPriority(String letra){
        int priority=0;

        //Restaremos estos valores al valor ASCII
        //Nos basamos en esta tabla https://www.cs.cmu.edu/~pattis/15-1XX/common/handouts/ascii.html
        int asciiRestaUpper = 64;
        int asciiRestaLower = 96;
        int sumaPrioridadUpper = 26;

        boolean isLowerCase = true;

        if(Character.isUpperCase(letra.charAt(0)))
            isLowerCase=false;

        if(isLowerCase){
            priority = (int)(letra.charAt(0)) - asciiRestaLower;
        }else{
            priority = (int)(letra.charAt(0)) - asciiRestaUpper + sumaPrioridadUpper;
        }

        return priority;
    }

}
