package com.TI.laba4;

public class EulerFunction {

    public static long calculate( long num){
        long result = num;
        for (long i = 2; (i *i)< num; ++i){
            if (num % i == 0){
                num/= i;
                result -= result/ i;
            }

        }
        if (num > 1) {
            result -= result / num;
        }
        return result;
    }
}
