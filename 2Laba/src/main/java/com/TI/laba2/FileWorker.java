package com.TI.laba2;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWorker {

    public static File chooseSingleFileToOpen() throws IOException{
        Stage stage = new Stage();
        String userDirectory = System.getProperty("user.dir");
        Path dir = Files.createDirectories(Path.of(userDirectory+"\\Tests"));
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(dir.toFile());
        return fc.showOpenDialog(stage);
    }

    public static byte[] readFile(File file) {
        byte[] byteArray = new byte[ (int) file.length()];
        try {
            FileInputStream input = new FileInputStream(file.getPath());
            input.read(byteArray, 0, byteArray.length);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArray;
    }

    public static void writeToFile(byte[] result, File file) {
        if (file != null) {
            try {
                FileOutputStream fw = new FileOutputStream(file.getAbsolutePath());
                fw.write(result);
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File chooseSingleFileToSave(String userDirectory, String fileName) throws IOException{
        Stage stage = new Stage();
        Path dir = Files.createDirectories(Path.of(userDirectory));
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(dir.toFile());
        fc.setInitialFileName("result_"+ fileName);
        return fc.showSaveDialog(stage);
    }
}
