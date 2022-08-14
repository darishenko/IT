package com.TI.laba2;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class HelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button b_FileChooser;

    @FXML
    private TextArea input_text;

    @FXML
    private TextArea input_key;

    @FXML
    private Button b_encrypt;

    @FXML
    private TextArea resGen_key;

    @FXML
    private TextArea out_text;

    private byte[] byteArray;
    private String openFileName = "";
    private String openFilePath = "";

    public void showWarningMessage(String Title, String HeaderText, String ContentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Title);
        alert.setHeaderText(HeaderText);
        alert.setContentText(ContentText);
        alert.showAndWait();
    }

    public void toEncrypt(ActionEvent event) {
        LinearFeedbackShiftRegister LFSR = new LinearFeedbackShiftRegister(new int[] {13, 33});
        if(LFSR.setInitialState(input_key.getText())){
            input_key.setText( LFSR.getInitialState().replaceAll("(.{8})", "$1 "));
            out_text.clear();
            resGen_key.clear();

            if (byteArray != null) {
                byte[] encryptByteArray = new byte[byteArray.length];
                StringBuilder key = new StringBuilder();
                StringBuilder encryptBytesStr = new StringBuilder();
                for (int i = 0; i < byteArray.length; i++) {
                    byte keyByte = (byte) LFSR.shift();
                    key.append(Converter.convertingByteToBinary(keyByte));
                    encryptByteArray[i] = (byte) (keyByte ^ byteArray[i]);
                    encryptBytesStr.append(Converter.convertingByteToBinary(encryptByteArray[i]));
                }

                resGen_key.setText(key.toString());
                out_text.setText(encryptBytesStr.toString());
                try {
                    FileWorker.writeToFile(encryptByteArray, FileWorker.chooseSingleFileToSave(openFilePath, openFileName));
                }catch (IOException e){
                    showWarningMessage("File ERROR","File not found","Please try again");
                }
            }else {
                showWarningMessage("Error", "File error", "Choose a file!");
            }
        }else {
            showWarningMessage("Initial State error", "Invalid initial State value", "Enter the correct initial State value!");
            input_key.clear();
        }
    }
    
    @FXML
    void openFile(ActionEvent event){
        try {
            File openFile = FileWorker.chooseSingleFileToOpen();
            if (openFile != null){
                byteArray = FileWorker.readFile(openFile);
                openFilePath = openFile.getParent();
                openFileName = openFile.getName();
                input_text.setText(Converter.convertingByteArrayToBinary(byteArray));
            }
        }catch (IOException e){
            showWarningMessage("File ERROR","File not found","Please try again");
        }
    }

    @FXML
    void initialize() {
        assert b_FileChooser != null : "fx:id=\"b_FileChooser\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_text != null : "fx:id=\"input_text\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert input_key != null : "fx:id=\"input_key\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert b_encrypt != null : "fx:id=\"b_encrypt\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert resGen_key != null : "fx:id=\"resGen_key\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_text != null : "fx:id=\"out_text\" was not injected: check your FXML file 'hello-view.fxml'.";
    }
}