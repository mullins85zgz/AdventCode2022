package day11;

import java.math.BigInteger;

public class testBigInteger {
    public static void main(String[] args) {
        BigInteger producto = new BigInteger("24");
        BigInteger resta = new BigInteger("5");

        BigInteger ret = new BigInteger(String.valueOf(producto));
        while(ret.compareTo(resta.multiply(new BigInteger("2")))>=0){
            ret = ret.subtract(resta);
        }

        System.out.println(ret);
    }
}
