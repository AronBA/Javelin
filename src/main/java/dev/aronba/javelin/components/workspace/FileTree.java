package dev.aronba.javelin.components.workspace;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.File;
import java.util.function.Consumer;

/**
 * The FileTree class represents a component in the Javelin IDE for displaying the project's file tree.
 * It includes a JTree to visualize the directory structure and notifies a callback when a file is selected.
 */
public class FileTree extends JPanel {

    /**
     * The callback function to be executed when a file is selected.
     */
    private final Consumer<File> selectedFileCallBack;

    /**
     * The root directory of the project.
     */
    private final File projectRoot;

    /**
     * Constructs a FileTree object with the specified project root and file selection callback.
     *
     * @param projectRoot         The root directory of the project.
     * @param selectedFileCallBack The callback function to be executed when a file is selected.
     */
    public FileTree(File projectRoot, Consumer<File> selectedFileCallBack) {
        this.selectedFileCallBack = selectedFileCallBack;
        this.projectRoot = projectRoot;

        JTree jTree = new JTree(generateFileTree());
        jTree.setCellRenderer(new FileTreeCellRenderer());
        jTree.addTreeSelectionListener(this::onFileClicked);

        JScrollPane jScrollPane = new JScrollPane(jTree);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(jScrollPane);
    }

    /**
     * Generates the file tree structure starting from the project root.
     *
     * @return The DefaultTreeModel representing the file tree.
     */
    private DefaultTreeModel generateFileTree() {
        String path = projectRoot.getAbsolutePath();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(path);
        loadFiles(new File(path), root);
        return new DefaultTreeModel(root);
    }

    /**
     * Handles the event when a file is clicked in the tree, executing the callback with the selected file.
     *
     * @param treeSelectionEvent The event triggered when a file is selected in the tree.
     */
    private void onFileClicked(TreeSelectionEvent treeSelectionEvent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSelectionEvent.getPath().getLastPathComponent();
        File file = (File) node.getUserObject();
        selectedFileCallBack.accept(file);
    }

    /**
     * Recursively loads files and directories into the tree structure.
     *
     * @param dir          The current directory.
     * @param parentNode   The parent node to which files and directories will be added.
     */
    private void loadFiles(File dir, DefaultMutableTreeNode parentNode) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file);
                    loadFiles(file, childNode);
                    parentNode.add(childNode);
                } else {
                    DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode(file);
                    parentNode.add(leafNode);
                }
            }
        }
    }
}
