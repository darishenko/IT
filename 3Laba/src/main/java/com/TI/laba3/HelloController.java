package com.TI.laba3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.TI.laba3.Converter.*;
import static com.TI.laba3.FileWorker.*;

public class HelloController {

    private byte [] byteArray;

    private String openFilePath = "";
    private String openFileName = "";

    private int P, Q, B, N;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label Lbl_Nvalue;

    @FXML
    private Button b_FileChooser;

    @FXML
    private Button b_Decrypt;

    @FXML
    private Button b_Encrypt;

    @FXML
    private TextField input_P;

    @FXML
    private TextField input_Q;

    @FXML
    private TextField input_B;

    @FXML
    private TextArea out_File;

    @FXML
    private TextArea out_Result;

    public void showWarningMessage(String Title, String HeaderText, String ContentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Title);
        alert.setHeaderText(HeaderText);
        alert.setContentText(ContentText);
        alert.showAndWait();
    }

    @FXML
    void openFile(ActionEvent event) {
        try{
            File file = chooseSingleFileToOpen();
            byteArray = readFile(file);
            out_File.setText(toASCIICode(byteArray));
            openFilePath = file.getParent();
            openFileName = file.getName();
            out_Result.clear();
        }catch (IOException e){
            showWarningMessage("File ERROR","File not chosen","Please chose file");
        }
    }

    private boolean isArrayNull(byte[] byteArray){
        if (byteArray!= null){
            return true;
        }
        showWarningMessage("File ERROR","File not chosen","Please choose a file");
        return false;
    }

    private boolean inputCheckPandQ(TextField inputParameter, String parameterName){
        inputParameter.setText(Checker.checkNumber(inputParameter.getText()));

        if (inputParameter.getText().isEmpty()){
            showWarningMessage("Error", "Invalid value of "+ parameterName, "Enter the correct value of "+parameterName+"!");
            return false;
        }
        if (Long.parseUnsignedLong(inputParameter.getText()) >= (Math.pow(2, 16)-1)){
            showWarningMessage("Error", parameterName + " is too large", "Enter the correct value of "+ parameterName +"!");
            return false;
        }
        if(!Checker.isPrimeNumber(inputParameter.getText())) {
            showWarningMessage("Error", parameterName + " is not the prime number", "Enter the correct value of "+ parameterName +"!");
            return false;
        }
        if (!RabinEncrypt.checkMod(inputParameter.getText())) {
            showWarningMessage("Error", parameterName + " mod 4 != 3", "Enter the correct value of "+parameterName + "!");
            return false;
        }

        return true;
    }

    private boolean inputCheckB(){
        input_B.setText(Checker.checkNumber(input_B.getText()));

        if (input_B.getText().isEmpty()){
            showWarningMessage("Error", "Invalid value of B", "Enter the correct value of B!");
            return false;
        }
        if (Long.parseUnsignedLong(input_B.getText()) >= N || Integer.parseInt(input_B.getText()) == 0 ){
            showWarningMessage("Error", "Invalid value of B", "Enter the correct value of B!\nB need be < P * Q");
            return false;
        }

        return true;
    }

    private boolean inputCheck(){
        boolean trueP= false, trueQ= false,  trueN= false;
        Lbl_Nvalue.setText("");

        if (inputCheckPandQ(input_P, "P")){
            P = Integer.parseInt(input_P.getText());
            trueP = true;
        }else{
            input_P.clear();
        }

        if (inputCheckPandQ(input_Q, "Q")){
            Q = Integer.parseInt(input_Q.getText());
            trueQ = true;
        }else{
            input_Q.clear();
        }

        if (trueP && trueQ){
            N= P * Q;
            if(Integer.MAX_VALUE > N){
                trueN=true;
            }else{
                if (Q==P){
                    showWarningMessage("Error", "Invalid value of P and Q", "Enter the P and Q that P!=Q");
                }else {
                    showWarningMessage("Error", "N is too large", "Enter the correct value of P and Q!");
                }
                input_P.clear();
                input_Q.clear();
            }
        }

        if (inputCheckB()){
            B = Integer.parseInt(input_B.getText());
            if (trueN){
                Lbl_Nvalue.setText(String.valueOf(N));
                return true;
            }
        }else{
            input_B.clear();
        }

        return false;
    }

    @FXML
    void decrypt(ActionEvent event) {
        if (inputCheck() && isArrayNull(byteArray)) {

            int[] intArrayForDecrypt = RabinEncrypt.convertByteArrayToIntArray(byteArray);
            byte[] decryptArray = new byte[intArrayForDecrypt.length];

            StringBuilder tempStr = new StringBuilder();
            long[] Yp_Yq = ExtendedEuclidean.calculate(P, Q);
            for (int i = 0; i < intArrayForDecrypt.length; i++) {
                decryptArray[i] = RabinEncrypt.decrypt(B, intArrayForDecrypt[i], N, Yp_Yq[0], Yp_Yq[1], P, Q);
                tempStr.append(toASCIICode(decryptArray[i])).append(" ");
            }

            out_Result.setText(tempStr.toString());
            try {
                writeToFile(decryptArray, chooseSingleFileToSave(openFilePath, openFileName, "decrypt_"));
            }
            catch (IOException e){
                showWarningMessage("File ERROR","File not found","Please try again");
            }
           }
    }

    @FXML
    void encrypt(ActionEvent event) {
        if (inputCheck() && isArrayNull(byteArray)) {

            long M;
            StringBuilder tempStrM = new StringBuilder();
            byte[] resultEncrypt = new byte[byteArray.length * 4];
            for(int i = 0; i < byteArray.length; i++){
                M = RabinEncrypt.calculateM(N, B, byteArray[i]);
                System.arraycopy(RabinEncrypt.convertLongToByteArray(M), 0, resultEncrypt, i * 4, 4);
                tempStrM.append(M).append(" ");
            }

            out_Result.setText(tempStrM.toString());
            try{
            writeToFile(resultEncrypt, chooseSingleFileToSave(openFilePath, openFileName, "encrypt_"));
            }
            catch (IOException e){
                showWarningMessage("File ERROR","File not found","Please create a file");
            }
        }
    }

    @FXML
    void initialize() {
        assert b_Encrypt != null : "fx:id=\"b_Encrypt\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert b_FileChooser != null : "fx:id=\"b_FileChooser\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_P != null : "fx:id=\"input_P\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_Q != null : "fx:id=\"input_Q\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_B != null : "fx:id=\"input_B\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert b_Decrypt != null : "fx:id=\"b_Decrypt\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_File != null : "fx:id=\"out_File\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_Result != null : "fx:id=\"out_Result\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert Lbl_Nvalue != null : "fx:id=\"Lbl_Nvalue\" was not injected: check your FXML file 'hello-view.fxml'.";
    }

}

