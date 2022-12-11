package day11;

import utils.Constantes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

/**
 * Today is difficult. I had to read some post on reddit
 * More info: https://en.wikipedia.org/wiki/Congruence_relation
 *
 * I'll try to give a good explanation for the part 2 that doesn't require fancy math.
 *
 * Basically you want to find a lower worry level that produces the same result when checking for divisibility because that check determines the path the items take.
 *
 * Consider this sequence as example: Lowering worry level of 20 Using 2,3,4 as divisors.
 *
 * 20 is divisible by 2 and 4 but not 3. Therefore we need a lower number that still satisfies that rule. Possible alternatives: 4, 8 and 16.
 *
 * But some monkeys may cause problem by adding a flat number, like +1 to the worry level. Our number must also be addition proof:
 *
 * 20+1=21 has 3 as the only divisor, therefore, if we add 1 to 4,8,16: obtaining 5,9,17 then the result must follow the same rule.
 *
 * 5 doesn't work because it has no divisors.
 *
 * 9 works because it only has 3 as divisor.
 *
 * 17 doesn't work because it has no divisors.
 *
 * The only number that survives both conditions is 8. If you look at the table you can notice that the distance between 8 and it's nearest break-point, 12, is the same between 20 and 24. Therefore you can conclude that 20 - 8 = 12 because the divisibility follows a pattern of length 12. When the pattern ends, it starts again.
 *
 * How it changes if the target worry level becomes 35?.
 *
 * 35 - 12 = 23, but since 23 contains 12 then we can subtract it again to get 11. This operation is the definition of the remainder itself: "Subtract 12 as many times as you can, and when you can't do it anymore, tell me the number that remains"
 *
 * The final part is. How do we get that magic number 12?. Easy: it's the Least Common Multiple of 2,3,4. Because the L.C.M, as you can see in the table, marks the end of the divisibility pattern.
 *
 * In short: Calculate the remainder of the target worry level with the L.C.M of all the divisors.
 *
 */

public class Day11_2 {



    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;


