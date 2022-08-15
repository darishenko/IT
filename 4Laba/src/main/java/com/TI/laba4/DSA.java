package com.TI.laba4;

public class DSA {
    private long Q;
    private long P;
    private long h;
    private long X;
    private long K;

    private long G = 0;
    private long Y = 0;

    private long r = 0;
    private long Hash = 0;

    private long s = 0;
    private long v = 0;
    private long w = 0;
    private long u1 = 0;
    private long u2 = 0;

    public DSA(long Q,long P, long h, long X, long K ){
        this.Q = Q;
        this.P = P;
        this.h = h;
        this.X = X;
        this.K = K;
    }

    public DSA (long r,long s, long Q, long P, long h, long X){
        this.r = r;
        this.s = s;
        this.Q = Q;
        this.P = P;
        this.h = h;
        this.X = X;
    }

    public long getHash() {
        return Hash;
    }

    public long getV() {
        return v;
    }

    public long getW() {
        return v;
    }

    public long getU1() {
        return u1;
    }

    public long getU2() {
        return u2;
    }

    public long getS() {
        return s;
    }

    public long getR() {
        return r;
    }

    private void calculateOpenKey(){
        G = FastModularExponentiation.calculate(h, (P-1)/Q, P);
        if (G >1){
            Y =  FastModularExponentiation.calculate(G, X, P);
        }
    }

    private void calculateR(){
        r = FastModularExponentiation.calculate(G, K, P);
        r= r % Q;
    }

    private void calculateS(){
        long _K  = FastModularExponentiation.calculate(K, Q-2, Q);
        s =  (_K * ((Hash + X*r) % Q)) % Q;
    }

    private void calculateHash(String text, long Q){
        HashCalculator hashCalculator = new HashCalculator();
        Hash = hashCalculator.calculateHashCode(text, Q);
    }

    public long[] generateSign(String text) throws ArgumentException {
        long[] result = new long[2];
        calculateOpenKey();
        if(G > 1){
            calculateHash(text, Q);
            calculateR();
            calculateS();
            result[0] = r;
            result[1] = s;
            if (r == 0 || s == 0){
                throw new ArgumentException("Wrong value of r or s","r should be != 0\nand s should be !=0 ","Enter another value of K and try again");
            }
        }else{
            throw new ArgumentException("Wrong value of G","G should be > 1","Enter another value of h and try again");
        }

        return result;
    }

    public static long[] parsingSignBeforeCheckDSA(String sign) throws ArgumentException {
        long [] result = new long[2];
        try {
            String[] DSAFromFile = sign.split(" ");
            result[0] = Long.parseLong(DSAFromFile[0]);
            result[1] = Long.parseLong(DSAFromFile[1]);
        }
        catch ( NumberFormatException e) {
            throw new ArgumentException("File ERROR", "This file haven't got sign", "Choose another file");
        }
        return result;
    }

    public boolean checkDSA(String text){
        w = FastModularExponentiation.calculate(s, Q-2, Q);
        HashCalculator hashCalculator = new HashCalculator();
        Hash = hashCalculator.calculateHashCode(text, Q);
        u1 = (Hash * w) % Q;
        u2 = (r * w) % Q;
        calculateOpenKey();
        v = ( ( (FastModularExponentiation.calculate(G, u1, P) ) * (FastModularExponentiation.calculate(Y, u2, P) ) ) % P) % Q;
        return v == r;
    }

}
