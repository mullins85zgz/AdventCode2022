package day5;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class Day5_1 {

    static ArrayList<LinkedList<String>> stacks;

    final static int TOTAL_STACKS = 9;
    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        String message="";

        //LinkedList
        // addLast --> añado al final (equivale a add)
        // addFirst --> añado al inicio
        // addAll --> añade coleccion
        // remove --> quita al principio
        // remove(index) --> especifica
        // poll Retrieves and removes the head (first element) of this list.
        // pollLast Retrieves and removes the last element of this list, or returns null if this list is empty.
        // push Pushes an element onto the stack represented by this list. In other words, inserts the element at the front of this list.This method is equivalent to addFirst(E).
        // pop Pops an element from the stack represented by this list. In other words, removes and returns the first element of this list.This method is equivalent to removeFirst().
        // removeLastOccurrence
        // Coceptos:
        //      Aqui first es la caja mas arriba del stack
        //      last sera la que esta al fondo. En el ejemplo uno siempre jugamos con la de arriba


        stacks = new ArrayList<LinkedList<String>>();
        //init()
        for(int i=0;i<TOTAL_STACKS;i++){
            LinkedList<String> nuevoStack = new LinkedList<String>();
            stacks.add(nuevoStack);
        }

        //Array Ejemplo
//        LinkedList<String> stack1 = new LinkedList<String>();
//        stack1.addFirst("Z");
//        stack1.addFirst("N");
//        stacks.add(stack1);
//        LinkedList<String> stack2 = new LinkedList<String>();
//        stack2.addFirst("M");
//        stack2.addFirst("C");
//        stack2.addFirst("D");
//        stacks.add(stack2);
//        LinkedList<String> stack3 = new LinkedList<String>();
//        stack3.addFirst("P");
//        stacks.add(stack3);

        try {
            archivo = new File (Constantes.inputRoot + "input5b.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    lectura(stacks,linea);
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


        // Ordenacion
        try {
            archivo = new File (Constantes.inputRoot + "input5a.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    String[] lectura = linea.split(" ");
                    int cantidad = Integer.valueOf(lectura[1]);
                    int origen = Integer.valueOf(lectura[3]) - 1;   //Para cuadrar con el indice
                    int destino = Integer.valueOf(lectura[5]) - 1;  //Para cuadrar con el indice
                    mueve(stacks,cantidad,origen,destino);
                }
            }

            System.out.println(pintaMensaje(stacks));

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

    public static void mueve(ArrayList<LinkedList<String>> stacks,int cantidad,int origen,int destino){
        for(int i=0; i < cantidad; i++){
            String letraOrigen = stacks.get(origen).removeFirst();
            stacks.get(destino).addFirst(letraOrigen);
        }
    }

    public static String pintaMensaje(ArrayList<LinkedList<String>> stacks){
        String res="";
        for(LinkedList<String> stack:stacks){
            res+= stack.getFirst();
        }
        return res;
    }

    public static void lectura(ArrayList<LinkedList<String>> stacks,String linea){
        for(int i = 0; i< TOTAL_STACKS; i++){
            String letra = linea.substring(1+ (i==0?0:4*i),2 + (i==0?0:4*i));
            if(!"".equals(letra.trim())){
                if(stacks.get(i)!=null)
                    stacks.get(i).addLast(letra);
            }
        }
    }

}