        try {
            archivo = new File (Constantes.inputRoot + "input11.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            HashMap<Integer, Monkey2> monkeys = new HashMap<>();

            // Lectura del fichero
            String linea;

            while((linea=br.readLine())!=null){
                if(linea.trim().length()!=0){
                    procesaLinea(linea,monkeys);
                }
            }

            Monkey2.allDivisors = lcm(Monkey2.divisors);

            System.out.println("TEST");
            System.out.println(calculateTimes(monkeys,10000   ,2));
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

    private static int lcm(int a, int b)
    {
        return a * (b / gcd(a, b));
    }

    private static int lcm(ArrayList<Integer> input)
    {
        int result = input.get(0);
        for(int i = 1; i < input.size(); i++) result = lcm(result, input.get(i));
        return result;
    }

    private static int gcd(int a, int b)
    {
        while (b > 0)
        {
            int temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    private static int gcd(int[] input)
    {
        int result = input[0];
        for(int i = 1; i < input.length; i++) result = gcd(result, input[i]);
        return result;
    }

    public static long calculateTimes(HashMap<Integer, Monkey2> monkeys, int iteraciones, int searchMaxMonkeys){
        long sumTimes = 1;
        for(int i=0;i<iteraciones;i++){
            itera(monkeys);
            System.out.println("PROGRESO " + i);
        }
        List<Map.Entry<Integer, Monkey2>> entries = new ArrayList<Map.Entry<Integer, Monkey2>>(monkeys.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Integer, Monkey2>>() {
            public int compare(Map.Entry<Integer, Monkey2> o1, Map.Entry<Integer, Monkey2> o2) {
                return ((Integer)o1.getValue().getInspectedItemCount()).compareTo(o2.getValue().getInspectedItemCount()) * -1;  // reverse order
            }});

        Map<Integer, Monkey2> sortedMap = new LinkedHashMap<Integer, Monkey2>();
        for (Map.Entry<Integer, Monkey2> entry : entries)
            sortedMap.put(entry.getKey(), entry.getValue());

        int cont=0;
        for(Monkey2 mono:sortedMap.values()){
            if(cont<searchMaxMonkeys)   sumTimes*= mono.getInspectedItemCount();
            System.out.println(mono.getMonkeyId() + " - " + mono.getInspectedItemCount());
            cont++;
        }

        return  sumTimes;
    }

    public static void itera(HashMap<Integer, Monkey2> monkeys){

        for(Monkey2 mono:monkeys.values()){
            ArrayList<BigInteger> removeItems = new ArrayList<>();
            for(BigInteger item:mono.getItems()){



                BigInteger newItem = new BigInteger(item.toString());

                //Update inspection
                mono.setInspectedItemCount(mono.getInspectedItemCount()+1);

                //Ejecutamos operaciones
                newItem = newItem.add(new BigInteger(String.valueOf(mono.getAdd())));
                newItem = newItem.multiply(new BigInteger(String.valueOf(mono.getMultiply())));
                if(mono.isRaise())
                    newItem=newItem.multiply(newItem);

                //Despreocupamos al mono
//                newItem = newItem.divide(new BigInteger("3"));

                //REDUCCION (AQUI ESTA LA MIGA DEL PROBLEMA MATEMATICO)
                int LCM = Monkey2.allDivisors;
                newItem = Monkey2.reduce(newItem,new BigInteger(String.valueOf(LCM)));

                // Testeamos si es disible
                boolean isDivisble = false;
                if(newItem.mod(new BigInteger(String.valueOf(mono.getDivisibleBy()))).intValue()==0)
                    isDivisble=true;

                //Movemos al mono
                int monoDestino = isDivisble?mono.getMonkeyIdIfTrue():mono.getMonkeyIdIfFalse();
                monkeys.get(monoDestino).getItems().add(newItem);

                //Eliminamos el item original
                removeItems.add(item);
            }
            for(BigInteger item:removeItems){
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
    public static void procesaLinea(String linea,HashMap<Integer, Monkey2> monkeys) throws Exception {
        linea = linea.trim();
        Monkey2 mono = null;
        if(linea.startsWith("Monkey")){
            int monkeyId = Integer.valueOf(linea.substring(7,8));
            mono = new Monkey2();
            mono.setActualMonkey(monkeyId);
            mono.setMonkeyId(mono.getActualMonkey());
            if(monkeys.containsKey(mono.getActualMonkey()))   throw new Exception("No deberia haber monos con este ID ya metidos");
            monkeys.put(mono.getActualMonkey(),mono);
        }else{
            mono = monkeys.get(Monkey2.getActualMonkey());
        }
        //ITEMS
        if(linea.startsWith("Starting")){
            String[] partes = linea.split(":");
            String[] items = partes[1].split(",");
            for(String item:items){
                mono.getItems().add(new BigInteger(item.trim()));
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
            Monkey2.allDivisors*=Integer.valueOf(linea.substring(19,linea.length()));
            Monkey2.divisors.add(Integer.valueOf(linea.substring(19,linea.length())));
        }else if(linea.startsWith("If true")){
            mono.setMonkeyIdIfTrue(Integer.valueOf(linea.substring(25,linea.length())));
        }else if(linea.startsWith("If false")){
            mono.setMonkeyIdIfFalse(Integer.valueOf(linea.substring(26,linea.length())));
        }
    }
}
class Monkey2 {
    final static int DIVISOR_MONO = 3;
    static int allDivisors = 1;
    static ArrayList<Integer> divisors = new ArrayList();
    int monkeyId=0;
    ArrayList<BigInteger> items = new ArrayList<>();
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

    public ArrayList<BigInteger> getItems() {
        return items;
    }

    public void setItems(ArrayList<BigInteger> items) {
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
        Monkey2.actualMonkey = actualMonkey;
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

    public static BigInteger reduce(BigInteger origen,BigInteger resta){
//        BigInteger ret = new BigInteger(String.valueOf(origen));


//        resta = resta.multiply(new BigInteger("2"));
//        while(ret.compareTo(resta)>=0){
//            ret = ret.subtract(resta);
//        }
//        return ret;

        BigInteger restaTotal = resta.multiply(origen.divide(resta).subtract(new BigInteger("1")));
        return origen.subtract(restaTotal);
    }
}
