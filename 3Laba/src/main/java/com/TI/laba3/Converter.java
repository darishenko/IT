package com.TI.laba3;

public class Converter {

    public static String toASCIICode(byte [] byteArray){
        StringBuilder preBytes = new StringBuilder();
        for (byte b : byteArray) {
            String str = String.valueOf(Integer.parseInt(Integer.toBinaryString(b & 0xFF), 2));
            preBytes.append(str).append(" ");
        }
        return preBytes.toString();
    }

    public static String toASCIICode(byte b){
        StringBuilder preBytes = new StringBuilder();
        String str = String.valueOf(Integer.parseInt(Integer.toBinaryString(b & 0xFF), 2));
        preBytes.append(str);
        return preBytes.toString();
    }
}
