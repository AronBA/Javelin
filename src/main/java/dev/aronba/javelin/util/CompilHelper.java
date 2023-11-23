package dev.aronba.javelin.util;

import java.io.File;
import java.io.IOException;

public class CompilHelper {


    public static int compileJava(File file) {

        String fileName = file.getName();
        Process compileProcess = null;
        try {
            compileProcess = new ProcessBuilder("javac", fileName).start();

            return compileProcess.waitFor();


        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static Process runJava(String fileName) {
        try {

            String className = fileName.substring(0, fileName.lastIndexOf('.'));
             return new ProcessBuilder("java", className).start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
