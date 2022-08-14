package com.TI.laba1;

import SimplestCiphers.ColumnarImprovedEncryption;
import SimplestCiphers.DecimationEncryption;
import SimplestCiphers.VigenereEncryption;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public void showWarningMessage(String Title, String HeaderText, String ContentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Title);
        alert.setHeaderText(HeaderText);
        alert.setContentText(ContentText);
        alert.showAndWait();
    }

    @FXML
    private Button b_FileChooser;

    @FXML
    private Button b_encrypt;
    public void crypt_text(){
        out_text.clear();

        switch (cipher_selection.getValue()) {
            case "Виженера" -> {
                VigenereEncryption vigenereEncryption = new VigenereEncryption();
                String key = vigenereEncryption.editKey(inp_key.getText());
                if (!key.isEmpty()) {
                    inp_key.setText(key);
                    String OutputText = vigenereEncryption.cipher(inp_text.getText(), key, true);
                    out_text.appendText(OutputText);
                    try {
                        FileWorker.writeToFile(OutputText, FileWorker.chooseSingleFileToSave("Текстовый файл", "txt"));
                    }catch (IOException exception){
                        showWarningMessage("File ERROR","File not create","Please try again");
                    }
                } else {
                    showWarningMessage("Key error", "Invalid key value", "Enter the correct key value!");
                    inp_key.clear();
                }
            }
            case "Децимации" -> {
                DecimationEncryption decimationEncryption = new DecimationEncryption();
                int key = decimationEncryption.editKey(inp_key.getText());
                if (key != 0) {
                    inp_key.setText(Integer.toString(key));
                    String OutputText = decimationEncryption.cipher(inp_text.getText(), key, true);
                    out_text.appendText(OutputText);
                    try{
                    FileWorker.writeToFile(OutputText, FileWorker.chooseSingleFileToSave("Текстовый файл" , "txt"));
                    }catch (IOException exception){
                        showWarningMessage("File ERROR","File not create","Please try again");
                    }
                } else {
                    showWarningMessage("Key error", "Invalid key value", "Enter the correct key value!");
                    inp_key.clear();
                }
            }
            case "Столбцовый улучшенный" -> {
                ColumnarImprovedEncryption columnarImprovedEncryption = new ColumnarImprovedEncryption();
                String key = columnarImprovedEncryption.editKey(inp_key.getText());
                if (!key.isEmpty() ) {
                    inp_key.setText(key);
                    String OutputText = columnarImprovedEncryption.encrypt(inp_text.getText(), key);
                    out_text.appendText(OutputText);
                    try{
                    FileWorker.writeToFile(OutputText, FileWorker.chooseSingleFileToSave("Текстовый файл" , "txt"));
                    }catch (IOException exception){
                        showWarningMessage("File ERROR","File not create","Please try again");
                    }
                    }else{
                    showWarningMessage("Key error", "Invalid key value", "Enter the correct key value!");
                    inp_key.clear();
                }
            }
        }
    }

    @FXML
    private Button b_decrypt;
    public void decrypt_text(){
        out_text.clear();

        switch (cipher_selection.getValue()) {
            case "Виженера" -> {
                VigenereEncryption vigenereEncryption = new VigenereEncryption();
                String key = vigenereEncryption.editKey(inp_key.getText());
                if (!key.isEmpty()){
                    inp_key.setText(key);
                    String OutputText = vigenereEncryption.cipher(inp_text.getText(), key, false);
                    out_text.appendText(OutputText);
                    try{
                    FileWorker.writeToFile(OutputText, FileWorker.chooseSingleFileToSave("Текстовый файл" , "txt"));
                    }catch (IOException exception){
                        showWarningMessage("File ERROR","File not create","Please try again");
                    }
                }
                else{
                    showWarningMessage("Key error", "Invalid key value", "Enter the correct key value!");
                    inp_key.clear();
                }
            }
            case "Децимации" -> {
                DecimationEncryption decimationEncryption = new DecimationEncryption();
                int key = decimationEncryption.editKey(inp_key.getText());
                if (key != 0){
                    inp_key.setText(Integer.toString(key));
                    String OutputText = decimationEncryption.cipher(inp_text.getText(), key, false);
                    out_text.appendText(OutputText);
                    try{
                    FileWorker.writeToFile(OutputText, FileWorker.chooseSingleFileToSave("Текстовый файл" , "txt"));
                    }catch (IOException exception){
                        showWarningMessage("File ERROR","File not create","Please try again");
                    }
                }
                else{
                    showWarningMessage("Key error", "Invalid key value", "Enter the correct key value!");
                    inp_key.clear();
                }
            }
            case "Столбцовый улучшенный" -> {
                ColumnarImprovedEncryption columnarImprovedEncryption = new ColumnarImprovedEncryption();
                String key = columnarImprovedEncryption.editKey(inp_key.getText());
                if (!key.isEmpty() ) {
                    inp_key.setText(key);
                    String OutputText = columnarImprovedEncryption.decrypt(inp_text.getText(), key);
                    out_text.appendText(OutputText);
                    try{
                    FileWorker.writeToFile(OutputText, FileWorker.chooseSingleFileToSave("Текстовый файл" , "txt"));
                    }catch (IOException exception){
                        showWarningMessage("File ERROR","File not create","Please try again");
                    }
                }else{
                    showWarningMessage("Key error", "Invalid key value", "Enter the correct key value!");
                    inp_key.clear();
                }
            }
        }
    }

    @FXML
    private TextArea out_text;

    @FXML
    private ChoiceBox<String> cipher_selection;

    @FXML
    private TextArea inp_text;

    @FXML
    private TextArea inp_key;

    @FXML
    void initialize() {
        assert b_decrypt != null : "fx:id=\"b_decrypt\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert b_encrypt != null : "fx:id=\"b_encrypt\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert cipher_selection != null : "fx:id=\"cipher_selection\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert b_FileChooser != null : "fx:id=\"b_FileChooser\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert inp_text != null : "fx:id=\"inp_text\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert inp_key != null : "fx:id=\"inp_key\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert out_text != null : "fx:id=\"out_text\" was not injected: check your FXML file 'hello-view.fxml'.";

        cipher_selection.getItems().addAll("Виженера", "Децимации", "Столбцовый улучшенный");
        cipher_selection.setValue("Виженера");

        b_FileChooser.setOnAction(e -> {
            try {
                inp_text.setText(FileWorker.readFile(FileWorker.chooseSingleFileToOpen("Текстовый файл", "txt")));
            }catch (IOException exception){
                showWarningMessage("File ERROR","File not exit","Please create a file");
            }
        });
    }
}


