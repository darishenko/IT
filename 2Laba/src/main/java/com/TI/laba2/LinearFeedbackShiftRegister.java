package com.TI.laba2;

import java.util.Arrays;

public class LinearFeedbackShiftRegister {
    private String initialState;
    private long state;
    private final int length;
    private final int[] degrees;

    public LinearFeedbackShiftRegister(int[] degrees) {
        Arrays.sort(degrees);
        this.degrees = degrees;
        this.length = this.degrees[this.degrees.length -1];
    }

    public int shift() {
        StringBuilder keyByte = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            keyByte.append(getBit(state, length));
            long bbit = getBit(state, degrees[0]);
            for (int j = 1; j< degrees.length ; j++){
                bbit ^= getBit(state, degrees[j]);
            }
            state =(state << 1) + bbit;
        }

        return Integer.parseInt(keyByte.toString(), 2);
    }

    private long getBit(long num, int n) {
        return ( (num >> (n -1))  & 1);
    }

    public boolean setInitialState(String newInitialState){
        newInitialState = checkInitialState(newInitialState);
        if (newInitialState.length() == this.length) {
            initialState = newInitialState;
            state = Long.parseLong(initialState, 2);
            return true;
        }
        return false;
    }

    public String getInitialState(){
        return this.initialState;
    }

    private String checkInitialState(String key) {
        StringBuilder newInitialState = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            if (key.charAt(i) == '1' || key.charAt(i) == '0') {
                newInitialState.append(key.charAt(i));
            }
        }
        if (newInitialState.length() < length) {
            newInitialState.setLength(0);
        } else {
            newInitialState.setLength(length);
        }

        return newInitialState.toString();
    }
}
