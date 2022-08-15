package com.TI.laba3;

public class FastModularExponentiation {

    //fast Modular Exponentiation algorithm : x = a^z mod m
    public static long calculate (long a, long z, long m){
        long result = 1;
        while (z != 0) {
            while (z % 2 == 0) {
                z = z / 2;
                a = ((a * a) % m);
            }
            z = z-1;
            result = (result * a ) % m;
        }
        return result;
    }
}
