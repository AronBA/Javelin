package dev.aronba.javelin.components;

import dev.aronba.javelin.util.FileIO;
import lombok.Getter;

import java.io.File;

/**
 * The Project class represents a software project with properties such as the project root,
 * README file, and project name.
 */
@Getter
public class Project {

    private File targetRoot;

    private File sourceRoot;

    private File projectRoot;

    private File mainFile;

    private File executableJar;

    private ProjectRunner projectRunner;

    @Getter
    private File readmeFile;

    private String projectName;


    public Project() {
        this.projectRoot = FileIO.openFolder().get();
        initialize();
    }

    public Project(File file) {
        this.projectRoot = file;
        initialize();
    }

    private void initialize() {
        this.readmeFile = tryFindFile(projectRoot, "readme.md");
        this.mainFile = tryFindFile(projectRoot, "main.java");
        this.sourceRoot = tryFindFile(projectRoot, "src", true);
        this.targetRoot = tryFindFile(projectRoot,"target",true);
    }

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
            sourceRoot = FileIO.openFolder().get();
        }
        if (mainFile == null){
            mainFile = FileIO.openFolder().get();
        }
        if (targetRoot == null){
            targetRoot = FileIO.openFolder().get();
        }
            String threadName = (projectName == null) ? "Unnamed Project" : projectName;
            this.projectRunner = new ProjectRunner(this.sourceRoot, this.mainFile,targetRoot,threadName);
            projectRunner.start();






    }
}
