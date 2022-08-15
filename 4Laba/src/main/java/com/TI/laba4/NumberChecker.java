package com.TI.laba4;

public class NumberChecker {

    /**
     * Checking a number for valid characters:
     *
     */
    public static String toNumberString(String number) {
        StringBuilder new_number = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) >= '0' && number.charAt(i) <= '9') {
                new_number.append(number.charAt(i));
            }
        }
        return new_number.toString();
    }

    /**
     * Checking whether a number is prime
     * @param number
     */
    public static boolean isNumberPrime(String number){
        if (!number.isEmpty()){
            long num = Long.parseLong(number);
            long temp;
            if (num < 2 ){
                return false;
            }
            double s = Math.sqrt(num);
            for (int i = 2; i <= s; i++) {
                temp = num % i;
                if (temp == 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

