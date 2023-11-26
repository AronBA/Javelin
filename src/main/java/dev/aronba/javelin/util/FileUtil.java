package dev.aronba.javelin.util;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Scanner;

/**
 * The FileUtil class provides utility methods for working with files, such as saving files,
 * determining file types, opening folders and files, and reading file contents.
 */
public class FileUtil {

    private FileUtil() {
        // Private constructor to prevent instantiation; all methods are static.
    }

    /**
     * Saves the given text to the specified file. If the file is null, opens a file dialog to choose a file.
     *
     * @param file The file to save the text to, or null to open a file dialog.
     * @param text The text to save.
     */
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

    /**
     * Gets the file type (extension) of the specified file.
     *
     * @param file The file to determine the type for.
     * @return The file type (extension) as a lowercase string, or "Unknown" if the type is not recognized.
     */
    public static String getFileType(File file) {
        String fileName = file.getName();
        int dotI = fileName.lastIndexOf(".");
        if (dotI > 0 && dotI < fileName.length() - 1) {
            return fileName.substring(dotI + 1).toLowerCase();
        } else {
            return "Unknown";
        }
    }

    /**
     * Opens a file dialog to choose a folder and returns an Optional containing the selected folder.
     *
     * @return An Optional containing the selected folder, or empty if the user cancels the operation.
     */
    public static Optional<File> openFolder() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("."));
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (jFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return Optional.empty();

        return Optional.of(jFileChooser.getSelectedFile());
    }

    /**
     * Opens a file dialog to choose a file and returns an Optional containing the selected file.
     *
     * @return An Optional containing the selected file, or empty if the user cancels the operation.
     */
    public static Optional<File> openFile() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("."));
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (jFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return Optional.empty();

        return Optional.of(jFileChooser.getSelectedFile());
    }

    /**
     * Reads the contents of the specified file and returns them as a string.
     *
     * @param file The file to read.
     * @return The contents of the file as a string.
     */
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
