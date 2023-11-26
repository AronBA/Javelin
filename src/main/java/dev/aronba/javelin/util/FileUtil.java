package dev.aronba.javelin.util;

import org.fife.ui.rsyntaxtextarea.FileTypeUtil;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

public class FileUtil {

    private FileUtil() {

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
            throw new RuntimeException(e);
        }
    }

    public static String getFileType(File file) {
        String fileName = file.getName();
        int dotI = fileName.lastIndexOf(".");
        if (dotI > 0 && dotI < fileName.length() - 1) {
            return fileName.substring(dotI + 1).toLowerCase();
        } else {
            return "Unknown";
        }
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
            throw new RuntimeException(e);
        }
        return text.toString();
    }
}
