package com.TI.laba3;

public class ExtendedEuclidean {

    public static long[] calculate(long a, long b, long[] x, long[] y){
        long[] result = new long[2];

        if(b==0){
            result[0] = x[0];
            result[1] = y[0];
            return result;
        }
        long q=a/b;
        long tx1 = x[0]-q*x[1];
        long ty1 = y[0]-q*y[1];
        long[] tx = {x[1],tx1};
        long[] ty = {y[1],ty1};
        return calculate(b,a%b,tx,ty);
    }

    //extended Euclidean algorithm
    public static long[] calculate(long a, long b){
        long[] x = {1,0};
        long[] y = {0,1};
        return calculate(a,b,x,y);
    }
}
