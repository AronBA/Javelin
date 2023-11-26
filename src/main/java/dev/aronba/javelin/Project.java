package dev.aronba.javelin;

import dev.aronba.javelin.util.FileUtil;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * The Project class represents a software project with properties such as the project root,
 * README file, and project name.
 */
@Getter
public class Project {

    /**
     * The root directory of the project.
     */
    private File projectRoot;

    private File mainFile;

    private File executableJar;
    /**
     * The README file associated with the project.
     */
    @Getter
    private File readmeFile;

    /**
     * The name of the project.
     */
    private String projectName;

    /**
     * Constructs a Project object with the project root set to the result of opening a folder
     * using FileUtil, and initializes the project properties.
     */
    public Project() {
        this.projectRoot = FileUtil.openFolder().get();
        initialize();
    }

    /**
     * Constructs a Project object with the specified project root and initializes the project properties.
     *
     * @param file The root directory of the project.
     */
    public Project(File file) {
        this.projectRoot = file;
        initialize();
    }

    /**
     * Initializes the project properties, including attempting to find the README file.
     */
    private void initialize() {
        this.readmeFile = tryFindFile(projectRoot, "readme.md");
        this.mainFile = tryFindFile(projectRoot, "main.java");
    }

    /**
     * Attempts to find the README file in the specified directory and its subdirectories.
     *
     * @param directory The directory to search for the README file.
     * @return A File object representing the README file if found, or null if not found.
     */
    private File tryFindFile(File directory, String fileName) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equalsIgnoreCase(fileName)) {
                    return file;
                }
            }
        }

        for (File subDirectory : files) {
            File readme = tryFindFile(subDirectory, fileName);
            if (readme != null) {
                return readme;
            }
        }

        return null;
    }

    public void runMain() {
        try {


            //todo fix this messy code (It doesnt even work)

            System.out.println("running main");
            String absolutePath = mainFile.getAbsolutePath();
            String className = absolutePath.replaceFirst("[.][^.]+$", "");
            int srcIndex = className.lastIndexOf("src");
            if (srcIndex != -1) {
                className = className.substring(srcIndex);
            }

            className = className.replace(File.separator, ".");
            System.out.println(className);


            ProcessBuilder processBuilder = new ProcessBuilder("mvn exec:java -Dexec.mainClass="+className);
            processBuilder.directory(projectRoot);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Program executed successfully!");
            } else {
                System.err.println("Error executing the program. Exit code: " + exitCode);
            }

        } catch(Exception e) {
            //todo exception handling
        }
    }
}
