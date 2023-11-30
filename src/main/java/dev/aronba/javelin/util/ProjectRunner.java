package dev.aronba.javelin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProjectRunner {


    public static void compileProject(File mainClass, File srcRoot) throws IOException, InterruptedException {

        ProcessBuilder processBuilder = new ProcessBuilder("javac","-d bin",mainClass.getName());
        processBuilder.directory(mainClass.getParentFile());
        processBuilder.redirectErrorStream(true);

        System.out.println(mainClass.getName());
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();



    }
    public static void runProject(){


    }
}
