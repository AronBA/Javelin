package dev.aronba.javelin;

import dev.aronba.javelin.util.FileUtil;
import lombok.Getter;

import java.io.File;

@Getter
public class Project {

    private File projectRoot;

    private String projectName;



    public Project(){
        this.projectRoot = FileUtil.openFolder().get();
    }
    public Project(File file){
        this.projectRoot = file;
    }
}
