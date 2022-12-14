package day12;

import java.util.ArrayList;

public class TestBucles {

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<>();
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);

        int cont = 0;
        while (cont<array.size()){
            Integer num = array.get(cont); cont++;
            if(num==2) array.add(5);
            System.out.println(num);
        }
    }
}
