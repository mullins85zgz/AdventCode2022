package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Day3_2 {

    final static int groupNumber = 3;
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        int totalScore=0;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File ("D:\\input3.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            ArrayList<String> grupoLineas= new ArrayList<String>();
            int cont=1;

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    grupoLineas.add(linea);
                    if(cont==groupNumber){
                        //Procesamos grupo
                        String letraComun = encuentraLetraComunGrupos(grupoLineas,0);
                        totalScore+= getPriority(letraComun);
                        //Reiniciamos
                        cont=1;
                        grupoLineas = new ArrayList<String>();
                    }else{
                        cont++;
                    }
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

    public static String encuentraLetraComunGrupos(ArrayList<String> grupoLineas,int posicion){
        String letraABuscar = "";

        for(int i=0; i < grupoLineas.size();i++){
            String linea=grupoLineas.get(i);
            //Letra a leer (Miramos siempre sobre la primera linea del grupo ya que lo que buscamos esta en las 3 del grupo

            if(i==0){
                letraABuscar = String.valueOf(linea.charAt(posicion));
            }
            if(!linea.contains(letraABuscar)){
                return encuentraLetraComunGrupos(grupoLineas,++posicion);
            }
        }

        return letraABuscar;
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
