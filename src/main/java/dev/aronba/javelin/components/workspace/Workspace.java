package dev.aronba.javelin.components.workspace;

import dev.aronba.javelin.Project;
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

    /**
     *
     * The project associated with the workspace.
     */
    @Getter
    private Project project;

    /**
     * The file tree component displaying the project's directory structure.
     */
    private FileTree fileTree;

    /**
     * The text editor component for editing files.
     */
    private TextEditor textEditor;

    /**
     * The terminal component for executing commands.
     */
    private Terminal terminal;

    /**
     * The horizontal split pane containing the file tree and text editor.
     */
    private JSplitPane horizontalSplit;

    /**
     * The vertical split pane containing the horizontal split pane and terminal.
     */
    private JSplitPane verticalSplit;

    /**
     * Constructs a Workspace object with a default project.
     */
    public Workspace() {
        this.project = new Project();
        initialize();
    }

    /**
     * Constructs a Workspace object with the specified project.
     *
     * @param project The project to associate with the workspace.
     */
    public Workspace(Project project) {
        this.project = project;
        initialize();
    }

    /**
     * Initializes the workspace components and layout.
     */
    private void initialize() {
        // Save the last opened project
        LastProjectManager.saveLastProject(project.getProjectRoot());

        this.setLayout(new BorderLayout());
        this.fileTree = new FileTree(project.getProjectRoot(), this::onFileSelected);
        this.terminal = new Terminal();
        this.textEditor = new TextEditor();

        this.horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, fileTree, textEditor);
        this.verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, horizontalSplit, terminal);

        SwingUtilities.invokeLater(() -> {
            this.horizontalSplit.setDividerLocation(0.1);
            this.verticalSplit.setDividerLocation(0.8);
        });

        this.add(verticalSplit, BorderLayout.CENTER);

        // Open README file in the text editor if available
        if (project.getReadmeFile() != null) {
            textEditor.openFile(project.getReadmeFile());
        }
    }

    /**
     * Handles the event when a file is selected in the file tree.
     *
     * @param file The selected file.
     */
    private void onFileSelected(File file) {
        textEditor.openFile(file);
    }
}
