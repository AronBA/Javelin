package dev.aronba.javelin.core.project;

import lombok.Builder;
import lombok.Data;

import java.nio.file.Path;
import java.util.List;

@Data
@Builder
public class Project {

    String name;

    BuildTool buildTool;
    VersionControlSystem versionControlSystem;

    Path projectRoot;
    Path targetRoot;
    Path testRoot;
    Path sourceRoot;
    Path configRoot;

    Path mainClass;
    Path readme;

    List<String> dependencies;

}
