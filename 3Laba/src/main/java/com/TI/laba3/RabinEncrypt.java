package com.TI.laba3;

import java.nio.ByteBuffer;

public class RabinEncrypt {

    //checking for condition fulfillment: p mod 4 = 3, q mod 4 = 3
    public static boolean checkMod(String number){
        long num = Long.parseLong(number);
        return num % 4 == 3;
    }

    //encryption using the Rabin algorithm
    public static byte[] convertLongToByteArray(long M){
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.putInt((int)M);
        return byteBuffer.array();
    }

    public static long calculateM(int N,int B, byte b){
        long M = Long.parseLong(Long.toBinaryString(b & 0xFF), 2);
        M = ( M * ( M + B )) % N;
        return M;
    }

    //decryption using the Rabin algorithm
    public static byte decrypt (int B, int C_, int N, long Yp, long Yq, int P, int Q){
        byte result=0;
        long D_i = (long) ((Math.pow(B, 2) + 4 * (long) C_) % N);
        long Mp = FastModularExponentiation.calculate(D_i, (P+1)/4, P);
        long Mq = FastModularExponentiation.calculate(D_i, (Q+1)/4, Q);

        long[] arrayD = new long[4];
        arrayD[0] = (Yp*P*Mq + Yq*Q* Mp) % N;
        arrayD[1] = N - arrayD[0];
        arrayD[2] = (Yp*P*Mq - Yq*Q* Mp) % N;
        arrayD[3] = N - arrayD[2];

        long[] results = new long[4];
        for (int i=0; i<arrayD.length;i++) {
            if ((arrayD[i] - B) % 2 == 0) {
                results [i] = (((-1 * B) + arrayD[i]) / 2) % N;
            } else {
                results [i] = (((-1 * B) + N + arrayD[i]) / 2) % N;
            }

            if(results[i]<256 && results[i]>=0){
                result = (byte)results[i];
                break;
            }
        }

        return result;
    }

    public static int convertByteArrayToInt(byte [] byteArray){
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(byteArray);
        buffer.rewind();
        return buffer.getInt();
    }

    public static int [] convertByteArrayToIntArray(byte [] byteArray){
        int [] result = new int[byteArray.length / 4];
        for (int i = 0; i < byteArray.length; i += 4) {
            byte[] bytes = new byte[4];
            System.arraycopy(byteArray, i, bytes, 0, 4);
            result[i / 4] = convertByteArrayToInt(bytes);
        }
        return result;
    }

}
