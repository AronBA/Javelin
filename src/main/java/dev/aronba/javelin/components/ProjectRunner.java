package dev.aronba.javelin.components;


import dev.aronba.javelin.util.FileIO;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.ToolProvider;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ProjectRunner extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(ProjectRunner.class);
    private File mainFile;
    private File srcRoot;
    private File tagetFolder;

    ProjectRunner(File srcRoot, File mainFile, File tagetFolder) {

        initlialize(srcRoot, mainFile, tagetFolder);
    }

    ProjectRunner(File srcRoot, File mainFile, File tagetFolder, String name) {
        super(name);
        initlialize(srcRoot, mainFile, tagetFolder);
    }

    private void initlialize(File srcRoot, File mainFile, File tagetFolder) {
        this.mainFile = mainFile;
        this.srcRoot = srcRoot;
        this.tagetFolder = tagetFolder;
    }


    @Override
    public void run() {

        //todo -> fix this shit and make it recursive

        String[] list = FileIO.getALlJavaFiles(mainFile.getParentFile());

        List<String> command = new ArrayList<>();
        command.add("-d");
        command.add(tagetFolder.getAbsolutePath());
        command.addAll(List.of(list));


        int result = ToolProvider.getSystemJavaCompiler().run(null, null, null, command.toArray(String[]::new));

        logger.info("compiler exited with exitcode: " + result);

        logger.info("Started running" + mainFile.getName() + " on " + getName());
        logger.debug("srcRoot:" + srcRoot.getAbsolutePath());
        logger.debug("mainFIle" + mainFile.getAbsolutePath());


        String exec = "java " + mainFile.getName();
        try {
            Process process = Runtime.getRuntime().exec(exec);

            InputStream t = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(t));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                logger.debug(line);
            }



            int exitcode = process.waitFor();
            logger.info("program exit with code: " + exitcode);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}
