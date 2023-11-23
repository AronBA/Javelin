package dev.aronba.javelin.util;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class FileIO {


    public static void saveFile(File file, String text){
        if (file == null){
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));

            int response = jFileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION){
                file = jFileChooser.getSelectedFile().getAbsoluteFile();
            } else {
                return;
            }
        }

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
            printWriter.println(text);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
         } finally {
            printWriter.close();
        }



    }

    public static Optional<File> open() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("."));

        if (jFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return Optional.empty();

        return Optional.of(jFileChooser.getSelectedFile());
    }

    public static String read(File file) {
        StringBuilder text = new StringBuilder();
        try {

            if (file.isFile()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine() + "\n";
                    text.append(line);
                }
                scanner.close();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return text.toString();
    }
}
