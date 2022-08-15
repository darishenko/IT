package com.TI.laba4;

public class ArgumentException extends Exception{

    private final String messageCorrection;
    private final String messageAdvice;

    public String getMessageCorrection() {
        return messageCorrection;
    }

    public String getMessageAdvice() {
        return messageAdvice;
    }

    public ArgumentException (String message, String messageCorrection, String messageAdvice){
        super(message);
        this.messageCorrection = messageCorrection;
        this.messageAdvice = messageAdvice;
    }

}
