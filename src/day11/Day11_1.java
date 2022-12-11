package day11;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day11_1 {



    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;


        try {
            archivo = new File (Constantes.inputRoot + "input11tst.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            HashMap<Integer, Monkey> monkeys = new HashMap<>();

            // Lectura del fichero
            String linea;

            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                     procesaLinea(linea,monkeys);
                }
            }

            System.out.println("TEST");
            System.out.println(calculateTimes(monkeys,20,2));
            System.out.println("TEST2");


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

    public static int calculateTimes(HashMap<Integer, Monkey> monkeys, int iteraciones, int searchMaxMonkeys){
        int sumTimes = 1;
        for(int i=0;i<iteraciones;i++){
            itera(monkeys);
        }
        List<Map.Entry<Integer, Monkey>> entries = new ArrayList<Map.Entry<Integer, Monkey>>(monkeys.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Integer, Monkey>>() {
            public int compare(Map.Entry<Integer, Monkey> o1, Map.Entry<Integer, Monkey> o2) {
                return ((Integer)o1.getValue().getInspectedItemCount()).compareTo(o2.getValue().getInspectedItemCount()) * -1;  // reverse order
            }});

        Map<Integer, Monkey> sortedMap = new LinkedHashMap<Integer, Monkey>();
        for (Map.Entry<Integer, Monkey> entry : entries)
            sortedMap.put(entry.getKey(), entry.getValue());

        int cont=0;
        for(Monkey mono:sortedMap.values()){
            if(cont<searchMaxMonkeys)   sumTimes*= mono.getInspectedItemCount();
            System.out.println(mono.getMonkeyId() + " - " + mono.getInspectedItemCount());
            cont++;
        }

        return  sumTimes;
    }

    public static void itera(HashMap<Integer, Monkey> monkeys){
        for(Monkey mono:monkeys.values()){
            ArrayList<Integer> removeItems = new ArrayList<>();
            for(Integer item:mono.getItems()){
                Integer newItem = item.intValue();

                //Update inspection
                mono.setInspectedItemCount(mono.getInspectedItemCount()+1);

                //Ejecutamos operaciones
                newItem += mono.getAdd();
                newItem *= mono.getMultiply();
                if(mono.isRaise())
                    newItem=newItem*newItem;

                //Despreocupamos al mono
                newItem = Math.floorDiv(newItem, Monkey.DIVISOR_MONO);

                // Testeamos si es disible
                boolean isDivisble = false;
                if(newItem%mono.getDivisibleBy()==0)
                    isDivisble=true;

                //Movemos al mono
                int monoDestino = isDivisble?mono.getMonkeyIdIfTrue():mono.getMonkeyIdIfFalse();
                monkeys.get(monoDestino).getItems().add(newItem);

                //Eliminamos el item original
                removeItems.add(item);
            }
            for(Integer item:removeItems){
                mono.getItems().remove(item);
            }
        }
    }

    /**
     * Lectura de los monos
     * @param linea
     * @param monkeys
     * @throws Exception
     */
    public static void procesaLinea(String linea,HashMap<Integer, Monkey> monkeys) throws Exception {
        linea = linea.trim();
        Monkey mono = null;
        if(linea.startsWith("Monkey")){
            int monkeyId = Integer.valueOf(linea.substring(7,8));
            mono = new Monkey();
            mono.setActualMonkey(monkeyId);
            mono.setMonkeyId(mono.getActualMonkey());
            if(monkeys.containsKey(mono.getActualMonkey()))   throw new Exception("No deberia haber monos con este ID ya metidos");
            monkeys.put(mono.getActualMonkey(),mono);
        }else{
            mono = monkeys.get(Monkey.getActualMonkey());
        }
        //ITEMS
        if(linea.startsWith("Starting")){
            String[] partes = linea.split(":");
            String[] items = partes[1].split(",");
            for(String item:items){
                mono.getItems().add(Integer.valueOf(item.trim()));
            }
            // OPERATION
        }else if(linea.startsWith("Operation")){
            String operation = linea.substring(21,22);
            if("*".equals(operation)){
                if("old".equals(linea.substring(23,linea.length()))){
                    mono.setRaise(true);
                }else{
                    mono.setMultiply(Integer.valueOf(linea.substring(23,linea.length())));
                }
            }
            if("+".equals(operation)){
                mono.setAdd(Integer.valueOf(linea.substring(23,linea.length())));
            }
            //TEST
        }else if(linea.startsWith("Test")){
            mono.setDivisibleBy(Integer.valueOf(linea.substring(19,linea.length())));
        }else if(linea.startsWith("If true")){
            mono.setMonkeyIdIfTrue(Integer.valueOf(linea.substring(25,linea.length())));
        }else if(linea.startsWith("If false")){
            mono.setMonkeyIdIfFalse(Integer.valueOf(linea.substring(26,linea.length())));
        }
    }
}
class Monkey{
    final static int DIVISOR_MONO = 3;
    int monkeyId=0;
    ArrayList<Integer> items = new ArrayList<>();
    int multiply = 1;
    int add = 0;
    boolean raise = false;
    int divisibleBy = 0;
    int monkeyIdIfTrue = -1;
    int monkeyIdIfFalse = -1;
    int inspectedItemCount = 0;

    static int actualMonkey = 0;

    public int getMonkeyId() {
        return monkeyId;
    }

    public void setMonkeyId(int monkeyId) {
        this.monkeyId = monkeyId;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public void setItems(ArrayList<Integer> items) {
        this.items = items;
    }

    public int getMultiply() {
        return multiply;
    }

    public void setMultiply(int multiply) {
        this.multiply = multiply;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }

    public int getDivisibleBy() {
        return divisibleBy;
    }

    public void setDivisibleBy(int divisibleBy) {
        this.divisibleBy = divisibleBy;
    }

    public int getMonkeyIdIfTrue() {
        return monkeyIdIfTrue;
    }

    public void setMonkeyIdIfTrue(int monkeyIdIfTrue) {
        this.monkeyIdIfTrue = monkeyIdIfTrue;
    }

    public int getMonkeyIdIfFalse() {
        return monkeyIdIfFalse;
    }

    public void setMonkeyIdIfFalse(int monkeyIdIfFalse) {
        this.monkeyIdIfFalse = monkeyIdIfFalse;
    }

    public static int getActualMonkey() {
        return actualMonkey;
    }

    public static void setActualMonkey(int actualMonkey) {
        Monkey.actualMonkey = actualMonkey;
    }

    public boolean isRaise() {
        return raise;
    }

    public void setRaise(boolean raise) {
        this.raise = raise;
    }

    public int getInspectedItemCount() {
        return inspectedItemCount;
    }

    public void setInspectedItemCount(int inspectedItemCount) {
        this.inspectedItemCount = inspectedItemCount;
    }
}
