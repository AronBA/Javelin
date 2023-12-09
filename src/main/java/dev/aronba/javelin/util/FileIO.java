package dev.aronba.javelin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * The FileIO class provides utility methods for working with files, such as saving files,
 * determining file types, opening folders and files, and reading file contents.
 */
public class FileIO {

    private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

    private FileIO() {
    }

    public static void saveFile(File file, String text) {
        if (file == null) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File("."));

            int response = jFileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                file = jFileChooser.getSelectedFile().getAbsoluteFile();
            } else {
                return;
            }
        }
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(text);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        }
    }

    public static String getFileType(File file) {
        String fileName = file.getName();
        int dotI = fileName.lastIndexOf(".");
        if (dotI > 0 && dotI < fileName.length() - 1) {
            return fileName.substring(dotI + 1).toLowerCase();
        } else {
          //todo -> stupid ass log  logger.warn("Unknown fileType for file: " + file.getName());
            return "Unknown";
        }
    }

    public static String[] getALlJavaFiles(File srcRoot){

       if (!srcRoot.isDirectory()){
           logger.info("invalid src root " + srcRoot.getAbsolutePath());
           return new String[0];
       }
        File[] javaFiles = srcRoot.listFiles((dir, name) -> name.endsWith(".java"));
       if (javaFiles == null || javaFiles.length <= 0){
           logger.info("empty src soot " + srcRoot.getAbsolutePath());
           return new String[0];
       }

       return Arrays.stream(javaFiles).map(File::getAbsolutePath).toArray(String[]::new);
    }
    public static Optional<File> openFolder() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("."));
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return Optional.empty();

        return Optional.of(jFileChooser.getSelectedFile());
    }


    public static Optional<File> openFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("."));
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
            logger.error(e.getMessage());
        }
        return text.toString();
    }
}
