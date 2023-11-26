package dev.aronba.javelin.components.workspace;

import dev.aronba.javelin.Project;
import javax.swing.*;
import java.awt.*;
import java.io.File;


public class Workspace extends JPanel {

    private Project project;
    private FileTree fileTree;
    private TextEditor textEditor;
    private Terminal terminal;
    private JSplitPane horizontalSplit;
    private JSplitPane verticalSplit;


    public Workspace(){
        this.project = new Project();
        initialize();
    }
    public Workspace(Project project){
       this.project = project;
       initialize();
    }
    private void initialize(){
        this.setLayout(new BorderLayout());
        this.fileTree = new FileTree(project.getProjectRoot(),this::onFileSelected);
        this.terminal = new Terminal();
        this.textEditor = new TextEditor();

        this.horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,fileTree,textEditor);
        this.verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,horizontalSplit, terminal);

        SwingUtilities.invokeLater(() ->{
            this.horizontalSplit.setDividerLocation(0.1);
            this.verticalSplit.setDividerLocation(0.8);

        });


        this.add(verticalSplit,BorderLayout.CENTER);
    }
    private void onFileSelected(File file){
        textEditor.openFile(file);
    }

}
