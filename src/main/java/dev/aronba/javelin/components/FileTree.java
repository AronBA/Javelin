package dev.aronba.javelin.components;

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

    private final Consumer<File> selectedFileCallBack;
    private final File projectRoot;

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

    private DefaultTreeModel generateFileTree() {
        String path = projectRoot.getAbsolutePath();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(path);
        loadFiles(new File(path), root);
        return new DefaultTreeModel(root);



    }

    private void onFileClicked(TreeSelectionEvent treeSelectionEvent) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSelectionEvent.getPath().getLastPathComponent();
        File file = (File) node.getUserObject();
        selectedFileCallBack.accept(file);

    }
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
