package dev.aronba.javelin;

import dev.aronba.javelin.util.FileUtil;
import dev.aronba.javelin.util.ProjectRunner;
import lombok.Getter;

import java.io.File;
import java.io.IOException;

/**
 * The Project class represents a software project with properties such as the project root,
 * README file, and project name.
 */
@Getter
public class Project {

    private File targetRoot;

    private File sourceRoot;
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
        this.sourceRoot = tryFindFile(projectRoot, "src", true);
        this.targetRoot = tryFindFile(projectRoot,"target",true);
    }

    /**
     * Attempts to find the README file in the specified directory and its subdirectories.
     *
     * @param directory The directory to search for the README file.
     * @return A File object representing the README file if found, or null if not found.
     */
    private File tryFindFile(File directory, String fileName, boolean includeDir) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if ((file.isFile() || (includeDir && file.isDirectory())) && file.getName().equalsIgnoreCase(fileName)) {
                    return file;
                }
            }
        }

        for (File subDirectory : files) {
            File readme = tryFindFile(subDirectory, fileName,includeDir);
            if (readme != null) {
                return readme;
            }
        }

        return null;
    }
    private File tryFindFile(File directory, String filename){
        return tryFindFile(directory,filename,false);
    }

    public void runProject() {
        if (sourceRoot == null){
            sourceRoot = FileUtil.openFolder().get();
        }

        try {
            ProjectRunner.compileProject(mainFile, sourceRoot);
        } catch (IOException | InterruptedException e){
            System.out.println(e);
        }
    }
}
