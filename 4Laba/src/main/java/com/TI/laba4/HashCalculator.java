package com.TI.laba4;

/**
 * The HashCalculator class allow to calculate the HashCode of some text.
 */
public class HashCalculator {
    private long defaultHash;

    public HashCalculator (){
        setDefaultHash(100);
    }

    public HashCalculator (long defaultHash){
        setDefaultHash(defaultHash);
    }

    private void setDefaultHash (long defaultHash){
        this.defaultHash = defaultHash;
    }

    /**
     *
     * @param text String, which  HashCode necessary to calculate
     * @param Q
     * @return HashCode of the text
     */
    public long calculateHashCode (String text, long Q)
    {
        long H = defaultHash;

        char[] symbolsArray = text.toCharArray();
        for (char c : symbolsArray) {
            long o = H + c;
            H = FastModularExponentiation.calculate(o, 2, Q);
        }
        return H;
    }

}
