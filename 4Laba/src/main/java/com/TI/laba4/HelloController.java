package com.TI.laba4;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField input_Q;

    @FXML
    private TextField input_P;

    @FXML
    private TextField input_h;

    @FXML
    private TextField input_X;

    @FXML
    private TextField input_k;

    @FXML
    private Label out_H;

    @FXML
    private Label out_r;

    @FXML
    private Label out_s;

    @FXML
    private Label out_v;

    @FXML
    private Text output_checkResultWhy;

    @FXML
    private Text output_checkResult;

    @FXML
    private Text output_checkResultWhy0;

    public void showWarningMessage(String Title, String HeaderText, String ContentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Title);
        alert.setHeaderText(HeaderText);
        alert.setContentText(ContentText);
        alert.showAndWait();
    }

     private long Q ;
     private long P ;
     private long h ;
     private long X ;
     private long K ;

     private final byte COUNT_OF_ERRORS_FOR_SIGN = 5;
     private final byte COUNT_OF_ERRORS_FOR_CHECKING_SIGN = 4;

     private File file;

    public void toRightInput(){
        input_Q.setText(NumberChecker.toNumberString(input_Q.getText()));
        input_P.setText(NumberChecker.toNumberString(input_P.getText()));
        input_h.setText(NumberChecker.toNumberString(input_h.getText()));
        input_X.setText(NumberChecker.toNumberString(input_X.getText()));
        input_k.setText(NumberChecker.toNumberString(input_k.getText()));

        out_H.setText("");
        out_r.setText("");
        out_s.setText("");
        out_v.setText("");
        output_checkResult.setText("");
        output_checkResultWhy.setText("");
        output_checkResultWhy0.setText("");
    }

    public boolean inputCheckQ(){
        if (NumberChecker.isNumberPrime(input_Q.getText())){
            Q = Long.parseLong(input_Q.getText());
            return true;
        }
        showWarningMessage("Invalid input of Q!","Q should be a prime number.","Enter the right value of Q and check P, X, K.");
        return false;
    }
    public boolean inputCheckP(){
        if( !NumberChecker.isNumberPrime(input_P.getText()) ) {
            showWarningMessage("Invalid input of P","P should be a prime number","Enter the right value of P and check h");
            return false;
        }
        P = Long.parseLong(input_P.getText());
        if ((P - 1) % Q != 0){
            showWarningMessage("Invalid input of P","(P -1) % Q should be =0","Enter the right value of P and check h");
            return false;
        }
        return true;
    }
    public boolean inputCheckX(){
        if( !input_X.getText().isEmpty() ){
            X = Long.parseLong(input_X.getText());
            if (0 < X && X < Q) return true;
        }
        showWarningMessage("Invalid input of X","X should be: 0 < X < Q","Enter the right value of X");
        return false;
    }
    public boolean inputCheckK(){
        if( !input_k.getText().isEmpty() ){
            K = Long.parseLong(input_k.getText());
            if (0 < K && K < Q) return true;
        }
        showWarningMessage("Invalid input of K","K should be: 0 < K < Q","Enter the right value of K");
        return false;
    }
    //P
    public boolean inputCheckH(){
        if( !input_h.getText().isEmpty()){
            h = Long.parseLong(input_h.getText());
            if (1 < h && h < ( P -1 )) return true;
        }
        showWarningMessage("Invalid input of h","h should be: 1 < h < P -1","Enter the right value of h");
        return false;
    }

    public boolean checkParametersBeforeSignDSA(){
        byte Error = COUNT_OF_ERRORS_FOR_SIGN;
        if(inputCheckQ()){
            Error--;
            if (inputCheckP()){
                if (inputCheckH()) Error --;
                Error--;
            }
            if(inputCheckX()) Error--;
            if (inputCheckK()) Error--;
        }
        return Error == 0;
    }

    public boolean checkParametersBeforeCheckingSignDSA(){
        int Error = COUNT_OF_ERRORS_FOR_CHECKING_SIGN;
        if(inputCheckQ()){
            Error--;
            if (inputCheckP()){
                if (inputCheckH()) Error --;
                Error--;
            }
            if(inputCheckX()) Error--;
        }
        return Error == 0;
    }

    @FXML
    private Button b_Implementation;

    private void signingDSA(){
            try {
                String text = FileWorker.readFile(file);

                DSA generateDSA = new DSA(Q, P, h, X, K);
                long [] result = generateDSA.generateSign(text);
                long r = result[0];
                long s = result[1];

                out_H.setText(Long.toString(generateDSA.getHash()));
                out_r.setText(Long.toString(r));
                out_s.setText(Long.toString(s));

                FileWorker.writeToFile(FileWorker.chooseSingleFileToSave(file.getParent(), file.getName(), "sign_"), text, r, s);
            }catch (ArgumentException e) {
                showWarningMessage(e.getMessage(), e.getMessageCorrection(), e.getMessageAdvice());
            }
            catch (IOException e){
                showWarningMessage("File ERROR","File not chosen","Please choose a file");
            }
    }

    private void checkingDSA(){
            try {
                String[] fileParameters = FileWorker.readFileForCheckDSA(file);
                long[] sign = DSA.parsingSignBeforeCheckDSA(fileParameters[1]);
                out_r.setText(Long.toString(sign[0]));
                out_s.setText(Long.toString(sign[1]));

                DSA generateCheckDSA = new DSA(sign[0], sign[1], Q, P, h, X);
                if (generateCheckDSA.checkDSA(fileParameters[0])){
                    output_checkResult.setText("true");
                    output_checkResultWhy.setText("v = r");
                }else {
                    output_checkResult.setText("false");
                    output_checkResultWhy.setText("v != r" );
                }

                out_H.setText(Long.toString(generateCheckDSA.getHash()));
                out_v.setText(Long.toString(generateCheckDSA.getV()));
                output_checkResultWhy0.setText("w = "+generateCheckDSA.getW() + "   u1 = " + generateCheckDSA.getU1() + "   u2 = "+ generateCheckDSA.getU2());

            }catch (FileNotFoundException e){
                showWarningMessage("File ERROR","File not chosen","Please choose a file");
            }catch (ArgumentException e){
                showWarningMessage(e.getMessage(), e.getMessageCorrection(), e.getMessageAdvice());
            }
    }

    @FXML
    void startDSA(ActionEvent event) throws Exception {
        toRightInput();

        if(RB_sign.isSelected() && checkParametersBeforeSignDSA()){
            signingDSA();
        }

        if(RB_checkSign.isSelected() && checkParametersBeforeCheckingSignDSA()){
            checkingDSA();
        }
    }


    @FXML
    private RadioButton RB_sign;

    @FXML
    private RadioButton RB_checkSign;

    @FXML
    private Button b_FileChooser;

    @FXML
    void openFile(ActionEvent event) {
        try{
            file = FileWorker.chooseSingleFileToOpen("Текстовый файл", "txt");
        }
        catch (IOException e){
            showWarningMessage("File ERROR","File not exit","Please create a file");
        }
    }

    @FXML
    void initialize() {
        assert input_Q != null : "fx:id=\"input_Q\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_P != null : "fx:id=\"input_P\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_h != null : "fx:id=\"input_h\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_X != null : "fx:id=\"input_X\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_k != null : "fx:id=\"input_k\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_H != null : "fx:id=\"out_H\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_r != null : "fx:id=\"out_r\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_s != null : "fx:id=\"out_s\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_v != null : "fx:id=\"out_v\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert output_checkResultWhy != null : "fx:id=\"output_checkResultWhy\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert output_checkResult != null : "fx:id=\"output_checkResult\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert output_checkResultWhy0 != null : "fx:id=\"output_checkResultWhy1\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert b_FileChooser != null : "fx:id=\"b_FileChooser\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RB_sign != null : "fx:id=\"RB_sign\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert RB_checkSign != null : "fx:id=\"RB_checkSign\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert b_Implementation != null : "fx:id=\"b_FileChooser1\" was not injected: check your FXML file 'hello-view.fxml'.";


        ToggleGroup choiceGroup = new ToggleGroup();
        RB_sign.setToggleGroup(choiceGroup);
        RB_checkSign.setToggleGroup(choiceGroup);
    }
}
