package day10;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day10_2 {

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

            for(String[] lineaCRT:cyclesRecord.getSalidaVideo()){
                for(String pixel:lineaCRT){
                    System.out.print(pixel);
                }
                System.out.println("");
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
class CyclesRecord2 {
    int x=1;
    int currentCycle=0;
    int REQ_CYCLES=1;
    final static int FIRST_CYCLE=20;
    final static int NEXT_CYCLE_SUM=40;
    final static int SPRITE_SIZE=3;
    final static int CRT_SIZE=40;
    final static int CRT_VSIZE=6;
    final static String PIXEL_VIVO="#";
    final static String PIXEL_MUERTO=".";
    HashMap<Integer,Integer> buffer = new HashMap<>();
    ArrayList<Integer> suma = new ArrayList<>();
    int[] sprite= new int[CRT_SIZE];
    String[][] salidaVideo = new String[CRT_VSIZE][CRT_SIZE];

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
        //Se pinta antes
        int fila = (int) Math.floor(((getCurrentCycle()-1) / CRT_SIZE)) ;
        actualizaSalidaVideo(fila,getCurrentCycle());
        actualizaSuma();
        ejecutaAccionesSuma();
        actualizaSprite(getX());
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

    public void actualizaSprite(int posicion){
        for(int i=0;i<CRT_SIZE;i++){
            sprite[i]=0;
        }
        for(int i=0;i<SPRITE_SIZE;i++){
            if((i+posicion-1)>=0 &&  (i+posicion-1)<CRT_SIZE)
                sprite[i+posicion-1]=1;
        }
    }

    public void actualizaSalidaVideo(int fila,int ciclo){
        int posicionH = ciclo - (fila*CRT_SIZE) - 1;
        pintaSprite();
        if(sprite[posicionH] > 0)
            salidaVideo[fila][posicionH] = PIXEL_VIVO;
        else
            salidaVideo[fila][posicionH] = PIXEL_MUERTO;
    }

    CyclesRecord2(){
        for(int i=0;i<SPRITE_SIZE;i++){
            sprite[i]=1;
        }
    }

    public String[][] getSalidaVideo() {
        return salidaVideo;
    }

    public void pintaSprite(){
        for(int i=0;i<CRT_SIZE;i++){
            System.out.print(sprite[i]==1?"#":".");
        }
        System.out.println("");
    }
}
