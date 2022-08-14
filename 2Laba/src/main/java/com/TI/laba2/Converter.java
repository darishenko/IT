package com.TI.laba2;

public class Converter {

    public static String convertingByteToBinary(byte b){
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " ";
    }

    public static String convertingByteArrayToBinary(byte [] byteArray){
        StringBuilder bitsStr = new StringBuilder();
        for (byte b : byteArray) {
            bitsStr.append(convertingByteToBinary(b));
        }
        return bitsStr.toString();
    }
}
