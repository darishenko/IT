package com.TI.laba3;

public class Checker {

    //checking a number for valid characters
    public static String checkNumber(String number) {
        StringBuilder new_number = new StringBuilder();
        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i) >= '0' && number.charAt(i) <= '9') {
                new_number.append(number.charAt(i));
            }
        }

        return new_number.toString();
    }

    //checking whether a number is prime
    public static boolean isPrimeNumber(String number){
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

}
