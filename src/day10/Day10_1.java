package day10;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day10_1 {

    static int sum = 0;


    final static String ADDX = "addx";
    final static String NOOP = "noop";

    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        CyclesRecord2 cyclesRecord = new CyclesRecord2();

        try {
            archivo = new File (Constantes.inputRoot + "input10.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);


            String linea;

            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    procesaLinea(linea,cyclesRecord);
                }
            }

            int sumaTotal=0;
            for(Integer suma: cyclesRecord.getSuma()){
                sumaTotal+=suma;
            }
            System.out.println(sumaTotal);


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
    public static void procesaLinea(String linea, CyclesRecord2 cyclesRecord){
        cyclesRecord.incCurrentCycle();
        if(linea.startsWith(NOOP)){
            cyclesRecord.addInstruction(NOOP,0);
        }
        if(linea.startsWith(ADDX)){
            String[] valores = linea.split(" ");
            String cantidad = valores[1];
            cyclesRecord.addInstruction(ADDX,Integer.valueOf(cantidad));
        }
    }
}
class CyclesRecord{
    int x=1;
    int currentCycle=0;
    int REQ_CYCLES=1;
    final static int FIRST_CYCLE=20;
    final static int NEXT_CYCLE_SUM=40;
    HashMap<Integer,Integer> buffer = new HashMap<>();
    ArrayList<Integer> suma = new ArrayList<>();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public void incCurrentCycle() {
        this.currentCycle = currentCycle + 1;
    }

    public void addInstruction(String instruction,int cantidad){
        int posBuffer=bufferPosition(instruction);
        this.buffer.put(posBuffer,cantidad);
//        System.out.println("BUFFER CICLO [" + posBuffer + "] CANTIDAD [" + cantidad + "]" );
        //Al finalizar este ciclo es cuando sumamos
//        System.out.print("CICLO:[" + getCurrentCycle() + "] INSTRUCCION " + instruction +" - " + cantidad + " VALOR ACTUAL X a [" + getX() + "]");
        actualizaSuma();
        ejecutaAccionesSuma();
//        System.out.println (" - AL FINALIZAR ACTUALIZA X a [" + getX() + "]");
    }

    public int bufferPosition(String instruction){
        int ret=getCurrentCycle();
        if(buffer.size()==0) return ret + (Day10_1.NOOP.equals(instruction)?0:1);
        for(Integer bufferPos:buffer.keySet()){
            if(bufferPos>ret)
                ret=bufferPos;
        }
        return ret +(Day10_1.NOOP.equals(instruction)?1:2);
    }

    public void actualizaSuma(){
        if(FIRST_CYCLE==getCurrentCycle() || (FIRST_CYCLE + getCurrentCycle())%NEXT_CYCLE_SUM==0){
            suma.add(getCurrentCycle()*getX());
                    System.out.println("CICLO:[" + getCurrentCycle() + "] " + " ACTUALIZA X a [" + getX() + "]");
        }
    }

    public void ejecutaAccionesSuma(){
        int buffered = currentCycle;
        if(buffer.containsKey(buffered)) {
            setX(getX() + buffer.get(buffered));
            buffer.remove(buffered);    //Lo eliminamos
        }
    }

    public ArrayList<Integer> getSuma() {
        return suma;
    }

    public void setSuma(ArrayList<Integer> suma) {
        this.suma = suma;
    }

    CyclesRecord(){
    }
}
