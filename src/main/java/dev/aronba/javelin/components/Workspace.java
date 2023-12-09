package dev.aronba.javelin.components;

import dev.aronba.javelin.util.LastProjectManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * The Workspace class represents the main workspace of the Javelin IDE,
 * containing components like the file tree, text editor, and terminal.
 */
public class Workspace extends JPanel {

    @Getter
    private Project project;
    private FileTree fileTree;
    private TextEditor textEditor;
    private Terminal terminal;
    private JSplitPane horizontalSplit;
    private JSplitPane verticalSplit;

    public Workspace() {
        this.project = new Project();
        initialize();
    }

    public Workspace(Project project) {
        this.project = project;
        initialize();
    }

    private void initialize() {
        LastProjectManager.saveLastProject(project.getProjectRoot());

        this.setLayout(new BorderLayout());

        this.fileTree = new FileTree(project.getProjectRoot(), this::onFileSelected);
        fileTree.setPreferredSize(new Dimension(100,Integer.MAX_VALUE));
        this.terminal = new Terminal();
        this.textEditor = new TextEditor();

        this.horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileTree, textEditor);
        this.verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplit, terminal);

        this.add(verticalSplit, BorderLayout.CENTER);

        if (project.getReadmeFile() != null) textEditor.openFile(project.getReadmeFile());
    }

    private void onFileSelected(File file) {
        textEditor.openFile(file);
    }
}
