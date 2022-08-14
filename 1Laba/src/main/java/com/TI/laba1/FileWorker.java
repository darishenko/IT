package com.TI.laba1;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWorker {
    public static File chooseSingleFileToOpen(String fileInfo, String extension) throws IOException{
        Stage stage = new Stage();
        String userDirectory = System.getProperty("user.dir");
        Path dir = Files.createDirectories(Path.of(userDirectory +"\\Tests"));
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(dir.toFile());
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileInfo, "*."+extension));
        return fc.showOpenDialog(stage);
    }

    public static void writeToFile(String text, File file) {
        if (file != null) {
            try {
                FileWriter writer = new FileWriter(file, false);
                System.out.println(file.getAbsolutePath());
                writer.write(text);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFile(File file) {
        StringBuilder text = new StringBuilder();
        if (file != null){
            try {
                FileReader r = new FileReader(file);
                int c;
                while ((c = r.read()) != -1) {
                    text.append((char) c);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return text.toString();
    }

    public static File chooseSingleFileToSave(String fileInfo, String extension) throws IOException{
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();
        String userDirectory = System.getProperty("user.dir");
        Path dir = Files.createDirectories(Path.of(userDirectory + "\\Tests"));
        fc.setInitialDirectory(dir.toFile());
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileInfo, "*."+extension));
        return fc.showSaveDialog(stage);
    }
}
