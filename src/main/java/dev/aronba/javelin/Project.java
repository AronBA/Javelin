package dev.aronba.javelin;

import dev.aronba.javelin.util.FileUtil;
import lombok.Getter;
import java.io.File;

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

    /**
     * The README file associated with the project.
     */
    @Getter
    private File readme;

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
        this.readme = tryFindReadme(projectRoot);
    }

    /**
     * Attempts to find the README file in the specified directory and its subdirectories.
     *
     * @param directory The directory to search for the README file.
     * @return A File object representing the README file if found, or null if not found.
     */
    private File tryFindReadme(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().equalsIgnoreCase("readme.md")) {
                    return file;
                }
            }
        }

        for (File subDirectory : files) {
            File readme = tryFindReadme(subDirectory);
            if (readme != null) {
                return readme;
            }
        }

        return null;
    }
}
