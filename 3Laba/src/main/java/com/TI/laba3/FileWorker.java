package com.TI.laba3;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWorker {

    public static File chooseSingleFileToOpen() throws java.io.IOException{
        Stage stage = new Stage();
        String userDirectory = System.getProperty("user.dir");
        FileChooser fc = new FileChooser();
        Path dir  = Files.createDirectories(Path.of(userDirectory + "\\Tests"));
        fc.setInitialDirectory(dir.toFile());
        return fc.showOpenDialog(stage);
    }

    public static byte[] readFile(File file) throws FileNotFoundException {
        byte[] byteArray;
        try {
            byteArray = new byte[ (int) file.length()];
            FileInputStream input = new FileInputStream(file.getPath());
            input.read(byteArray, 0, byteArray.length);
            input.close();
        } catch (NullPointerException | IOException e) {
            throw new FileNotFoundException();
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
                System.out.println("File error");
            }
        }
    }

    public static File chooseSingleFileToSave(String userDirectory, String fileName, String extBeforeFileName) throws IOException{
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();
        Path dir = Files.createDirectories(Path.of(userDirectory));
        fc.setInitialDirectory(dir.toFile());
        fc.setInitialFileName(extBeforeFileName+ fileName);
        return fc.showSaveDialog(stage);
    }
}
