package com.TI.laba4;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWorker {

    public static File chooseSingleFileToOpen(String fileInfo, String extension) throws IOException{
        Stage stage = new Stage();
        String userDirectory = System.getProperty("user.dir");
        FileChooser fc = new FileChooser();
        Path dir = Files.createDirectories(Path.of(userDirectory +"\\Tests"));
        fc.setInitialDirectory(dir.toFile());
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileInfo, "*."+extension));
        return fc.showOpenDialog(stage);
    }

    public static File chooseSingleFileToSave(String userDirectory, String fileName, String extBeforeFileName)throws IOException{
        Stage stage = new Stage();
        FileChooser fc = new FileChooser();
        Path dir = Files.createDirectories(Path.of(userDirectory));
        fc.setInitialDirectory(dir.toFile());
        fc.setInitialFileName(extBeforeFileName + fileName);
        return fc.showSaveDialog(stage);
    }

    static void writeToFile(File file, String text, long r, long s) throws FileNotFoundException {
        if (file != null){
            try (FileWriter fileWriter = new FileWriter(file)) {
                if(!text.isEmpty()) {
                   fileWriter.write(text+"\n");
                }
            fileWriter.write(r +" "+ s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFile(File file) throws FileNotFoundException {
        StringBuilder text = new StringBuilder();
        BufferedReader bufferedReader;
        if (file != null){
            try {
                FileReader fr = new FileReader(file);
                bufferedReader = new BufferedReader(fr);
                String fLines;
                while ((fLines = bufferedReader.readLine()) != null) {
                    text.append(fLines).append("\r\n");
                }
                if (text.length() > 1) text.delete(text.length() - 2, text.length());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else throw new FileNotFoundException();

        return text.toString();
    }

    public static String[] readFileForCheckDSA(File file) throws FileNotFoundException, ArgumentException {
        String[] result = new String[2];
        StringBuilder text = new StringBuilder();
        if (file != null){
            try {
                FileReader fr = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fr);
                String fLines, nextFLine, lineWithSign;
                fLines = bufferedReader.readLine();
                lineWithSign = fLines;
                nextFLine = fLines;
                while (fLines != null) {
                    lineWithSign = nextFLine;
                    if ((nextFLine = bufferedReader.readLine()) != null) {
                        text.append(fLines).append("\r\n");
                    }
                    fLines = nextFLine;
                }
                if (text.length() > 1) text.delete(text.length() - 2, text.length());

                result[0] = text.toString();
                result[1] = lineWithSign;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            throw new FileNotFoundException();
        }

        return result;
    }

}
